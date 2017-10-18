package com.example.llcgs.android_kotlin.other.reflect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.other.reflect.bean.User
import com.example.llcgs.android_kotlin.other.reflect.presenter.impl.ThirtyOnePresenter
import com.example.llcgs.android_kotlin.other.reflect.view.ThirtyOneView
import com.example.llcgs.android_kotlin.stringandregex.ThirtyTwoActivity
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_thirty_one.*
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaGetter

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

    var x = 1
    override fun createPresenter()= ThirtyOnePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thirty_one)

        RxView.clicks(button29).subscribe {
            startActivity(Intent(this, ThirtyTwoActivity::class.java))
        }

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

        // 属性引用
        // 要把属性作为Kotlin中的一等对象来访问，我们也可以使⽤ :: 运算符：
        ThirtyOneActivity::x.get(this).logD()
        ThirtyOneActivity::x.set(this, 2)
        x.logD()
        // 表达式 ::x 求值为KProperty<Int>类型的属性对象，它允许我们使用get()读取它的值，或者使用name属性来获取属性名
        // 对于可变属性，例如var y = 1，::y 返回 KMutableProperty<Int> 类型的⼀个值， 该类型有⼀个 set() 方法
        // 属性引用可以用在不需要参数的函数处
        val strs = listOf("1","12","123")
        strs.map(String::length).forEach { it.logD() }
        strs.map(String::toInt).forEach { it.logD() }
        // 等价于 strs.map(String::toInt).forEach { it.logD() }
        strs.map { it.toInt() }.forEach { it.logD() }

        // 要访问属于类的成员的属性，我们这样限定它
        val prop = A::p
        prop.get(A(1)).logD()

        // 对于扩展属性
        //String::lastChar.get("Kobe").logD()

        // 与 Java反射的互操作性
        // 在Java平台上，标准库包含反射类的扩展，它提供了与Java反射对象之间映射（参⻅ kotlin.reflect.jvm 包）。 例如，要查找⼀个用作Kotlin属性
        // getter的幕后字段或Java⽅法，可以这样写
        A::p.javaGetter.toString().logD() // 输出 "public final int A.getP()"
        A::p.javaField.toString().logD() // 输出 "private final int A.p"
        A::p.javaClass.name.logD()

        // 要获得对应于 Java 类的 Kotlin 类，请使⽤ .kotlin 扩展属性
        fun getKClass(o:Any):KClass<Any> = o.javaClass.kotlin
        getKClass("Kobe").isFinal.logD()

        // 构造函数引用
        // 构造函数可以像方法和属性那样引用。他们可以用于期待这样的函数类型对象的任何地方：它与该构造函数接受相同参数并且返回相应类型的对象。通过
        // 使⽤ :: 操作符并添加类名来引用构造函数。考虑下面的函数， 它期待⼀个无参并返回Foo类型的函数参数

        fun func(factory:() ->Foo){
            val x :Foo = factory()
            x.logD()
        }
        // 反射Foo的构造函数
        // 使⽤ ::Foo ，类 Foo 的零参数构造函数，我们可以这样简单地调用它：
        func(::Foo)

        // 绑定的函数与属性引⽤（⾃ 1.1 起）
        // 你可以引用特定对象的实例方法


    }

    // 声明一个扩展属性
    val String.lastChar: Char
        get() = this[length - 1]

    companion object ThirtyOne{
        val id = 1
    }

    class A(val p:Int)

    class Foo
}
