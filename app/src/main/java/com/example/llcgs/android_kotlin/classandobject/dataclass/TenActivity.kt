package com.example.llcgs.android_kotlin.classandobject.dataclass

import android.content.Intent
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.dataclass.bean.User
import com.example.llcgs.android_kotlin.classandobject.dataclass.presenter.impl.TenPreseneter
import com.example.llcgs.android_kotlin.classandobject.dataclass.view.TenView
import com.example.llcgs.android_kotlin.classandobject.sealedclass.ElevenActivity
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_ten.*

/**
 *  数据类
 *    1.复制
 *    2.数据类和结构声明
 *    3.标准数据类
 * **/

class TenActivity : BaseActivity<TenView, TenPreseneter>(), TenView {

    override fun createPresenter()= TenPreseneter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ten)

        RxView.clicks(button8).subscribe {
            startActivity(Intent(this@TenActivity, ElevenActivity::class.java))
        }

    // 数据类的声明
        // data修饰的类 kotlin中称为数据类
        // 声明数据类，编译器⾃动从主构造函数中声明的所有属性导出以下成员
        // 1.equals()/hasCode()
        // 2.toString()
        // 3.componentN() 函数 按声明顺序对应于所有属性
        // 4.copy() 函数
        // 如果这些函数中的任何⼀个在类体中显式定义或继承⾃其基类型，则不会⽣成该函数

        /**
         *  public int hashCode() {
                return (this.name != null?this.name.hashCode():0) * 31 + (this.age != null?this.age.hashCode():0);
            }

            public boolean equals(Object var1) {
                if(this != var1) {
                    if(var1 instanceof User) {
                        User var2 = (User)var1;
                        if(Intrinsics.areEqual(this.name, var2.name) && Intrinsics.areEqual(this.age, var2.age)) {
                            return true;
                        }
                    }
                    return false;
                } else {
                    return true;
                }
            }
         * */

        /**
         *  public String toString() {
                return "User(name=" + this.name + ", age=" + this.age + ")";
            }
         * */

        /**
         *  @NotNull
            public final String component1() {
                return this.name;
            }

            @NotNull
            public final String component2() {
                return this.age;
            }
         *
         * */

        /**
         *  @NotNull
            public final User copy(@NotNull String name, @NotNull String age) {
                Intrinsics.checkParameterIsNotNull(name, "name");
                Intrinsics.checkParameterIsNotNull(age, "age");
                return new User(name, age);
            }
         * */
        val user = User()
        user.age = "32"
        user.name = "Kobe"
        user.toString().logD()

    // 复制
        // copy函数为复制而生成，复制一个对象改变它的一些属性，但是其余部分保持不变
        val user2 = user.copy(name = "McGrady")
        user2.toString().logD()

    // 数据类和结构声明
       // 为数据类⽣成的  Component 函数 使它们可在解构声明中使⽤

    // 标准数据类
       // 标准库提供了 Pair 和 Triple 。尽管在很多情况下命名数据类是更好的设计选择， 因为它们通过为属性提供有意义的名称使代码更具可读性


    }
}
