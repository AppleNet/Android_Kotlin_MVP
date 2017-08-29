package com.example.llcgs.android_kotlin.home.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * User
 * @author liulongchao
 * @since 2017/5/18
 */


data class User (var name:String, var pwd:String) : Parcelable {

    fun User() {}

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(pwd)
    }
}