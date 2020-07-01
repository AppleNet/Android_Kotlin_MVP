package com.example.kotlin.plugin;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.DexClassLoader;

public class PluginLoad {

    /**
     *  可以是 apk 路径，dex 路径，jar 路径。8.0之后 使用 DexClassLoader 和 PathClassLoader 没有任何区别
     * */
    private static final String apkPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "plugin-debug.apk";


    /**
     *  Class<?> clazz = Class.forName("");
     *  Method method = clazz.getMethod("");
     *  method.invoke(null);
     * */
    public static void loadClass(Context context) {

        try {
            // Element[]
            Class<?> dexPathList = Class.forName("dalvik.system.DexPathList");
            Field dexElementsFiled = dexPathList.getDeclaredField("dexElements");
            dexElementsFiled.setAccessible(true);

            // DexPathList
            Class<?> baseDexClassLoader = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListFiled = baseDexClassLoader.getDeclaredField("pathList");
            pathListFiled.setAccessible(true);

            // 1. 获取宿主的类加载器
            ClassLoader pathClassLoader = context.getClassLoader();
            // 获取宿主PathClassLoader中的 DexPathList 对象
            Object hostPathList = pathListFiled.get(pathClassLoader);
            // 获取宿主PathClassLoader中的 Elements[]
            Object[] hostDexElements = (Object[]) dexElementsFiled.get(hostPathList);

            // 2. 获取插件的类加载器
            /*
             *  dexPath : 插件 apk 的目录；
             *  optimizedDirectory : 优化路径 context.getCacheDir().getAbsolutePath()
             *  librarySearchPath ：
             *  ClassLoader parent ： 7.0以前的版本 不可以传递 null，否则会抛出异常。7.0之后没有问题
             * */
            ClassLoader pluginClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(), null, pathClassLoader);
            // 获取宿主PathClassLoader中的 PathList 对象
            Object pluginPathList = pathListFiled.get(pluginClassLoader);
            // 获取宿主PathClassLoader中的 DexElements[]
            Object[] pluginDexElements = (Object[]) dexElementsFiled.get(pluginPathList);

            // 3. 合并
            // 反射创建一个新的数组。 与热修复的原理不同的地方在于 这里不需要放到最前面，因为它本身就是一个没有被加载过的类，
            // 而热修复是有可能这个类已经被加载了，当新的 dex 文件下发下来并且被放到 dexElements 中的时候需要重新被加载一次，并且要比有问题的类先加载到才行，所以需要重启
            Object[] newElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(), hostDexElements.length + pluginDexElements.length);
            System.arraycopy(hostDexElements, 0, newElements, 0, hostDexElements.length);
            System.arraycopy(pluginDexElements, 0, newElements, hostDexElements.length, pluginDexElements.length);

            // 4. 将新的数组复制到宿主的 dexElements
            dexElementsFiled.set(hostPathList, newElements);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
