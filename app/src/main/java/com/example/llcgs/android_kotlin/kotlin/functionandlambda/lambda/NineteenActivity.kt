package com.example.llcgs.android_kotlin.kotlin.functionandlambda.lambda

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.functionandlambda.inline.TwentyActivity
import com.example.llcgs.android_kotlin.kotlin.functionandlambda.lambda.presenter.impl.NineteenPresenter
import com.example.llcgs.android_kotlin.kotlin.functionandlambda.lambda.view.NineteenView
import com.example.llcgs.android_kotlin.utils.log.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_nineteen.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock

/**
 *  高阶函数和lambda表达式
 *    1.高阶函数
 *      it，单个函数的隐式名称
 *      下划线用于未使用的变量
 *      在lambda表达式中结构
 *    2.内联函数
 *    3.lambda表达式和匿名函数
 *      函数类型
 *      lambda表达式语法
 *      匿名函数
 *      闭包
 *      带接收者的函数字面值
 *
 * **/

class NineteenActivity : BaseActivity<NineteenView, NineteenPresenter>(), NineteenView {

    override fun createPresenter()= NineteenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nineteen)

        RxView.clicks(button17).subscribe {
            startActivity(Intent(this@NineteenActivity, TwentyActivity::class.java))
        }

    // 高阶函数
        // 高阶函数是将函数用作参数或者返回值，这种函数的一个很好例子是lock(),它接受一个对象和一个函数，获取锁，运行函数并释放锁
        val lock: Lock = object : Lock {
            override fun tryLock(): Boolean {
                return false
            }

            override fun tryLock(time: Long, unit: TimeUnit?): Boolean {
                return false
            }

            override fun lockInterruptibly() {
            }

            override fun newCondition(): Condition? {
                return null
            }

            override fun lock() {
                "start lock".logD()
            }

            override fun unlock() {
                "start unlock".logD()
            }
        }
        fun getName() = "Kobe"
        // 调用高阶函数，当函数以参数的形式传递的时候，使用::函数名字 来调用
        // 用三种调用方式

        // 第一种形式的调用
        val lock1 = lock(lock, ::getName)
        lock1.forEach { it.logD() }

        // 第二种形式的调用
        // 使用lambda表达式调用
        // lambda 表达式总是被大括号括着，
        // 其参数（如果有的话）在 -> 之前声明（参数类型可以省略），
        // 函数体（如果存在的话）在 -> 后⾯
        val lock2 = lock(lock, { getName() })
        lock2.forEach { it.logD() }

        // 第三种形式的调用
        // 在 Kotlin 中有⼀个约定，如果函数的最后⼀个参数是⼀个函数，并且你传递⼀个 lambda 表达式作为相应的参数，你可以在圆括号之外指定它
        val lock3 = lock(lock) { getName() }
        lock3.forEach { it.logD() }

        // 入参应该是 transform: (T) -> R
        // 所以可以声明一个方法入参是lock3的泛型，出参是R
        val map = lock3.map({"value: $it"})
        map.forEach { it.logD() }

        // it:单个函数的隐士名称
        // 另⼀个有用的约定是，如果函数字面值只有⼀个参数，那么它的声明可以省略（连同 ->），其名称是 it
        val mutablelist = mutableListOf<String>("Kobe","James","Jodn","McGrady","Bosh","Answer","Durant")
        val intMutableList = mutableListOf<Int>(-4, -3, -2, -1, 0, 1, 2, 3, 4, 0)
        // 这些约定可以写LINQ-⻛格的代码
        mutablelist.filter { it.length ==4 }
                .sortedBy { it }
                .map { "four length: $it" }
                .forEach { it.logD() }

        // 下划线用于未使用的变量(⾃ 1.1起)
        // 如果 lambda 表达式的参数未使用，那么可以用下划线取代其名称

        // 在lambda表达式中结构(自1.1起)
        // 在 lambda 表达式中解构是作为解构声明的⼀部分描述的

        // 内联函数

    // lambda表达式和匿名函数
        // ⼀个 lambda 表达式或匿名函数是⼀个“函数字面值”，即⼀个未声明的函数，但立即做为表达式传递
        // 函数类型
        max("Kobe", "James"){
            str1, str2 ->  str1.length < str2.length
        }.logD()

        // lambda表达式语法
        // lambda 表达式总是被大括号括着，完整语法形式的参数声明放在括号内，并有可选的类型标注， 函数体跟在⼀个->符号之后。如果推断出的该lambda的返回类型不是Unit ，那么该lambda主体中的最后⼀个（或可能是单个）表达式会视为返回值
        val sum = {x:Int, y:Int -> x + y}
        // 所有可选标注都留下
        val sum1 :(Int, Int) ->Int =  {x, y -> x+y}
        // ⼀个 lambda 表达式只有⼀个参数是很常⻅的。 如果 Kotlin 可以自己计算出签名，它允许我们不声明唯⼀的参数，并且将隐含地为我们声明其名称为it
        mutablelist.forEach { it.logD() }
        // 我们可以使用限定的返回语法从lambda显式返回⼀个值。否则，将隐式返回最后⼀个表达式的值
        mutablelist.filter {
            val filter = it.length > 4
            return@filter  filter
        }
        // 等价于
        mutablelist.filter {
            val filter = it.length > 4
            filter
        }

        // 匿名函数
        // lambda 表达式语法缺少的⼀个东西是指定函数的返回类型的能力。在大多数情况下，这是不必要的。因为返回类型可以自动推断出来。然⽽，如果确实需要显式指定，可以使用另⼀种语法:匿名函数
        // 声明一个匿名函数(没有名臣)
        // 第一种
        fun(x: Int, y: Int): Int = x + y
        // 第二种
        fun(x: Int, y: Int):Int{
            return x+y
        }
        // 第三种
        fun(item: String) = item.length > 4

        val filter = mutablelist.filter(fun(it) = it.length > 4/*匿名函数的调用*/)
        filter.forEach { it.logD() }

        val findLast = intMutableList.findLast(fun(it) = it + 1 > 3)
        findLast?.logD()

        // 匿名函数的返回类型推断机制与正常函数⼀样：对于具有表达式函数体的匿名函数将自动推断返回类型，而具有代码块函数体的返回类型必须显式指定（或者已假定为 Unit ）
        // 请注意，匿名函数参数总是在/***/括号内传递/***/。 允许将函数 留在圆括号外的简写语法仅适⽤于 lambda 表达式

        // Lambda表达式和匿名函数之间的另⼀个区别是 非局部返回的行为。⼀个不带标签的return语句 总是在用 fun 关键字声明的函数中返回。这意味着
        // lambda 表达式中的 return 将从包含它的函数返回，而匿名函数中的 return 将从匿名函数自身返回

        // 匿名函数的声明和使用放在()代码块中。
        // 执行()代码块的时候 自动调用执行

        // -4 -3 -2 -1 0 1 2 3 4
        val takeLastWhile = intMutableList.takeLastWhile(
                // 匿名函数
                fun(it): Boolean {
                    it.logD()
                    val list = ArrayList<Int>()
                    if (it + 1 > 2) {
                        list.add(it)
                    }
                    return list.size < 2
                }
        )
        takeLastWhile.forEach { it.logD() }

        // 闭包
        // 函数、Lambda、if语句、for、when，都可以称之为闭包，但通常情况下，我们所说的闭包是 Lambda 表达式
        // 直接调用这个闭包 输出 5>3
        test

        // 自执行闭包
        // 自执行闭包就是在定义闭包的同时直接执行闭包，一般用于初始化上下文环境
        { x: Int, y: Int ->
            "${x + y}".logD()
        }(1, 3)

        // 带接收者的函数字面值
        // Kotlin提供了指定的接收者调用函数字面值的功能。在函数字面值的函数体中，可以调用该接收者对象上的方法而无需任何额外的限定符。这样的函数字面值的类型是一个带有接收者的函数类型
        // this 表示的是调用sums的Int值 it表示的是other
        val sums : Int.(other: Int) -> Int = {it + this}
        val sums1 = 1.sums(5456)
        sums1.logD()

        // 当接收者类型可以从上下文推断时，lambda 表达式可以用作带接收者的函数字面值
        html { // 带接收者的 lambda 由此开始
            body() // 调⽤该接收者对象的⼀个⽅法
        }


    }

    // 声明一个高阶函数
    fun <T> lock(lock: Lock, body:() -> T): List<T>{
        lock.lock()
        try {
            val list = ArrayList<T>()
            for (x in 1..10){
                list.add(body())
            }
            return list
        }finally {
            lock.unlock()
        }
    }
    // ⾼阶函数的另⼀个例子是 map() ：
    fun <T, R> List<T>.map(transform: (T) -> R): List<R> {
        val result = arrayListOf<R>()
        for (item in this)
            result.add(transform(item))
        return result
    }

    // 对于接受另一个函数作为参数的函数，我们必须为改参数指定函数类型。
    fun <T1, T2>max(str: T1, str1: T2, body:(T1, T2) -> Boolean): String{
        if (body(str, str1)){
            return "true" + str
        }
        return ""
    }

    // 声明一个闭包
    val test = if(true){
        "5>3".logD()
    }else{
        "5<3".logD()
    }

    fun html(init: HTML.() -> Unit): HTML {
        val html = HTML() // 创建接收者对象
        html.init() // 将该接收者对象传给该 lambda
        return html
    }

}

class HTML {
    fun body() {
        "创建HTML class".logD()
    }
}

