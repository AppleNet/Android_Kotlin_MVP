package com.example.llcgs.android_kotlin.base.router.update

import com.gomejr.myf.core.kotlin.logD
import org.lzh.framework.updatepluginlib.model.Update
import org.lzh.framework.updatepluginlib.model.UpdateChecker

/**
 * com.example.llcgs.android_kotlin.base.router.update.PluginChecker
 * @author liulongchao
 * @since 2017/8/28
 */

/**
 *  对插件api通过上方JsonParser解析后的更新实体类进行检查。检查是否需要进行更新下载安装
 * */
class PluginChecker: UpdateChecker {
    override fun check(update: Update?): Boolean {
        "update is true".logD()
        return true
    }
}