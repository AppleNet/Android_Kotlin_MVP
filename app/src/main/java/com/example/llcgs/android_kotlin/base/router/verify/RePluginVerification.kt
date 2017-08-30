package com.example.llcgs.android_kotlin.base.router.verify

import android.content.Context
import android.os.Binder
import com.gomejr.myf.core.kotlin.logD
import com.lzh.nonview.router.host.RemoteVerify

/**
 * com.example.llcgs.android_kotlin.base.verify.RePluginVerification
 * @author liulongchao
 * @since 2017/8/26
 */


class RePluginVerification: RemoteVerify {
    override fun verify(context: Context):Boolean{
        val packageName = context.packageName
        val uid = Binder.getCallingUid()
        var packages = context.packageManager.getPackagesForUid(uid)
        if (packages == null){
            packages = arrayOf("")
        }
        packages.forEach {
            return it == packageName
        }
        // android.os.Process.myUid() == Binder.getCallingUid()
        String.format("The client with uid %s connected failed:", uid).logD()
        return false
    }
}