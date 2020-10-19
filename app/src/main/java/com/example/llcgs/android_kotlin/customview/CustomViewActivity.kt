package com.example.llcgs.android_kotlin.customview

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.customview.presenter.impl.CustomViewPresenter
import com.example.llcgs.android_kotlin.customview.view.CustomViewView
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_custom_view.*

/**
 * com.example.llcgs.android_kotlin.customview.CustomViewActivity
 * @author liulongchao
 * @since 2018/11/10
 */
@RouterRule("CustomView")
class CustomViewActivity: BaseActivity<CustomViewView, CustomViewPresenter>() {

    override fun createPresenter()= CustomViewPresenter()

    /*
    *
    *   format 可选项
        "reference" //引用
        "color" //颜色
        "boolean" //布尔值
        "dimension" //尺寸值
        "float" //浮点值
        "integer" //整型值
        "string" //字符串
        "fraction" //百分数,比如 200%
        枚举值，格式如下：
        <attr name="orientation">
            <enum name="horizontal" value="0" />
            <enum name="vertical" value="1" />
        </attr>

        < attr name="windowSoftInputMode">
            < flag name = "stateUnspecified" value = "0" />
            < flag name = "stateUnchanged" value = "1" />
            < flag name = "stateHidden" value = "2" />
            < flag name = "stateAlwaysHidden" value = "3" />
            < flag name = "stateVisible" value = "4" />
            < flag name = "stateAlwaysVisible" value = "5" />
            < flag name = "adjustUnspecified" value = "0x00" />
            < flag name = "adjustResize" value = "0x10" />
            < flag name = "adjustPan" value = "0x20" />
            < flag name = "adjustNothing" value = "0x30" />
        < /attr>
        xml 中使用时：android:windowSoftInputMode = "stateUnspecified | stateUnchanged | stateHidden">
    *
    *
    *
    * */

    @SuppressLint("ObjectAnimatorBinding")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view)

        myCustomView.setOnClickListener {
            startActivity(Intent(this, FlowLayoutActivity::class.java))

            ObjectAnimator.ofFloat(this, "percent", 0f, 1f).setDuration(2500).start()
        }
    }
}