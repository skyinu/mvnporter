package com.skyinu.porter.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chen on 2018/11/18.
 */
public class ResourceUtils {
    public static void syncScript(Class c, String resourceName){
        InputStream inputStream = c.getResourceAsStream("/" + resourceName);
        File outFile = new File(System.getProperty("user.dir"), resourceName);
        if(outFile.exists()){
            return;
        }
        FileOutputStream result;
        byte []buffer = new byte[4096];
        int len;
        try {
            result = new FileOutputStream(outFile);
            while ((len = inputStream.read(buffer)) > 0){
                result.write(buffer, 0,len);
            }
            inputStream.close();
            result.flush();
            result.close();
            Runtime.getRuntime().exec("chmod +x " + resourceName).waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("occur error when copy script");
            e.printStackTrace();
        }
    }
}
