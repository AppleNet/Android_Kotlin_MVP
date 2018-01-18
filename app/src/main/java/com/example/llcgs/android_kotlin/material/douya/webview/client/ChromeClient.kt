package com.example.llcgs.android_kotlin.material.douya.webview.client

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar

/**
 * com.example.llcgs.android_kotlin.material.webview.client.ChromeClient
 * @author liulongchao
 * @since 2017/12/12
 */
class ChromeClient(private val progressBar: ProgressBar) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        if (newProgress <= 100) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onReceivedTitle(view: WebView, title: String) {
        receivedTitleListener?.let {
            it(view, title)
        }
    }

    private var receivedTitleListener: ((view: WebView, title:String) -> Unit)? = null

    fun setReceivedTitleListener(listener: ((view: WebView, title:String) -> Unit)){
        receivedTitleListener = listener
    }

}