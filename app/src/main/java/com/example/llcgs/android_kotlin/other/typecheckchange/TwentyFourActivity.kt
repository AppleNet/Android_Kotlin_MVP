package com.example.llcgs.android_kotlin.other.typecheckchange

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.other.thisexperssion.TwentyFiveActivity
import com.example.llcgs.android_kotlin.other.typecheckchange.bean.Person
import com.example.llcgs.android_kotlin.other.typecheckchange.presenter.impl.TwentyFourPresenter
import com.example.llcgs.android_kotlin.other.typecheckchange.view.TwentyFourView
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_twenty_four.*

/**
 *  类型的检查与转换
 *   1.is和!is操作符
 *   2.智能转换
 *   3.不安全的转换操作符
 *   4.安全的可空转换操作符
 *
 * */

class TwentyFourActivity : BaseActivity<TwentyFourView, TwentyFourPresenter>(), TwentyFourView {

    // val属性
    private val four: Any = "String"
    internal val five: Int = 5
    open val six : Double = 6.0
    val seven : String
    get() {
        return "Kobe"
    }

    override fun createPresenter() = TwentyFourPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty_four)

        RxView.clicks(button22).subscribe {
            startActivity(Intent(this@TwentyFourActivity, TwentyFiveActivity::class.java))
        }

    // is和!is操作符
        // 我们可以在运⾏时通过使⽤ is 操作符或其否定形式 !is 来检查对象是否符合给定类型
        val obj = "Kobe"
        if (obj is String){
            obj.length.logD()
        }

    // 智能转换
        // 在kotlin中，编译器帮你完成了这些工作，如果你检查过一个变量是某种类型，后面就不需要在转换它，可以把它当作你检查过的类型使用。编译器为你执行了类型转换
        // TODO 智能转换只在变量经过is检查且之后不会在发生变化的情况下有效
        // TODO 当对一个类的属性进行智能转换的时候，这个属性必须是一个val属性，而且不能有自定义的访问器
        // 在许多情况下，不需要在Kotlin中使用显式转换操作符，因为编译器跟踪不可变值的 is -检查，并在需要时自动插⼊（安全的）转换
        demo(1)
        demo("McGrady")
        // 智能转换同样适用于when表达式 while循环
        // 注意： 当编译器不能保证变量在检查和使用之间不可改变时，智能转换不能用
            // val局部变量——总是可以
            // val属性⸺ 如果属性是private或internal，或者该检查在声明属性的同⼀模块中执行。 智能转换不适用于open的属性或者具有自定义 getter 的属性
            // var 局部变量⸺如果变量在检查和使⽤之间没有修改、并且没有在会修改它的 lambda 中捕获
            // var 属性⸺ 决不可能（因为该变量可以随时被其他代码修改）

        // val局部变量
        val my = "Wade"
        when (my){
            is String ->{

            }
            // my已经声明成String类型的了，已经明确类型，不能在使用is Int来判断类型了，否则报Incompatible types:Int and String
//            is Int ->{
//
//            }
        }
        // val属性 如果属性是private或internal ，可以进行智能转换
        if (four is String){
            "$four is String".logD()
        }
        // val属性 open的属性或者具有自定义 getter 的属性
        // 不是不能智能转换 是不适用于智能转换，因为可能同时修改这个属性
        if (six is Double){
            "$six is Double".logD()
        }
        if (seven is String){
            "$seven is String".logD()
        }

    // “不安全的” 转换操作符
        // 如果转换是不可能的，转换操作符会抛出一个异常，因此我们称之为不安全的。kotlin中的不安全转换由中缀操作符as表示
        // as运算符和java一样，如果被转换的值不是你试图转换的类型，就抛出一个ClasCastException异常
            //val button = findViewById(R.id.button22) as RecyclerView
        // 使用as的时候  执行上面的代码 会抛出一个异常ClasCastException 将button转换为RecyclerView 失败
        // 编译的时候 就会报错


    // “安全的”（可空）转换操作符
        // 为了避免出现异常，可以使用安全转换操作符as?，它可以在失败的时候返回false
        val kobe: Person = Person("Kobe", "Bryant", 35)
        val subKobe: Person = Person("KOBE", "Bryant", 35)
        val subKobe2: Person = Person("Kobe", "Bryant", 35)
        val mcgrady: Person = Person("McGrady", "Tracy", 36)
        val nulll: Person? = null

        "kobe equals subkobe is ${kobe.equals(subKobe).logD()}"
        // ==运算符会调用equals方法
        "kobe == subkobe is ${(kobe == subKobe).logD()}"
        "kobe equals subKobe2 is ${kobe.equals(subKobe2).logD()}"
        "kobe == subKobe2 is ${(kobe == subKobe2).logD()}"



    }


    fun demo(x:Any){
        if (x !is Double){
            "x is not Double, and this result is ${x.logD()}"
        }
        if (x is String){
            // if 花括号中 已经完成了强制类型转换
            // x已经可以作为String类型来使用了
            "x is String, and this length is ${x.length.logD()}"
        }else if (x is Int){
            "x is Int, and this result is ${x.logD()}"
        }

        when(x){
            //
            x is Int ->{

            }
            x is String ->{

            }
            // x还可以省略不写
            is Double ->{

            }

        }
    }
}
