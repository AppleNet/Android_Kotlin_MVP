package com.example.llcgs.android_kotlin.classandobject.genericity

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.genericity.presenter.impl.TwelvePresenter
import com.example.llcgs.android_kotlin.classandobject.genericity.view.TwelveView
import com.example.llcgs.android_kotlin.classandobject.nestclass.ThirteenActivity
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_twelve.*

/**
 *  泛型
 *    1.型变
 *      1)声明处型变
 *    2.类型投影
 *    3.泛型约束
 * */

class TwelveActivity : BaseActivity<TwelveView, TwelvePresenter>() {

    override fun createPresenter()= TwelvePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twelve)

        RxView.clicks(button10).subscribe {
            startActivity(Intent(this@TwelveActivity, ThirteenActivity::class.java))
        }

    // 泛型
        // 和java一样，kotlin中也支持泛型
        // 要创建实例，需要提供这样的参数，传递类型去
        val box: Box<Int> = Box<Int>(1)

    // 型变
        // 型变包括协变，逆变，不变三种

        // kotlin中没有通配符类型一说，它有的是 1.声明处型变 2.类型投影
        // 首先java中的泛型是不型变的，这意味着List<String>不是List<Object>的子类型。

        // java中声明集合
        // List<String> strs = new ArrayList<String>();
        // List<Object> objs = strs; // ！！！即将来临的问题的原因就在这⾥。Java 禁⽌这样！
        // objs.add(1); // 这⾥我们把⼀个整数放⼊⼀个字符串列表
        // String s = strs.get(0); // ！！！ ClassCastException：⽆法将整数转换为字符串
        // 这就说明了java的不型变

        // 声明处型变
        // kotlin中使用out修饰符 向编译器解释情况

        // 在java中声明一个泛型
        /*
        * interface Source<T> {
            T nextT()
          }
        * */

        // 那么，在 Source <Object> 类型的变量中存储 Source <String> 实例的引⽤是极为安全的⸺没有消费者-⽅法可以调⽤。但是 Java 并不知道这⼀点，并且仍然禁⽌这样操作
        // Java中进行下面这样的操作
        /**
         *  void demo(Source<String> strs) {
                Source<Object> objects = strs; // ！！！在 Java 中不允许
                // ……
            }
         * */

        // 在kotlin中，使用声明处型变 标注Source的类型参数T来确保它仅从Source<T>成员中返回（⽣ 产），并从不被消费。为此，我们提供out修饰符
        val source = object : Source<String>(){
            override fun nextT(): String {
                return "Kobe"
            }
        }
        demo(source)
        // 一般原则 当类Source的类型参数T被声明为out的时候，它只能出现在Source的成员的输出位置，回报就是Source<Any>可以安全的作为Source<String>的父类。
        // 简言之，类Source是在参数T上是协变的，或者说T是一个协变的类型参数，可以认为Source是String的生产者，而不是String的消费者
        // out修饰符称为 型变注解，并且由于它在类型参数声明处提供，所以我们讲 声明处型变。这与java的使用处型变完全相反。

        // 在kotlin中，还补充了一个型变注解：in。使得一个类型参数逆变：只可以被消费不可以被生产。
        val source1 = object : Comparable<Number>(){
            // 进来的是一个Number类型 出去的是一个Int 逆向改变
            override fun compareTo(other: Number): Int {
                return other.toInt()
            }

        }
        demo1(source1)

    // 类型投影 使用处型变-类型投影
        // 在kotlin中 Array <T> 在 T 上是 不型变的，因此 Array <Int> 和 Array <Any> 都不是 另⼀个的⼦类型
        /**
         *  val ints: Array<Int> = arrayOf(1, 2, 3)
            val any = Array<Any>(3)
            copy(ints, any) // 错误：期望 (Array<Any>, Array<Any>)
         *
         * */
        // 在kotlin中是不允许这样操作的
        // 但是 我们可以阻止copy做这样的操作。使用类型投影
        val ints: Array<Int> = arrayOf(1, 2, 3)
        val strings: Array<Double> = arrayOf(1.0, 2.0, 3.0)
        // 此时调用copy1 会报错，期望Any 给到的是Double类型
        //copy1(ints, strings) // 错误：期望Any

    }
}


class Box<T>(t: T){
    val value = t
}

// 声明处型变-协变
abstract class Source<out T>{
    abstract fun nextT(): T
}

fun demo(strs: Source<String>){
    val objects: Source<Any> = strs // 在kotlin中这样是没问题的，因为T是一个out类型参数
    val nextT = objects.nextT()
    nextT.logD()
}

// 声明处型变-逆变
abstract class Comparable<in T>{
    abstract fun compareTo(other: T): Int
}

fun demo1(x: Comparable<Number>){
    x.compareTo(1.0) // 1.0 拥有类型 Double，它是 Number 的⼦类型
    // 因此，我们可以将 x 赋给类型为 Comparable <Double> 的变量
    val y: Comparable<Double> = x
    y.logD()
}

fun copy(from: Array<Any>, to: Array<Any>){
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
}

// 类型投影
fun copy1(from: Array<out Any>, to: Array<Any>){
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
}


