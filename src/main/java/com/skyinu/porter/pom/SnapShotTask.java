package com.skyinu.porter.pom;

import com.skyinu.porter.Porter;
import com.skyinu.porter.model.Meta;
import com.skyinu.porter.model.Pom;
import com.skyinu.porter.utils.DeployerUtils;
import com.skyinu.porter.utils.StringUtils;
import com.skyinu.porter.utils.DownloadUtils;
import com.skyinu.porter.model.PomNode;
import com.skyinu.porter.utils.XmlUtils;

import java.io.File;

public class SnapShotTask implements Runnable {

    private Porter porter;
    private String repo;
    private String directory;
    private PomNode pomNode;

    public SnapShotTask(Porter porter, String repo, String directory, PomNode pomNode) {
        this.porter = porter;
        this.repo = repo;
        this.directory = directory;
        this.pomNode = pomNode;
    }

    @Override
    public void run() {
        //step 1:download meta file
        String metaUrl = DeployerUtils.buildMetaDownloadPath(repo, pomNode);
        File metaFile = DeployerUtils.buildMetaSaveFile(directory, pomNode);
        metaFile = DownloadUtils.downloadFile(metaUrl, metaFile);
        //step 2:parse meta xml
        Meta metaModel = XmlUtils.toBean(metaFile, Meta.class);
        //step 3:download library file
        File libraryFile;
        String packageType;
        if (StringUtils.isBlank(pomNode.getType())) {
            packageType = "jar";
            String libraryUrl = DeployerUtils.buildLibraryPathWithMeta(repo, metaModel, packageType);
            libraryFile = DeployerUtils.buildLibrarySaveFileWithMeta(directory, metaModel, packageType);
            libraryFile = DownloadUtils.downloadFile(libraryUrl, libraryFile);
            if (libraryFile == null) {
                packageType = "aar";
                libraryUrl = DeployerUtils.buildLibraryPathWithMeta(repo, metaModel, packageType);
                libraryFile = DeployerUtils.buildLibrarySaveFileWithMeta(directory, metaModel, packageType);
                libraryFile = DownloadUtils.downloadFile(libraryUrl, libraryFile);
            }
        } else {
            packageType = pomNode.getType();
            String libraryUrl = DeployerUtils.buildLibraryPathWithMeta(repo, metaModel, pomNode.getType());
            libraryFile = DeployerUtils.buildLibrarySaveFileWithMeta(directory, metaModel, pomNode.getType());
            DownloadUtils.downloadFile(libraryUrl, libraryFile);
        }
        //step 4:download pom
        String pomUrl = DeployerUtils.buildPomPathWithMeta(repo, metaModel);
        File pomFile = DeployerUtils.buildPomSaveFileWithMeta(directory, metaModel);
        pomFile = DownloadUtils.downloadFile(pomUrl, pomFile);
        //step 5:parse pom
        Pom pom = XmlUtils.toBean(pomFile, Pom.class);
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
        //step 6:upload library
        DeployTask deployTask = new DeployTask(porter.getOptions(),pomNode.getGroupId(), pomNode.getArtifactId(),
                pomNode.getVersion(), packageType, libraryFile.getAbsolutePath(), pomFile.getAbsolutePath());
        porter.submitTask(deployTask);

    }
}
