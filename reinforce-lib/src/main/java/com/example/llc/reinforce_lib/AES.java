package com.example.llc.reinforce_lib;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *  com.example.llc.reinforce_lib.AES
 * @author liulongchao
 * @since 2020-09-04
 *
 *  AES 加密工具类
 * */
public class AES {

    public static final String DEFAULT_PWD = "abcdefghijklmnopqrstuvwxyz";
    private static final String algorithmStr = "AES/ECB/PKCS5Padding";

    private static Cipher encryptCipher;
    private static Cipher decryptCipher;

    /**
     * 初始化 加密解密器
     * */
    static void init(String password) {
        try {
            // 生成一个实现指定转换的 Cipher 对象
            encryptCipher = Cipher.getInstance(algorithmStr);
            decryptCipher = Cipher.getInstance(algorithmStr);
            byte[] bytes = password.getBytes();
            SecretKeySpec key = new SecretKeySpec(bytes, "AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  给解压后的 dex 文件进行加密操作
     *
     *  1. 拿到 dex 文件，
     *  2. 将 dex 文件转换成二进制 byte[]
     *  3. 对 byte 进行加密
     * */
    public static File encryptAPKFile(File dstApkFile) {
        // 获取此路径下的所有 dex 文件
        File[] dexFiles = dstApkFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".dex");
            }
        });
        File mainDexFile = null;
        byte[] mainDexData = null;

        if (dexFiles != null && dexFiles.length > 0) {
            for (File dexFile : dexFiles) {
                // 读数据
                byte[] buffer = Utils.getBytes(dexFile);
                // 加密
                byte[] encryptBytes = encrypt(buffer);

                if (dexFile.getName().endsWith("classes.dex")) {
                    mainDexData = encryptBytes;
                    mainDexFile = dexFile;
                }

                if (mainDexData != null && mainDexData.length > 0){
                    // 写数据，替换原来的数据
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(mainDexFile);
                        fileOutputStream.write(mainDexData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return mainDexFile;
    }

    private static byte[] encrypt(byte[] content) {
        try {
            return encryptCipher.doFinal(content);
        } catch (Exception e) {
            //
        }
        return null;
    }

}
