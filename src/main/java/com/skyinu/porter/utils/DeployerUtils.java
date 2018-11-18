package com.skyinu.porter.utils;

import com.skyinu.porter.model.Meta;
import com.skyinu.porter.model.PomNode;
import java.io.File;

public class DeployerUtils {


    public static String buildDownloadPrefixPath(String repo, String group, String artifact, String version) {
        StringBuilder result = new StringBuilder(repo);
        String[] groupInfo = group.split("\\.");
        for (int i = 0; i < groupInfo.length; i++) {
            result.append("/").append(groupInfo[i]);
        }
        result.append("/")
                .append(artifact)
                .append("/")
                .append(version);
        return result.toString();
    }

    public static String buildLocalDirectory(String directory, String group, String artifact, String version) {
        StringBuilder result = new StringBuilder(directory);
        String[] groupInfo = group.split("\\.");
        for (int i = 0; i < groupInfo.length; i++) {
            result.append(File.separator).append(groupInfo[i]);
        }
        result.append(File.separator)
                .append(artifact)
                .append(File.separator)
                .append(version);
        return result.toString();
    }

    public static String buildMetaDownloadPath(String repo, PomNode pomNode) {
        StringBuilder result = new StringBuilder(buildDownloadPrefixPath(repo, pomNode.getGroupId(), pomNode.getArtifactId(),
                pomNode.getVersion()));
        result.append("/")
                .append("maven-metadata.xml");
        return result.toString();
    }

    public static File buildMetaSaveFile(String directory, PomNode pomNode) {
        String path = buildLocalDirectory(directory, pomNode.getGroupId(), pomNode.getArtifactId(), pomNode.getVersion());
        File parent = new File(path);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        File metaFile = new File(path, "maven-metadata.xml");
        return metaFile;
    }


    public static String buildLibraryPathWithMeta(String repo, Meta metaModel, String type) {
        StringBuilder result = new StringBuilder(buildDownloadPrefixPath(repo, metaModel.getGroupId(), metaModel.getArtifactId(),
                metaModel.getVersion()));
        String downloadFileVersion = metaModel.getVersion();
        if (isSnapshot(metaModel.getVersion())) {
            int index = metaModel.getVersion().lastIndexOf("-");
            downloadFileVersion = metaModel.getVersion().substring(0, index);
        }
        result.append("/")
                .append(metaModel.getArtifactId())
                .append("-")
                .append(downloadFileVersion)
                .append("-")
                .append(metaModel.getVersioning().getSnapshot().getTimestamp())
                .append("-")
                .append(metaModel.getVersioning().getSnapshot().getBuildNumber())
                .append(".")
                .append(type);
        return result.toString();
    }

    public static File buildLibrarySaveFileWithMeta(String directory, Meta metaModel, String type) {
        String path = buildLocalDirectory(directory, metaModel.getGroupId(), metaModel.getArtifactId(),
                metaModel.getVersion());
        File parent = new File(path);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        String downloadFileVersion = metaModel.getVersion();
        if (isSnapshot(metaModel.getVersion())) {
            int index = metaModel.getVersion().lastIndexOf("-");
            downloadFileVersion = metaModel.getVersion().substring(0, index);
        }
        StringBuilder name = new StringBuilder();
        name.append(metaModel.getArtifactId())
                .append("-")
                .append(downloadFileVersion)
                .append("-")
                .append(metaModel.getVersioning().getSnapshot().getTimestamp())
                .append("-")
                .append(metaModel.getVersioning().getSnapshot().getBuildNumber())
                .append(".")
                .append(type);
        return new File(path, name.toString());
    }

    public static String buildPomPathWithMeta(String repo, Meta metaModel) {
        StringBuilder result = new StringBuilder(buildDownloadPrefixPath(repo, metaModel.getGroupId(), metaModel.getArtifactId(),
                metaModel.getVersion()));
        String downloadFileVersion = metaModel.getVersion();
        if (isSnapshot(metaModel.getVersion())) {
            int index = metaModel.getVersion().lastIndexOf("-");
            downloadFileVersion = metaModel.getVersion().substring(0, index);
        }
        result.append("/")
                .append(metaModel.getArtifactId())
                .append("-")
                .append(downloadFileVersion)
                .append("-")
                .append(metaModel.getVersioning().getSnapshot().getTimestamp())
                .append("-")
                .append(metaModel.getVersioning().getSnapshot().getBuildNumber())
                .append(".pom");
        return result.toString();
    }

    public static File buildPomSaveFileWithMeta(String directory, Meta metaModel) {
        String path = buildLocalDirectory(directory, metaModel.getGroupId(), metaModel.getArtifactId(),
                metaModel.getVersion());
        File parent = new File(path);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        String downloadFileVersion = metaModel.getVersion();
        if (isSnapshot(metaModel.getVersion())) {
            int index = metaModel.getVersion().lastIndexOf("-");
            downloadFileVersion = metaModel.getVersion().substring(0, index);
        }
        StringBuilder name = new StringBuilder();
        name.append(metaModel.getArtifactId())
                .append("-")
                .append(downloadFileVersion)
                .append("-")
                .append(metaModel.getVersioning().getSnapshot().getTimestamp())
                .append("-")
                .append(metaModel.getVersioning().getSnapshot().getBuildNumber())
                .append(".pom");
        return new File(path, name.toString());
    }

    public static String buildPomPathWithPomNode(String repo, PomNode pomNode) {
        StringBuilder result = new StringBuilder(buildDownloadPrefixPath(repo, pomNode.getGroupId(),
                pomNode.getArtifactId(), pomNode.getVersion()));
        result.append("/")
                .append(pomNode.getArtifactId())
                .append("-")
                .append(pomNode.getVersion())
                .append(".pom");
        return result.toString();
    }


    public static File buildPomSaveFileWithPomNode(String directory, PomNode pomNode) {
        String path = buildLocalDirectory(directory, pomNode.getGroupId(), pomNode.getArtifactId(),
                pomNode.getVersion());
        File parent = new File(path);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        StringBuilder name = new StringBuilder();
        name.append(pomNode.getArtifactId())
                .append("-")
                .append(pomNode.getVersion())
                .append(".pom");
        return new File(path, name.toString());
    }

    public static String buildLibraryPathWithPomNode(String repo, PomNode pomNode, String type) {
        StringBuilder result = new StringBuilder(buildDownloadPrefixPath(repo, pomNode.getGroupId(),
                pomNode.getArtifactId(), pomNode.getVersion()));
        result.append("/")
                .append(pomNode.getArtifactId())
                .append("-")
                .append(pomNode.getVersion())
                .append(".")
                .append(type);
        return result.toString();
    }

    public static File buildLibrarySaveFileWithPomNode(String directory, PomNode pomNode, String type) {
        String path = buildLocalDirectory(directory, pomNode.getGroupId(), pomNode.getArtifactId(),
                pomNode.getVersion());
        File parent = new File(path);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        StringBuilder name = new StringBuilder();
        name.append(pomNode.getArtifactId())
                .append("-")
                .append(pomNode.getVersion())
                .append(".")
                .append(type);
        return new File(path, name.toString());
    }

    public static boolean isSnapshot(String version) {
        return version.toLowerCase().endsWith("snapshot");
    }


}
