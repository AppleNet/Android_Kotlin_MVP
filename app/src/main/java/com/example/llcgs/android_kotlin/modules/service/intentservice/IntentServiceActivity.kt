package com.example.llcgs.android_kotlin.modules.service.intentservice

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.service.intentservice.presenter.impl.IntentServicePresenter
import com.example.llcgs.android_kotlin.modules.service.intentservice.service.MyIntentService
import com.example.llcgs.android_kotlin.modules.service.intentservice.view.IntentServiceView
import kotlinx.android.synthetic.main.activity_intent_service.*

/**
 * com.example.llcgs.android_kotlin.modules.service.intentservice.IntentServiceActivity
 * @author liulongchao
 * @since 2018/3/22
 */
class IntentServiceActivity: BaseActivity<IntentServiceView, IntentServicePresenter>() {

    override fun createPresenter()= IntentServicePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_service)
        button37.setOnClickListener {
            startService(Intent(this@IntentServiceActivity, MyIntentService::class.java).apply {
                putExtra("down", "url")
            })
        }
    }
}