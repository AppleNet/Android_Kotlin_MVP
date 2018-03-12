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
class SingleTop2Activity: BaseActivity<SingleView, SinglePresenter>() {
    
    override fun createPresenter()= SinglePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singletop)
        "SingleTop2Activity ---- onCreate()".logD()
        titleTextView.text = "SingleTop2Activity"
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
        "SingleTop2Activity ---- onStart()".logD()
    }

    override fun onResume() {
        super.onResume()
        "SingleTop2Activity ---- onResume()".logD()
    }

    override fun onPause() {
        super.onPause()
        "SingleTop2Activity ---- onPause()".logD()
    }

    override fun onStop() {
        super.onStop()
        "SingleTop2Activity ---- onStop()".logD()
    }

    override fun onDestroy() {
        super.onDestroy()
        "SingleTop2Activity ---- onDestroy()".logD()
    }

    override fun onRestart() {
        super.onRestart()
        "SingleTop2Activity ---- onRestart()".logD()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        "SingleTop2Activity ---- onNewIntent()".logD()

    }
}