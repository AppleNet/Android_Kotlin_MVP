package com.example.llcgs.android_kotlin.classandobject.`object`

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.`object`.presenter.impl.FifteenPresenter
import com.example.llcgs.android_kotlin.classandobject.`object`.view.FifteenView
import com.gomejr.myf.core.kotlin.logD
import kotlinx.android.synthetic.main.activity_fifteen.*

/**
 *  对象表达式和对象声明
 *     1.对象表达式
 *     2.对象声明
 *       伴生对象
 *       对象表达式和对象声明之间的关系
 *
 * */
class FifteenActivity : BaseActivity<FifteenView, FifteenPresenter>(), FifteenView {

    override fun createPresenter()= FifteenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fifteen)

    // 对象表达式
        // 有时候，我们需要创建⼀个对某个类做了轻微改动的类的对象，⽽不⽤为之显式声明新的⼦类。 Java⽤匿名内部类处理这种情况。Kotlin⽤ 对象表达式和对象声明 对这个概念稍微概括了下。
        // 声明一个对象表达式
        button13.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View) {

            }
        })

        // 如果父类有一个构造函数，则必须传递适当的构造函数参数给它。多个超类型可以由跟在冒号后面的逗号分隔的列表指定
        ab.logD()

        // 任何时候，我们只需要一个对象而已，并不需要特殊类型，那么我们可以简单地写成：
        val adHoc = object {
            var x: Int = 0
            var y: Int = 1
        }
        adHoc.x.logD()
        adHoc.y.logD()

        // 请注意，匿名对象可以用作只在本地和私有作用域中声明的类型。
        // 如果你使用匿名对象作为公有函数的返回类型 或者用作公有属性的类型，那么该函数或属性的实际类型 会是匿名对象声明的超类型，如果你没有声明任何超类型，就会是Any。 在匿名对象中添加的成员将无法访问
        val x = foo().x
        x.logD()
        // publicFoo().y 调用不到，因为返回类型是Any
        val y = publicFoo().toString()
        y.logD()

    // 对象声明
        // 单例模式是⼀种非常有用的模式，⽽Kotlin（继Scala之后）使单例声明变得很容易
        // 使用object关键字 object{} 来表示对象声明 生成的是一个单例模式的类
        // 引⽤该对象，我们直接使⽤其名称即可
        NBA.getAllStarts()

        // 对象声明可以有父类，接口,重载父类的属性和方法
        NBA.y.logD()
        // 注意：对象声明不能在局部作用域（即直接嵌套在函数内部），但是它们可以嵌套到其他对象声明或⾮内部类中
        // CBA对象声明嵌套在NBA对象声明中
        NBA.CBA.getAllCBAStarts()

    // 伴生对象
        // 类内部的对象声明可以⽤ companion 关键字标记 companion object{} 声明一个伴生对象
        FifteenActivity.getMyNba().logD()
        // 或者
        FifteenActivity.MyNBA.getMyNba().logD()

    // 对象表达式和对象声明之间的语义差异

        // 1.对象表达式和对象声明之间有一个重要的语义差别：
        // 2.对象表达式是在使用他们的地方立即执行的
        // 3.对象声明是在第一次被访问到时延迟初始化的
        // 4.伴生对象的初始化是在相应的类被加载（解析）时，与 Java 静态初始化器的语义相匹配
    }
    // 私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object {
        val x: Int = 100
    }

    // 公有函数，所以其返回类型是Any
    fun publicFoo() = object {
        val y: Int = 200
    }

    // 伴生对象
    companion object MyNBA{
        // MyNBA 可有可无
        fun getMyNba() = "liulongchao"
    }

}

// 多个对象表达式 逗号分隔
open class A(x:Int){
    open val y: Int = x
}

interface B{

}

val ab: A = object : A(1), B{
    override val y = 15
}

// 对象声明 它总是在object关键字后跟⼀个名称。就像变量声明⼀样，对象声明不是⼀个表达式，不能用在赋值语句的右边
object NBA: B, A(1){
   fun getAllStarts(){
       Log.d("MainActivity", "Kobe,McGrady,James,Wade,Paul,Anthdoy")
   }

    override val y: Int
        get() = 32

    object CBA{
        fun getAllCBAStarts(){
            Log.d("MainActivity", "朱芳雨,周琦,易建联,姚明,王治郅,巴特尔")
        }
    }
}
