package com.example.llcgs.android_kotlin.classandobject.propertyandfield

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.propertyandfield.bean.NBAGS
import com.example.llcgs.android_kotlin.classandobject.propertyandfield.presenter.SixPresenter
import com.example.llcgs.android_kotlin.classandobject.propertyandfield.view.SixView
import com.gomejr.myf.core.kotlin.logD

/**
 * 属性和字段
 *   1.声明属性
 *   2.Getters和Setters
 *      2.1.幕后字段
 *      2.2.幕后属性
 *   3.编译期常量
 *   4.惰性初始化属性
 *   5.覆盖属性
 *   6.委托属性
 *
 * */
const val TOP: String = "top"
class SixActivity : BaseActivity<SixView, SixPresenter>() {

    lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_six)

        // 声明属性
        var kobe: String? = null

        // Getters and Setters
        val gs = NBAGS()
        gs.hobby.logD()

        // 编译期常量
        // 使用const 修饰符标记为 编译器常量
        // 1.位于顶层或者是object的一个成员
        // 2.用String或者原生类型初始化，也就是说 对象不能声明编译期常量
        // 3.没有自定义getter

        // 位于顶层的编译期常量
        TOP.logD()
        // object的一个成员编译期常量
        PATH.logD()

        // 惰性初始化属性 使用lateinit关键字 进行属性的惰性初始化 可以修饰对象属性
        // 使用lateinit关键字的声明的属性 不能声明为局部的，不能识别.该修饰符只能⽤于在类体中（不是在主构造函数中）声明的 var 属性
        // lateinit修饰的属性 没有自定义get和set方法
        name = "McGrady"
        name.logD()

        // 覆盖属性 override关键字 同继承中覆盖属性 已经讲解

        // 委托属性


    }

    override fun createPresenter() = SixPresenter()

    companion object {
        const val PATH:String = "object_path"
    }
}
