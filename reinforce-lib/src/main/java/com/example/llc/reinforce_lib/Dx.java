package com.example.llc.reinforce_lib;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;

public class Dx {

    public static File jar2Dex(File fakeDex) {
        File[] files = fakeDex.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.equals("classes.jar");
            }
        });

        if (files != null && files.length > 0) {
            File classes_jar = files[0];
            File aarDex = new File(classes_jar.getParent(), "classes.dex");
            dxCommand(aarDex,classes_jar);
            return aarDex;
        }
        return null;
    }

    private static void dxCommand(File aarDex, File classes_jar) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("");
            process.waitFor();
            if (process.exitValue() != 0) {
                InputStream inputStream = process.getErrorStream();
                int len;
                byte[] buffer = new byte[2048];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while((len=inputStream.read(buffer)) != -1){
                    byteArrayOutputStream.write(buffer,0,len);
                }
            }
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
