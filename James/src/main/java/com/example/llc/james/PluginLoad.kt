package com.example.llc.james;

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Environment
import java.io.File

/**
 * com.example.llc.james.PluginLoad
 * @author liulongchao
 * @since 2017/7/26
 *
 */
class PluginLoad {

    companion object{
        /**
         * 可以是 apk 路径，dex 路径，jar 路径。8.0之后 使用 DexClassLoader 和 PathClassLoader 没有任何区别
         */
        private val apkPath = Environment.getExternalStorageDirectory().path + File.separator + "plugin-debug.apk"

        private var mResources: Resources? = null

        private fun loadResources(context: Context): Resources? {
            // 1. 创建一个AssetManager
            try {
                val assetManager = AssetManager::class.java.newInstance()

                // 2. 添加插件资源
                val addAssetPathMethod = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
                addAssetPathMethod.invoke(assetManager, apkPath)

                // 3. 创建Resources，传入创建的AssetManager
                val resources = context.resources
                return Resources(assetManager, resources.displayMetrics, resources.configuration)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        fun getResources(context: Context): Resources? {
            if (mResources == null) {
                mResources = loadResources(context)
            }
            return mResources
        }

    }

}
