package com.skyinu.porter;

import com.skyinu.porter.utils.StringUtils;

import java.io.File;

/**
 * Created by chen on 2018/11/18.
 */
public class PorterOptions {
    private static final String DEFAULT_DIRECTORY = "mvn";
    private String fromRepository;
    private String localDirectory;
    private String dependency;
    private String toRespository;
    private String toRespositoryId;

    public PorterOptions(Builder builder) {
        this.fromRepository = builder.fromRepository;
        this.localDirectory = builder.localDirectory;
        this.dependency = builder.dependency;
        this.toRespository = builder.toRespository;
        this.toRespositoryId = builder.toRespositoryId;
    }

    public String getFromRepository() {
        return fromRepository;
    }

    public String getLocalDirectory() {
        return localDirectory;
    }

    public String getDependency() {
        return dependency;
    }

    public String getToRespository() {
        return toRespository;
    }

    public String getToRespositoryId() {
        return toRespositoryId;
    }

    public static final class Builder {
        private String fromRepository;
        private String localDirectory;
        private String dependency;
        private String toRespository;
        private String toRespositoryId;

        public Builder fromRepository(String fromRepository) {
            this.fromRepository = fromRepository;
            return this;
        }

        public Builder dependency(String dependency) {
            this.dependency = dependency;
            return this;
        }

        public Builder localDirectory(String localDirectory) {
            this.localDirectory = localDirectory;
            return this;
        }

        public Builder toRespository(String toRespository) {
            this.toRespository = toRespository;
            return this;
        }

        public Builder toRespositoryId(String toRespositoryId) {
            this.toRespositoryId = toRespositoryId;
            return this;
        }

        public PorterOptions build() {
            String userDir = System.getProperty("user.dir");
            if (StringUtils.isBlank(localDirectory)) {
                this.localDirectory = userDir + File.separator + DEFAULT_DIRECTORY;
            }
            return new PorterOptions(this);

        }
    }
}
