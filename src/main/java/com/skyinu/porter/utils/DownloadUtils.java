package com.skyinu.porter.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUtils {
    public static File downloadFile(String url, File output) {
        if (!output.exists() && output.length() > 5) {
            return output;
        }
        try {
            URL conn = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) conn.openConnection();
            connection.setConnectTimeout(15000);
            if (connection.getResponseCode() != 200) {
                return null;
            } else {
                if (!output.exists()) {
                    output.createNewFile();
                }
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(output));
                BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
                byte[] buffer = new byte[2048];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                outputStream.close();
                return output;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
