package com.example.llcgs.android_kotlin.classandobject.propertydelegate

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.classandobject.propertydelegate.presenter.impl.SeventeenPresenter
import com.example.llcgs.android_kotlin.classandobject.propertydelegate.view.SeventeenView

class SeventeenActivity : BaseActivity<SeventeenView, SeventeenPresenter>(), SeventeenView {

    override fun createPresenter()= SeventeenPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seventenn)
    }
}
