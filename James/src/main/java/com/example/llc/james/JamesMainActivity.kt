package com.example.llc.james

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.lzh.nonview.router.anno.RouterRule

@RouterRule("James")
class JamesMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.james_activity_main)
    }
}
