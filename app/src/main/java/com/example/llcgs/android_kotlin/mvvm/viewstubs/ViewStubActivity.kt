package com.example.llcgs.android_kotlin.mvvm.viewstubs

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityViewStubBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.viewstubs.model.ViewStubModel
import com.example.llcgs.android_kotlin.mvvm.viewstubs.view.ViewStubView
import com.example.llcgs.android_kotlin.mvvm.viewstubs.viewmodel.ViewStubViewModel
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.mvvm.viewstubs.ViewStubActivity
 * @author liulongchao
 * @since 2017/11/20
 */
class ViewStubActivity:BaseActivity<ViewStubViewModel, ActivityViewStubBinding>(), ViewStubView {

    private var inflate: View? = null

    override fun createViewModel()= ViewStubViewModel(this)

    override fun createViewDataBinding(): ActivityViewStubBinding= DataBindingUtil.setContentView(this, R.layout.activity_view_stub)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "viewStubs"
    }

    private fun initData(){
        viewModel.viewStubModel = ViewStubModel().apply {
            sub1 = "McGrady"
            sub2 = "38"
            sub3 = "American"
        }
        binding.setVariable(BR.viewStubViewModel, viewModel)
    }

    override fun onGetViews(view: View, viewStubModel: ViewStubModel) {
        when(view.id){
            R.id.unfold ->{
                if (!binding.viewStub.isInflated){
                    viewModel.viewStubModel = viewStubModel
                    inflate = binding.viewStub.viewStub.inflate()
                }else{
                    val linearLayout = inflate?.findViewById<LinearLayout>(R.id.subLayout)
                    if (linearLayout?.visibility == View.GONE) {
                        linearLayout.visibility = View.VISIBLE
                    } else {
                        linearLayout?.visibility = View.GONE
                    }
                }
            }
        }
    }
}