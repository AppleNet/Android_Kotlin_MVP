package com.example.llcgs.android_kotlin.kotlin.classandobject.propertydelegate

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.classandobject.propertydelegate.presenter.impl.SeventeenPresenter
import com.example.llcgs.android_kotlin.kotlin.classandobject.propertydelegate.view.SeventeenView
import com.example.llcgs.android_kotlin.kotlin.functionandlambda.function.EighteenActivity
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_seventenn.*
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *  委托属性
 *   1.标准委托
 *     延迟属性
 *     可观察属性
 *   2.把属性储存在映射中
 *   3.局部委托属性
 *   4.属性委托要求
 *     翻译规则
 *     提供委托
 * */
class SeventeenActivity : BaseActivity<SeventeenView, SeventeenPresenter>(), SeventeenView {

    override fun createPresenter()= SeventeenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seventenn)

        RxView.clicks(button15).subscribe {
            startActivity(Intent(this@SeventeenActivity, EighteenActivity::class.java))
        }

    // 委托属性
        // 延迟属性（lazy properties）: 其值只在⾸次访问时计算
        // 可观察属性（observable properties）: 监听器会收到有关此属性变更的通知
        // 把多个属性储存在⼀个映射（map）中，⽽不是每个存在单独的字段中
        // 为了涵盖这些（以及其他）情况，Kotlin ⽀持  委托属性

        // 委托属性语法：
          // val/var <属性名>: <类型> by <表达式>。在 by 后面的表达式是该委托，因为属性对应的 get()（和 set()）会被委托给它的
          // getValue() 和 setValue() 方法。 属性的委托不必实现任何的接口，但是需要提供⼀个 getValue() 函数（和 setValue()⸺对于 var 属
          // 性）

        val my = MyDelegate()
        my.p.logD()
        my.p = "Kobe"

        // 标准委托

        // 延迟属性lazy
        // lazy() 是接受⼀个lambda并返回⼀个Lazy <T> 实例的函数，返回的实例可以作为实现延迟属性的委托：第⼀次调⽤ get()会执行已传递给lazy()的lamda表达式并记录结果，后续调用get()只是返回记录的结果

        val lazyValue: String by lazy {
            // LazyThreadSafetyMode.PUBLICATION // 初始化委托的同步锁不是必须的，多个线程可以同时执行
            "Hello"
        }
        lazyValue.logD()
        lazyValue.logD()
        // 对于 lazy 属性的求值是） 同步锁的（synchronized）：该值只在⼀个线程中计算，并且所有线程 会看到相同的值。如果初始化委托的同步锁不
        // 是必需的，这样多个线程 可以同时执⾏，那么将 LazyThreadSafetyMode.PUBLICATION 作为参数传递给 lazy() 函数。⽽如果你确定初始化将总
        // 是发生在单个线程，那么你可以使⽤ LazyThreadSafetyMode.NONE 模式， 它不会有任何线程安全的保证和相关的开销

        // 可观察属性Observable
        // Delegates.observable() 接受两个参数：初始值和修改时处理程序（handler）。每当我们给属性赋值时会调用该处理程序（在赋值后执行）。它有三个参数：被赋值的属性、旧值和新值
        // 需要观察属性，旧值，新值
        val user = User()
        user.age.logD()
        user.age = "Jodn"
        user.age = "Kobe"
        // 如果你想能够截获⼀个赋值并“否决”它，就使⽤ vetoable() 取代observable()，在属性被赋新值生效之前会调用传递给vetoable的处理程序

        user.hobby.logD()
        user.hobby = "playfootball"
        user.hobby.logD()

        // 把属性储存在映射中
        // ⼀个常见的用例是在⼀个映射（map）里存储属性的值。这经常出现在像解析JSON或者做其他“动态”事情的应用中。 在这种情况下，你可以使用映射实例自身作为委托来实现委托属性
        // 属性储存在映射中 如果被储存的是val类型的 则属性也要声明成var类型的
        // map中的key必须和属性保持一致 否则报异常java.util.NoSuchElementException: Key name is missing in the map 对应的key找不到
        val house = House(mapOf("name" to "别墅" , "height" to 10))
        house.name.logD()
        house.height.logD()

        val mu = MyHouse(mutableMapOf("name" to User(), "height" to 10, "furniture" to "美凯龙"))
        mu.name.logD()
        mu.height.logD()
        mu.furniture.logD()

        // 局部委托属性
        // 这个感觉屌屌的
        example {
            House(mapOf("name" to "别野", "height" to 10))
        }

        // 属性委托要求
            // 对于⼀个 只读属性（即val声明的），委托必须提供⼀个名为getValue的函数，该函数接受以下参数
               // - thisRef ⸺ 必须与属性所有者类型（对于扩展属性⸺指被扩展的类型）相同或者是它的超类型
               // - property ⸺ 必须是类型 KProperty<*> 或其超类型
            // 这个getValue函数必须返回和属性相同的类型

            // 对于一个可变属性(即var声明的)，委托必须额外提供一个setValue函数，该函数接受以下参数
                // -thisRef ⸺ 同 getValue()
                // -property ⸺ 必须是类型 KProperty<*> 或其超类型
                // -newalue 必须和属性同类型或者是它的超类型

            // getValue() 或/和 setValue() 函数可以通过委托类的成员函数提供或者由扩展函数提供。
            // 当你需要委托属性到原本未提供的这些函数的对象时后者会更便利。
            // 两函数都需要⽤ operator 关键字来进行标记

            // 委托类可以实现包含所需operator方法的ReadOnlyProperty 或 ReadWriteProperty 接口之⼀
            // MyUser的使用，
            my.p1.logD()
            my.p1 = "Wade"

        // 翻译规则
            // 在每个委托属性的实现的背后，Kotlin 编译器都会生成辅助属性并委托给它。例如，对于属性 prop ，生成隐藏属性 prop$delegate ，而访问器的代码只是简单地委托给这个附加属性
            // MyDelegate这个类中的p和p1委托给Delegate() 和 MyUser()

        // 提供委托
            // 通过定义provideDelegate操作符，可以扩展创建属性实现所委托对象的逻辑，如果 by 右侧所使用的对象将 provideDelegate 定义为成员或扩展函数，那么会调用该函数来 创建属性委托实例
    }

    inline fun example(noinline computerFoo:() -> House){
        // 局部委托属性
        // 将memoizedFoo委托给computerFoo 是House对象
        val memoizedFoo by lazy(computerFoo)
        if (memoizedFoo.name == "别墅"){
            memoizedFoo.name.logD()
        }else{
            "name is: ${memoizedFoo.name}".logD()
        }
    }
}

class MyDelegate{
    var p: String by Delegate()
    // 当我们从委托到⼀个 Delegate 实例的 p 读取时，将调⽤ Delegate 中的 getValue() 函数， 所以它第⼀个参数是读出 p 的对象、第⼆个参数保存了对 p ⾃⾝的描述 （例如你可以取它的名字)

    //
    var p1: String by MyUser()
}

class Delegate{
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String{
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String){
        "$value has been assigned to '${property.name} in $thisRef.'".logD()
    }
}

class User{
    var age: String by Delegates.observable("NBA"/*默认值*/){
        property/*被赋值的属性*/, oldValue/*旧值*/, newValue/*新值*/ ->
        "property: ${property.name}, oldValue: ${oldValue}, newValue: ${newValue}".logD()
    }

    var hobby: String by Delegates.vetoable("playBasketBall"){
        property, oldValue, newValue ->
        "property: ${property.name}, oldValue: ${oldValue}, newValue: ${newValue}".logD()
        // 只有条件为true的时候 newValue才会替换掉oldValue 否则不替换
        return@vetoable oldValue != newValue
    }
}

class House(map: Map<String, *>){
    val name: String by map
    val height: Int by map
}

class MyHouse<T>(map: MutableMap<String, T>){
    var name : T by map
    var height: T by map
    var furniture: T by map
}

class MyUser: ReadOnlyProperty<Any, String>, ReadWriteProperty<Any, String>{

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String) {
        "$value has been assigned to '${property.name} in $thisRef.'".logD()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }
}

// 提供委托


