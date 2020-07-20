package com.example.kotlin.hotfix;

import android.app.Application;
import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HotFix {

    /**
     *  1. 获取当前应用到PathClassLoader
     *
     *  2. 反射获取DexPathList属性 pathList
     *
     *  3. 反射修改pathList的dexElements[]
     *     3.1 把补丁包path.dex转换成Element[](path)
     *     3.2 获得旧的pathList的dexElements()
     *     3.3 新的和旧的合并，并反射赋值给pathList的dexElements
     * */
    public static void installPatch(Application application, File patch) {

        if (!patch.exists()) {
            return;
        }

        // 1. 获取当前应用到PathClassLoader
        ClassLoader pathClassLoader = application.getClassLoader();

        List<File> fileList = new ArrayList<>();
        fileList.add(patch);
        File dexOutputDir = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dexOutputDir = application.getCodeCacheDir();
        }
        ArrayList<IOException> suppressedExceptions = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                // 仿 Tinker 写法
                pathClassLoader = NewClassLoaderInjector.inject(application, pathClassLoader, dexOutputDir, fileList);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return;
        }

        try {
            // 2. 反射获取DexPathList属性 pathList
            Field pathListField = ShareReflectUtils.findFiled(pathClassLoader, "pathList");
            Object pathList = pathListField.get(pathClassLoader);
            if (pathList == null) {
                return;
            }

            // 3.反射修改pathList的dexElements[]
            // 3.1 把补丁包path.dex转换成Element[](path)
            Method makePathElements;

            // 如果是静态方法，invoke方法中的第一个参数可以传递null，如果不是静态方法，则不可以
            Object[] newDexElements;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                makePathElements =
                        ShareReflectUtils.findMethod(pathList, "makePathElements", List.class, File.class, List.class);
                newDexElements = (Object[]) makePathElements.invoke(pathList, fileList, dexOutputDir, suppressedExceptions);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                makePathElements =
                        ShareReflectUtils.findMethod(pathList, "makeDexElements", ArrayList.class, File.class, ArrayList.class);
                newDexElements = (Object[]) makePathElements.invoke(pathList, new ArrayList<File>(fileList), dexOutputDir, suppressedExceptions);
            } else {
                makePathElements =
                        ShareReflectUtils.findMethod(pathList, "makeDexElements", ArrayList.class, File.class);
                newDexElements = (Object[]) makePathElements.invoke(pathList, fileList, dexOutputDir);
            }
            // 3.2 获得旧的pathList的dexElements()
            Field dexElementsField = ShareReflectUtils.findFiled(pathList, "dexElements");
            Object[] oldDexElements = (Object[]) dexElementsField.get(pathList);

            // 3.3 新的和旧的合并，并反射赋值给pathList的dexElements
            if (newDexElements == null || oldDexElements == null) {
                return;
            }
            // 创建一个新的数组
            if (oldDexElements.getClass().getComponentType() == null) {
                return;
            }
            Object[] newElements = (Object[]) Array.newInstance(oldDexElements.getClass().getComponentType(), newDexElements.length + oldDexElements.length);
            System.arraycopy(newDexElements, 0,newElements, 0, newDexElements.length);
            System.arraycopy(oldDexElements, 0,newElements, newDexElements.length, oldDexElements.length);

            dexElementsField.set(pathList, newElements);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
