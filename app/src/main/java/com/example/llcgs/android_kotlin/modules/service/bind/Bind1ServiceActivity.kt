package com.example.llcgs.android_kotlin.modules.service.bind

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.service.bind.presenter.impl.BindServicePresenter
import com.example.llcgs.android_kotlin.modules.service.bind.service.BindService
import com.example.llcgs.android_kotlin.modules.service.bind.view.BindServiceView
import kotlinx.android.synthetic.main.activity_bind_service.*

/**
 * com.example.llcgs.android_kotlin.modules.service.bind.BindServiceActivity
 * @author liulongchao
 * @since 2018/3/21
 */
class Bind1ServiceActivity: BaseActivity<BindServiceView, BindServicePresenter>() {

    private val connection : ServiceConnection = object : ServiceConnection{

        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            (service as BindService.MyBinder).log()
        }
    }

    override fun createPresenter()= BindServicePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bind_service)
        button35.setOnClickListener {
            bindService(Intent(this, BindService::class.java), connection, Context.BIND_AUTO_CREATE)
        }

    }

}