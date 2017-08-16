package com.example.llcgs.android_kotlin.other.operator

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.other.airsafety.TwentyEightActivity
import com.example.llcgs.android_kotlin.other.operator.bean.Person
import com.example.llcgs.android_kotlin.other.operator.presenter.impl.TwentySevenPresenter
import com.example.llcgs.android_kotlin.other.operator.view.TwentySevenView
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_twenty_seven.*
import java.math.BigDecimal

/**
 *  操作符重载
 *   1.约定
 *     一元操作
 *     二元操作
 *     复合赋值
 *     比较运算
 *     排序运算
 *   2.命名函数的中缀调用
 *
 * */
class TwentySevenActivity : BaseActivity<TwentySevenView, TwentySevenPresenter>(), TwentySevenView {

    override fun createPresenter()= TwentySevenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty_seven)

        RxView.clicks(button25).subscribe {
            startActivity(Intent(this@TwentySevenActivity, TwentyEightActivity::class.java))
        }

    // 操作符重载
        // Kotlin 允许我们为自己的类型提供预定义的⼀组操作符的实现。这些操作符具有固定的符号表示 （如 + 或 * ）和固定的优先级。为实现这样的操作符，我
        // 们为相应的类型（即二元操作符左侧的类型和⼀元操作符的参数类型）提供了⼀个固定名字的成员函数或扩展函数。 重载操作符的函数需要用 operator 修饰符标记

        // 约定
        // 一元操作
        // 用预先定义的一个名称来声明函数(成员函数或者扩展函数)，并用修饰符operator标记。

        // 可重载的一元算法的运算符
        //表达式 翻译为
        // +a   a.unaryPlus()
        // -a   a.unaryMinus()
        // !a   a.not()
        // ++a,a++  a.inc
        // --a,a--  a.dec

        val p = Point(10, 20)
        (-p).logD()
        // 上述说明，当编译器处理-p时，它执行一下步骤
        // 1. 确定p的类型，令其为T
        // 2. 为接收者T查找一个带有operator修饰符无参数unaryMinus(),即成员函数或者扩展函数
        // 3. 如果函数不存在或者不明确，则导致编译不通过
        // 4. 如果函数存且返回类型为R，那就表达式具有类型R

        // 注意这些操作以及所有其他操作都针对基本类型做了优化，不会为他们引入函数调用的开销

        var bd = BigDecimal.ONE
        (bd++).logD()
        (++bd).logD()
        // 上述说明，当编译器处理bd++或者++bd的时候，它执行以下步骤
        // 1. 确定bd类型，令其为T
        // 2. 查找一个适用于类型为T的接收者，带有operator修饰符的无参数函数inc()
        // 3. 检查函数的返回类型时T的子类型
        // 计算表达式的步骤是：
        // 1.把bd的初始值存储到临时存储bd0中
        // 2.把bd.inc()结果赋值给bd
        // 3.把bd0作为表达式的结果返回

        // 注意 运算符重载 应该定义成包级别的 否则没有什么意义，有的操作符还可能报错

        // 二元操作
        // 可以声明为成员函数和扩展函数
        val p1 = Point(10, 20)
        val p2 = Point(20, 30)
        (p1 + p2).logD()
        // 注意：使用operator关键字声明plus函数。用于重载运算符的所有函数都需要用该关键字标记，用来表示你打算把这个函数作为相应的约定的实现
        val t = TwentySevenActivity()
        (t + 1).logD()
        (1 + 2).logD()

        // 可重载的二元算数运算符
        // 表达式   翻译为
        // a + b   a.plus(b)
        // a - b   a.minus(b)
        // a * b   a.times(b)
        // a / b   a.div(b)
        // a % b   a.rem(b)、a.mod(b)

        // 对于此表中的操作，编译器只是解析成 翻译为 列中的表达式
        // 请注意，⾃ Kotlin 1.1 起⽀持 rem 运算符。Kotlin 1.0 使⽤ mod 运算符，它在 Kotlin 1.1 中被弃用
        // 由于Java没有定义任何用于标记运算符函数的语法，所以使用operator修饰符的要求对它不适用，唯一的约束是参数需要匹配名称和数量

        // 定义一个运算符的时候，不要求两个运算数是相同类型。
        val p3 = Point(15, 20)
        (p3 * 1.6).logD()
        // 注意，Kotlin运算符不会自动支持交换性(交换运算符的左右两边)。如果希望用户能够使用1.6*p3以外，还能使用p3 * 1.6，你需要为它定义一个单独的运算符：operator fun Double.times(p: Point):Point
        (1.6 * p3).logD()

        // 还可以定义返回结果不同的运算符
        ('K' * 3).logD()

        // 注意： 没有用于位运算的特殊运算符
        // Kotlin没有为标准数字类型定义任何位运算符，因此，也不允许你为自定义类型定义它们。相反，它使用支持中缀调用语法的常规函数，可以为自定义类型定义相似的函数
        // 支持位运算的完整函数列表
        // shl 带符号左移
        // shr 带符号右移
        // ushr 无符号右移
        // and 按位与
        // or 按位或
        // xor 按位异或
        // inv 按位取反

        // 重载复合赋值运算符
        // Kotlin也支持+=  -=  *=  /=  %=的重载

        // 表达式   翻译为
        // a += b  a.plusAssign(b)
        // a -= b  a.minusAssign(b)
        // a *= b  a.timesAssign(b)
        // a /= b  a.divAssign(b)
        // a %= b  a.modAssign(b)
        5 += 3

        // Kotlin标准库支持集合的两种方法。+和-运算符总是返回一个新的集合。+=和-= 运算符用于可变集合的时候，始终在一个地方修改它们，而他们用于只读集合时，会返回一个修改过的副本(这意味着只有当引用只读集合的变量被声明为var的时候，才能使用+=和-=)
        // 作为他们的运算数，可以使用单个元素，也可以使用元素类型一致的其他集合
        val list = arrayListOf(1,2)
        // 用于只读集合时，会返回一个修改过的副本
        list += 3
        val newList = list + listOf(4,5)
        list.forEach {
            it.logD()
        }
        newList.forEach {
            it.logD()
        }

        // +=和-= 运算符用于可变集合的时候，始终在一个地方修改它们
        val list1 = ArrayList<Int>()
        list1.add(0)
        list1.add(1)
        list1.add(2)
        list1.add(3)
        list1.add(4)
        list1 += 5
        list1.forEach {
            it.logD()
        }

        // 重载比较运算符 equals
        // 在kotlin中可以对任何对象使用比较运算符（== != > <）而不仅仅限于基本数据类型。不用像java那样调用equals或compareTo函数，可以直接使用比较运算符。
        // 在kotlin中使用==运算符，将被转换成equals方法的调用 != 也是转换成equals调用
        // 注意，和其他运算符不同的是 ==和!=可以用于空运算符，因为这些运算符事实上会检查运算数是否为null。
        // a==b ——> a?.equals(b) ?: (b==null)
        // ===引用相等运算符不能被重载
        // equals不能实现为扩展函数，因为继承自Any类的实现始终优于扩展函数
        // 表达式 翻译为
        // a == b a?.equals(b) ?: (b === null)
        // a != b  !(a?.equals(b) ?: (b === null))

        // 排序运算符 compareTo
        // kotlin中比较运算符的使用(> < >= <=)将被转换为compareTo，compareTo的返回类型是Int
        // a > b ——> a.compareTo(b) > 0
        val a = 5
        val b = 3
        (a.compareTo(b)>0).logD()

        val p4 = Person("Mars","Liu")
        val p5 = Person("Bob", "Johnson")
        (p4 > p5).logD()
        // 表达式  翻译为
        // a > b  a.compareTo(b) > 0
        // a < b  a.compareTo(b) < 0
        // a >= b a.compareTo(b) >= 0
        // a <= b a.compareTo(b) <= 0
        // 所有的比较都转换为对 compareTo 的调用，这个函数需要返回Int值

    }

    // 给TwentySevenActivity这个类声明一个成员的二元操作符
    operator fun plus(x: Int): Int{
        return 0 + x
    }

}

// 定义一个一元运算符
operator fun Point.unaryMinus():Point{ // 一元运算符无参数
    return Point(-x, -y)// 坐标取反，然后返回
}
// 定义一个自增一元运算符
operator fun BigDecimal.inc() = BigDecimal.TEN + this

// 定义一个扩展的二元操作符
operator fun Point.plus(x:Point): Point{
    return Point(x.x + this.x,  x.y + this.y)
}

// 定义一个运算数是不同类型的二元操作符
// x是int类型 scale是double类型
operator fun Point.times(scale: Double): Point{
    return Point((scale * x).toInt(), (y * scale).toInt())
}

operator fun Double.times(p:Point): Point{
    return Point((p.x * this).toInt(), (p.y * this).toInt())
}

// 定义一个返回结果不同的运算符
operator fun Char.times(count: Int): String{
    return this.toString().repeat(count)
}

// 定义一个复合赋值运算符
operator fun Int.plusAssign(count : Int){
    (this * count).logD()
}

operator fun <T> MutableCollection<T>.plusAssign(element: T){
    this.add(element)
}




