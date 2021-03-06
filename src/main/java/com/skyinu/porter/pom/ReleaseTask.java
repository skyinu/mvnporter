package com.skyinu.porter.pom;

import com.skyinu.porter.Porter;
import com.skyinu.porter.model.Pom;
import com.skyinu.porter.utils.StringUtils;
import com.skyinu.porter.utils.DownloadUtils;
import com.skyinu.porter.model.PomNode;
import com.skyinu.porter.utils.DeployerUtils;
import com.skyinu.porter.utils.XmlUtils;
import java.io.File;

public class ReleaseTask implements Runnable {

    private Porter porter;
    private String repo;
    private String directory;
    private PomNode pomNode;

    public ReleaseTask(Porter porter, String repo, String directory, PomNode pomNode) {
        this.porter = porter;
        this.repo = repo;
        this.directory = directory;
        this.pomNode = pomNode;
    }

    @Override
    public void run() {
        //step 1:download library
        File libraryFile;
        String packageType;
        if (StringUtils.isBlank(pomNode.getType())) {
            packageType = "jar";
            String libraryUrl = DeployerUtils.buildLibraryPathWithPomNode(repo, pomNode, packageType);
            libraryFile = DeployerUtils.buildLibrarySaveFileWithPomNode(directory, pomNode, packageType);
            libraryFile = DownloadUtils.downloadFile(libraryUrl, libraryFile);
            if (libraryFile == null) {
                packageType = "aar";
                libraryUrl = DeployerUtils.buildLibraryPathWithPomNode(repo, pomNode, packageType);
                libraryFile = DeployerUtils.buildLibrarySaveFileWithPomNode(directory, pomNode, packageType);
                libraryFile = DownloadUtils.downloadFile(libraryUrl, libraryFile);
            }
        } else {
            packageType = pomNode.getType();
            String libraryUrl = DeployerUtils.buildLibraryPathWithPomNode(repo, pomNode, pomNode.getType());
            libraryFile = DeployerUtils.buildLibrarySaveFileWithPomNode(directory, pomNode, pomNode.getType());
            DownloadUtils.downloadFile(libraryUrl, libraryFile);
        }
        //step 2:download pom
        String pomUrl = DeployerUtils.buildPomPathWithPomNode(repo, pomNode);
        File pomFile = DeployerUtils.buildPomSaveFileWithPomNode(directory, pomNode);
        pomFile = DownloadUtils.downloadFile(pomUrl, pomFile);
        //step 3:parse pom
        Pom pom = XmlUtils.toBean(pomFile, Pom.class);
        System.out.println(pom.toString());
        for (PomNode nodeKey : pom.getDependencies()) {
            if (porter.hasResolved(nodeKey)) {
                continue;
            }
            if (DeployerUtils.isSnapshot(nodeKey.getVersion())) {
                porter.submitTask(new SnapShotTask(porter, repo, directory, nodeKey));
            } else {
                porter.submitTask(new ReleaseTask(porter, repo, directory, nodeKey));
            }
        }
        //step 4: upload library
        DeployTask deployTask = new DeployTask(porter.getOptions(),pomNode.getGroupId(), pomNode.getArtifactId(),
                pomNode.getVersion(), packageType, libraryFile.getAbsolutePath(), pomFile.getAbsolutePath());
        porter.submitTask(deployTask);
    }
}
