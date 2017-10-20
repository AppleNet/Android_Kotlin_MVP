package com.example.llcgs.android_kotlin.classandobject.sealedclass.bean

/**
 * com.example.llcgs.android_kotlin.classandobject.sealedclass.bean.SubConst
 * @author liulongchao
 * @since 2017/10/20
 */

// TODO 密封类的子类的继承者（间接继承）可以在任何地方声明，不⼀定要在这个密封类声明内部
class SubConst(var double: Double): User.Const(double) {

}