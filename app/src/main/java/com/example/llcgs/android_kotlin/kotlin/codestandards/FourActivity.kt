package com.example.llcgs.android_kotlin.kotlin.codestandards

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.classandobject.classandextends.FiveActivity
import com.example.llcgs.android_kotlin.kotlin.codestandards.bean.Dot
import com.example.llcgs.android_kotlin.kotlin.codestandards.presenter.impl.FourPresenter
import com.example.llcgs.android_kotlin.kotlin.codestandards.view.FourView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_four.*

/**
 * 编码规范
 *  1.命名风格
 *  2.冒号
 *  3.lambda表达式
 *  4.类头格式化
 *  5.Unit
 *  6.函数还是属性
 * **/

class FourActivity : BaseActivity<FourView, FourPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_four)

        RxView.clicks(button2).subscribe{
            startActivity(Intent(this@FourActivity, FiveActivity::class.java))
        }

        // 冒号
        // 类型和超类型之间的冒号前要有⼀个空格，⽽实例和类型之间的冒号前不要有空格
        var f = object : Foo<String>{
            override fun foo(a: Int): String {
                return a.toString()
            }
        }

    }

    // 单表达式
    override fun createPresenter() = FourPresenter()

    // Unit
    // 如果函数返回 Unit 类型，该返回类型应该省略
    fun foo(){

    }
}


interface Foo<out T: Any> : Dot {

    fun foo(a: Int): T
}