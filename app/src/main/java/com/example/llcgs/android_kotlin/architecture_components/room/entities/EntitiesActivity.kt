package com.example.llcgs.android_kotlin.architecture_components.room.entities

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.room.entities.presenter.IEntitiesPresenter
import com.example.llcgs.android_kotlin.architecture_components.room.entities.presenter.impl.EntitiesPresenter

/**
 * com.example.llcgs.android_kotlin.architecture_components.room.entities.EntitiesActivity
 * @author liulongchao
 * @since 2017/12/8
 */
class EntitiesActivity: BaseOwnerActivity<IEntitiesPresenter>() {



    override fun createPresenter(): IEntitiesPresenter = EntitiesPresenter()

    override fun getLayoutId(): Int = R.layout.activity_entities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}