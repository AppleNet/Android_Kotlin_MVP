package com.example.llcgs.android_kotlin.other.airsafety

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.other.airsafety.bean.Employee
import com.example.llcgs.android_kotlin.other.airsafety.bean.Person
import com.example.llcgs.android_kotlin.other.airsafety.presenter.impl.TwentyEightPresenter
import com.example.llcgs.android_kotlin.other.airsafety.view.TwentyEightView
import com.example.llcgs.android_kotlin.other.exception.TwentyNineActivity
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_twenty_eight.*

/**
 *  空安全
 *    1.可空类型与非空类型
 *    2.在条件中检查null
 *    3.安全的调用
 *    4.Elvis操作符
 *    5.!!操作符
 *    6.安全的类型转换 {@link TwentyFourActivity}
 *    7.可空类型集合
 * */

class TwentyEightActivity : BaseActivity<TwentyEightView, TwentyEightPresenter>(), TwentyEightView {

    override fun createPresenter()= TwentyEightPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty_eight)

        RxView.clicks(button26).subscribe { startActivity(Intent(this@TwentyEightActivity, TwentyNineActivity::class.java)) }

    // 可空类型和非空类型
        // Kotlin的类型系统旨在消除来自代码空引用的危险
        // Kotlin对可空类型是显示支持的，编译期指出代码中那些变量和属性允许为null的方式
        // 在 Kotlin 中，类型系统区分⼀个引用可以容纳 null （可空引用）还是不能容纳（非空引用）
        var a: String = "abc"
        // 此时就会编译报错 Null can not be a value of a non-null type String
        // a = null 强制性的检查
        // 如果要允许为空，可以声明一个变量为可空字符串
        var b: String? = null
        // ?可以加在任何类型的后面来表示这个类型的变量可以存储null引用
        // Type? = Type or Null
        // 注意：没有问号的类型表示这种类型的变量不能存储null引用，这说明所有常见类型默认都是非空的，除非显示地把它标记为可空

        // 不能将可空类型赋值给非可空的类型
        // required String found String?
        // 不允许 a = b

    //  在条件中检查null
        // 使用if显示检查进行判断
        val l = if (b != null) b.length else 0
        // 编译器会跟踪所执行检查的信息，并允许你在if内部调用length。同时，也支持更复杂（更智能）的条件
        if (b != null && b.length > 0) {
            print("String of length ${b.length}")
        } else {
            print("Empty string")
        }
        // 注意，这只适用于b是不可变的情况（即在检查和使用之间没有修改过的局部变量，或者不可覆盖并且有幕后字段的val成员），因为否则可能会发生
        // 在检查之后b 又变为null 的情况

    // 安全的调用 ?.
        // kotlin中使用?.允许你把一次null检查和一次方法调用合并成一个操作
                     // foo != bull foo.bar()
        // foo?.bar()
                     // foo == null null

        // 安全调用运算符只会调用非空值的方法
        printAllCaps("kobe")
        printAllCaps(null)

        // 安全调用不光可以调用方法，也能用来访问属性。
        val ceo = Employee("kobe", null)
        val dev = Employee("bosh", ceo)
        managerName(ceo)?.logD()
        managerName(dev)?.logD()

        // 链接多个安全调用
        val p = Person("City", null)
        p.countryName().logD()
        // 如果要只对非空值执行某个操作，安全调用操作符可以与 let ⼀起使用
        val listWithNulls: List<String?> = listOf("A", null)
        for (item in listWithNulls) {
            item?.let {
                println(it)
            } // 输出 A 并忽略 null
        }

    // Elvis运算符 ?: null合并运算符
        // 当我们有⼀个可空的引⽤ r 时，我们可以说“如果 r ⾮空，我使⽤它；否则使⽤某个⾮空的值 x ”：
        val ll: Int = if (b != null) b.length else -1
                        // foo != null  foo
        // foo?:bar ——>
                        // foo == null  bar

        // Elvis运算符经常和安全运算符一起使用，用一个值代替对null对象调用方法时返回的null
        strLenghtSafe("kobe").logD()
        strLenghtSafe(null).logD()
        //如果 ?: 左侧表达式⾮空，elvis 操作符就返回其左侧表达式，否则返回右侧表达式。 请注意，当且仅当左侧为空时，才会对右侧表达式求值。

        // 请注意，因为 throw 和 return 在 Kotlin 中都是表达式，所以它们也可以用在 elvis 操作符右侧

    // 非空断言 !!操作符
        // 对null值做非空断言，为空的时候显式抛异常
                    // foo!= null foo
        // foo!! ——>
                    // foo==null NullPointException

    // 安全类型转换 as?
        // 如果对象不是⽬标类型，那么常规类型转换可能会导致 ClassCastException 。 另⼀个选择是使用安全的类型转换(as?)，如果尝试转换不成功则返回null

    // 可空类型集合
        // 如果你有⼀个可空类型元素的集合，并且想要过滤非空元素，你可以使⽤ filterNotNull 来实现
        val nullableList: List<Int?> = listOf(1, 2, null, 4)// 创建包含可空Int值的列表
        val intList: List<Int> = nullableList.filterNotNull()
        intList.forEach { it.logD() }
        // List<Int?>是能持有Int?类型值的列表：换句话说可以持有Int或者null。
        // 变量自己类型的可空性和用作类型参数的类型的可空性是又区别的，包含可空Int的列表和包含Int的可空列表之间的区别
        // Int null                 Int
        // Int null List<Int?>      Int null  List<Int>?
        // Int mull                 Int
        // 即List<Int?>? 有两个问号的时候，使用变量自己的值的时候，以及使用列表中每个元素的值的时候，都需要null检查
        // Kotlin库提供了一个filterNotNull 来完成对包含可空值

    }

    // 可空类型的值，能对它进行的操作是受限的，不能再调用它的方法
    // 编译的时候 就会报错 only safe (?.) or non-null asserted(!!.) calls are allowed on a nullable receiver of type kotlin.String
    fun strLenSafe(s: String?) = s?.length

    fun printAllCaps(s:String?){
        val allCaps: String? = s?.toUpperCase() // allCaps可能是null
        allCaps?.logD()
    }

    fun managerName(employee: Employee):String? = employee.manager?.name

    fun Person.countryName():String{
        val country = this.company?.address?.country
        return if (country != null) country else "Unknown"
    }

    //

    fun strLenghtSafe(s:String?) = s?.length ?: 0

    // throw 结合 猫王表达式使用
    fun logLabel(person: Person){
        val address = person.company?.address?: throw IllegalArgumentException("No Address")
        address.let {
            it.logD()
        }
        with(address){
            address.logD()
        }
    }
}
