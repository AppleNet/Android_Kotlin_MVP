package com.example.llc.reinforce_lib;

import java.io.File;
import java.io.RandomAccessFile;

public class Utils {


    public static byte[] getBytes(File dexFile) {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(dexFile, "r");
            byte[] buffer = new byte[(int) randomAccessFile.length()];
            randomAccessFile.readFully(buffer);
            randomAccessFile.close();
            return buffer;
        } catch (Exception e) {
            //
            return new byte[1024];
        }
    }
}
