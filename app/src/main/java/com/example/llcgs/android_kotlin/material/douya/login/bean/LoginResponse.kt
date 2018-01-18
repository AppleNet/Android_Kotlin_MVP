package com.example.llcgs.android_kotlin.material.douya.login.bean

import com.google.gson.annotations.SerializedName

/**
 * com.example.llcgs.android_kotlin.material.login.bean.LoginResponse
 * @author liulongchao
 * @since 2017/12/11
 */
class LoginResponse {

    @SerializedName("access_token")
    var accessToken: String =""

    @SerializedName("douban_user_name")
    var userName: String =""

    @SerializedName("douban_user_id")
    var userId: Long = 0

    @SerializedName("expires_in")
    var accessTokenExpiresIn: Long = 0

    @SerializedName("refresh_token")
    var refreshToken: String =""
}