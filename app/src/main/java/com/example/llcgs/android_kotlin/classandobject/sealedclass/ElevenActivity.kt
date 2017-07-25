package com.example.llcgs.android_kotlin.classandobject.sealedclass

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.genericity.TwelveActivity
import com.example.llcgs.android_kotlin.classandobject.sealedclass.presenter.ElevenPresenter
import com.example.llcgs.android_kotlin.classandobject.sealedclass.view.ElevenView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_eleven.*

/**
 *  密封类
 *     1.同一文件中的子类
 *     2.密封类与数据类
 * */

class ElevenActivity : BaseActivity<ElevenView, ElevenPresenter>(),ElevenView {

    override fun createPresenter()= ElevenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleven)

        RxView.clicks(button9).subscribe {
            startActivity(Intent(this@ElevenActivity, TwelveActivity::class.java))
        }

    // 密封类
        // 声明一个密封类使用sealed关键字，用来表示受限的类继承结构
        // 当⼀个值为有限集中的类型、⽽不能有任何其他类型时。在某种意义上，他们是枚举类的扩展：枚举类型的值集合也是受限的，但每个枚举常量只存在⼀个实例，⽽密封类的⼀个⼦类可以有可包含状态的多个实例
        // 密封类 编译之后 是一个抽象类
        // 密封类可以有子类，但是所有子类的声明必须嵌套在这个密封类内部
        // 密封类的子类的继承者（间接继承）可以在任何地方声明，不⼀定要在这个密封类声明内部
        // 使用密封类的关键在于使用when表达式的时候，如果能够验证语句覆盖了所有情况，就不需要为该语句添加一个else子句了


    }
}
