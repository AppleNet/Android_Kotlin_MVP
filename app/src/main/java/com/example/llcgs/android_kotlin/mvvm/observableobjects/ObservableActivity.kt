package com.example.llcgs.android_kotlin.mvvm.observableobjects

import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityObservableBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.observableobjects.model.ObservableModel
import com.example.llcgs.android_kotlin.mvvm.observableobjects.view.ObservableView
import com.example.llcgs.android_kotlin.mvvm.observableobjects.viewmodel.ObservableViewModel
import kotlinx.android.synthetic.main.activity_observable.*
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.mvvm.observableobjects.ObservableActivity
 * @author liulongchao
 * @since 2017/11/15
 */
class ObservableActivity:BaseActivity<ObservableViewModel, ActivityObservableBinding>() , (Observable?, View?) -> Unit {

    override fun createViewModel()= ObservableViewModel()

    override fun createViewDataBinding(): ActivityObservableBinding= DataBindingUtil.setContentView(this, R.layout.activity_observable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "observableObjects"
    }

    private fun initData(){
        viewModel.setOnClickListenerCallBack(this)
        binding.setVariable(BR.observableViewModel, viewModel)
    }

    override fun invoke(observable: Observable?, view: View?) {
        if (view != null){
            //  reset
            viewModel.observableModel.firstName = firstName.text.toString()
            viewModel.observableModel.lastName = lastName.text.toString()
        }
        if (observable != null && observable is ObservableModel){
            Toast.makeText(this, "firstName:${observable.firstName}, lastName:${observable.lastName}", Toast.LENGTH_SHORT).show()
        }
    }

}

