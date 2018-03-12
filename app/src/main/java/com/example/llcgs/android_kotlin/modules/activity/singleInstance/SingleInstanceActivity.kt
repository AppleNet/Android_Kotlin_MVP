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
 * com.example.llcgs.android_kotlin.modules.activity.singleInstance.SingleInstanceActivity
 * @author liulongchao
 * @since 2018/3/9
 */
class SingleInstanceActivity: BaseActivity<SingleInstanceView, SingleInstancePresenter>() {

    override fun createPresenter()= SingleInstancePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singleinstance)
        "SingleInstanceActivity ---- onCreate()".logD()
        titleTextView.text = "SingleInstanceActivity"
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
        "SingleInstanceActivity ---- onStart()".logD()
    }

    override fun onResume() {
        super.onResume()
        "SingleInstanceActivity ---- onResume()".logD()
    }

    override fun onPause() {
        super.onPause()
        "SingleInstanceActivity ---- onPause()".logD()
    }

    override fun onStop() {
        super.onStop()
        "SingleInstanceActivity ---- onStop()".logD()
    }

    override fun onDestroy() {
        super.onDestroy()
        "SingleInstanceActivity ---- onDestroy()".logD()
    }

    override fun onRestart() {
        super.onRestart()
        "SingleInstanceActivity ---- onRestart()".logD()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        "SingleInstanceActivity ---- onNewIntent()".logD()

    }
}