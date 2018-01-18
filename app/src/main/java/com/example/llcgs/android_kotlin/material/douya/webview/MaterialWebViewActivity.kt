package com.example.llcgs.android_kotlin.material.douya.webview

import android.content.Intent
import android.graphics.Bitmap
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import com.example.llcgs.android_kotlin.material.douya.webview.client.ChromeClient
import com.example.llcgs.android_kotlin.material.douya.webview.client.ViewClient
import com.example.llcgs.android_kotlin.material.douya.webview.presenter.IMaterialWebViewPresenter
import com.example.llcgs.android_kotlin.material.douya.webview.presenter.impl.MaterialWebViewPresenter
import com.example.llcgs.android_kotlin.material.douya.webview.view.MaterialWebViewView
import kotlinx.android.synthetic.main.activity_material_webview.*
import kotlinx.android.synthetic.main.view_material_toolbar.*

/**
 * com.example.llcgs.android_kotlin.material.webview.MaterialWebViewActivity
 * @author liulongchao
 * @since 2017/12/12
 */
class MaterialWebViewActivity : BaseMaterialActivity<IMaterialWebViewPresenter>(), MaterialWebViewView, (WebView, String) -> Unit, (WebView, String, Bitmap?) -> Unit {

    private var mGoForwardMenuItem: MenuItem? = null
    private var mOpenWithNativeMenuItem: MenuItem? = null
    private var mRequestDesktopSiteMenuItem: MenuItem? = null
    private var url:String = ""

    override fun createPresenter() = MaterialWebViewPresenter(this)

    override fun getLayoutId() = R.layout.activity_material_webview

    override fun initViews() {
        url = intent.getStringExtra("EXTRA_URL")
        setSupportActionBar(toolbar)
        mPresenter.updateToolbarTitleAndSubtitle(url, "")
    }

    override fun initData() {
        mPresenter.setupWebView(webView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.webview, menu)
        mGoForwardMenuItem = menu.findItem(R.id.action_go_forward).apply { isEnabled = webView.canGoForward() }
        mOpenWithNativeMenuItem = menu.findItem(R.id.action_open_with_native).apply { isChecked = false }
        mRequestDesktopSiteMenuItem = menu.findItem(R.id.action_request_desktop_site).apply { isChecked = false }
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
                mPresenter.copy(this, webView.title, webView.url)
                return true
            }
            R.id.action_share ->{
                mPresenter.share(webView.url)
                return true
            }
            R.id.action_open_with_native ->{
                mOpenWithNativeMenuItem?.isChecked = true
                return true
            }
            R.id.action_request_desktop_site ->{
                mRequestDesktopSiteMenuItem?.isChecked = true
                return true
            }
            R.id.action_open_in_browser ->{
                mPresenter.browser(webView.url)
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

    override fun onSetupWebViewSuccess(webView: WebView) {
        val chromeClient = ChromeClient(toolbar_progress).apply { setReceivedTitleListener(this@MaterialWebViewActivity) }
        webView.webChromeClient = chromeClient
        val viewClient = ViewClient().apply { setOnPageStated(this@MaterialWebViewActivity) }
        webView.webViewClient = viewClient
        mPresenter.loadUrl(webView, url)
    }

    override fun onUpdateToolbarTitleAndSubtitle(hostTitle: String, title: String, url: String) {
        val actionBar = supportActionBar
        this@MaterialWebViewActivity.title = title
        actionBar?.subtitle = url
    }

    override fun onShare(intent: Intent) {
        startActivity(Intent.createChooser(intent, "分享方式"))
    }

    override fun onBrowser(intent: Intent) {
        startActivity(intent)
    }

    override fun invoke(view: WebView, title: String) {
        mPresenter.updateToolbarTitleAndSubtitle(view.url, title)
    }

    override fun invoke(view: WebView, url: String, favicon: Bitmap?) {
        mGoForwardMenuItem?.isEnabled = view.canGoForward()
        mPresenter.updateToolbarTitleAndSubtitle(view.url, "")
    }
}