package com.example.llcgs.android_kotlin.kotlin.stringandregex

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.stringandregex.bean.User
import com.example.llcgs.android_kotlin.kotlin.stringandregex.model.ThirtyTwoModel
import com.example.llcgs.android_kotlin.kotlin.stringandregex.model.ThirtyTwoModelJava
import com.example.llcgs.android_kotlin.kotlin.stringandregex.presenter.impl.ThirtyTwoPresenter
import com.example.llcgs.android_kotlin.kotlin.stringandregex.view.ThirtyTwoView
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_thirty_two.*

/**
 * com.example.llcgs.android_kotlin.stringandregex.ThirtyTwoActivity
 * @author liulongchao
 * @since 2017/10/16
 */

/**
 *  字符串和正则表达式处理
 *
 *   分割字符串
 *   正则表达式和三重引号的字符串
 *   多行三重引号字符串
 *
 * */
class ThirtyTwoActivity: BaseActivity<ThirtyTwoView, ThirtyTwoPresenter>() {

    override fun createPresenter()= ThirtyTwoPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thirty_two)

        RxView.clicks(button32).subscribe {
        }

        // Java中的split不适用于一个点号，当代码写成“12.345-6.A”.split(".")的时候，返回的是一个空数组
        val thirty = ThirtyTwoModelJava()
        // 等到的是一个空数组，长度为0。这是因为它将一个正则表达式作为参数，并根据表达式将字符串分割成多个字符串。这里点号是表示任何字符的正则表达式。
        thirty.getSplit()

        // TODO Kotlin提供了一些名为split的，具有不同参数的重载的扩展函数。用来承载正则表达式的值需要一个Regex类型，而不是String类型。
        // TODO 这样确保了当有一个字符串传递给函数的时候，不会被当作正则表达式。
        // Kotlin语法实现
        "12.345-6.A".split("\\.|-".toRegex()).logD()
        "12.345-6.A".split(".".toRegex()).logD()
        // Kotlin将这个坑给填埋了，这样写 可以得到一个不空的数组
        "12.345-6.A".split(".").logD()

        // 正则表达式和三重引号的字符串
        // kotlin中使用String的扩展函数来解析文件路径
        val str = "/Users/yole/kotlin-book/chapter.adoc"
        val dir = str.substringBeforeLast("/")
        dir.logD() // /Users/yole/kotlin-book
        val full = str.substringAfterLast("/")
        full.logD() // chapter.adoc
        val filename = full.substringBeforeLast(".")
        filename.logD() // chapter
        val extension = full.substringAfterLast(".")
        extension.logD() // adoc

        // 多行三重引号字符串
        // 三重引号字符串的目的，不仅在于避免转义字符，而且使它可以包含任何字符，包括换行符。另外，它提供了一种更简单的方法，从而可以简单地把包含换行符的文本嵌入到程序中。
        val kotlinLogo =
                """| //
                  .|//
                  .|// \"""
        kotlinLogo.trimMargin(".").logD()

        val dire = """C:\\Users\\yole\\kotlin-book"""
        dire.logD()
        val dires = "C:\\Users\\yole\\kotlin-book"
        dires.logD()
        val price = """${'$'} 99.9"""
        price.logD()
        val price1 = "${'$'} 99.9"
        price1.logD()
        "$99.9".logD()

        // 局部函数和扩展
        val thirtyModel = ThirtyTwoModel()
        val user = User(0, "1", "2")
        thirtyModel.save(user)
        thirtyModel.saveUser(user)
    }

}