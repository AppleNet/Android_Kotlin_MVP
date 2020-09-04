package com.example.llc.reinforce_lib;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Signature {

    public static void signature(File unsignedApk, File signedApk) throws InterruptedException, IOException {

        String cmd[] = {"cmd.exe", "/C ","jarsigner",  "-sigalg", "MD5withRSA",
                "-digestalg", "SHA1",
                "-keystore", "C:/Users/allen/.android/debug.keystore",
                "-storepass", "android",
                "-keypass", "android",
                "-signedjar", signedApk.getAbsolutePath(),
                unsignedApk.getAbsolutePath(),
                "androiddebugkey"};

        Process process = Runtime.getRuntime().exec(cmd);
        try {
            int waitResult = process.waitFor();
            System.out.println("waitResult: " + waitResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }
        if (process.exitValue() != 0) {
            InputStream inputStream = process.getErrorStream();
            int len;
            byte[] buffer = new byte[2048];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while((len=inputStream.read(buffer)) != -1){
                bos.write(buffer,0,len);
            }
            System.out.println(new String(bos.toByteArray(),"GBK"));
            throw new RuntimeException("process.exitValue() != 0");
        }
        process.destroy();
    }
}
