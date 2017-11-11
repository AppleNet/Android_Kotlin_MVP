package com.example.llcgs.android_kotlin.kotlin.idiom.kt

/**
 * com.example.llcgs.android_kotlin.third.kt.StringExtension
 * @author liulongchao
 * @since 2017/7/20
 */

fun String.prefix(index: Int): String{
    return this.substring(index)
}

// TODO 扩展属性
var StringBuilder.lastChar: Char
 get() {
     return get(length - 1)
 }
 set(value) {
     this.setCharAt(length-1, value)
 }

