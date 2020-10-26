package com.example.llcgs.android_kotlin.customview;

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.customview.presenter.impl.CustomViewPresenter
import com.example.llcgs.android_kotlin.customview.selfdrawable.FishDrawable
import com.example.llcgs.android_kotlin.customview.view.CustomViewView
import kotlinx.android.synthetic.main.activity_fish.*

class FishActivity: BaseActivity<CustomViewView, CustomViewPresenter>()  {

    override fun createPresenter()= CustomViewPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fish)

        // fishImage.setImageDrawable(FishDrawable())
    }

}
