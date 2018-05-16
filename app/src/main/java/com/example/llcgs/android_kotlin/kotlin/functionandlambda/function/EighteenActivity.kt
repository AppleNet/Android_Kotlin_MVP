package com.example.llcgs.android_kotlin.kotlin.functionandlambda.function

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.kotlin.functionandlambda.function.model.showoff
import com.example.llcgs.android_kotlin.kotlin.functionandlambda.function.presenter.impl.EighteenPresenter
import com.example.llcgs.android_kotlin.kotlin.functionandlambda.function.presenter.impl.getPresenter
import com.example.llcgs.android_kotlin.kotlin.functionandlambda.function.view.EighteenView
import com.example.llcgs.android_kotlin.kotlin.functionandlambda.lambda.NineteenActivity
import com.example.llcgs.android_kotlin.utils.log.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_eighteen.*

/**
 *  函数
 *    1.函数声明
 *    2.函数用法
 *      1.中缀表达式
 *      2.参数
 *      3.默认参数
 *      4.命名参数
 *      5.返回Unit的函数
 *      6.单表达式函数
 *      7.显示返回函数
 *      8.可变数量的参数
 *    3.函数作用域
 *      1.局部函数
 *      2.成员函数
 *    4.泛型函数
 *    5.内联函数
 *    6.扩展函数
 *    7.高阶函数和lambda表达式
 *    8.尾递归函数
 *
 * */
/**
 *   函数--> 语句和表达式
 *     语句和表达式的区别在于：表达式有值，并且能够作为另一个表达式的一部分使用，而语句总是包围着它的代码块中的顶层元素，并且没有自己的值
 * */

class EighteenActivity : BaseActivity<EighteenView, EighteenPresenter>(), EighteenView {

    override fun createPresenter()= EighteenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eighteen)
        RxView.clicks(button16).subscribe {
            startActivity(Intent(this@EighteenActivity, NineteenActivity::class.java))
        }
    // 函数声明
        double(1)
    // 函数用法
        // 中缀表达式（使用infix关键字）
          // 函数可以使用中缀表示法调用
          // 他们是成员函数或扩展函数
          // 他们只有⼀个参数
          // 他们⽤ infix 关键字标注
        // 中缀表达式调用 shl左移 shr右移
        val i = 3 shl 2
        i.logD()
        val shl = 1 shll 2
        shl.logD()
        val s = "Kobe" toM 32
        s.logD()

        // 参数
        // 函数参数使⽤ Pascal 表⽰法定义，即  name:type, 参数⽤逗号隔开。每个参数必须有显式类型
        paramters("Wade")

        // 默认参数
        // 函数参数可以有默认值，当省略相应的参数时使用默认值。与其他语言相⽐，这可以减少 重载数量
        // 覆盖方法总是使用与基类型方法相同的默认参数值。 当覆盖⼀个带有默认参数值的方法时，必须从签名中省略默认参数值
        // TODO 参数的默认值是被编码到被调用的函数中，而不是调用的地方。如果你改变了参数的默认值并重新编译这个函数，没有给参数重新赋值的调用者，将会开始使用新的默认值
        reformat()
        reformat("1", false, false, true, 'k')
        reformat()



        // 命名参数
        // 调用参数的 并且赋值的形式调用 称为命名参数，这样可以不用给所有的参数赋值，减少了方法的重载。
        // 如果再调用一个函数时，指明了一个参数名称，为了避免混淆，那它之后的所有参数都需要标明名称
        // 请注意，在调用Java 函数时不能使用命名参数语法，因为 Java 字节码并不 总是保留函数参数的名称
        reformat(str = "", normalizeCase = false)

        // 返回Unit的函数
        // 如果⼀个函数不返回任何有用的值，它的返回类型是Unit 。Unit是⼀种只有⼀个值⸺Unit的类型。这个值不需要显式返回
        printHello("james")

        // 单表达式函数
        // 当函数返回单个表达式时，可以省略花括号并且在=符号之后指定代码体即可
        createPresenter() // 就是一个单表达式函数
        getPresenter() // 也是一个单表达式函数

        // 显示返回类型
        // 返回一个指定的类型

        // 可变数量的参数(varargs)
        // 函数的参数(通常最后一个)可以用varargs修饰符标记
        // arrayOf的
        val array = arrayOf(1,2,3)
        // 如果我们已经有⼀个数组 并希望将其内容传给该函数，我们使⽤ 伸展（spread）操作符（在数组前面加 * ）
        val asList = asList(0, */*伸展操作符*/array, 4, 5, 6)
        asList.forEach { it.logD() }

    // 函数作用域
        //在 Kotlin 中函数可以在⽂件顶层声明，这意味着你不需要像⼀些语⾔如 Java、C# 或 Scala 那样创建⼀个类来保存⼀个函数。
        // 此外除了顶层函数，Kotlin中函数也可以声明在局部作用域、作为成员函数以及扩展函数

        // 局部函数
        // 在kotlin中支持局部函数，即一个函数在另一个函数的内部
        // 方法里面写方法。感觉吊吊的，但是局部函数只能局部调用
        dfs("Kobe")

        // 成员函数
        // 成员函数是在类或对象内部定义的函数

        // 泛型函数
        // 函数可以有泛型参数，通过在函数名前使用尖括号指定
        val generics = getGenerics("wade")
        generics.forEach { it.logD() }

        // 内联函数

        // 扩展函数
        // 给别人的类添加方法。就说一个类的成员函数，不过定义在类的外面
        // 扩展函数并不允许打破它的封装性，扩展函数不能访问私有的或者受保护的成员
        // 扩展函数不能被子类重写
        mPresenter.getPresenter()
        // 调用一个kotlin中带有的扩展函数
        val list = listOf("kobe","james","wade")
        // joinTo 就是一个扩展函数
        // list.joinTo("")
        // TODO 不能被重写的扩展函数，Kotlin把它当作静态函数对待
        val view: View = Button(this)
        view.showoff()


        // 高阶函数和lambda表达式

        // 尾递归函数
        // Kotlin ⽀持⼀种称为尾递归的函数式编程⻛格。 这允许⼀些通常用循环写的算法改用递归函数来写，而无堆栈溢出的风险。当⼀个函数用 tailrec 修
        // 饰符标记并满足所需的形式时，编译器会优化该递归，留下⼀个快速而高效的基于循环的版本
        val findFixPoint = findFixPoint()
        findFixPoint.logD()
        // 注意： 要符合 tailrec 修饰符的条件的话，函数必须将其自身调用作为它执行的最后⼀个操作。在递归调用后有更多代码时，不能使用尾递归，并且不能用在try/catch/finally 块中。
        // 目前尾部递归只在 JVM 后端中⽀持

    }
    // 函数声明
    // 声明一个函数 使用fun
    // 关键字
    fun double(x: Int): Double{
        return x.toDouble()
    }

    // 声明中缀表达式函数
    infix fun Int.shll(x: Int): Int{
        return this shl x
    }

    infix fun String.toM(x: Int): String{
        return this + x + "岁"
    }

    // 参数
    fun paramters(s: String){
        s.logD()
    }

    // 命名参数
    fun reformat(str: String = "",
                 normalizeCase: Boolean = true,
                 upperCaseFirstLetter: Boolean = true,
                 divideByCamelHumps: Boolean = false,
                 wordSeparator: Char = ' '){
        str.logD()
        normalizeCase.logD()
        upperCaseFirstLetter.logD()
        divideByCamelHumps.logD()
        wordSeparator.logD()
    }

    fun reformat(str: String = "aaa",
                 normalizeCase: Boolean = false,
                 upperCaseFirstLetter: Boolean = false,
                 divideByCamelHumps: Boolean = true){

        str.logD()
        normalizeCase.logD()
        upperCaseFirstLetter.logD()
        divideByCamelHumps.logD()

    }

    // 返回Unit函数
    fun printHello(name: String?): Unit {
        if (name != null)
            "Hello ${name}".logD()
        else
            "Hi there!".logD()
        // return Unit 或者 直接return 是可选的
    }
    // 还可以这样写
    fun printHello(name: Int){
        // ....
        return Unit
    }

    fun getPresenter() = { EighteenPresenter()}

    // 可变数量的参数
    fun <T>asList(vararg ts: T): List<T>{
        val result = ArrayList<T>()
        for (t in ts){
            result.add(t)
        }
        return result
    }

    // 局部函数
    fun dfs(name:String){
        fun dfs1(index:Int, height: String){
            "索引：$index, height: $height".logD()
        }
        // 局部函数 需要在局部调用，要想执行dfs1这个方法 需要手动调用下
        dfs1(1, "183")
//        dfs("Wade")
        "dfs-name: $name".logD()
    }

    // 泛型函数
    fun <T> getGenerics(item: T): List<T>{
        val myList = ArrayList<T>()
        for (x in 1..10){
            myList.add(item)
        }
        return myList
    }

    // 尾递归函数
    tailrec fun findFixPoint(x: Double = 1.0): Double =
            if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))
}
