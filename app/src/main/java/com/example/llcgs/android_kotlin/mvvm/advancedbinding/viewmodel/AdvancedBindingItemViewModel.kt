package com.example.llcgs.android_kotlin.mvvm.advancedbinding.viewmodel

import android.view.View
import com.example.llcgs.android_kotlin.mvvm.advancedbinding.model.AdvancedBindingModel
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.advancedbinding.viewmodel.AdvancedBindingItemViewModel
 * @author liulongchao
 * @since 2017/11/20
 */
class AdvancedBindingItemViewModel:BaseViewModel() {

    var advancedBindingModel = AdvancedBindingModel()

    // TODO View onClick
    fun onItemClickListener(view: View){
        itemClickListener?.let {
            it(advancedBindingModel)
        }
    }

    private var itemClickListener: ((advancedBindingModel: AdvancedBindingModel) ->Unit)? = null

    fun setItemClickListener(listener: (advancedBindingModel: AdvancedBindingModel) ->Unit){
        this.itemClickListener = listener
    }
}