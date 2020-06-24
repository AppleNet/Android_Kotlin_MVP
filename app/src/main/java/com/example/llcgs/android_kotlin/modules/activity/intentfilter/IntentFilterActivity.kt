package com.example.llcgs.android_kotlin.modules.activity.intentfilter;

import android.os.Bundle
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.modules.activity.intentfilter.presenter.impl.IntentFilterPresenter
import com.example.llcgs.android_kotlin.modules.activity.intentfilter.view.IntentFilterView

/**
 * com.example.llcgs.android_kotlin.modules.activity.intentfilter.IntentFilterActivity
 * @author liulongchao
 * @since 2018/12/22
 */
class IntentFilterActivity: BaseActivity<IntentFilterView, IntentFilterPresenter>() {

    /**
     *  为了匹配过滤列表，需要同时匹配过滤列表中的action，category，data信息，否则匹配失败，一个过滤列表中的action，category，data
     *  可以有多个，所有的action，category，data分别构成不同类别，同一类别的信息共同约束当前类别的匹配过程。
     *  只有一个Intent同时匹配action，category，data才算完全匹配，只有完全匹配才能成功启动目标Activity。另外一点，一个Activity中可以
     *  有多个intent-filter，一个intent只要匹配任何一组intent-filter即可以成功启动对应的activity。
     *
     * */

    override fun createPresenter()= IntentFilterPresenter ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
