package com.example.llcgs.android_kotlin.other.exception

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.other.exception.presenter.impl.TwentyNinePresenter
import com.example.llcgs.android_kotlin.other.exception.view.TwentyNineView
import com.gomejr.myf.core.kotlin.logD
import java.io.BufferedReader
import java.io.StringReader

/**
 *   异常
 *      1.异常类
 *        Try是一个表达式
 *      2.受检的异常
 *      3.Nothing类型
 *      4.Java互操作
 * */
class TwentyNineActivity : BaseActivity<TwentyNineView, TwentyNinePresenter>(), TwentyNineView {

    override fun createPresenter()= TwentyNinePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twenty_nine)

    // 异常类
        // Kotlin 中所有异常类都是 Throwable 类的⼦孙类。 每个异常都有消息、堆栈回溯信息和可选的原因
        // 使用throw表达式来抛出异常
        var a = 1
        if (1+2 > 2){
            throw IllegalArgumentException("A percentage value must be between 0 and 100")
        }
        // Kotlin中throw结构是一个表达式，能作为另一个表达式的一部分使用
        val m = if (a>1){
            a.logD()
        }else{
            throw IllegalArgumentException("A percentage value must be between 0 and 100")
        }

        // try/catch/finally
        // 和java一样，使用带有catch和finally子句的try结构来处理异常。
        try {
            // ⼀些代码
        }
        catch (e: Exception) {
            // 处理程序
        }
        finally {
            // 可选的 finally 块
        }
        readNumber(BufferedReader(StringReader("123")))?.logD()
        // Kotlin并不会区分受检异常和非受检异常。不用指定函数抛出的异常，而且可以处理也可以不处理异常。

        // Try是一个表达式， 即它可以有一个返回值
        readNumber(BufferedReader(StringReader("456")))?.logD()
        // try-表达式的返回值是 try 块中的 最后⼀个表达式或者是（所有）catch 块中的最后⼀个表达式。 finally 块中的内容不会影响表达式的结果
        // Kotlin中try关键字就像if和when一样，引入了一个表达式，可以把它的值赋给一个变量。
        val n: Int? = try { Integer.parseInt("") } catch (e: NumberFormatException) { null }
        // try catch代码块中的最后一行 代表着返回结果

        // 受检的异常
        // Kotlin中没有受检的异常。。。。

        // Nothing类型
        // 在 Kotlin 中 throw 是表达式，所以你可以使用它（比如）作为 Elvis(猫王表达式) 表达式的⼀部分
        val s = null ?: throw IllegalArgumentException("Name required")
        //throw 表达式的类型是特殊类型 Nothing。该类型没有值，而是用于标记永远不能达到的代码位置。 在你⾃⼰的代码中，你可以使用Nothing来标记⼀个永远不会返回的函数
        fail("Nothing")

        val p = null?:fail("p is null")

    }

    fun readNumber(reader: BufferedReader):Int?{// 不必显示地指定这个函数可能抛出的异常
        try {
            val line = reader.readLine()
            return Integer.parseInt(line)
        }catch (e: NumberFormatException){
            return null
        }finally {
            reader.close()
        }
    }

    // 也可以在catch中返回值
    fun readNumbers(reader: BufferedReader) = try {
        Integer.parseInt(reader.readLine()) // 变成try表达式的值
    }catch (e: NumberFormatException){
        null // 发生异常使用null
    }

    fun readNumberss(reader:BufferedReader){
        try {
            Integer.parseInt(reader.readLine())
        }catch (e:NumberFormatException){
            return
        }
    }

    fun fail(message:String):Nothing{
        throw IllegalArgumentException(message)
    }
}
