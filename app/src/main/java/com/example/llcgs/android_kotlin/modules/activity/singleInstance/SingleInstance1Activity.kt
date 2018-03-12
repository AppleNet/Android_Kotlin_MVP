package com.example.llcgs.android_kotlin.modules.activity.singleInstance

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.activity.singleInstance.presenter.impl.SingleInstancePresenter
import com.example.llcgs.android_kotlin.modules.activity.singleInstance.view.SingleInstanceView
import com.gomejr.myf.core.kotlin.logD
import kotlinx.android.synthetic.main.activity_singletask.*

/**
 * com.example.llcgs.android_kotlin.modules.activity.singleInstance.SingleInstance1Activity
 * @author liulongchao
 * @since 2018/3/9
 */
class SingleInstance1Activity: BaseActivity<SingleInstanceView, SingleInstancePresenter>() {

    override fun createPresenter()= SingleInstancePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singleinstance)
        "SingleInstance1Activity ---- onCreate()".logD()
        titleTextView.text = "SingleInstance1Activity"
        button30.setOnClickListener {
            startActivity(Intent(this, SingleInstanceActivity::class.java))
        }
        button31.setOnClickListener {
            startActivity(Intent(this, SingleInstance1Activity::class.java))
        }
        button32.setOnClickListener {
            startActivity(Intent(this, SingleInstance2Activity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        "SingleInstance1Activity ---- onStart()".logD()
    }

    override fun onResume() {
        super.onResume()
        "SingleInstance1Activity ---- onResume()".logD()
    }

    override fun onPause() {
        super.onPause()
        "SingleInstance1Activity ---- onPause()".logD()
    }

    override fun onStop() {
        super.onStop()
        "SingleInstance1Activity ---- onStop()".logD()
    }

    override fun onDestroy() {
        super.onDestroy()
        "SingleInstance1Activity ---- onDestroy()".logD()
    }

    override fun onRestart() {
        super.onRestart()
        "SingleInstance1Activity ---- onRestart()".logD()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        "SingleInstance1Activity ---- onNewIntent()".logD()

    }
}