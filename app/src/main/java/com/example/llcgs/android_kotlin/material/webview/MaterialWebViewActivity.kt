package com.example.llcgs.android_kotlin.material.webview

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import com.example.llcgs.android_kotlin.material.webview.presenter.IMaterialWebViewPresenter
import com.example.llcgs.android_kotlin.material.webview.presenter.impl.MaterialWebViewPresenter
import kotlinx.android.synthetic.main.activity_material_webview.*
import kotlinx.android.synthetic.main.view_material_toolbar.*

/**
 * com.example.llcgs.android_kotlin.material.webview.MaterialWebViewActivity
 * @author liulongchao
 * @since 2017/12/12
 */
class MaterialWebViewActivity : BaseMaterialActivity<IMaterialWebViewPresenter>() {

    private var mTitleOrError: String = ""
    private var mDefaultUserAgent: String = ""
    private var mDesktopUserAgent: String = ""
    private var mGoForwardMenuItem: MenuItem? = null
    private var mOpenWithNativeMenuItem: MenuItem? = null
    private var mRequestDesktopSiteMenuItem: MenuItem? = null


    override fun createPresenter(): IMaterialWebViewPresenter = MaterialWebViewPresenter()

    override fun getLayoutId(): Int = R.layout.activity_material_webview

    override fun initViews() {
        setSupportActionBar(toolbar)
        updateToolbarTitleAndSubtitle()
    }

    override fun initData() {
        setupWebView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.webview, menu)
        mGoForwardMenuItem = menu.findItem(R.id.action_go_forward)
        updateGoForward()
        mOpenWithNativeMenuItem = menu.findItem(R.id.action_open_with_native)
        updateOpenWithNative(false)
        mRequestDesktopSiteMenuItem = menu.findItem(R.id.action_request_desktop_site)
        updateRequestDesktopSite(false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
            R.id.action_go_forward ->{
                webView.goForward()
                return true
            }
            R.id.action_reload ->{
                webView.reload()
                return true
            }
            R.id.action_copy_url ->{
                val clipData = ClipData.newPlainText(webView.title, webView.url)
                val systemService = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                systemService.primaryClip = clipData
                return true
            }
            R.id.action_share ->{
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, webView.url)
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intent, "分享方式"))
                return true
            }
            R.id.action_open_with_native ->{
                updateOpenWithNative(true)
                return true
            }
            R.id.action_request_desktop_site ->{
                updateRequestDesktopSite(true)
                return true
            }
            R.id.action_open_in_browser ->{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webView.url))
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val settings = webView.settings
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        settings.loadWithOverviewMode = true
        settings.javaScriptEnabled = true
        initializeUserAgents(settings)
        updateUserAgent(settings)
        settings.useWideViewPort = true
        webView.webChromeClient = ChromeClient()
        webView.webViewClient = ViewClient()
        onLoadUrl(webView)
    }

    private fun onLoadUrl(webView: WebView) {
        val url = intent.getStringExtra("EXTRA_URL")
        webView.loadUrl(url)
    }

    private fun updateToolbarTitleAndSubtitle() {
        val url = webView.url
        if (TextUtils.isEmpty(url)) {
            return
        }
        if (mTitleOrError.isEmpty()) {
            mTitleOrError = Uri.parse(url).host
        }
        val actionBar = supportActionBar
        title = mTitleOrError
        actionBar?.subtitle = url
    }

    private fun initializeUserAgents(settings: WebSettings) {
        mDefaultUserAgent = settings.userAgentString
        mDesktopUserAgent = mDefaultUserAgent.replaceFirst("(Linux;.*?)", "(X11; Linux x86_64)")
                .replace("Mobile Safari/", "Safari/")
    }

    private fun updateUserAgent(webSettings: WebSettings) {
        val oldUserAgent = webSettings.userAgentString
        var changed = false
        if (!TextUtils.equals(oldUserAgent, mDesktopUserAgent)) {
            webSettings.userAgentString = mDesktopUserAgent
            changed = true
        } else if (!TextUtils.equals(oldUserAgent, mDefaultUserAgent)) {
            webSettings.userAgentString = mDefaultUserAgent
            changed = true
        }
        val url = webView.url
        if (!TextUtils.isEmpty(url) && changed) {
            val doubanDesktopSiteUrl = getDoubanDesktopSiteUrl(url)
            if (!TextUtils.equals(url, doubanDesktopSiteUrl)) {
                webView.loadUrl(doubanDesktopSiteUrl)
            } else {
                webView.reload()
            }
        } else {
            webView.reload()
        }
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

    private fun updateGoForward(){
        if (mGoForwardMenuItem == null){
            return
        }
        mGoForwardMenuItem?.isEnabled = webView.canGoForward()
    }

    private fun updateOpenWithNative(boolean: Boolean) {
        if (mOpenWithNativeMenuItem == null) {
            return
        }
        mOpenWithNativeMenuItem?.isChecked = boolean
    }

    private fun updateRequestDesktopSite(boolean: Boolean) {
        if (mRequestDesktopSiteMenuItem == null) {
            return
        }
        mRequestDesktopSiteMenuItem?.isChecked = boolean
    }

    private fun onPageStarted(){
        updateGoForward()
        updateToolbarTitleAndSubtitle()
    }

    inner class ChromeClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            if (newProgress <= 100) {
                toolbar_progress.visibility = View.VISIBLE
            } else {
                toolbar_progress.visibility = View.GONE
            }
        }

        override fun onReceivedTitle(view: WebView?, title: String) {
            mTitleOrError = title
            updateToolbarTitleAndSubtitle()
        }
    }

    inner class ViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            this@MaterialWebViewActivity.onPageStarted()
        }
    }
}