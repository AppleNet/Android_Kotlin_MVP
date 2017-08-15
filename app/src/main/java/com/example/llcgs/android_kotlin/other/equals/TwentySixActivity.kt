package com.example.llcgs.android_kotlin.other.equals

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.other.equals.bean.Equals
import com.example.llcgs.android_kotlin.other.equals.presenter.impl.TwentySixPresenter
import com.example.llcgs.android_kotlin.other.equals.view.TwentySixView
import com.example.llcgs.android_kotlin.other.operator.TwentySevenActivity
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_twenty_six.*

/**
 * 相等性
 *  1.引用相等
 *  2.结构相等
 * */
class TwentySixActivity : BaseActivity<TwentySixView, TwentySixPresenter>(), TwentySixView {


    override fun createPresenter()= TwentySixPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty_six)

        RxView.clicks(button24).subscribe {
            startActivity(Intent(this@TwentySixActivity, TwentySevenActivity::class.java))
        }

    // 相等性
        // 引用相等
        // 引用相等由 ===（以及其否定形式 !== ）操作判断。a === b 当且仅当 a和b 指向同⼀个对象时求值为 true

        val e = Equals(a = 1, b = 2, c = 3)
        val m = Equals(a = 1, b = 2, c = 3)
        val n = e
        // e和m不是指向的同一个对象，所以返回为false
        (e === m).logD()
        // e和n指向的同一个对象，返回true
        (e === n).logD()


        // 结构相等
        // 结构相等由==（以及其否定形式!=）操作判断。按照惯例a==b这样的表达式会翻译成 a?.equals(b) ?: (b === null)
        // 也就是说如果 a 不是 null 则调用 equals(Any?) 函数，否则（即 a 是 null ）检查 b 是否与 null 引⽤相等。
        // 请注意，当与 null 显式比较时完全没必要优化你的代码：a == null 会被自动转换为 a=== null


    }
}
