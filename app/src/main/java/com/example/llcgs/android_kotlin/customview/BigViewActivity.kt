package com.example.llcgs.android_kotlin.customview

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.customview.presenter.impl.CustomViewPresenter
import com.example.llcgs.android_kotlin.customview.view.CustomViewView
import kotlinx.android.synthetic.main.activity_big_view.*

class BigViewActivity: BaseActivity<CustomViewView, CustomViewPresenter>()  {

    override fun createPresenter()= CustomViewPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_view)

        val inputStream = assets.open("house.jpeg");
        bigView.setImageFromInputStream(inputStream)
    }

}
