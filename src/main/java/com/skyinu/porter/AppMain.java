package com.skyinu.porter;


public class AppMain {
    public static void main(String[] args) {
        String repo = args[0];
        String library =args[1];
        String toRepository = args[2];
        String repositoryId = args[3];
        String save = "";
        if(args.length > 4){
            save = args[4];
        }
        System.out.println(repo);
        System.out.println(library);
        System.out.println(save);
        PorterOptions.Builder builder = new PorterOptions.Builder();
        builder.fromRepository(repo)
                .localDirectory(save)
                .dependency(library)
                .toRespository(toRepository)
                .toRespositoryId(repositoryId);
        Porter porter = new Porter(builder.build());
        porter.startDeploy();
    }
}
