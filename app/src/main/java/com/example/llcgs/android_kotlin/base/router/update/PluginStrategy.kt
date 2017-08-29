package com.example.llcgs.android_kotlin.base.router.update

import org.lzh.framework.updatepluginlib.model.Update
import org.lzh.framework.updatepluginlib.strategy.UpdateStrategy

/**
 * com.example.llcgs.android_kotlin.base.router.update.PluginStrategy
 * @author liulongchao
 * @since 2017/8/28
 */


class PluginStrategy: UpdateStrategy {
    override fun isShowDownloadDialog(): Boolean {
        return true
    }

    override fun isAutoInstall(): Boolean {
        return true
    }

    override fun isShowUpdateDialog(update: Update?): Boolean {
        return true
    }

}