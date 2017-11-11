package com.example.llcgs.android_kotlin.kotlin.other.structdeclarations

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.other.gather.TwentyTwoActivity
import com.example.llcgs.android_kotlin.kotlin.other.structdeclarations.bean.Person
import com.example.llcgs.android_kotlin.kotlin.other.structdeclarations.bean.User
import com.example.llcgs.android_kotlin.kotlin.other.structdeclarations.presenter.impl.TwentyOnePresenter
import com.example.llcgs.android_kotlin.kotlin.other.structdeclarations.view.TwentyOneView
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_twenty_one.*

/**
 *  解构声明
 *     1.从函数中返回两个变量
 *     2.解构声明和映射
 *     3.下划线用于未使用变量
 *     4.lambda表达式中结构
 * */
class TwentyOneActivity : BaseActivity<TwentyOneView, TwentyOnePresenter>(), TwentyOneView {

    val userList = ArrayList<User>()

    override fun createPresenter()= TwentyOnePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty_one)

        RxView.clicks(button19).subscribe {
            val intent = Intent(this@TwentyOneActivity, TwentyTwoActivity::class.java)
            startActivity(intent)
        }

    // 解构声明
        // 把一个对象解构成很多变量
        val person = Person("Wade","34")
        // 对象解构
        // 将person中的name值赋值给声明的name
        // 将person中的age值赋值给声明的age
        val(name, age) = person
        name.logD()
        age.logD()
        // 解构声明会被编译成一下代码
        val myname = person.component1()
        val myage = person.component2()
        myname.logD()
        myage.logD()

        // 普通类 没有自动生成component**()方法，所以普通类对象不能解构
        // 要想让普通类使用解构声明，需要自己实现component**()方法，component**()需要使用operator
        val user = User("James", "32")
        val(userName, userAge) = user
        userName.logD()
        userAge.logD()

        // 解构声明可以用在fot循环中
        userList.add(User("James", "32"))
        userList.add(User("Kobe", "32"))
        userList.add(User("Wade", "32"))
        userList.add(User("Anthdoy", "32"))
        userList.add(User("Nash", "32"))
        userList.add(User("jordan", "50"))
        // name的值取自集合中的元素上调用component1的返回值
        for((name) in userList){
            name.logD()
        }
        // 解构声明可以用在map循环中
        val map = mapOf(1 to "James", 2 to "Kobe", 3 to "wade", 4 to "Anthdoy", 5 to "Nash", 6 to "Jodn")
        for ((k, v) in map) {
            k.logD()
            v.logD()
        }

        // 从函数中返回两个变量
        val(aa,bb) = getPerson("Kobe", "36")
        aa.logD()
        bb.logD()

        // 下划线用于未使用的变量(⾃1.1起)
        // 如果在解构声明中你不需要某个变量，那么可以⽤下划线取代其名称
        val (_, cc) = getPerson("Bosh","32")
        cc.logD()

        // 在lambda表达式中解构
        // 你可以对 lambda 表达式参数使⽤解构声明语法。 如果lambda表达式具有Pair类型（或者Map.Entry或任何其他具有相应componentN函数的类型）的参数，
        // 那么可以通过将它们放在括号中来引入多个新参数来取代单个新参数
        // mapValues 是将map中所有的value替换
        val mapValues = map.mapValues { entry -> " ${"NBA star: " + entry.value} " }
        val entries = mapValues.entries
        entries.forEach {
            it.key.logD()
            it.value.logD()
        }
        // mapKeys是将map中所有的key替换
        val mapKeys = map.mapKeys { entry -> "NBA index: ${entry.key}" }
        val keys = mapKeys.keys
        keys.forEach {
            it.logD()
            mapKeys.get(it)?.logD()
        }
        // 等价于 mapValues
        // 如果解构的参数中的⼀个组件未使⽤，那么可以将其替换为下划线，以避免编造其名称
        val mapValu = map.mapValues { (k,v) -> " ${"NBA star: " + v}" }
        val mapValue = map.mapValues { (_, v) -> " ${"NBA star: " + v} " }

        // 注意声明两个参数和声明⼀个解构对来取代单个参数之间的区别
        // { a //-> …… } // ⼀个参数
        // { a, b //-> …… } // 两个参数
        // { (a, b) //-> …… } // ⼀个解构对
        // { (a, b), c //-> …… } // ⼀个解构对以及其他参数

        // 解构声明中可以指定参数类型
        // 整体指定类型
        map.mapValues { entry: Map.Entry<Int, String> -> "NBA star: " + entry.value }
        // 单个指定类型
        map.mapValues { (k: Int, v: String)-> "" }

    }

    // 从函数中返回两个变量
    fun getPerson(str: String, length:String): Person{
        val person = Person("Wade","34")
        val(name, age) = person
        if (name.equals(str, false) && age == length){
            return Person(name, age)
        }else{
            return Person(str, length)
        }
    }
}
