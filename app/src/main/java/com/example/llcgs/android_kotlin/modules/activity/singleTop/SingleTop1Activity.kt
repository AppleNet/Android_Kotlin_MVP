package com.example.llcgs.android_kotlin.modules.activity.singleTop

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.activity.singleTop.presenter.impl.SinglePresenter
import com.example.llcgs.android_kotlin.modules.activity.singleTop.view.SingleView
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.activity_singletop.*

/**
 * com.example.llcgs.android_kotlin.modules.activity.singleTop.SingleTopActivity
 * @author liulongchao
 * @since 2018/3/9
 */
class SingleTop1Activity: BaseActivity<SingleView, SinglePresenter>() {

    /**
     *  singleTop模式
     *
     * */
    override fun createPresenter()= SinglePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singletop)
        "SingleTop1Activity ---- onCreate()".logD()
        titleTextView.text = "SingleTop1Activity"
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
        "SingleTop1Activity ---- onStart()".logD()
    }

    override fun onResume() {
        super.onResume()
        "SingleTop1Activity ---- onResume()".logD()
    }

    override fun onPause() {
        super.onPause()
        "SingleTop1Activity ---- onPause()".logD()
    }

    override fun onStop() {
        super.onStop()
        "SingleTop1Activity ---- onStop()".logD()
    }

    override fun onDestroy() {
        super.onDestroy()
        "SingleTop1Activity ---- onDestroy()".logD()
    }

    override fun onRestart() {
        super.onRestart()
        "SingleTop1Activity ---- onRestart()".logD()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        "SingleTop1Activity ---- onNewIntent()".logD()

    }
}