package com.example.llcgs.android_kotlin.classandobject.classandextends

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.classandextends.bean.CBA
import com.example.llcgs.android_kotlin.classandobject.classandextends.bean.CBA1
import com.example.llcgs.android_kotlin.classandobject.classandextends.bean.FootBa
import com.example.llcgs.android_kotlin.classandobject.classandextends.presenter.impl.FivePresenter
import com.example.llcgs.android_kotlin.classandobject.classandextends.view.FiveView
import com.example.llcgs.android_kotlin.classandobject.propertyandfield.SixActivity
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_five.*

/**
 * 类和继承
 *  一.类
 *    1.构造函数
 *    2.创建类的实例
 *    3.类成员
 *  二.继承
 *    1.覆盖方法
 *    2.覆盖属性
 *    3.覆盖规则
 *
 * */

class FiveActivity : BaseActivity<FiveView, FivePresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_five)

        RxView.clicks(button3).subscribe {
            startActivity(Intent(this@FiveActivity, SixActivity::class.java))
        }
        /*
        * 类
        * */

        // 类 kotlin中和java一样 使用class声明一个类
        // 类声明由类名、类头（指定其类型参数、主 构造函数等）和由⼤括号包围的类体构成。类头和类体都是可选的； 如果⼀个类没有类体，可以省略花括号
        val cba = CBA()

        // 构造函数 CBA() 为默认的构造函数，也可以显示声明构造函数，使用constructor()显示的声明构造函数

        // 创建类的实例 kotlin中抛弃了new关键字。
        val cba1 = CBA1()

        /*
        * 继承
        * */
        // 在 Kotlin 中所有类都有⼀个共同的超类 Any ，这对于没有超类型声明的类是默认超类 所有的类都默认继承Any 就像java中所有的类继承Object一样
        // 但是Any 不是 java.lang.Object；尤其是，它除了 equals() 、hashCode() 和 toString() 外没有任何成员

        // 声明超类  使用open关键字，不写open 默认编出来的类是final类型的 不能被继承。

        /*
        * 抽象类
        *
        * */
        // 抽象类 kotlin中和java一样 使用abstract声明一个抽象类
        // 再抽象类中 可以不用像open类中那样使用open关键字  需要注意的是，我们并不需要⽤ open 标注⼀个抽象类或者函数

        /*
        * 伴生对象
        *
        * */
        // 再kotlin中 伴生对象 使用companion object 声明一个伴生对象
        // 对象声明 object(生成一个单例)
        // 对象表达式 一般用来生成接口实例
        // 伴生对象 类型类中的静态方法调用 可以通过类名.方法名
        FootBa.palyerFootBasketBall("Jodn").logD()

    }

    override fun createPresenter() = FivePresenter()
}
