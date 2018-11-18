package com.skyinu.porter.pom;

import com.skyinu.porter.PorterOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DeployTask implements Runnable {

    private PorterOptions options;
    private String groupId;
    private String artifactId;
    private String version;
    private String packaging;
    private String file;
    private String pomFile;


    public DeployTask(PorterOptions options,String groupId, String artifactId, String version,
                      String packaging, String file, String pomFile) {
        this.options = options;
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.packaging = packaging;
        this.file = file;
        this.pomFile = pomFile;
    }

    @Override
    public void run() {
        StringBuilder command = new StringBuilder();
        String userDir = System.getProperty("user.dir");
        command.append(userDir)
                .append(File.separator)
                .append("deployer.sh")
                .append(" ")
                .append(groupId)
                .append(" ")
                .append(artifactId)
                .append(" ")
                .append(version)
                .append(" ")
                .append(packaging)
                .append(" ")
                .append(file)
                .append(" ")
                .append(pomFile)
                .append(" ")
                .append(options.getToRespositoryId())
                .append(" ")
                .append(options.getToRespository());
        Process process;
        System.out.println("command = " + command);
        List<String> processList = new ArrayList<>();
        try {
            process = Runtime.getRuntime().exec(command.toString());
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
            int exitValue = process.waitFor();
            if (0 != exitValue) {
                processList.add("call shell failed. error code is :" + exitValue);
            }
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
            processList.add("error");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (String line : processList) {
            System.out.println(line);
        }
    }
}
