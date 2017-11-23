package com.example.llcgs.android_kotlin.mvvm.attributesetters.viewmodel

import android.view.View
import com.example.llcgs.android_kotlin.mvvm.attributesetters.model.MenuAdapterModel
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.attributesetters.viewmodel.MenuAdapterViewModel
 * @author liulongchao
 * @since 2017/11/22
 */
class MenuAdapterViewModel : BaseViewModel() {

    var model = MenuAdapterModel()

    // RecyclerView的item 点击事件
    fun menuAdapterItemClickListener(view: View) {
        listener?.let {
            it(model)
        }
    }

    private var listener: ((model: MenuAdapterModel) -> Unit)? = null

    fun setListener(listener: ((model: MenuAdapterModel) -> Unit)){
        this.listener = listener
    }
}