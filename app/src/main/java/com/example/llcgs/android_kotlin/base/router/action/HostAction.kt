package com.example.llcgs.android_kotlin.base.router.action

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.lzh.nonview.router.anno.RouterRule
import com.lzh.nonview.router.route.ActionSupport

/**
 * com.example.llcgs.android_kotlin.base.action.HostAction
 * @author liulongchao
 * @since 2017/8/26
 */

@RouterRule("action")
class HostAction: ActionSupport() {
    override fun onRouteTrigger(context: Context?, bundle: Bundle?) {
        Toast.makeText(context, "Host Action invoked!", Toast.LENGTH_SHORT).show();
    }
}