package com.example.llcgs.android_kotlin.idiom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.codestandards.FourActivity
import com.example.llcgs.android_kotlin.idiom.bean.MySingleton
import com.example.llcgs.android_kotlin.idiom.bean.NBA
import com.example.llcgs.android_kotlin.idiom.bean.NBAWith
import com.example.llcgs.android_kotlin.idiom.kt.prefix
import com.example.llcgs.android_kotlin.idiom.presenter.impl.ThirdPresenter
import com.example.llcgs.android_kotlin.idiom.view.ThirdView
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_third.*

/**
 * 习惯用法
 *   1.创建DTOs（POJOs/POCOs）
 *   2.函数默认参数
 *   3.过滤list
 *   4.string内插
 *   5.类型判断
 *   6.遍历map/pair型list
 *   7.使用区间
 *   8.只读list
 *   9.只读map
 *   10.访问map
 *   11.延迟属性
 *   12.拓展函数
 *   13.创建单例
 *   14.if not null缩写
 *   15.if not null and else 缩写
 *   16.if null执行一个语句
 *   17.if not null执行一个代码块
 *   18.返回when表达式
 *   19.try/catch表达式
 *   20.if表达式
 *   21.返回类型为 Unit  的⽅法的 Builder  ⻛格⽤法
 *   22.单表达式函数
 *   23.对⼀个对象实例调⽤多个⽅法  （with）
 *   24.Java 7  的 try with resources
 *   25.对于需要泛型信息的泛型函数的适宜形式
 *   26.使⽤可空布尔
 * */

class ThirdActivity : BaseActivity<ThirdView, ThirdPresenter>() {

    // 只读list
    val thirdList = listOf("Kobe","James","Jodn","McGrady","Bosh","Answer","Durant")
    // 只读map
    val thirdMap = mapOf(
            1 to "kobe",
            2 to "James",
            3 to "Jodn",
            4 to "McGrady",
            5 to "Bosh",
            6 to "Answer",
            7 to "Durant"
    )
    // pair型list
    var list = ArrayList<Pair<Int, String>>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        RxView.clicks(textView3).subscribe{
            startActivity(Intent(this@ThirdActivity, FourActivity::class.java))
        }

    // 创建DTOs（POJOs/POCOs）数据类
        val nba: NBA = NBA("Kobe", 32)
        nba.toString().logD()
        nba.hashCode().logD()
        val nba2 = NBA("McGrady",34)
        nba.equals(nba2).logD()

    // 函数默认参数
        // 函数在调用的时候 可以不用传递参数进去。
        getThird().logD()
        getThird("Kobe", 32).logD()

    // 过滤list
        // 使用filter关键字 其实是将需要的信息筛选出来
        thirdList.filter { x -> x.equals("Kobe") }.logD()
        // 或者可以简写为
        thirdList.filter { it.equals("mcgrady", true) }.logD()

    // String内插 // 使用${} 符号进行

    // 类型判断 使用 is 关键字
        var x = null
        when(x){
            is Int -> { Log.d("MainActivity", "flag: " + (x is Int)) }
            !is String -> {Log.d("MainActivity", "flag: " + (x is String))}
        }

    // 遍历map/pais型list
        for (entry in thirdMap) {
            entry.key.logD()
            entry.value.logD()
        }

        //thirdMap.forEach { t, u ->  }

        for ((k, v) in thirdMap){
            k.logD()
            v.logD()
            // pair型list
            list.add(Pair<Int, String>(k, v))
        }
        // 遍历pair型list
        for (pair in list) {
            pair.first.logD()
            pair.second.logD()
        }

    // 使用区间
    // 只读list thirdList
    // 只读map thirdMap

    // 访问map
        thirdMap[1]?.logD()

    // 延迟属性
        // 通过使用关键字 lazy 延迟初始化
        val hobby: String by lazy { "basketball" }
        hobby.logD()

    // 拓展函数 kotlin中 任何类型 都可以自定义一些函数，供这个类型的实例调用
        // demo StringExtensin.kt 声明了一个String的拓展方法
        // 任何字符串都可以调用自定义的prefix函数
        val prefix = "NBAKobe".prefix(2)
        prefix.logD()

    // 创建单例 使用object修饰的类 kotlin模式实现构造方法私有化的holder类单例模式
        // MySingleton 就是object修饰的类 默认实现了单例模式，直接通过类名.属性名调用相应的属性
        MySingleton.name = "James"
        MySingleton.name?.logD()

    // if not null 缩写
        // kotlin中使用?来表示为空判断
        if (MySingleton.name != null){
            Log.d("MainActivity", MySingleton.name);
        }
        // 上面的表达式等价于
        MySingleton.name?.logD()

    // if not null and else 缩写
        // kotlin中使用?:来表示if...else...
        if (MySingleton.name != null){
            Log.d("MainActivity", MySingleton.name)
        }else{
            Log.d("MainActivity", "MySingleton.name == null")
        }
        // 上面的表达式等价于
        MySingleton.name?: "Jodn".logD()

    // if not null执行一个代码块
        // 在kotlin中使用let{...}代码块来表示 当MySingleton.name不为null的时候 执行let{}代码块中的操作
        MySingleton.name.let {
            MySingleton.name = "NBA where amazing happens"
        }

    // 返回when表达式
        // 一般是在方法中 返回一个when表达式
        // transform方法返回的就是一个使用when的表达式
        transform("Black").logD()

    // try/catch表达式

    // if表达式

    // 返回类型为Unit的方法的Builder风格用法
        // apply{} 为Builder风格用法，即返回它自己。 Unit是类似java中的void 无返回值类型
        arrayOfMinusOnes(2)[0].logD()

    // 单表达式函数
        // 类似可以直接给函数赋值
        getInt().logD()
        // 等价于 fun getInteger() : Int{ return 2}

    // 对一个对象实例调用多个方法(with) 表达式
        // 使用with表达式 可以对一个实例 实现方法的多个调用
        val nbaWith = NBAWith()
        with(nbaWith){
            // 可以执行一些其他方法
            nbaWith.name = getThird("Answer")
        }
        nbaWith.name.logD()

    // 对于需要泛型信息的泛型函数的适宜形式



    }

    override fun createPresenter() = ThirdPresenter()

    fun getThird(name: String="", age:Int=0): String{
        return "name: ${name}, age: ${age}"
    }

    // 返回when表达式
    fun transform(color: String): Int{
        return when(color){
            "Red".toLowerCase() -> 0
            "Green".toLowerCase() -> 1
            "Blue".toLowerCase() -> 2
            else -> -1
        }
    }

    // 返回类型为Unit的方法的Builder风格用法
    // apply{} 为Builder风格用法，即返回它自己。 Unit是类似java中的void 无返回值类型
    fun arrayOfMinusOnes(size: Int): IntArray {
        return IntArray(size).apply { fill(-1) }
    }

    // 单函数表达式
    fun getInt() = 2
    // 单函数表达式
    fun transformColor(color: String) = when(color){
        "Red".toLowerCase() -> 0
        "Green".toLowerCase() -> 1
        "Blue".toLowerCase() -> 2
        else -> -1
    }
    // 等价于 fun getInt() = 2
    fun getInteger() : Int{
        return 2
    }

}
