package com.example.llcgs.android_kotlin.modules.activity.singleTop

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.activity.singleTop.presenter.impl.SinglePresenter
import com.example.llcgs.android_kotlin.modules.activity.singleTop.view.SingleView
import com.gomejr.myf.core.kotlin.logD
import kotlinx.android.synthetic.main.activity_singletop.*

/**
 * com.example.llcgs.android_kotlin.modules.activity.singleTop.SingleTopActivity
 * @author liulongchao
 * @since 2018/3/9
 */
class SingleTopActivity: BaseActivity<SingleView, SinglePresenter>() {

    override fun createPresenter()= SinglePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singletop)
        "SingleTopActivity ---- onCreate()".logD()
        button30.setOnClickListener {
            startActivity(Intent(this, SingleTopActivity::class.java))
        }

        button31.setOnClickListener {
            startActivity(Intent(this, SingleTop1Activity::class.java))
        }

        button32.setOnClickListener {
            startActivity(Intent(this, SingleTop2Activity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        "SingleTopActivity ---- onStart()".logD()
    }

    override fun onResume() {
        super.onResume()
        "SingleTopActivity ---- onResume()".logD()
    }

    override fun onPause() {
        super.onPause()
        "SingleTopActivity ---- onPause()".logD()
    }

    override fun onStop() {
        super.onStop()
        "SingleTopActivity ---- onStop()".logD()
    }

    override fun onDestroy() {
        super.onDestroy()
        "SingleTopActivity ---- onDestroy()".logD()
    }

    override fun onRestart() {
        super.onRestart()
        "SingleTopActivity ---- onRestart()".logD()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        "SingleTopActivity ---- onNewIntent()".logD()

    }
}