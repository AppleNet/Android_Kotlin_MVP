package com.example.llcgs.android_kotlin.kotlin.classandobject.classandextends.bean

import android.content.Context
import android.util.AttributeSet

/**
 * com.example.llcgs.android_kotlin.classandobject.classandextends.bean.MyButton
 * @author liulongchao
 * @since 2017/10/20
 */


class MyButton1 : View {

    // TODO 第二种初始化父类 使用this关键字
    constructor(context: Context):this(context, object : AttributeSet{
        override fun getPositionDescription(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeNameResource(index: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeUnsignedIntValue(namespace: String?, attribute: String?, defaultValue: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeUnsignedIntValue(index: Int, defaultValue: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeValue(index: Int): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeValue(namespace: String?, name: String?): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeIntValue(namespace: String?, attribute: String?, defaultValue: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeIntValue(index: Int, defaultValue: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getIdAttribute(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getIdAttributeResourceValue(defaultValue: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeFloatValue(namespace: String?, attribute: String?, defaultValue: Float): Float {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeFloatValue(index: Int, defaultValue: Float): Float {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getStyleAttribute(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeName(index: Int): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeListValue(namespace: String?, attribute: String?, options: Array<out String>?, defaultValue: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeListValue(index: Int, options: Array<out String>?, defaultValue: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getClassAttribute(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeBooleanValue(namespace: String?, attribute: String?, defaultValue: Boolean): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeBooleanValue(index: Int, defaultValue: Boolean): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeResourceValue(namespace: String?, attribute: String?, defaultValue: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAttributeResourceValue(index: Int, defaultValue: Int): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    })

    constructor(context: Context, attr: AttributeSet): super(context,attr){

    }

}