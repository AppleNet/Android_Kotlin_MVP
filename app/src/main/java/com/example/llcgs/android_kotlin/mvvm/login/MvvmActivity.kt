package com.example.llcgs.android_kotlin.mvvm.login

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import com.example.llcgs.android_kotlin.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.login.model.User
import com.example.llcgs.android_kotlin.mvvm.login.view.MvvmView
import com.example.llcgs.android_kotlin.mvvm.login.viewmodel.HomeViewModel
import com.example.llcgs.android_kotlin.mvvm.show.PhotoShowActivity
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.view_title.*

@RouterRule("mvvm")
class MvvmActivity : BaseActivity<HomeViewModel, ViewDataBinding>(), MvvmView {

    override fun createViewModel()= HomeViewModel(this)

    override fun createViewDataBinding(): ViewDataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this@MvvmActivity, R.layout.activity_mvvm)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.viewModel, viewModel)
        initViews()
    }

    private fun initViews(){
        pluginTitleTV.text = "Mvvm"
    }

    override fun onLoginSuccess(user: User) {
        //startActivity(Intent(this@MvvmActivity, MvvmListActivity::class.java))
        startActivity(Intent(this@MvvmActivity, PhotoShowActivity::class.java).apply {
            putExtra("imageUrl", "https://timgsa.baidu.com/timg?" +
                    "image&quality=80&" +
                    "size=b9999_10000&" +
                    "sec=1509790606182&di=df1422d4220d14ac87f5dc1a51ecd904&" +
                    "imgtype=0&" +
                    "src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3D1c7184c0d243ad4ba62e46c8b2035a89%2F478ca4c27d1ed21bcbb65780ae6eddc451da3f74.jpg")
        })
    }
}
