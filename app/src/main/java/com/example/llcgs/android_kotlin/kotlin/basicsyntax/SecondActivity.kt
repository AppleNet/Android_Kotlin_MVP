package com.example.llcgs.android_kotlin.kotlin.basicsyntax

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.home.bean.User
import com.example.llcgs.android_kotlin.kotlin.basicsyntax.presenter.impl.SecondPresenter
import com.example.llcgs.android_kotlin.kotlin.basicsyntax.view.SecondView
import com.example.llcgs.android_kotlin.kotlin.idiom.ThirdActivity
import com.example.llcgs.android_kotlin.utils.log.logD
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *  基本语法
 *   1.定义包
 *   2.定义函数
 *   3.定义局部变量
 *   4.注释
 *   5.使用字符串模板
 *   6.使用条件表达式
 *   7.使用可空值及null检测
 *   8.使用类型检测及自动类型转换
 *   9.使用for循环
 *   10.使用while循环
 *   11.使用when循环
 *   12.使用区间
 *   13.使用集合
 * */
class SecondActivity : BaseActivity<SecondView, SecondPresenter>() {

    val secondPreneter: SecondPresenter = SecondPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val intent = intent
        val id = intent.getStringExtra("id")
        val user = intent.getParcelableExtra<User>("user")
        // 使用字符串模板
        // ${} 表示字符串模板，进行字符串的拼接于替换
        textView.text = "time: ${System.currentTimeMillis()}, id: ${id}, name: ${user.name}, pwd${user.pwd}"

        RxView.clicks(textView)
                .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .subscribe {
                    startActivity(Intent(this@SecondActivity, ThirdActivity::class.java))
                }.addDisposable(compositeDisposable)



        // 使用条件表达式
        if (user.name.equals("McGrady", true)){
            Observable.just(user.name)
                    .subscribeOn(Schedulers.io())
                    .delay(2,TimeUnit.SECONDS)
                    .compose(bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { t ->
                        RxTextView.text(textView).accept(t)
                        Log.d("MainActivity", t)
                    }.addDisposable(compositeDisposable)
        }

        // 使用for循环
        val items = listOf("Kobe","James","Jodn","McGrady","Bosh","Answer","Durant")
        for (item in items) {
            // 此循环获取的集合的每一项元素
        }

        for (item in items.indices) {
            //此循环获取的集合的每一项的角标
            Log.d("MainActivity", "item at ${item} is ${items[item]}")
        }

        // 使用while循环
        var index = 0
        while (index < items.size){
            Log.d("MainActivity", "item at ${index} is ${items[index]}")
            index ++
        }

        // 使用when表达式
        when (index) {
            0 -> {
                1
            }
            else -> {
                null
            }
        }

        // 使用when表达式替代for循环查找
        // when结构中可以使用任意对象
        // 如果没给给when表达式提供参数，分支条件就是任意的布尔型表达式。优点是不会创建额外的对象
        when{
            "kobe" in items -> Log.d("MainActivity", "id: "+items.indexOf("kobe"))
        }

        // 使用区间
        val x = 10
        val y = 9
        // in 表示是否在指定的区间。 1..y+1 左右取等 即 1<= x <=10
        if (x in 1..y+1){
            Log.d("MainActivity", "x in 1 - ${y+1}")
        }
        // !in 表示是否不在指定的区间
        if (x !in 1..y+1){
            Log.d("MainActivity", "x in 1 - ${y+1}")
        }
        // 半开区间 不包含y+1的值
        for (x in 1 until y+1){

        }
        // 区间迭代
        for (x in 1..5){
            // 从1打印到5
            x.logD()
        }
        // 数列迭代
        for (x in 1..10 step 2){
            // 1-10之间 从1开始 每次加2
            x.logD()
        }
        for (x in 10 downTo 0 step 2){
            // 1-10之间 从0开始 每次加2
            x.logD()
        }

        //使⽤ lambda 表达式来过滤（filter）和映射（map）集合
        // filter 从集合中将McGrady取出来之后转大写 并且打印出来，而不是丢掉不要
        items.filter { it == "McGrady" }
                .map { it.toUpperCase(Locale.getDefault()) }
                .forEach { it.logD() }
    }

    override fun createPresenter(): SecondPresenter {
        return secondPreneter
    }

    // 使用空值以及null检测
    // 当某个变量的值可以为 null 的时候，必须在声明处的类型后添加? 来标识该引⽤可为空
    fun isString(str: String): Int?{
        // is 运算符检测⼀个表达式是否某类型的⼀个实例
        if (str is String)
            // str在该条件分只能自动转换成string
            return str.length
        else
            // 在该条件分支能可以自动转换为null
            return null
    }
}
