package com.example.llcgs.android_kotlin.mvvm.show

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityPhotoShowBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.show.viewmodel.PhotoShowViewModel
import com.gomejr.myf.core.kotlin.logD
import kotlinx.android.synthetic.main.view_title.*
import java.util.*

/**
 * com.example.llcgs.android_kotlin.mvvm.show.PhotoShowActivity
 * @author liulongchao
 * @since 2017/10/31
 */


class PhotoShowActivity:BaseActivity<PhotoShowViewModel, ActivityPhotoShowBinding>() {

    override fun createViewModel()= PhotoShowViewModel()

    override fun createViewDataBinding(): ActivityPhotoShowBinding= DataBindingUtil.setContentView(this, R.layout.activity_photo_show)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "Show"
        viewModel.addObserver(this)
    }

    private fun initData(){
        val url = intent.getStringExtra("imageUrl")
        viewModel.url = url
        val variable = binding.setVariable(BR.photoShowViewModel, viewModel)
        variable.logD()
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is PhotoShowViewModel){
            "url: ${o.url} ".logD()
        }
    }

}