package com.example.llcgs.android_kotlin.kotlin.other.thisexperssion

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.other.equals.TwentySixActivity
import com.example.llcgs.android_kotlin.kotlin.other.thisexperssion.presenter.impl.TwentyFivePresenter
import com.example.llcgs.android_kotlin.kotlin.other.thisexperssion.view.TwentyFiveView
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_twenty_five.*

/**
 *  this表达式
 *    1. 限定的this
 * */

class TwentyFiveActivity : BaseActivity<TwentyFiveView, TwentyFivePresenter>() {// 隐式标签 this@TwentyFiveActivity


    override fun createPresenter()= TwentyFivePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty_five)

        RxView.clicks(button23).subscribe {
            startActivity(Intent(this@TwentyFiveActivity, TwentySixActivity::class.java))
        }

    // This表达式
        // 为了表⽰当前的  接收者 我们使⽤ this 表达式
        // 在类成员中，this指的是该类的当前对象
        // 在扩展函数或者贷接受者的函数字面值中，this表示在点左侧传递的接收者参数
        // 如果this没有限定符，它指的是内层的包含它的作用域。要引用其他作用域中的this，请使用标签限定符

        // 限定的this
        // 要访问来⾃外部作⽤域的this（⼀个类或者扩展函数，或者带标签的带接收者的函数字面值）我们使⽤ this@label ，其中 @label 是⼀个代指this来源的标签

        val string = StringBuilder()
        with(string){
            for (letter in 'A'..'Z'){
                this.append(letter+"\n")
            }
            append("\n Now I know this alphabet")
            this.toString()
            return@with // 可以省略掉
        }
        string.logD()

    }

    inner class B{ // this@B 隐式标签
        fun Int.foo(){
            val a = this@TwentyFiveActivity
            val b = this@B

            val c = this // foo()的接收者，一个Int
            val c1 = this@foo // foo()的接收者，一个Int

            val funList = lambda@ fun String.(){
                val d = this // funLit 的接收者
            }

            val funLit2 = { s: String ->
                // foo() 的接收者，因为它包含的 lambda 表达式
                // 没有任何接收者
                val d1 = this
            }
        }
    }
}
