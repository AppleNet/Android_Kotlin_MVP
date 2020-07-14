package com.example.llc.kotlin.basic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseModuleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
    }

    abstract fun getLayoutId(): Int
}
