package com.example.llcgs.android_kotlin.other.annotation

import android.app.Activity
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.classandextends.bean.Inject
import com.example.llcgs.android_kotlin.other.annotation.presenter.impl.ThirtyPresenter
import com.example.llcgs.android_kotlin.other.annotation.view.ThirtyView
import com.gomejr.myf.core.kotlin.logD
import kotlin.reflect.KClass

/**
 *  注解
 *    1.注解声明
 *      用法
 *      构造函数
 *      lambda表达式
 *    2.注解使用处目标
 *    3.Java注解
 *
 *
 * */
class ThirtyActivity : BaseActivity<ThirtyView, ThirtyPresenter>(),ThirtyView {

    override fun createPresenter()= ThirtyPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thirty)

    // 注解声明
        // 注解是将元数据附加到代码的方法。要声明注解，将annotation注解放到类的前面
        // Fancy 就是声明的一个注解
        // 注解的附加属性 可以通过 元注解 标注 注解类 来指定

        // @Target 指定可以用 该注解标注的元素的可能的类型（类、函数、属性、表达式等）
        // @Retention 指定该注解是否 存储在编译后的 class ⽂件中，以及它在运⾏时能否通过反射可⻅ （默认都是 true）
        // @Repeatable 允许 在单个元素上多次使用相同的该注解
        // @MustBeDocumented 指定 该注解是公有 API 的⼀部分，并且应该包含在生成的API文档中显示的类或方法的签名中

        // 用法
        val foo = Foo()
        foo.baz(2).logD()
        // 如果需要对类的主构造函数进行标注，则需要在构造函数声明中添加constructor关键字，并将注解添加到其前面

        // 构造函数
        // 注解可以有接受参数的构造函数
        // 允许的参数类型有
        // - 对应于Java原生类型的类型(Int, Long, Double等等)
        // - 字符串
        // - 枚举
        // - 其他注解
        // -上面已列类型的数组

        // 注意：注解参数不能有可空类型，因为JVM不支持将null作为注解属性的值存储
        // 如果注解用作另一个注解的参数，则其名称不以@字符为前缀
        @Deprecated("This val is deprecated, use AppcompactActivity", ReplaceWith("this == other"))
        val thirtyActivity: Activity

        // 如果需要将⼀个类指定为注解的参数，请使用Kotlin类（KClass）。Kotlin 编译器会 自动将其转换为Java类，以便Java代码能够正常看到该注解和参数

        // Lambda表达式
        // 注解也可用于lambda表达式。它们会被应用于生成lambda表达式体的invoke()方法上。这对于像Quasar这样的框架很有用，该框架使用注解进行并发控制
        val f = @Supendable{ Thread.sleep(1000) }

    // 注解使用处目标
        // 当对属性或主构造函数参数进行标注时，从相应的Kotlin元素 生成的Java元素会有多个，因此在生成的Java字节码中该注解有多个可能位置 。如果要
        // 指定精确地指定应该如何生成该注解，请使用以下语法
        val e =Example("")
        // 支持的使用处目标的完整列表为：
        // file
        // property（具有此⽬标的注解对 Java 不可⻅）
        // field
        // get（属性 getter）
        // set（属性 setter）
        // Lambda  表达式
        // receiver（扩展函数或属性的接收者参数）
        // param（构造函数参数）
        // setparam（属性 setter 参数）
        // delegate（为委托属性存储其委托实例的字段）

        // 要标注扩展函数的接收者参数，请使⽤以下语法：
        fun @receiver:Fancy String.myExtension() { }

        // 如果不指定使⽤处⽬标，则根据正在使⽤的注解的 @Target 注解来选择⽬标 。如果有多个适⽤的⽬标，则使⽤以下列表中的第⼀个适⽤⽬标：
        // param
        // property
        // field

        // 如果你对同⼀目标有多个注解，那么可以这样来避免目标重复⸺在目标后面添加方括号 并将所有注解放在方括号内
        e.foot

        // 使用注解定义Json序列化
        // 使用JKid 纯kotlin库 进行json的序列化


    // Java注解
        // Java注解与Kotlin注解100%兼容


    }

}

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class Fancy

@Fancy class Foo{
    @Fancy fun baz(@Fancy foo:Int):Int{
        return (@Fancy 1)
    }
}

// 如果需要对类的主构造函数进行标注，则需要在构造函数声明中添加constructor关键字，并将注解添加到其前面
annotation class Fop @Inject constructor()

//构造函数
annotation class Special(val why:String)

annotation class ReplaceWith(val experssion: String)

annotation class Deprecated(val message:String, val replaceWith:ReplaceWith = ReplaceWith("my msg"))

annotation class Ann(val arg1:KClass<*>, val arg2:KClass<out Any>)

annotation class AnnString(val arg1:KClass<*>)

// 将Ann类指定为注解参数，使用KClass来指定
@Ann(String::class, Int::class) class MyClass

// lambda表达式
annotation class Supendable

// 注解使用处目标
class Example(@param:Ann(String::class, Int::class) val quux: String) {

    @field:Ann(String::class, Int::class)
    var foo = ""
    @get:Ann(String::class, Int::class)
    var bar = 0

    @field:AnnString(String::class)
    var ball = 0

    // 同一个目标 多个注解
    @set:[Inject VisibleForTesting]
    var foot = ""


}

