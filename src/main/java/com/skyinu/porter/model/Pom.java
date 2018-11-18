package com.skyinu.porter.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.List;

@XStreamAlias("project")
public class Pom {

    @XStreamAlias("dependencies")
    List<PomNode> dependencies = new ArrayList<>();


    public List<PomNode> getDependencies() {
        return dependencies;
    }

    @Override
    public String toString() {
        return "Pom{" +
                "dependencies=" + dependencies +
                '}';
    }
}
