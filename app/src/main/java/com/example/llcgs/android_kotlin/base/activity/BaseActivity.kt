package com.example.llcgs.android_kotlin.base.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.base.view.BaseView

/**
 * com.example.llcgs.android_kotlin.base.activity.BaseActivity
 * @author liulongchao
 * @since 2017/5/18
 */


abstract class BaseActivity<V, P: BasePresenter<V>> : AppCompatActivity(), BaseView {

    lateinit var mPresenter : P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
    }

    abstract fun createPresenter(): P

    override fun showLoadingDialog(){
       Toast.makeText(this, "show loading", Toast.LENGTH_SHORT).show()
    }

    override fun dismissLoadingDialog(){
        Toast.makeText(this, "dismiss loading", Toast.LENGTH_SHORT).show()
    }
}