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
 * com.example.llcgs.android_kotlin.modules.activity.singleTask.SingleTaskActivity
 * @author liulongchao
 * @since 2018/3/9
 */
class SingleTaskActivity : BaseActivity<SingleTaskView, SingleTaskPresenter>(){

    override fun createPresenter()= SingleTaskPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singletask)
        "SingleTaskActivity ---- onCreate()".logD()
        titleTextView.text = "SingleTaskActivity"
        button30.setOnClickListener {
            startActivity(Intent(this, SingleTaskActivity::class.java))
        }
        button31.setOnClickListener {
            startActivity(Intent(this, SingleTask1Activity::class.java))
        }
        button32.setOnClickListener {
            startActivity(Intent(this, SingleTask2Activity::class.java))
        }
        button33.setOnClickListener {
            // 操作流程：
            // 单击按钮启动 SingleTaskAffinityActivity，在 SingleTaskAffinityActivity 中启动 SingleTaskAffinity1Activity
            // 在 SingleTaskAffinity1Activity 中启动 SingleTaskActivity
            // 在 SingleTaskActivity 中启动 SingleTaskAffinityActivity
            // 按 两次 back 键， 观察结果。
            startActivity(Intent(this, SingleTaskAffinityActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        "SingleTaskActivity ---- onStart()".logD()
    }

    override fun onResume() {
        super.onResume()
        "SingleTaskActivity ---- onResume()".logD()
    }

    override fun onPause() {
        super.onPause()
        "SingleTaskActivity ---- onPause()".logD()
    }

    override fun onStop() {
        super.onStop()
        "SingleTaskActivity ---- onStop()".logD()
    }

    override fun onDestroy() {
        super.onDestroy()
        "SingleTaskActivity ---- onDestroy()".logD()
    }

    override fun onRestart() {
        super.onRestart()
        "SingleTaskActivity ---- onRestart()".logD()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        "SingleTaskActivity ---- onNewIntent()".logD()

    }

}