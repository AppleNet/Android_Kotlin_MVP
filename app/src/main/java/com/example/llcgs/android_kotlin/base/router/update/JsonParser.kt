package com.example.llcgs.android_kotlin.base.router.update

import com.alibaba.fastjson.JSON
import com.gomejr.myf.core.kotlin.logD
import org.lzh.framework.updatepluginlib.model.Update
import org.lzh.framework.updatepluginlib.model.UpdateParser

/**
 * com.example.llcgs.android_kotlin.base.router.update.JsonParser
 * @author liulongchao
 * @since 2017/8/28
 */


class JsonParser: UpdateParser {
    override fun parse(httpResponse: String?): Update {
        "回调到了这里。。。。".logD()
        httpResponse?.logD()
        return JSON.parseObject(httpResponse, Update::class.java)
    }
}