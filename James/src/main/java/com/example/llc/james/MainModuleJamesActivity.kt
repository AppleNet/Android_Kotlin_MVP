package com.example.llc.james

import android.os.Bundle
import com.example.llc.annotation.BindPath
import com.lzh.nonview.router.Router
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.james_activity_main.*

/**
 * com.example.llc.james.MainModuleJamesActivity
 * @author liulongchao
 * @since 2017/7/26
 *
 * 处理AppCompatActivity
 */
@RouterRule("James/james")
@BindPath("James/james")
class MainModuleJamesActivity : BaseModuleJamesActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title_template.setOnClickListener {
            Router.create("module://Login/login").activityRoute.open(this)
        }
    }

    override fun getLayoutId(): Int = R.layout.james_activity_main
}
