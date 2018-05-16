package com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata

import android.arch.lifecycle.Observer
import android.os.Build
import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.architecture_components.base.BaseOwnerActivity
import com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.ld.SecondsLiveData
import com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.presenter.IExtendLiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.presenter.impl.ExtendLiveDataPresenter
import com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.view.ExtendLiveDataView
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.activity_extend_livedata.*
import kotlinx.android.synthetic.main.view_title.*
import java.util.*

/**
 * com.example.llcgs.android_kotlin.architecture_components.livedata.extend_livedata.ExtendLiveDataActivity
 * @author liulongchao
 * @since 2017/11/29
 */
class ExtendLiveDataActivity : BaseOwnerActivity<IExtendLiveDataPresenter>(), ExtendLiveDataView, Observer<Long> {

    private lateinit var sLiveData: SecondsLiveData

    override fun createPresenter(): IExtendLiveDataPresenter = ExtendLiveDataPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_extend_livedata

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews() {
        pluginTitleTV.text = "Extend LiveData"
        timePicker.setIs24HourView(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.hour = Calendar.getInstance().get(Calendar.HOUR)
        }else{
            timePicker.currentHour = Calendar.getInstance().get(Calendar.HOUR)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.minute = Calendar.getInstance().get(Calendar.MINUTE)
        }else{
            timePicker.currentMinute = Calendar.getInstance().get(Calendar.MINUTE)
        }
        progressBar.progress = Calendar.getInstance().get(Calendar.SECOND)
        progressBar.progress.logD()
        mPresenter.getSeconds(progressBar.progress.toLong())
    }

    private fun initData() {
        sLiveData = SecondsLiveData()
        sLiveData.observe(this, this)

        // SingletonSecondsLiveData.observe(this, Observer<Long> { t -> SingletonSecondsLiveData.postSecond(t?:0) })
    }

    override fun onGetSeconds(mLong: Long) {
        sLiveData.postSecond(mLong)
    }

    override fun onChanged(t: Long?) {
        // Update the UI
        progressBar.progress = t?.toInt() ?: 0
        if (progressBar.progress == 60) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.hour = Calendar.getInstance().get(Calendar.HOUR)
            }else{
                timePicker.currentHour = Calendar.getInstance().get(Calendar.HOUR)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.minute = Calendar.getInstance().get(Calendar.MINUTE)
            }else{
                timePicker.currentMinute = Calendar.getInstance().get(Calendar.MINUTE)
            }
        }
    }
}