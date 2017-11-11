package com.example.llcgs.android_kotlin.kotlin.other.typecheckchange.bean

/**
 * com.example.llcgs.android_kotlin.other.typecheckchange.bean.Person
 * @author liulongchao
 * @since 2017/8/11
 */


class Person(val name: String, val lastName: String, val age:Int) {

    override fun equals(other: Any?): Boolean {
        // 检查类型，如果不匹配就返回false
        val otherPerson  = other as? Person ?: return false
        // 在安全转换之后，otherPerson被智能的转换成Person类型
        return otherPerson.name == name && otherPerson.lastName == lastName && otherPerson.age == age
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + age
        return result
    }
}