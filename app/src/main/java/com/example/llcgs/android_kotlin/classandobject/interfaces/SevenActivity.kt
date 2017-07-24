package com.example.llcgs.android_kotlin.classandobject.interfaces

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.interfaces.presenter.impl.SevenPresenter
import com.example.llcgs.android_kotlin.classandobject.interfaces.view.SevenView
import com.example.llcgs.android_kotlin.classandobject.modifier.EightActivity
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_seven.*

/**
 * 接口
 *  1.实现接口
 *  2.接口中的属性
 *  3.解决覆盖冲突
 *
 * **/

class SevenActivity : BaseActivity<SevenView, SevenPresenter>() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seven)

        RxView.clicks(button5).subscribe {
            startActivity(Intent(this@SevenActivity, EightActivity::class.java))
        }

        // 接口定义
        // 使用interface关键字定义接口
        // Kotlin 的接⼝与 Java 8 类似，既包含抽象⽅法的声明，也包含实现。与抽象类不同的是，接⼝⽆法保存状态。它可以有 属性但必须声明为抽象或提供访问器实现
        var sevenInterface = object : SevenInterface{
            override fun one(): String {
                return "Kobe"
            }
        }
        sevenInterface.name = "James"
        sevenInterface.name.logD()
        sevenInterface.one().logD()
        sevenInterface.two().logD()

        // 实现接口
        // 1.声明一个类实现这个接口
        val sub = SubSeven()
        // 2.对象表达式 接收这个接口
        object : SevenInterface{
            override fun one(): String {
                return "Kobe"
            }
        }

        // 接口中的属性
        sevenInterface.name = "James"
        sevenInterface.name.logD()

        // 解决覆盖冲突


    }

    override fun createPresenter()= SevenPresenter()
}

// 声明一个接口
interface SevenInterface{
    // 可以定义抽象方法和具体实现方法

    // 抽象方法
    fun one() : String
    // 具体实现
    fun two(): String{
        return "Wade"
    }

    // 还可以定义属性
    // 接口中定义属性 不需要初始值
    // 这个属性是抽象的
    // 可以自定义getter setter
    // 接口中不能有幕后字段 否则编译期报 property in an interface cannot have a backing field
    var name: String
        get() {
            return name
        }
        set(value) {
            name = value
        }

}

// 接口的实现
class SubSeven: SevenInterface{

    override fun one(): String {
        return ""
    }
}