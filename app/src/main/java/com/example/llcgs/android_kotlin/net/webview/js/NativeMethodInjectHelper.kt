package com.example.llcgs.android_kotlin.net.webview.js

import android.text.TextUtils
import android.util.ArrayMap
import java.lang.reflect.Method
import java.lang.reflect.Modifier

/**
 * com.example.llcgs.android_kotlin.net.webview.js.NativeMethodInjectHelper
 * @author liulongchao
 * @since 2018/5/9
 */
object NativeMethodInjectHelper {

    private val mArrayMap = ArrayMap<String, ArrayMap<String, Method>>()
    private val mInjectClasses = ArrayList<Class<*>>()

    /**
     *  封装需要JS调用的Native类加入到集合中
     * */
    fun clazz(clazz: Class<*>): NativeMethodInjectHelper{
        mInjectClasses.add(clazz)
        return this
    }

    /**
     *  将Native类中的方法 加入到Map中
     *
     * */
    fun inject(){
        val size = mInjectClasses.size
        if (size > 0){
            mArrayMap.clear()
            mInjectClasses.forEach {
                putMethod(it)
            }
            mInjectClasses.clear()
        }
    }

    fun findMethod(className: String, methodName: String): Method?{
        if (TextUtils.isEmpty(className) || TextUtils.isEmpty(methodName))
            return null
        if (mArrayMap.contains(className)){
            val arrayMap = mArrayMap[className] ?: return null
            if (arrayMap.containsKey(methodName))
                return arrayMap[methodName]
        }
        return null
    }

    private fun putMethod(clazz: Class<*>){
        val arrayMap = ArrayMap<String, Method>()
        val methods = clazz.declaredMethods
        methods.forEach {
            val methodModifiers = it.modifiers
            if (methodModifiers and Modifier.PUBLIC !== 0 && it.returnType === Void.TYPE){
                arrayMap[it.name] = it
            }
        }
        mArrayMap[clazz.simpleName] = arrayMap
    }

}