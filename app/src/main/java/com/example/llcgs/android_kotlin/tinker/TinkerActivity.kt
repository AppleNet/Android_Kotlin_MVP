package com.example.llcgs.android_kotlin.tinker

import android.os.Bundle
import android.os.Environment
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.tinker.presenter.impl.TinkerPresenter
import com.example.llcgs.android_kotlin.tinker.view.TinkerView
import com.lzh.nonview.router.anno.RouterRule
import com.tencent.tinker.lib.tinker.TinkerInstaller

/**
 * com.example.llcgs.android_kotlin.tinker.TinkerActivity
 * @author liulongchao
 * @since 2018/11/12
 */
@RouterRule("Tinker")
class TinkerActivity: BaseActivity<TinkerView, TinkerPresenter>() {

    override fun createPresenter()= TinkerPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tinker)
        TinkerInstaller.onReceiveUpgradePatch(applicationContext, Environment.getExternalStorageDirectory().absolutePath + "/patch_signed.apk")
    }
}