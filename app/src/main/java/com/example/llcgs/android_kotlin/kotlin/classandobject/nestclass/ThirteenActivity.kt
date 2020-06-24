package com.example.llcgs.android_kotlin.kotlin.classandobject.nestclass

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.classandobject.enumclass.FourteenActivity
import com.example.llcgs.android_kotlin.kotlin.classandobject.nestclass.presenter.impl.ThirteenPresenter
import com.example.llcgs.android_kotlin.kotlin.classandobject.nestclass.view.ThirteenView
import com.example.llcgs.android_kotlin.utils.log.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_thirteen.*

/**
 *  嵌套类
 *    1. 内部类
 *    2. 内部匿名类
 * */
class ThirteenActivity : BaseActivity<ThirteenView, ThirteenPresenter>(), ThirteenView {

    // 声明外部类成员
    var nameOuter: String? = "NBA"
    var ageOuter: String? = "50"

    override fun createPresenter()= ThirteenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thirteen)

        RxView.clicks(button11).subscribe {
            startActivity(Intent(this@ThirteenActivity, FourteenActivity::class.java))
        }.addDisposable(compositeDisposable)

        // 嵌套类
        // 在kotlin中 类也可以嵌套在其他类中。

        // 嵌套类的 调用方式
        val th = ThirteenActivity.ThirteenActivitySub().onCreate(Bundle())
        th.logD()

        // 内部类的 调用方式
        val inn = ThirteenActivity().ThieteenActivityInner().getNameOuter()
        inn?.logD()

        val button = com.example.llcgs.android_kotlin.kotlin.classandobject.nestclass.bean.Button()
        val currentState = button.currentState as com.example.llcgs.android_kotlin.kotlin.classandobject.nestclass.bean.Button.ButtonState
        currentState.name.logD()
    }

    // TODO 嵌套类不持有外部类的引用，内部类使用inner修饰后 就会持有外部类的引用。

    // 嵌套类

    // 内部类
        // kotlin中声明一个内部类 使用inner关键字，声明内部类 是为了可以访问外部类的成员

    // 匿名内部类
       // kotlin中声明一个匿名内部类 使用对象表达式 object关键字

    // 声明一个嵌套在ThirteenActivity这个类中的一个类
    class ThirteenActivitySub{
        fun onCreate(savedInstanceState: Bundle?){

        }
        // TODO 这个类不能访问外部类的实例
        // TODO 没有使用inner关键字 不能访问外部的成员变量
        //fun getNameOuter() = nameOuter

    }

    // 声明一个内部类
    inner class ThieteenActivityInner{
        // 在内部类中 访问外部成员变量
        fun getNameOuter() = nameOuter

        fun getAgeOuter() = ageOuter
    }

    // 声明一个匿名内部类
    val nam = object : MyAnonymousInnerClass{
        override fun getAnonymous() {

        }
    }

}

interface MyAnonymousInnerClass{
    fun getAnonymous()
}