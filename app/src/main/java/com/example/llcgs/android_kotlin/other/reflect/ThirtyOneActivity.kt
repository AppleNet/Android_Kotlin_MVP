package com.example.llcgs.android_kotlin.other.reflect

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.other.reflect.bean.User
import com.example.llcgs.android_kotlin.other.reflect.presenter.impl.ThirtyOnePresenter
import com.example.llcgs.android_kotlin.other.reflect.view.ThirtyOneView
import com.gomejr.myf.core.kotlin.logD
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.memberProperties

/**
 *  反射 -- 一种在运行时动态的访问对象属性和方法的方式，不需要确定这些属性是什么
 *   1.类引用
 *   2.绑定的类引用
 *   3.函数引用
 *     示例-组合函数
 *   4.属性引用
 *     与java反射的互操作性
 *   5.构造函数引用
 *   6.绑定的函数和属性引用
 *
 * */
class ThirtyOneActivity : BaseActivity<ThirtyOneView, ThirtyOnePresenter>(), ThirtyOneView {

    override fun createPresenter()= ThirtyOnePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thirty_one)
        // 在kotlin中使用反射，有两种方式
           // 1. 标准的Java反射，定义在java.lang.reflect中
           // 2. 标准的kotlin反射，定义在kotlin.reflect中
        // Kotlin中反射API的主要入口就是KClass,它代表了一个类。

        // 反射是这样的⼀组语言和库功能，它允许在运行时自省你的程序的结构。 Kotlin让语言中的函数和属性做为⼀等公民、并对其自省（即在运行时获悉⼀个
        // 名称或者⼀个属性或函数的类型）与简单地使用函数式或响应式风格紧密相关

        // 在Java平台上，使用反射功能所需的运行时组件作为单独的JAR⽂件（kotlin-reflect.jar）分发。这样做是为了减少不使用反射功能的
        // 应用程序所需的运行时库的大小。如果你需要使用反射，请确保该.jar⽂件添加到项⽬的 classpath 中

        val user = User("Kobe", 32)
        // 获取的是java的
        val clazz = user.javaClass
        clazz.simpleName.logD()
        clazz.canonicalName.logD()
        clazz.classLoader.toString().logD()
        clazz.constructors.forEach { it.parameterTypes.forEach { it.canonicalName.logD() } }
        clazz.declaredFields.forEach { it.type.logD() }
        // 获取kotlin的
        val kClass = user.javaClass.kotlin
        kClass.simpleName?.logD()
        kClass.isSealed.logD()
        kClass.memberProperties.forEach { it.logD() }
        kClass.companionObject?.simpleName?.logD()

    // 类引用
        // 最基本的反射功能是获取 Kotlin 类的运行时引用。要获取对静态已知的Kotlin类的引用，可以使用 类字面值 语法
        // Kotlin类引用
        val thirtyOneActvity = ThirtyOneActivity::class
        thirtyOneActvity.simpleName?.logD()
        // 请注意，Kotlin 类引用与 Java 类引用不同。要获得Java类引用， 请在KClass实例上使⽤ .java 属性
        // Kotlin上Java引用
        val t = ThirtyOneActivity::class.java
        t.simpleName.logD()
        val th = ThirtyOneActivity.javaClass.kotlin
        th.companionObject?.simpleName?.logD()

    // 绑定的类引用(自1.1起)
        // 通过使用对象作为接收者，可以用相同的 ::class 语法获取指定对象的类的引用
        val view: View = TextView(this)
        assert(view is TextView){}

    // 函数引用
        // 我们可以把一个函数作为一个值传递。例如传给另一个函数，为此，我们使用::操作符
        fun isOdd(x:Int) = x % 2 != 0
        fun isOdd(s:String) = s =="brilling"

        val numbers = listOf(1,2,3)
        // 两种调用方式
        numbers.filter { isOdd(it) }.forEach { it.logD() }
        // 当上下文中已知函数期望的类型时，::可以用于重载函数
        numbers.filter(::isOdd).forEach { it.logD() }

        // 但是 下面这种情况不行，因为上下文中并不知道期望函数的类型，此时会报错
        // val o = ::isOdd
        // 改成下面这种方式就不会报错，因为指定了函数期望的类型
        val o: (String)->Boolean = ::isOdd
        // 如果一个具体类型的KFunction，它的形参类型和返回类型是确定的，那么应该优先使用这个具体类型的invoke方法
        val call = o.invoke("")
        call.logD()

        // 函数组合
        fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
            return { x -> f(g(x)) }
        }
        fun length(s:String) = s.length
        val oldLength = compose(::isOdd, ::length)
        val strings = listOf("a", "ab", "abc")
        strings.filter(oldLength).forEach { it.logD() }
    }

    companion object ThirtyOne{
        val id = 1
    }
}
