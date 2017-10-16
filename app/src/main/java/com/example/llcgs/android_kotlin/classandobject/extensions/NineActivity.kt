package com.example.llcgs.android_kotlin.classandobject.extensions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.dataclass.TenActivity
import com.example.llcgs.android_kotlin.classandobject.extensions.presenter.impl.NinePresenter
import com.example.llcgs.android_kotlin.classandobject.extensions.view.NineView
import com.example.llcgs.android_kotlin.idiom.kt.lastChar
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_nine.*

/**
 * 扩展
 *  1.扩展函数
 *  2.扩展事静态解析的
 *  3.可空接收者
 *  4.扩展属性
 *  5.伴生对象扩展
 *  6.扩展的作用域
 *  7.扩展声明为成员
 *  8.动机
 * */

class NineActivity : BaseActivity<NineView, NinePresenter>(),NineView {

    override fun createPresenter()= NinePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nine)

        RxView.clicks(button7).subscribe {
            startActivity(Intent(this@NineActivity, TenActivity::class.java))
        }

    // 一。扩展函数
        // 声明⼀个扩展函数，我们需要⽤⼀个接收者类型 也就是被扩展的类型来作为他的前缀
        // 所有的List都可以调用change方法
        // 这个 this 关键字在扩展函数内部对应到接收者对象（传过来的在点符号前的对象）
        val mutablelist = mutableListOf<String>("Kobe","James","Jodn","McGrady","Bosh","Answer","Durant")
        // 调用拓展函数change
        mutablelist.change(0,3)
        mutablelist.forEach { it.logD() }

    // 二。扩展是静态解析的
        // 扩展不能真正的修改他们所扩展的类。通过定义⼀个扩展，你并没有在⼀个类中插⼊新成员，仅仅是可以通过该类型的变量⽤点表达式去调⽤这个新函数
        // 扩展函数是静态分发的，即他们不是根据接收者类型的虚方法。这意味着调用扩展函数是由函数调用所在的表达式的类型来决定的，而不是由表达式运行时结果决定的
        // 此方法打印出“c”， 因为调用的扩展函数只取决于参数c的声明类型，该类型是C类。
        printFoo(D())

        // 如果⼀个类定义有⼀个成员函数和⼀个扩展函数，⽽这两个函数⼜有相同的接收者类型、相同的名字 并且都适⽤给定的参数，这种情况 总是取成员函数
        // 调用e.foo或输出 member成员函数
        val e = E()
        e.foo().logD()
        // 但是扩展函数重载同样名字但是不同签名成员函数也完全可以
        // 输出 extension扩展函数重载
        e.foo(1).logD()

    // 三。可空接收者
        // 可以为可空的接收者定义扩展函数。这样的扩展可以在对象变量上使用，即使其值为null，并且可以在函数体内检测this==null 这能让你在没有检测null的时候调用kotlin中的toString()
        // String()方法中 会检测对象e是否为空，空检测之后，e自动转换为非空类型
        e.String()

        // 四。扩展属性
        // 和函数类型，kotlin支持属性扩展
        // 注意：由于扩展没有实际的将成员插入类中，因此幕后字段对于扩展是无效的。这既是扩展属性为什么不能有初始化气，他们的行为只能由显示提供的getter/setter定义
        mutablelist.size.logD()
        mutablelist.lastIndex.logD()
        //
        val sb = StringBuilder("Kotlin?")
        sb.lastChar.logD()
        sb.lastChar = '!'
        sb.logD()

    // 五。伴生对象的扩展
        // 如果一个类定义有一个伴生对象，完全可以给这个伴生对象定义扩展
        // 调用伴生对象的拓展方法
        NineActivity.foo()

    // 六。扩展的作用域
        // 大多数时候我们在顶层定义扩展，即直接在包里
        // 要使⽤所定义包之外的⼀个扩展，我们需要在调⽤⽅导⼊它

    // 七。扩展声明为成员
        // 在一个类内部可以为另一个类声明扩展，再这样的扩展内部有多个隐士接收者--其中的对象成员可以无需通过限定符访问。
        // 扩展声明所在的类的实例称为分发接收者，扩展方法调用所在的接收者类型实例称为扩展接收者
        val g = G()
        g.caller(F())
        // 对于分发接收者和扩展接收者的成员名字冲突的情况，扩展接收者 优先

    // 动机
        // 扩展函数弥补了java的静态导入缺点

    }

    val <T> List<T>.lastIndex: Int
        get() = size-1


    // 声明一个MutableList的扩展函数
    // 在接收者类型表达式中使⽤泛型，我们要在函数名前声明泛型参数
    fun <T> MutableList<T>.change(index1: Int, index2: Int){
        val tmp = this[index1] // “this”对应该列表
        this[index1] = this[index2]
        this[index2] = tmp
    }

    open class C
    class D : C()
    // 给class C声明一个扩展函数
    fun C.foo() = "c"
    // 给class D声明一个扩展函数
    fun D.foo() = "d"

    fun printFoo(c: C){
        Log.d("MainActivity", c.foo())
    }

    // 如果⼀个类定义有⼀个成员函数和⼀个扩展函数，⽽这两个函数⼜有相同的接收者类型、相同的名字 并且都适⽤给定的参数，这种情况 总是取成员函数
    class E{
        fun foo(){
            Log.d("MainActivity", "member成员函数")
        }
    }

    fun E.foo(){
        Log.d("MainActivity", "extension扩展函数")
    }

    fun E.foo(int: Int){
        Log.d("MainActivity", "extension扩展函数重载")
    }

    // 可空接收者
    fun Any?.String(): String{
        // this指的是对象变量
        if (this == null)
            return "null"
        // 空检测之后，“this”会自动转换为非空类型，所以下面的toString() 解析为Any类的成员函数
        return toString()
    }

    // 声明一个伴生对象
    companion object{
        var cb: Int = 0
    }

    // 伴生对象扩展
    fun NineActivity.Companion.foo(){
        Log.d("MainActivity", "伴生对象扩展函数")
    }

    // 扩展声明为成员
    class F{
        fun bar(){
            Log.d("MainaActivity", "class F")
        }
    }

    class G{
        // 在G中对F进行扩展 则G是分发接收者 F是扩展接收者
        fun baz(){

        }

        fun F.foo(){
            Log.d("MainaActivity", "class F的扩展函数")
            // F的拓展函数 可以调用F中的任何方法
            bar()
            baz()
            toString()// 调用的是扩展接收者的toString方法
            this@G.toString() //使用this@XX才能调用分发接收者的toString()方法
        }

        fun caller(f: F){
            // 但是在G中不能调用F的扩展函数foo 扩展函数只能被扩展的对象实例调用
            f.foo()// 调用扩展函数
        }
    }


}
