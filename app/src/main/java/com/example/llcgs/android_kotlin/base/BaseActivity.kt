package com.example.llcgs.android_kotlin.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * com.example.llcgs.android_kotlin.base.BaseActivity
 * @author liulongchao
 * @since 2017/5/18
 */


abstract class BaseActivity<V, P:BasePresenter<V>> : AppCompatActivity() {

    lateinit var mPresenter : P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter = createPresenter()
    }

    abstract fun createPresenter():P

    fun showLoadingDialog(){
       Toast.makeText(this, "show loading", Toast.LENGTH_SHORT).show()
    }

    fun dismissLoadingDialog(){
        Toast.makeText(this, "dismiss loading", Toast.LENGTH_SHORT).show()
    }
}