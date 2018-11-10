package com.example.llcgs.android_kotlin.modules.activity.saveinstance

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.home.MainActivity
import com.example.llcgs.android_kotlin.modules.activity.saveinstance.presenter.impl.OnSaveInstancePresenter
import com.example.llcgs.android_kotlin.modules.activity.saveinstance.view.OnSaveInstanceView
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.activity_onsaveinstance.*

/**
 * com.example.llcgs.android_kotlin.modules.activity.saveinstance.OnSaveInstanceActivity
 * @author liulongchao
 * @since 2018/11/9
 */
class OnSaveInstanceActivity: BaseActivity<OnSaveInstanceView, OnSaveInstancePresenter>() {

    /*
    *  home键的时候 会执行onSaveInstance方法，此方法在onPause之后 onStop之前执行
    *
    *  back键的时候不会执行onSaveInstance方法
    *
    *  Intent跳转的时候 会执行onSaveInstance方法，此方法在onPause之后 onStop之前执行
    *
    * **/

    override fun createPresenter()= OnSaveInstancePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onsaveinstance)

        button50.setOnClickListener {
            startActivity(Intent(this@OnSaveInstanceActivity, MainActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        "onStart...".logD()
    }

    override fun onResume() {
        super.onResume()
        "onResume...".logD()
    }

    override fun onPause() {
        super.onPause()
        "onPause...".logD()
    }

    override fun onStop() {
        super.onStop()
        "onStop...".logD()
    }

    override fun onDestroy() {
        super.onDestroy()
        "onDestroy...".logD()

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // 保存一些状态，用于再Activity再次创建的时候 使用
        "onSaveInstanceState....".logD()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

    }
}