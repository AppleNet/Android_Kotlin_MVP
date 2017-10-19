package com.example.llcgs.android_kotlin.classandobject.classandextends.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.classandextends.bean.Animated
 * @author liulongchao
 * @since 2017/10/19
 */

// 在kotlin中 同Java一样，可以将一个类声明为abstract的，这种类不能被实例化。一个抽象类通常包含一些没有实现并且必须在子类重写的抽象成员。
// 抽象成员始终是open的，所以不需要显示地使用open修饰符。
abstract class Animated { // 抽象类不能创建实例
    // 抽象函数 没有实现 必须在子类实现
    abstract fun animate()

    // 抽象类中的非抽象函数并不是默认open的，但是可以标注为open的
    open fun stopAnimating(){

    }

    // 这个方法不能被子类实现，因为不是open的
    fun anumateTwice(){

    }

}