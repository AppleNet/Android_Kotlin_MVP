package com.example.llc.james

import android.os.Bundle
import com.lzh.nonview.router.anno.RouterRule

/**
 * com.example.llc.james.JamesMainActivity
 * @author liulongchao
 * @since 2017/7/26
 *
 * 处理AppCompatActivity
 */
@RouterRule("James")
class JamesMainActivity : JamesBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int = R.layout.james_activity_main
}
