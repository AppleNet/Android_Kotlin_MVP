package com.example.llcgs.android_kotlin.material.detail.view

import com.example.llcgs.android_kotlin.material.base.BaseMaterialView
import com.example.llcgs.android_kotlin.material.detail.bean.Comment

/**
 * com.example.llcgs.android_kotlin.material.detail.view.DetailView
 * @author liulongchao
 * @since 2017/12/14
 */
interface DetailView : BaseMaterialView {

    fun onGetComments(list: List<Comment>)
}