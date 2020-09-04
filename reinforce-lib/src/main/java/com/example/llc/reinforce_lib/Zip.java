package com.example.llc.reinforce_lib;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 *  com.example.llc.reinforce_lib.Zip
 * @author liulongchao
 * @since 2020-09-04
 *
 *  Zip 压缩、解压缩工具类
 * */
public class Zip {

    /**
     *  解压缩
     *
     * @param zip 压缩文件
     * @param dir 解压缩文件
     * */
    static void unZip(File zip, File dir) {

        try {
            boolean delete = dir.delete();
            if (delete) {
                ZipFile zipFile = new ZipFile(zip);
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry zipEntry = entries.nextElement();
                    String name = zipEntry.getName();
                    if (name.equals("META-INF/CERT.RSA") || name.equals("META-INF/CERT.SF") || name
                            .equals("META-INF/MANIFEST.MF")) {
                        continue;
                    }
                    if (!zipEntry.isDirectory()) {
                        File file = new File(dir, name);
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        InputStream inputStream = zipFile.getInputStream(zipEntry);
                        byte[] bytes = new byte[1024];
                        int len;
                        while ((len = inputStream.read(bytes)) != -1) {
                            fileOutputStream.write(bytes, 0, len);
                        }
                        inputStream.close();
                        fileOutputStream.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void zip(File dir, File zip) {
        zip.delete();
        try {
            CheckedOutputStream checkedOutputStream = new CheckedOutputStream(new FileOutputStream(zip), new CRC32());
            ZipOutputStream zipOutputStream = new ZipOutputStream(checkedOutputStream);
            compress(dir, zipOutputStream, "");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void compress(File srcFile, ZipOutputStream zos,
                                 String basePath) {
            try {
                if (srcFile.isDirectory()) {
                    compressDir(srcFile, zos, basePath);
                } else {
                    compressFile(srcFile, zos, basePath);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    private static void compressDir(File dir, ZipOutputStream zos,
                                    String basePath) throws Exception {
        File[] files = dir.listFiles();
        if (files.length < 1) {
            ZipEntry entry = new ZipEntry(basePath + dir.getName() + "/");
            zos.putNextEntry(entry);
            zos.closeEntry();
        }
        for (File file : files) {
            compress(file, zos, basePath + dir.getName() + "/");
        }
    }

    private static void compressFile(File file, ZipOutputStream zos, String dir)
            throws Exception {


        String dirName = dir + file.getName();
        String[] dirNameNew = dirName.split("/");
        StringBuffer buffer = new StringBuffer();
        if (dirNameNew.length > 1) {
            for (int i = 1; i < dirNameNew.length; i++) {
                buffer.append("/");
                buffer.append(dirNameNew[i]);
            }
        } else {
            buffer.append("/");
        }
        ZipEntry entry = new ZipEntry(buffer.toString().substring(1));
        zos.putNextEntry(entry);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
                file));
        int count;
        byte data[] = new byte[1024];
        while ((count = bis.read(data, 0, 1024)) != -1) {
            zos.write(data, 0, count);
        }
        bis.close();
        zos.closeEntry();
    }
}
