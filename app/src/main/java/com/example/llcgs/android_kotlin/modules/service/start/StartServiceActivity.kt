package com.example.llcgs.android_kotlin.modules.service.start

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.service.start.presenter.impl.StartServicePresenter
import com.example.llcgs.android_kotlin.modules.service.start.service.StartService
import com.example.llcgs.android_kotlin.modules.service.start.view.StartServiceView
import kotlinx.android.synthetic.main.activity_start_service.*

/**
 * com.example.llcgs.android_kotlin.modules.service.start.StartServiceActivity
 * @author liulongchao
 * @since 2018/3/21
 */
class StartServiceActivity: BaseActivity<StartServiceView, StartServicePresenter>() {

    override fun createPresenter()= StartServicePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_service)
        button33.setOnClickListener {
            startService(Intent(this, StartService::class.java))
        }

        button34.setOnClickListener {
            startActivity(Intent(this, Start1ServiceActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, StartService::class.java))
    }
}