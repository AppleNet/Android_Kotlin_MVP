/**
 * Copyright 2016 Erik Jhordan Rey.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.llcgs.android_kotlin.mvvm.list.model

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Picture : Serializable {

    @SerializedName("large")
    var large: String = ""

    @SerializedName("medium")
    var medium: String = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E9%BA%A6%E8%BF%AA&step_word=&hs=0&pn=1&spn=0&di=4323729630&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=627493850%2C2098988723&os=1735371919%2C1300600316&simid=3454780277%2C547336315&adpicid=0&lpn=0&ln=1989&fr=&fmq=1509073398662_R&fm=detail&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2F8e5d3f29a1fc72dc99250a0a.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fzit1w5_z%26e3Bkwt17_z%26e3Bv54AzdH3Fq7jfpt5gAzdH3F8mmlcmlmd_z%26e3Bip4s&gsm=&rpstart=0&rpnum=0"

    @SerializedName("thumbnail")
    var thumbnail: String = ""
}
