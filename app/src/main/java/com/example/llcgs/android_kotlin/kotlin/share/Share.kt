package com.example.llcgs.android_kotlin.kotlin.share;

import android.widget.Button

class Share {

    lateinit var name: String

    // 只读map
    val thirdMap = mapOf(
            1 to "kobe",
            2 to "James",
            3 to "Jodn",
            4 to "McGrady",
            5 to "Bosh",
            6 to "Answer",
            7 to "Durant"
    )


    fun test(name: String = "kobe", age: Int = 34, height: Int = 198) {

        var a = if (name == "kobe"){
            ""
        } else {
            "james"
        }

        "name = ${name}"

        for ((k, v) in thirdMap) {

        }

    }

    fun txt(name: String?) {
        val n = name ?: ""
    }



    fun Button.log(): Int {
        test("james", age = 30)
        return 2
    }


    class InnerShare{


    }

}
