package com.example.llcgs.android_kotlin.material.douya.detail.fragment.view

import com.example.llcgs.android_kotlin.material.base.BaseMaterialView
import com.example.llcgs.android_kotlin.material.douya.detail.fragment.bean.LikeBroadcast

/**
 * com.example.llcgs.android_kotlin.material.detail.fragment.view.FragmentView
 * @author liulongchao
 * @since 2017/12/22
 */
interface FragmentView : BaseMaterialView {

    fun onGetLikers(list: List<com.example.llcgs.android_kotlin.material.douya.detail.fragment.bean.LikeBroadcast>)

    fun onGetRebroadcast(list: List<com.example.llcgs.android_kotlin.material.douya.detail.fragment.bean.LikeBroadcast>)
}