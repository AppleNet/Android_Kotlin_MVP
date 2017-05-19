package com.example.llcgs.android_kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.llcgs.android_kotlin.bean.User
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var intent = intent
        var id = intent.getStringExtra("id")
        var user = intent.getParcelableExtra<User>("user");
        textView.setText(id+ "name: " +user.name + ",pwd:" + user.pwd)

    }
}
