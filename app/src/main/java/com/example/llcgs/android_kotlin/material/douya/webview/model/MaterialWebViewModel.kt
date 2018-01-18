package com.example.llcgs.android_kotlin.material.douya.webview.model

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.webkit.WebView
import com.example.llcgs.android_kotlin.R.id.webView
import com.example.llcgs.android_kotlin.material.base.BaseMaterialModel
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_material_webview.*

/**
 * com.example.llcgs.android_kotlin.material.webview.model.MaterialWebViewModel
 * @author liulongchao
 * @since 2017/12/12
 */
class MaterialWebViewModel : BaseMaterialModel {

    @SuppressLint("SetJavaScriptEnabled")
    fun setupWebView(webView: WebView): Observable<WebView> {
        val settings = webView.settings
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        return Observable.just(webView)
    }

    fun loadUrl(webView: WebView, url: String) {
        webView.loadUrl(url)
    }

    fun updateToolbarTitleAndSubtitle(url: String): Observable<String> {
        if (TextUtils.isEmpty(url)) {
            return Observable.empty()
        }
        return Observable.just(Uri.parse(url).host)
    }

    fun share(url: String): Observable<Intent> {
        return Observable.just(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        })
    }

    fun browser(url: String): Observable<Intent> =
            Observable.just(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

    fun copy(context: Context, title: String, url: String){
        val clipData = ClipData.newPlainText(title, url)
        val systemService = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        systemService.primaryClip = clipData
    }
}