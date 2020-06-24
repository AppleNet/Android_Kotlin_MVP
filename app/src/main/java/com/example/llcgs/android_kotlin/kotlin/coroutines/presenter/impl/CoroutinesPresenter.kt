package com.example.llcgs.android_kotlin.kotlin.coroutines.presenter.impl;

import com.example.llcgs.android_kotlin.base.presenter.BasePresenter
import com.example.llcgs.android_kotlin.kotlin.coroutines.launch
import com.example.llcgs.android_kotlin.kotlin.coroutines.model.CoroutinesModel
import com.example.llcgs.android_kotlin.kotlin.coroutines.presenter.ICoroutinesPresenter
import com.example.llcgs.android_kotlin.kotlin.coroutines.view.CoroutinesView
import kotlinx.coroutines.Dispatchers

class CoroutinesPresenter(private var coroutinesView: CoroutinesView): BasePresenter<CoroutinesView>(), ICoroutinesPresenter {

    private val model = CoroutinesModel()

    override fun getUser(user: String){

        launch(Dispatchers.Main) {
            coroutinesView.onGetUser(model.getUser())
        }

    }

}
