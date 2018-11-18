package com.skyinu.porter.model;


import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias( "metadata")
public class Meta {
    @XStreamAlias( "groupId")
    private String groupId;
    @XStreamAlias("artifactId")
    private String artifactId;
    @XStreamAlias( "version")
    private String version;
    @XStreamAlias( "versioning")
    private Version versioning;

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public Version getVersioning() {
        return versioning;
    }


    public static class Version {
        @XStreamAlias( "snapshot")
        private Snapshot snapshot;
        @XStreamAlias( "lastUpdated")
        private String lastUpdated;

        public Snapshot getSnapshot() {
            return snapshot;
        }

    }

    public static class Snapshot {
        @XStreamAlias("timestamp")
        private String timestamp;
        @XStreamAlias( "buildNumber")
        private String buildNumber;

        public String getTimestamp() {
            return timestamp;
        }

        public String getBuildNumber() {
            return buildNumber;
        }
    }
}
