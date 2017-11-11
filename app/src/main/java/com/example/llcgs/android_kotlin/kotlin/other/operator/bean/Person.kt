package com.example.llcgs.android_kotlin.kotlin.other.operator.bean

/**
 * com.example.llcgs.android_kotlin.other.operator.bean.Person
 * @author liulongchao
 * @since 2017/8/15
 */


class Person(val firstName: String, val lastName: String):Comparable<Person> {

    override fun compareTo(other: Person): Int {
        return compareValuesBy(this, other, Person::lastName, Person::firstName)
    }
}