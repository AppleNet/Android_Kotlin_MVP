package com.example.llcgs.android_kotlin.material.main.fragment.home.view

import android.os.Bundle
import com.example.llcgs.android_kotlin.material.base.BaseMaterialView
import com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent

/**
 * com.example.llcgs.android_kotlin.material.main.fragment.home.view.HomeBroadcastListView
 * @author liulongchao
 * @since 2017/12/13
 */
interface HomeBroadcastListView : BaseMaterialView {

    fun onGetData(list: List<BroadListContent>)

    fun onGetBundle(bundle: Bundle)
}