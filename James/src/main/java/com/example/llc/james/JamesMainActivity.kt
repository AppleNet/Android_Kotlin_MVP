package com.example.llc.james

import android.os.Bundle
import com.lzh.nonview.router.anno.RouterRule

@RouterRule("James")
class JamesMainActivity : JamesBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.james_activity_main)
    }
}
