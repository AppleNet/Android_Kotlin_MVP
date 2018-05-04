package com.example.llcgs.android_kotlin.net.nfc

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.net.base.BaseNetWorkActivity
import com.example.llcgs.android_kotlin.net.nfc.presenter.INfcPresenter
import com.example.llcgs.android_kotlin.net.nfc.presenter.impl.NfcPresenter
import com.example.llcgs.android_kotlin.net.nfc.view.NfcView

/**
 * com.example.llcgs.android_kotlin.net.nfc.NfcActivity
 * @author liulongchao
 * @since 2018/5/2
 */
class NfcActivity: BaseNetWorkActivity<INfcPresenter>(), NfcView {

    override fun createPresenter(): INfcPresenter = NfcPresenter(this)

    override fun getLayoutId()= R.layout.activity_nfc

    override fun initViews() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}