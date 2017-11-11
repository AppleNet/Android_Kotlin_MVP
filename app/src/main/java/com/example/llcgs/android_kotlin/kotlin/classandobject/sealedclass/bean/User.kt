package com.example.llcgs.android_kotlin.kotlin.classandobject.sealedclass.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.sealedclass.bean.User
 * @author liulongchao
 * @since 2017/7/24
 */

// 1.1开始顶层声明密封类的子类
class UserSub: User(){

}
// sealed修饰符隐含的这个类一定是一个open的
// abstract修饰密封类的时候 是多余的，因为密封类是open的 present的
abstract sealed class User{
    // TODO 对可能创建的子类做出严格的控制。所有的子类必须嵌套在父类中！！
    // 要声明⼀个密封类，需要在类名前⾯添加 sealed 修饰符
    // 密封类 编译之后 是一个抽象类
    // 密封类可以有子类，但是所有子类的声明必须嵌套在这个密封类内部
    // 密封类的子类的继承者（间接继承）可以在任何地方声明，不⼀定要在这个密封类声明内部
    // 使用密封类的关键在于使用when表达式的时候，如果能够验证语句覆盖了所有情况，就不需要为该语句添加一个else子句了

    var name: String?= null
    var age: String? = null

    constructor(){}

    constructor(name: String, age:String){
        this.name = name
        this.age = age
    }

    // 声明密封类的子类
    open class Const(val number: Double) : User(){

    }
    class Sum(val user: User, val user1: User): User(){

    }
    // 对象声明也可以继承密封类
    object NotNumber: User(){

    }

    // 从 1.1 开始，你可以在顶层声明sealed类的子类，唯⼀的限制是它们应该和父类在同⼀个文件中

    // TODO 数据类可以扩展其他类，包括密封类，这使得继承结构更有用
    data class Const1(val number: Double) : User()
    data class Sum1(val user1: User, val user2: User): User()

}