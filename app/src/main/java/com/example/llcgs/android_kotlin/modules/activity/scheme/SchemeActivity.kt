package com.example.llcgs.android_kotlin.modules.activity.scheme

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.activity.scheme.presenter.impl.SchemePresenter
import com.example.llcgs.android_kotlin.modules.activity.scheme.view.SchemeView
import com.example.llcgs.android_kotlin.utils.log.logD

/**
 * com.example.llcgs.android_kotlin.modules.activity.scheme.SchemeActivity
 * @author liulongchao
 * @since 2018/11/10
 */
class SchemeActivity: BaseActivity<SchemeView, SchemePresenter>() {

    override fun createPresenter()= SchemePresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheme)

        // this.getIntent().getScheme(); //获得Scheme名称
        // this.getIntent().getDataString(); //获得Uri全部路径

        val intent = intent
        if (intent != null){
            val scheme: String = intent.scheme
            val dataString: String = intent.dataString
            if (!TextUtils.isEmpty(scheme) && !TextUtils.isEmpty(dataString)){
                Toast.makeText(this, "scheme: $scheme, dataString: $dataString", Toast.LENGTH_LONG).show()
            }
            val uri: Uri = intent.data // 获取Uri
            val pathSegments = uri.pathSegments
            pathSegments.forEach {
                it.logD()
            }
        }

    }
}