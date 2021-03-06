package com.example.llcgs.android_kotlin.base.router.update

import com.lzh.router.replugin.update.IUpdateCombine
import org.lzh.framework.updatepluginlib.model.CheckEntity
import org.lzh.framework.updatepluginlib.model.HttpMethod

/**
 * com.example.llcgs.android_kotlin.base.router.update.HostUpdateCombine
 * @author liulongchao
 * @since 2017/8/28
 */


class HostUpdateCombine: IUpdateCombine {
    override fun combine(alias: String?): CheckEntity {
        val check = CheckEntity()
        check.method = HttpMethod.POST
        check.url = /**此处方式更新的url*/""
        val params = mapOf(
                "version" to "3.0.14",
                "phoneType" to "1",
                "appType" to "1"
        )
        check.params = params
        return check
    }

}