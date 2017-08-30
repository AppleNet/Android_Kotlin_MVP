package com.example.llcgs.android_kotlin.plugin.interceptor

import android.content.Context
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import com.example.llcgs.android_kotlin.home.bean.User
import com.lzh.nonview.router.extras.RouteBundleExtras
import com.lzh.nonview.router.interceptors.RouteInterceptor

/**
 * com.example.llcgs.android_kotlin.plugin.interceptor.PluginInterceptor
 * @author liulongchao
 * @since 2017/8/29
 */

/***
 *  拦截器 在插件中 需要写成共享类
 *  宿主调起了别的插件。
 *  然后别的插件需要恢复拦截器信息。
 *  但是拦截器类在别的插件中没有定义。
 *  就会找不到类导致恢复不了数据，这个时候 就会报Parcelable序列化异常
 *
 * */
class PluginInterceptor(var id:String, var user:User):RouteInterceptor, Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(User::class.java.classLoader)) {
    }

    override fun intercept(uri: Uri?, extras: RouteBundleExtras?, context: Context?): Boolean {
        return user.name.length != 7 && user.pwd != "123456"
    }

    override fun onIntercepted(uri: Uri?, extras: RouteBundleExtras?, context: Context?) {
        Toast.makeText(context, "用户名或密码错误，请重新登录", Toast.LENGTH_SHORT).show()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PluginInterceptor> {
        override fun createFromParcel(parcel: Parcel): PluginInterceptor {
            return PluginInterceptor(parcel)
        }

        override fun newArray(size: Int): Array<PluginInterceptor?> {
            return arrayOfNulls(size)
        }
    }
}