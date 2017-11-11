package com.example.llcgs.android_kotlin.kotlin.classandobject.classandextends.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.classandextends.bean.CBA
 * @author liulongchao
 * @since 2017/7/20
 */

/**
 *  类
 *      类声明由类名、类头（指定其类型参数、主 构造函数等）和由大括号包围的类体构成。类头和类体都是可选的； 如果⼀个类没有类体，可以省略花括号
 *  构造函数
 *      1.在 Kotlin 中的⼀个类可以有⼀个 主构造函数和⼀个或多个次构造函数。主构造函数是类头的⼀部分：它跟在类名（和可选的类型参数）后
 *      2.如果主构造函数没有任何注解或者可⻅性修饰符，可以省略这个 constructor 关键字
 *      3.主构造函数不能包含任何的代码。初始化的代码可以放 到以 init 关键字作为前缀的） 初始化块（initializer blocks）中：
 *
 * */
class CBA constructor(){
    //constructor() 显示的声明了一个主构造函数
    var name: String? = null
    var age: String? = null

    init{
        name = "李根"
        age = "26"
        // 这里也可以直接生成CBA对象的实例，编译没问题 但是 运行报错 堆栈溢出
    }

    // 次构造函数必须继承this() 次构造函数可以声明一个或者多个
    // 如果类有⼀个主构造函数，每个次构造函数需要委托给主构造函数， 可以直接委托或者通过别的次构造函数间接委托。委托到同⼀个类的另⼀个构造函数⽤ this 关键字即可
    constructor(hobby: String = "basketball") : this() {

    }
    // 次构造函数必须满足java的一个规则 即方法的重载，所以参数列表必须不相同才行。。
    constructor(height: Int = 0): this(){

    }
}

class CBA1 constructor(val hobby: String = "basketball"){
    // 有一个默认的构造函数
    var name: String? = null
    var age: String? = null
    var height: String? = null
    // 主构造的参数 它们也可以在 类体内声明的属性初始化器中使⽤
    var cbaHobby = hobby

    init {
        // 主构造的参数可以在初始化块中使⽤
        height = hobby
    }
}

class CBA6{
    // 此时的主构造函数是默认的， 如果此时用constructor关键字声明的构造函数，则成为了次构造函数
    constructor(){
    }

    constructor(name: String){
    }

}

// 明属性以及从主构造函数初始化属性，Kotlin的简洁语法
class CBA2(val firstName: String, val lastName: String, var age: Int){
    // 主构造函数中声明的属性即可以是可变的 也可以是只读的
    // 构造函数中的属性 只能get使用 不能set使用
    var name = firstName
    // firstName = "" 或者 age=18 等是编译不过去的

}

/**
 *  constructor() 在有访问修饰符或者注解 修饰的时候 是不能省略掉的
 *
 * */
class CBA3 public constructor(){

}
class CBA4 private constructor(){

}
class CBA5 private @com.example.llcgs.android_kotlin.kotlin.classandobject.classandextends.bean.Inject constructor(name: String, age: Int){

}


