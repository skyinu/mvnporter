package com.skyinu.porter.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("dependency")
public class PomNode {
    @XStreamAlias("groupId")
    private String groupId;
    @XStreamAlias( "artifactId")
    private String artifactId;
    @XStreamAlias( "version")
    private String version;
    @XStreamAlias( "scope")
    private String scope;
    @XStreamAlias("type")
    private String type;

    public PomNode(String groupId, String artifactId, String version, String type) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.type = type;
    }

    public PomNode() {

    }

    public String getGroupId() {
        return groupId;
    }


    public String getArtifactId() {
        return artifactId;
    }


    public String getVersion() {
        return version;
    }


    public String getType() {
        return type;
    }


    public String getScope() {
        return scope;
    }


    @Override
    public String toString() {
        return "PomNode{" +
                "groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", scope='" + scope + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
