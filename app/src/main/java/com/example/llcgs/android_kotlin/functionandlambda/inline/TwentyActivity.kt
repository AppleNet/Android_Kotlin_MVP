package com.example.llcgs.android_kotlin.functionandlambda.inline

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.functionandlambda.inline.presenter.impl.TwentyPresenter
import com.example.llcgs.android_kotlin.functionandlambda.inline.view.TwentyView
import com.example.llcgs.android_kotlin.other.structdeclarations.TwentyOneActivity
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_twenty.*

/**
 *  内联函数
 *    1.禁用内联 noinline
 *    2.非局部返回 crossinline
 *    3.具体化的类型参数 reified
 *    4.内联属性 inline property
 * */
class TwentyActivity : BaseActivity<TwentyView, TwentyPresenter>(), TwentyView {

    // 当存在幕后字段的时候， inline不能修饰get/set和字段
    // 当inline修饰的时候，get/set方法必须同时都要提供出来，否则就会默认使用幕后字段
    // inline property cannot have backing field
    var name: String
        get (){
            return "Kobe"
        }
        inline set(value){

        }
    var age: Int
        inline get() {
            return 32
        }
        set(value) {

        }
    inline var hobby: String
        get() {
            return "basketBall"
        }
        set(value) {

        }


    override fun createPresenter()= TwentyPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty)

        RxView.clicks(button18).subscribe {
            startActivity(Intent(this@TwentyActivity, TwentyOneActivity::class.java))
        }

        // 内联函数
        // 声明一个内联函数 使用inline关键字 减少内存开销
        // 使用高阶函数造成一些运行时问题：每一个函数都是一个对象，它会持有一个闭包；即在函数体中可以访问这些变量。内存分配（包括函数对象和类）及虚拟调用都会作为运行开销
        // 通过内联Lambda表达式方式，可以减少这种开销
        val mutablelist = mutableListOf<String>("Kobe","James","Jodn","McGrady","Bosh","Answer","Durant")
        val intMutablelist = mutableListOf<Int>(0, 1, 2, 3, 4, 5, 6)
        val useInline = useInline {
            mutablelist
        }
        useInline.forEach { it.logD() }
        // inline 修饰符影响函数本身和传给它的lambda表达式：所有这些都将内联到调用处
        // 内联可能导致生成的代码增加，但是如果我们使用得当（不内联大函数），它将在 性能上有所提升，尤其是在循环中的“超多态（megamorphic）”调用处
        // 内联不是万能药，它以代码膨胀为代价 以时间换空间

        // 禁用内联
        // 如果你只想被（作为参数）传给⼀个内联函数的lambda表达式中只有⼀些被内联，你可以用noinline修饰符标记⼀些函数参数
        val userInline = userInline({ mutablelist }, { intMutablelist })
        val entries = userInline.entries
        entries.forEach {
            it.key.logD()
            it.value.logD()
        }
        // 需要注意的是，如果⼀个内联函数没有可内联的函数参数并且没有具体化的类型参数，编译器会产生⼀个警告，因为内联这样的函数 很可能并无益处（如果你确认需要内联，则可以关掉该警告）

        // 非局部返回
        // 在Kotlin中可以使用正常、无条件的return退出有名和匿名函数，也意味需要使用一个标签来退出Lambda，在Lambda中禁止使用赤裸return语句，因为Lambda不能够使闭合函数返回
        // 但是如果 lambda 表达式传给的函数是内联的，该 return 也可以内联，所以它是允许的
        val hasZeros = hasZeros(intMutablelist)
        hasZeros.logD()
        // 请注意，⼀些内联函数可能调用传给它们的不是直接来自函数体、而是来自另⼀个执行上下文的lambda表达式参数，例如来自局部对象或嵌套函数。在
        // 这种情况下，该lambda表达式中也不允许非局部控制流。为了标识这种情况，该lambda表达式参数需要，用crossinline修饰符标记
        f {
            for (t in 1..10){
                "user crossinline: $t".logD()
            }
        }

        // 具体化的类型参数(类型参数的具体化)
        // 使⽤ reified 修饰符来限定类型参数
        // reified关键字的使用 必须结合内联函数
        val findParentOfType = findParentOfType(Activity::class.java)
        findParentOfType?.logD()
        useReified { mutablelist }.forEach { it.logD() }
        // 我们使⽤ reified 修饰符来限定类型参数，现在可以在函数内部访问它了，几乎就像是⼀个普通的类⼀样。由于函数是内联的，不需要反射，正常的操作符如!is和as现在都能用了
        val membersOf = membersOf<StringBuilder>()
        membersOf.logD()

        // 内联属性
        // inline 修饰符可用于没有幕后字段的属性的访问器
        // 在调用处，内联访问器如同内联函数一样内联
        // inline可以修饰 单个的get set方法 也可以直接修饰属性 将set/get方法都内联
        name.logD()
        age.logD()
        hobby.logD()

    }

    // 声明一个内联函数 不使用reified关键字
    inline fun <T> useInline(body:()-> List<T>):List<T>{
        val list = ArrayList<T>()
        body().forEach {
            list.add("content value: $it" as T)
        }
        return list
    }

    // 声明一个禁用内联函数
    inline fun <T, R> userInline(body:()->List<T>, noinline header:()->List<R>): Map<R, T>{
        val map = HashMap<R, T>()
        for (t in 0..body().size-1) {
            map.put(header().get(t), body().get(t))
        }
        return map
    }

    // 非局部返回
    fun hasZeros(ints: List<Int>): Boolean {
        // forEach是一个内联函数，可以局部返回
        ints.forEach {
            if (it == 0) return true // 从 hasZeros 返回
        }
        return false
    }

    // crossinline
    // 请注意，⼀些内联函数可能调⽤传给它们的不是直接来⾃函数体、⽽是来⾃另⼀个执⾏ 上下⽂的 lambda 表达式参数，例如来⾃局部对象或嵌套函数。在
    // 这种情况下，该 lambda 表达式中 也不允许⾮局部控制流。为了标识这种情况，该 lambda 表达式参数需要 ⽤ crossinline 修饰符标记
    inline fun f(crossinline body:()-> Unit){
        // 声明一个局部对象
        val f = object :Runnable{
            override fun run()= body()
        }
        f.run()
        // 嵌套函数暂时还不知道怎么用
    }


    // 实例(类型参数-泛型) 默认情况下
    fun <T> findParentOfType(clazz: Class<T>): T?{
        var p = parent
        while (p != null && !clazz.isInstance(p)){
            p = p.parent
        }
        return p as T // 此时会提示一个异常 Unchecked cast：Activity! to T 并且调用的时候也比较不美观
    }

    // 声明一个reified 内联函数
    inline fun <reified T> useReified(body:()-> List<T>):List<T>{
        val list = ArrayList<T>()
        body().forEach {
            list.add("content value: $it" as T)
        }
        return list
    }
    // 使用reified的时候 还可以继续使用反射
    inline fun <reified T> membersOf() = T::class.java.name
}