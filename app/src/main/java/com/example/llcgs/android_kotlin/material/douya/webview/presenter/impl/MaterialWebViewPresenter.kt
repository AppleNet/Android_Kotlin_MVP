package com.example.llcgs.android_kotlin.material.douya.webview.presenter.impl

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.webkit.WebView
import com.example.llcgs.android_kotlin.material.douya.webview.model.MaterialWebViewModel
import com.example.llcgs.android_kotlin.material.douya.webview.presenter.IMaterialWebViewPresenter
import com.example.llcgs.android_kotlin.material.douya.webview.view.MaterialWebViewView

/**
 * com.example.llcgs.android_kotlin.material.webview.presenter.impl.MaterialWebViewPresenter
 * @author liulongchao
 * @since 2017/12/12
 */
class MaterialWebViewPresenter(private val view: MaterialWebViewView): IMaterialWebViewPresenter {

    private val model = MaterialWebViewModel()

    override fun setupWebView(webView: WebView) {
        model.setupWebView(webView)
                .map {
                    val mDefaultUserAgent = it.settings.userAgentString
                    val mDesktopUserAgent = mDefaultUserAgent.replaceFirst("(Linux;.*?)", "(X11; Linux x86_64)")
                            .replace("Mobile Safari/", "Safari/")
                    val oldUserAgent = it.settings.userAgentString
                    var changed = false
                    if (!TextUtils.equals(oldUserAgent, mDesktopUserAgent)) {
                        it.settings.userAgentString = mDesktopUserAgent
                        changed = true
                    } else if (!TextUtils.equals(oldUserAgent, mDefaultUserAgent)) {
                        it.settings.userAgentString = mDefaultUserAgent
                        changed = true
                    }
                    changed
                }
                .map {
                    val url = webView.url
                    if (!TextUtils.isEmpty(url) && it) {
                        val doubanDesktopSiteUrl = getDoubanDesktopSiteUrl(url)
                        if (!TextUtils.equals(url, doubanDesktopSiteUrl)) {
                            webView.loadUrl(doubanDesktopSiteUrl)
                        } else {
                            webView.reload()
                        }
                    } else {
                        webView.reload()
                    }
                    webView
                }
                .subscribe {
                    view.onSetupWebViewSuccess(it)
                }
    }

    override fun loadUrl(webView: WebView, url: String) {
        model.loadUrl(webView, url)
    }

    override fun updateToolbarTitleAndSubtitle(url: String, title: String) {
        model.updateToolbarTitleAndSubtitle(url)
                .subscribe {
                    // hostTitle, title, url
                    if (title.isEmpty()){
                        view.onUpdateToolbarTitleAndSubtitle(it, it, url)
                    }else{
                        view.onUpdateToolbarTitleAndSubtitle(it, title, url)
                    }
                }

    }

    override fun share(url: String) {
        model.share(url)
                .subscribe {
                    view.onShare(it)
                }
    }

    override fun browser(url: String) {
        model.browser(url)
                .subscribe {
                    view.onBrowser(it)
                }
    }

    override fun copy(context: Context, title: String, url: String) {
        model.copy(context, title, url)
    }

    private fun getDoubanDesktopSiteUrl(url: String): String {
        val uri = Uri.parse(url)
        return if (!TextUtils.equals(uri.host, "m.douban.com")) {
            url
        } else uri.buildUpon()
                .path("/to_pc/")
                .appendQueryParameter("url", url)
                .build()
                .toString()
    }
}