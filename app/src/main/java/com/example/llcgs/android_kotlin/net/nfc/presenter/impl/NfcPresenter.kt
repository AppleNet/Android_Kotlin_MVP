package com.example.llcgs.android_kotlin.net.nfc.presenter.impl

import com.example.llcgs.android_kotlin.net.nfc.model.NfcModel
import com.example.llcgs.android_kotlin.net.nfc.presenter.INfcPresenter
import com.example.llcgs.android_kotlin.net.nfc.view.NfcView

/**
 * com.example.llcgs.android_kotlin.net.nfc.presenter.impl.NfcPresenter
 * @author liulongchao
 * @since 2018/5/2
 */
class NfcPresenter(private val view: NfcView): INfcPresenter {

    private val model = NfcModel()

}