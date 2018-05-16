package com.example.llcgs.android_kotlin.modules.activity.singleTask

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.activity.singleTask.presenter.impl.SingleTaskPresenter
import com.example.llcgs.android_kotlin.modules.activity.singleTask.view.SingleTaskView
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.activity_singletask.*

/**
 * com.example.llcgs.android_kotlin.modules.activity.singleTask.SingleTask1Activity
 * @author liulongchao
 * @since 2018/3/9
 */
class SingleTask1Activity : BaseActivity<SingleTaskView, SingleTaskPresenter>(){

    override fun createPresenter()= SingleTaskPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singletask)
        "SingleTask1Activity ---- onCreate()".logD()
        titleTextView.text = "SingleTask1Activity"
        button30.setOnClickListener {
            startActivity(Intent(this, SingleTaskActivity::class.java))
        }
        button31.setOnClickListener {
            startActivity(Intent(this, SingleTask1Activity::class.java))
        }
        button32.setOnClickListener {
            startActivity(Intent(this, SingleTask2Activity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        "SingleTask1Activity ---- onStart()".logD()
    }

    override fun onResume() {
        super.onResume()
        "SingleTask1Activity ---- onResume()".logD()
    }

    override fun onPause() {
        super.onPause()
        "SingleTask1Activity ---- onPause()".logD()
    }

    override fun onStop() {
        super.onStop()
        "SingleTask1Activity ---- onStop()".logD()
    }

    override fun onDestroy() {
        super.onDestroy()
        "SingleTask1Activity ---- onDestroy()".logD()
    }

    override fun onRestart() {
        super.onRestart()
        "SingleTask1Activity ---- onRestart()".logD()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        "SingleTask1Activity ---- onNewIntent()".logD()

    }

}