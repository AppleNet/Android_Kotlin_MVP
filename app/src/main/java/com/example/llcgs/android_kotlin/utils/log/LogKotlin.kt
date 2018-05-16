@file:Suppress("unused", "VARIABLE_WITH_REDUNDANT_INITIALIZER")

package com.example.llcgs.android_kotlin.utils.log

import android.text.TextUtils
import android.util.Log
import com.example.llcgs.android_kotlin.utils.BaseUtil


/**
 * com.example.llcgs.android_kotlin.utils.log.LogKotlin
 * @author liulongchao
 * @since 2018/5/11
 */
fun Any.logV() {
    printLog(false, 1, "", *arrayOf(this.toString()))
}


fun Any.logV(isPrintTrack: Boolean) {
    printLog(isPrintTrack, 1, "", *arrayOf(this.toString()))
}

fun Any.logV(target: Any, isPrintTrack: Boolean) {
    printLog(isPrintTrack, 1, "", *arrayOf<Any>(this.toString() + " >>> " + target))
}

fun Any.logD() {
    printLog(false, 2, "", *arrayOf(this.toString()))
}

fun Any.logD(isPrintTrack: Boolean) {
    printLog(isPrintTrack, 2, "", *arrayOf(this.toString()))
}

fun Any.logD(target: Any, isPrintTrack: Boolean) {
    printLog(isPrintTrack, 2, "", *arrayOf<Any>(this.toString() + " >>> " + target))
}

fun Any.logI() {
    printLog(false, 3, "", *arrayOf(this.toString()))
}


fun Any.logI(isPrintTrack: Boolean) {
    printLog(isPrintTrack, 3, "", *arrayOf(this.toString()))
}

fun Any.logI(target: Any, isPrintTrack: Boolean) {
    printLog(isPrintTrack, 3, "", *arrayOf<Any>(this.toString() + " >>> " + target))
}

fun Any.logW() {
    printLog(false, 4, "", *arrayOf(this.toString()))
}


fun Any.logW(isPrintTrack: Boolean) {
    printLog(isPrintTrack, 4, "", *arrayOf(this.toString()))
}

fun Any.logW(target: Any, isPrintTrack: Boolean) {
    printLog(isPrintTrack, 4, "", *arrayOf<Any>(this.toString() + " >>> " + target))
}

fun Any.logE() {
    printLog(false, 5, "", *arrayOf(this.toString()))
}

fun Any.logE(isPrintTrack: Boolean) {
    printLog(isPrintTrack, 5, "", *arrayOf(this.toString()))
}

fun Any.logE(target: Any, isPrintTrack: Boolean) {
    printLog(isPrintTrack, 5, "", *arrayOf<Any>(this.toString() + " >>> " + target))
}

fun Any.logWtf() {
    printLog(false, 6, "", *arrayOf(this.toString()))
}

fun Any.logWtf(isPrintTrack: Boolean) {
    printLog(isPrintTrack, 6, "", *arrayOf(this.toString()))
}

fun Any.wtf(target: Any, isPrintTrack: Boolean) {
    printLog(isPrintTrack, 6, "", *arrayOf<Any>(this.toString() + " >>> " + target))
}

private fun printLog(isPrintTrack: Boolean, type: Int, tagStr: String, vararg objects: Any) {
    if (BaseUtil.debug()) {
        val contents = wrapperContent(5, tagStr, *objects)
        val tag = BaseUtil.headTag()

        var headBuilder = StringBuilder()
        if (isPrintTrack) {
            headBuilder.append(" 调用顺序 ")
        }
        val tmpSplit = "-->"
        run {
            var i = Thread.currentThread().stackTrace.size - 1
            while (i >= 0 && isPrintTrack) {
                val path = wrapperContent(i, tagStr, *objects)[2]
                if (isMineOwnThread(path)) {
                    headBuilder.append("\n" + path + tmpSplit)
                }
                i--
            }
        }
        if (isPrintTrack && headBuilder.length > tmpSplit.length) {
            headBuilder = headBuilder.delete(headBuilder.length - tmpSplit.length, headBuilder.length)
        }

        val msg = contents[2] + headBuilder.toString() + contents[1]
        val maxLength: Short = 4000
        val countOfSub = msg.length / maxLength
        var index = 0
        if (countOfSub > 0) {
            for (i in 0 until countOfSub) {
                val sub = msg.substring(index, index + maxLength)
                printSub(type, tag, sub)
                index += maxLength.toInt()
            }
            printSub(type, tag, msg.substring(index, msg.length))
        } else {
            printSub(type, tag, msg)
        }
    }
}

private fun wrapperContent(stackTraceIndex: Int, tagStr: String?, vararg objects: Any): Array<String> {
    val stackTrace = Thread.currentThread().stackTrace
    val targetElement = stackTrace[stackTraceIndex]
    var className = targetElement.className
    val classNameInfo = className.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    if (classNameInfo.isNotEmpty()) {
        className = subString4Start(classNameInfo[classNameInfo.size - 1], "$")
    }
    if (className.contains("Kt")) {
        className = className.substring(0, className.length - 2) + ".kt"
    } else {
        className += ".kt"
    }

    val methodName = targetElement.methodName
    var lineNumber = targetElement.lineNumber
    if (lineNumber < 0) {
        lineNumber = 0
    }

    val methodNameShort = methodName.substring(0, 1) + methodName.substring(1)
    var tag = tagStr ?: className
    if (TextUtils.isEmpty(tag)) {
        tag = "LogCat"
    }

    @Suppress("SENSELESS_COMPARISON")
    val msg = if (objects == null) "Log with null object" else getObjectsString(*objects)
    var headString = " "
    val result = classNameInfo[classNameInfo.size - 1]
    if (result.contains("$")) {
        if (className.contains("$")) {
            headString = "[ (" + subString4Start(className, "$") + ".kt:" + lineNumber + ")#" + subString4Start2StringEnd(result, "$") + "#" + methodNameShort + " ] "
        } else {
            headString = "[ (" + className + ":" + lineNumber + ")#" + subString4Start2StringEnd(result, "$") + "#" + methodNameShort + " ] "
        }
    } else {
        headString = "[ ($className:$lineNumber)#$methodNameShort ] "
    }

    return arrayOf(tag, msg, headString)
}

@Suppress("SENSELESS_COMPARISON")
private fun getObjectsString(vararg objects: Any): String {
    if (objects.size > 1) {
        val var4 = StringBuilder()
        var4.append("\n")

        for (i in objects.indices) {
            val object1 = objects[i]
            if (object1 == null) {
                var4.append("Param").append("[").append(i).append("]").append(" = ").append("null").append("\n")
            } else {
                var4.append("Param").append("[").append(i).append("]").append(" = ").append(object1.toString()).append("\n")
            }
        }

        return var4.toString()
    } else {
        val `object` = objects[0]
        @Suppress("UNNECESSARY_SAFE_CALL")
        return `object`?.toString()
    }
}

private fun printSub(type: Int, tag: String, sub: String) {
    when (type) {
        1 -> Log.v(tag, sub)
        2 -> Log.d(tag, sub)
        3 -> Log.i(tag, sub)
        4 -> Log.w(tag, sub)
        5 -> Log.e(tag, sub)
        6 -> Log.wtf(tag, sub)
    }

}

/**
 * 从 第一个，到split结束的字串（不包含split）
 */
private fun subString4Start(text: String, split: String): String {
    if (TextUtils.isEmpty(text)) {
        return ""
    }
    val startIndex = text.indexOf(split)
    if (startIndex < 0) {
        return text
    }
    return text.substring(0, startIndex)
}

/**
 * 从 最后一个 split开始，到text结束的字串（不包含split）
 */
private fun subString4End(text: String, split: String): String {
    if (TextUtils.isEmpty(text)) {
        return ""
    }
    val lastIndexOf = text.lastIndexOf(split)
    if (lastIndexOf < 0) {
        return text
    }
    return text.substring(lastIndexOf + 1, text.length)
}

/**
 * 从 最后一个 split开始，到text结束的字串（不包含split）
 */
private fun subString4Start2StringEnd(text: String, split: String): String {
    if (TextUtils.isEmpty(text)) {
        return ""
    }
    var startIndex = text.indexOf(split)
    if (startIndex < 0) {
        startIndex = 0
    }
    return text.substring(startIndex, text.length)
}

private fun isMineOwnThread(contents: String): Boolean {
    val array = arrayOf("" + "[ (Method.java", "[ (ActivityThread.java", "[ (Looper.java", "[ (Handler.java", "[ (ActivityThread.java", "[ (Instrumentation.java", "[ (Activity.java", "[ (Thread.java", "[ (VMStack.java", "[ (ZygoteInit.java", "[ (" + "LogKotlin"::class.java.simpleName + ".java")
    for (i in array.indices) {
        if (contents.startsWith(array[i])) {
            return false
        }
    }
    return true
}
