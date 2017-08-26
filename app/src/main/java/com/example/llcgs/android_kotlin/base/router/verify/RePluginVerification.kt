package com.example.llcgs.android_kotlin.base.router.verify

import android.content.Context
import android.os.Binder
import com.lzh.nonview.router.host.RemoteVerify

/**
 * com.example.llcgs.android_kotlin.base.verify.RePluginVerification
 * @author liulongchao
 * @since 2017/8/26
 */


class RePluginVerification: RemoteVerify {
    override fun verify(context: Context?) = android.os.Process.myUid() == Binder.getCallingUid()
}