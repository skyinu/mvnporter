package com.skyinu.porter;


import com.skyinu.porter.pom.DeployTask;
import com.skyinu.porter.utils.ResourceUtils;
import com.skyinu.porter.model.PomNode;
import com.skyinu.porter.pom.ReleaseTask;
import com.skyinu.porter.pom.SnapShotTask;
import com.skyinu.porter.utils.DeployerUtils;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

public class Porter {

    public static final String NAME_SCRIPT = "deployer.sh";
    private PorterOptions options;
    private ThreadPoolExecutor executor;
    private int snapshot;
    private int release;
    private int deploy;
    private Set<String> uploadTask = new HashSet<>();

    public Porter(PorterOptions porterOptions) {
        this.options = porterOptions;
        File file = new File(porterOptions.getLocalDirectory());
        if (!file.exists()) {
            file.mkdirs();
        }
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                50, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        executor.allowCoreThreadTimeOut(true);
        ResourceUtils.syncScript(getClass(), NAME_SCRIPT);
    }


    public void startDeploy() {
        String[] dependencyInfo = options.getDependency().split(":");
        String group = dependencyInfo[0];
        String artifact = dependencyInfo[1];
        String version = dependencyInfo[2];
        if (DeployerUtils.isSnapshot(version)) {
            PomNode pomNode = new PomNode(group, artifact, version, "");
            SnapShotTask snapShotTask = new SnapShotTask(this, options.getFromRepository(), options.getLocalDirectory(), pomNode);
            submitTask(snapShotTask);
        } else {
            PomNode pomNode = new PomNode(group, artifact, version, "");
            ReleaseTask releaseTask = new ReleaseTask(this, options.getFromRepository(), options.getLocalDirectory(), pomNode);
            submitTask(releaseTask);
        }
    }

    public void submitTask(Runnable task) {
        if (task instanceof SnapShotTask) {
            snapshot++;
        } else if (task instanceof ReleaseTask) {
            release++;
        } else if (task instanceof DeployTask) {
            deploy++;
        }
        System.out.println("snapshot=" + snapshot + "release=" + release + "deploy=" + deploy);
        executor.submit(task);
    }

    public boolean hasResolved(PomNode pomNode) {
        String key = pomNode.getGroupId() + ":" + pomNode.getArtifactId() + ":" + pomNode.getVersion();
        if (uploadTask.contains(key)) {
            return true;
        }
        uploadTask.add(key);
        return false;
    }

    public PorterOptions getOptions() {
        return options;
    }
}
