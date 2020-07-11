package com.example.llc.james

import android.os.Bundle
import com.example.llc.annotation.BindPath
import com.lzh.nonview.router.anno.RouterRule

/**
 * com.example.llc.james.JamesMainActivity
 * @author liulongchao
 * @since 2017/7/26
 *
 * 处理AppCompatActivity
 */
@RouterRule("James")
@BindPath("James/james")
class JamesMainActivity : JamesBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int = R.layout.james_activity_main
}
