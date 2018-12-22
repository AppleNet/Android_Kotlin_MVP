package com.example.llcgs.android_kotlin.modules.activity.singleTask;

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.activity.singleTask.presenter.impl.SingleTaskPresenter
import com.example.llcgs.android_kotlin.modules.activity.singleTask.view.SingleTaskView
import kotlinx.android.synthetic.main.activity_singletask_affinity.*

/**
 * com.example.llcgs.android_kotlin.modules.activity.singleTask.SingleTaskAffinityActivity
 * @author liulongchao
 * @since 2018/12/22
 */
class SingleTaskAffinity1Activity : BaseActivity<SingleTaskView, SingleTaskPresenter>() {

    override fun createPresenter()= SingleTaskPresenter ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singletask_affinity)
        titleTv.text = "SingleTaskAffinity1Activity"
        button35.setOnClickListener {
            startActivity(Intent(this, SingleTaskActivity::class.java))
        }
    }
}
