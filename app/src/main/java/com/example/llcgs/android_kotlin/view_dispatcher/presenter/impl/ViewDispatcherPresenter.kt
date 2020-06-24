

package com.example.llcgs.android_kotlin.view_dispatcher.presenter.impl;

import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.view_dispatcher.callback.ViewDispatcherCallback
import com.example.llcgs.android_kotlin.view_dispatcher.model.ViewDispatcherModel
import com.example.llcgs.android_kotlin.view_dispatcher.presenter.IViewDispatcherPresenter
import com.example.llcgs.android_kotlin.view_dispatcher.view.ViewDispatcherView

/*
 * com.example.llcgs.android_kotlin.view_dispatcher.presenter.impl.ViewDispatcherPresenter
 * @author liulongchao
 * @since 2018/12/12
 */
class ViewDispatcherPresenter(var view: ViewDispatcherView?): BasePresenter<ViewDispatcherView>(), IViewDispatcherPresenter {

    val model = ViewDispatcherModel()

    fun viewDispatcher(){
        model.viewDispatcher(object : ViewDispatcherCallback{
            override fun getResult() {
                view?.onViewDispatcher()
            }
        })
    }
}
