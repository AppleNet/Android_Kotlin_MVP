package com.example.llcgs.android_kotlin.net.webservice

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkActivity
import com.example.llcgs.android_kotlin.net.webservice.presenter.IWebServicePresenter
import com.example.llcgs.android_kotlin.net.webservice.presenter.impl.WebServicePresenter
import com.example.llcgs.android_kotlin.net.webservice.view.WebServiceView
import kotlinx.android.synthetic.main.activity_webservice.*

/**
 * com.example.llcgs.android_kotlin.net.webservice.WebServiceActivity
 * @author liulongchao
 * @since 2018/4/21
 */
class WebServiceActivity: BaseNetWorkActivity<IWebServicePresenter>(), WebServiceView, View.OnClickListener {

    override fun createPresenter(): IWebServicePresenter= WebServicePresenter(this)

    override fun getLayoutId()= R.layout.activity_webservice

    override fun initViews() {
        button49.setOnClickListener(this)
    }

    override fun onShowWeather(string: String) {
        textView6.text = string
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.button49 ->{
                mPresenter.getWeather(editText4.text.toString())
            }
        }
    }
}