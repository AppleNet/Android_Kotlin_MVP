package com.example.llcgs.android_kotlin.architecture_components.lifecycle

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.lifecycle.bean.Login
import com.example.llcgs.android_kotlin.architecture_components.lifecycle.presenter.impl.LifeCyclePresenter
import com.example.llcgs.android_kotlin.architecture_components.lifecycle.view.LifeCycleView
import com.example.llcgs.android_kotlin.architecture_components.livedata.use_livedata.LiveDataActivity
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_lifecycle.*
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.architechure_components.lifecycle.LifeCycleActivity
 * @author liulongchao
 * @since 2017/11/27
 */
@RouterRule("Architecture")
class LifeCycleActivity : BaseOwnerActivity<LifeCyclePresenter>(), LifeCycleView, View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    override fun createPresenter() = LifeCyclePresenter(this)

    override fun getLayoutId() = R.layout.activity_lifecycle

    private fun initViews(){
        pluginTitleTV.text = "LifeCycle"
        userLogin.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.userLogin ->{
                mPresenter.doLogin(Login(userName.text.toString(), userPwd.text.toString()))
            }
        }
    }

    override fun onLoginSuccess(success: Boolean) {
        if (success){
            startActivity(Intent(this, LiveDataActivity::class.java))
        }else{
            //
            Toast.makeText(this, "login error", Toast.LENGTH_SHORT).show()
        }
    }
}