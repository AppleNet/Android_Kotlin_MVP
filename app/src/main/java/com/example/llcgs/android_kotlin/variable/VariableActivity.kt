package com.example.llcgs.android_kotlin.variable

import android.os.Bundle
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.variable.presenter.impl.VariablePresenter
import com.example.llcgs.android_kotlin.variable.view.VariableView
import com.gomejr.myf.core.kotlin.logD

/**
 * com.example.llcgs.android_kotlin.variable.VariableActivity
 * @author liulongchao
 * @since 2017/9/22
 */

/**
 *  变量
 *
 * */
class VariableActivity: BaseActivity<VariableView, VariablePresenter>() {

    // 变量
    // 在Kotlin中声明变量 以关键字开始，然后是变量名称 最后可以加上类型(也可以不加类型)
    // 可变变量和不可变变量
    // val(来自Value) 不可变引用。使用val声明变量不能在初始化之后再次赋值。对应Java的final变量
    // var(来自variable) 可变引用。这种变量的值可以改变。这种声明对应的是普通的Java变量
    // 默认情况下，应该尽可能的使用val关键字来声明所有的Kotlin变量，仅在必要的时候换成var。使用不可变引用、不可变对象及无副作用的函数让你的代码风格更接近函数式编程风格。
    // val变量只进行一次初始化，但是编译器如果能确保唯一一条初始化语句被执行，可以根据条件使用不同的值来初始化它


    override fun createPresenter()= VariablePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // val 根据条件进行唯一的一次初始化
        val message:String
        if (getValue()){
            message = "Kobe"
        }else{
            message = "James"
        }
        message.logD()
        // TODO 尽管val引用自身是不可变的，但是它指向的对象可能是可变的。例如
        val list = arrayListOf<String>("Java", "Python")
        list.add("PHP")
        list.forEach {
            it.logD()
        }

        // 使用var关键字允许变量改变自己的值，但是它的类型确实改变不了的。
        var answer = 43
        //answer = ""// 编译失败，将var的类型修改了

        // 更简单的字符串格式化：字符串模板
        val name = if (getValue()) "kobe" else "James"
        "Hello $name".logD()// 字符串模板
        // Kotlin可以让你在字符串字面值中引用局部变量，只需要在变量名称前面加一个$符号，这等价于Java中的字符串拼接，效率一样但是更紧凑。
        // 如果要打印$符号 需要对其进行转义 使用\进行转义
        // 还可以使用更复杂的表达式，而不是仅限于简单的变量名称，只需要把表达式用花括号括起来：
        "Hello ${name + ", "+ name}".logD()

        // TODO Java类中getter可以被当成val属性在kotlin中访问，而一对儿getter/setter可以被当成var属性访问。

        // 自定义访问器
        // 属性isSquare不需要字段来保存它的值。它只有一个自定义实现的getter。
        var rectagle = Rectagle(41, 43)
        rectagle.isSquare.logD()

        rectagle = Rectagle(41, 41)
        rectagle.isSquare.logD()
    }


    fun getValue():Boolean{
        return true
    }

    class Rectagle(val width:Int, val height:Int){
        val isSquare:Boolean
            get()= width == height
    }
}