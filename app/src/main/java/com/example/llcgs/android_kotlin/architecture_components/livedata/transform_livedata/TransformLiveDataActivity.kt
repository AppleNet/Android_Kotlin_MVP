package com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.Transformations
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ViewSwitcher
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.presenter.ITransformLiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.presenter.impl.TransformLiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.view.TransformLiveDataView
import com.gomejr.myf.core.kotlin.logD
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_transform_livedata.*
import kotlinx.android.synthetic.main.view_title.*

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.transform_livedata.TransformLiveDataActivity
 * @author liulongchao
 * @since 2017/11/29
 */

class TransformLiveDataActivity : BaseOwnerActivity<ITransformLiveDataPresenter>(), ViewSwitcher.ViewFactory, TransformLiveDataView, Observer<String> {

    private var liveData = MutableLiveData<String>()
    private var tLiveData = MutableLiveData<Transform>()

    override fun createPresenter(): ITransformLiveDataPresenter= TransformLiveDataPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_transform_livedata

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews(){
        pluginTitleTV.text = "TransformLiveData"
        textSwitcher.setFactory(this)

        // TODO Transform
        Transformations.map(liveData) { string ->
            "switcher text: " + string
        }.observe(this, this)

        RxView.clicks(start).subscribe {
           mPresenter.getContents(resources.getStringArray(R.array.arch_menu))
        }

        Transformations.switchMap(liveData) { input ->
            val liveData = MutableLiveData<Transform>()
            liveData.value = Transform()
                    .apply {
                        content = "Transform Content: " + input
                    }
            liveData
        }.observe(this, Observer<Transform> {
            start.text = it?.content
        })

    }

    override fun makeView(): View {
        return TextView(this).apply {
            setTextColor(Color.parseColor("#FB4A4A"))
            textSize = 16F
        }
    }

    override fun onGetContent(string: String) {
        string.logD()
        liveData.value = string
    }

    override fun onChanged(t: String?) {
        textSwitcher.setText(t)
    }


    class Transform{
        var content:String = ""
    }
}