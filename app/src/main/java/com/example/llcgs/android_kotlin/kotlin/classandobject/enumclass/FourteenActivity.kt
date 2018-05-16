package com.example.llcgs.android_kotlin.kotlin.classandobject.enumclass

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.classandobject.`object`.FifteenActivity
import com.example.llcgs.android_kotlin.kotlin.classandobject.enumclass.bean.User
import com.example.llcgs.android_kotlin.kotlin.classandobject.enumclass.presenter.impl.FourteenPresenter
import com.example.llcgs.android_kotlin.kotlin.classandobject.enumclass.view.FourteenView
import com.example.llcgs.android_kotlin.utils.log.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_fourteen.*

/**
 *  枚举类
 *    1.初始化
 *    2.匿名类
 *    3.使用枚举常量
 * */
class FourteenActivity : BaseActivity<FourteenView, FourteenPresenter>(), FourteenView {

    override fun createPresenter()= FourteenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourteen)

        RxView.clicks(button12).subscribe {
            startActivity(Intent().apply {
                setClass(this@FourteenActivity, FifteenActivity::class.java)
            })
        }

        // 在kotlin中enum是一个所谓的软关键字：只有当它出现在class前面时才有特殊的意义，在其他地方可以把它当作普通的名称使用

    // 枚举类
        // 枚举类的基本用法是实现类型安全的枚举
        // User.kt 一个枚举类
        val user = User.KOBE.user
        user?.logD()
        //User.KOBE.printAllValues<User>()

        // 初始化

    // 匿名类
        // 枚举常量也可以声明自己的匿名类以及相应的方法，覆盖的方法
        // 如果枚举类定义任何 成员，要使⽤分号将成员定义中的枚举常量定义分隔开，就像 在 Java 中⼀样


    // 使用枚举常量
        // 自kotlin1.1起，可以使用enumValues<T>() 和 enumValueOf<T>() 函数以泛型的⽅式访问枚举类中的常量
        // java中使用valueOf和values 访问枚举中的常量
        val values = com.example.llcgs.android_kotlin.kotlin.classandobject.enumclass.bean.ProtocolStates.values()
        val valueOf = com.example.llcgs.android_kotlin.kotlin.classandobject.enumclass.bean.ProtocolStates.valueOf("KOBE")


        User.KOBE.printAllValues<User>()
        // 使用when处理枚举类
        when(user){
            User.KOBE.user->{}
            User.ANTHDOY.user ->{}
            User.BOSH.user ->{}

        }

    }
}


