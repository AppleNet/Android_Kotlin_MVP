package com.example.llc.reinforce_lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *  com.example.llc.reinforce_lib.Reinforce
 *
 * @author liulongchao
 * @since 2020-09-04
 *
 *
 *  手写加固框架，主要核心思想是文件的 IO 操作
 *  不能用于商用
 *
 * */
public class Reinforce {

    /**
     *  本地对未签名文件进行加固
     *
     * */
    public static void main(String[] args) {

        /*
         * 存储源 apk 中的 dex 文件
         * */
        byte[] mainDexBytes;
        /*
         * 存储壳中的 dex 文件
         * */
        byte[] aarData;
        /*
         * 存储壳 dex 和源 dex 合并后的 dex 文件
         * */
        byte[] mergeDex;

        File tempFileApk = new File("./src/source/apk/temp");
        if (tempFileApk.exists()) {
            File[] files = tempFileApk.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }

        File tempFileAar = new File("./src/source/aar/temp");
        if (tempFileAar.exists()) {
            File[] files = tempFileAar.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }


        /*
         *  第一步：处理源 APK，并加密 dex
         * */
        AES.init(AES.DEFAULT_PWD);
        File apkFile = new File("./src/source/apk/app-debug.apk");
        File newApkFile = new File("./src/source/apk/temp");
        if (!newApkFile.exists()) {
            newApkFile.mkdirs();
        }
        // 1. 解压 APK
        Zip.unZip(apkFile, newApkFile);
        // 2. 对解压后的文件夹中的 dex 文件进行加密操作
        AES.encryptAPKFile(newApkFile);
        // 3. 对加密后的 dex 进行重命名（这里是为了区分看下效果，两个 classes.dex 和 classes_.dex 都是经过加密的）
        if (newApkFile.isDirectory()) {
            File[] listFiles = newApkFile.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isFile()) {
                        String name = file.getName();
                        int cursor = name.indexOf(".dex");
                        String newName = file.getParent() + File.separator + name.substring(0, cursor) + "_" + ".dex";
                        file.renameTo(new File(newName));
                    }
                }
            }
        }

        /*
         * 第二步：处理壳 aar 获得壳 dex
         *
         * */
        File aarFile = new File("./src/aar/my-library.aar");
        File fakeDex = new File(aarFile.getParent() + File.separator + "temp");
        if (!fakeDex.exists()) {
            fakeDex.mkdirs();
        }
        // 1. 解压 aar
        Zip.unZip(aarFile,fakeDex);
        // 2. 将解压出来的 jar 转换成 dex
        File dexAar = Dx.jar2Dex(fakeDex);


        /*
         *  第三步：将壳 dex 和加密后的 dex 进行合并
         *
         * */
        File tempMainDex = new File(newApkFile.getPath() + File.separator + "classes.dex");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(tempMainDex);
            byte[] bytes = Utils.getBytes(dexAar);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
        } catch (Exception e) {
            //
        }


        /*
         *
         * 第四步：将合并后的 dex 打包成一个未签名的 apk
         * */
        File unsignedApk = new File("./src/result/apk-unsigned.apk");
        if (!unsignedApk.exists()) {
            unsignedApk.getParentFile().mkdirs();
        }
        // 1. 开始打包
        Zip.zip(newApkFile, unsignedApk);

        /*
         *  第五步：将未签名的 apk。进行重新签名
         * */
        File signedApk = new File("result/apk-signed.apk");
        try {
            Signature.signature(unsignedApk, signedApk);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
