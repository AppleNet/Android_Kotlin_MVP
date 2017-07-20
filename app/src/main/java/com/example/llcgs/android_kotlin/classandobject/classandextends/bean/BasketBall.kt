package com.example.llcgs.android_kotlin.classandobject.classandextends.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.classandextends.bean.BasketBall
 * @author liulongchao
 * @since 2017/7/20
 */


class BasketBall: Ball,Water {

    /**
     *
     * public final class BasketBall extends Ball {}
     *
     * */
    // 如果该类有⼀个主构造函数，其基类型可以（并且必须）⽤（基类型的）主构造函数参数就地初始化

    // 如果类没有主构造函数，那么每个次构造函数必须 使⽤ super 关键字初始化其基类型，或委托给另⼀个构造函数做到这⼀点。
    // 注意，在这种情况下，不同 的次构造函数可以调⽤基类型的不同的构造函数
    constructor() : super(){

    }
    //
    constructor(weight: Int) : super(weight){

    }

    // 覆盖方法
    // Kotlin 需要显式 标注可覆盖的成员（我们称之为开放）和覆盖后的成员
    // 子类要想重写父类中的方法，父类中的方法必须是open修饰的
    // 否则 生成的方法 是final类型的 不可以重写
    // 必有有override标注
    // 在⼀个final 类中（没有⽤ open标注的类），开放成员是禁⽌的
    override fun playBall(): String {
        return "BasketBall"
    }

    // 覆盖属性
    // 在超类中声明然后在派⽣类中重新声明的属性必须以 override 开头，并且它们必须具有兼容的类型。每个声明的属性可以由具有初始化器的属性或者具有 getter ⽅法的属性覆盖
    // 子类想覆盖父类的属性，父类中的属性必须是open的
    // 覆盖父类的属性 默认生成get set方法 可以删掉不用
    // 等同于 override var player: String = "Kobe"
    // 你也可以⽤⼀个 var 属性覆盖⼀个 val 属性，但反之则不⾏。这是允许的，因为⼀个 val 属性本质上声明了⼀个 getter ⽅法，⽽将其覆盖为 var 只是在⼦类中额外声明⼀个 setter ⽅法
    override var player: String
        get() = "Kobe"
        set(value) {}

    override fun drink() {
        super.drink()
        super.aa()
    }


}