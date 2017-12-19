package com.example.llcgs.android_kotlin.material.gallery

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import com.example.llcgs.android_kotlin.material.gallery.adapter.GalleryAdapter
import com.example.llcgs.android_kotlin.material.gallery.presenter.IGalleryPresenter
import com.example.llcgs.android_kotlin.material.gallery.presenter.impl.GalleryPresenter
import com.example.llcgs.android_kotlin.material.gallery.view.GalleryView
import com.gomejr.myf.core.kotlin.logD
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_gallery.*
import me.zhanghai.android.systemuihelper.SystemUiHelper

/**
 * com.example.llcgs.android_kotlin.material.gallery.GalleryActivity
 * @author liulongchao
 * @since 2017/12/18
 */
class GalleryActivity : BaseMaterialActivity<IGalleryPresenter>(), GalleryView, () -> Unit, ViewPager.PageTransformer {

    private lateinit var mSystemUiHelper: SystemUiHelper
    private lateinit var adapter: GalleryAdapter
    private var mImageList = ArrayList<String>()
    private var position = 0

    override fun createPresenter() = GalleryPresenter(this)

    override fun getLayoutId() = R.layout.activity_gallery

    override fun initViews() {
        setSupportActionBar(toolbar)
        mPresenter.setSystemUiHelper(this, toolbar = toolbar)

        adapter = GalleryAdapter()
        adapter.setListener(this)
        viewPager.adapter = adapter
        viewPager.setPageTransformer(true, this)
        viewPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                updateTitle()
            }
        })
        updateTitle()
    }

    override fun initData() {
        mImageList = intent.getStringArrayListExtra("urlList")
        position = intent.getIntExtra("position", 0)
        adapter.mImageList = mImageList
        adapter.notifyDataSetChanged()
        viewPager.currentItem = position
    }

    override fun onGetSystemUiHelper(systemUiHelper: SystemUiHelper) {
        mSystemUiHelper = systemUiHelper
        systemUiHelper.show()
    }

    override fun invoke() {
        mSystemUiHelper.toggle()
    }

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width
        if (position < -1) {
            page.alpha = 0f
        } else if (position <= 0) {
            page.alpha = 1f
            page.translationX = 0f
            page.scaleX = 1f
            page.scaleY = 1f
        } else if (position <= 1) {
            page.alpha = 1 - position
            page.translationX = pageWidth * -position
            page.scaleX = (0.75f + (1 - 0.75) * (1 - Math.abs(position))).toFloat()
            page.scaleY = page.scaleX
        } else {
            page.alpha = 0f
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.gallery, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
            R.id.action_save ->{
                saveImage()
                return true
            }
            R.id.action_share ->{
                shareImage()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateTitle(){
        val size = mImageList.size
        if (size <= 1){
            title = "查看大图"
            return
        }
        title = getString(R.string.gallery_title_multiple_format, viewPager.currentItem + 1, size)
    }

    private fun saveImage(){
        val rxPermission = RxPermissions(this)
        rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe {
                    if (it){
                        Toast.makeText(this, "保存成功", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "需要读取SD卡权限，请授予相关权限", Toast.LENGTH_LONG).show()
                    }
                }
    }

    private fun shareImage(){
        val intent = Intent()
                .setAction(Intent.ACTION_SEND)
                .putExtra(Intent.EXTRA_TEXT, "分享图片")
                .putExtra(Intent.EXTRA_TITLE, "分享图片")
                .putExtra(Intent.EXTRA_SUBJECT, "分享图片")
                .putExtra("Kdescription", "分享图片")
                .putExtra(Intent.EXTRA_STREAM, Uri.parse(mImageList[viewPager.currentItem]))
                .setType("image/*")
        startActivity(Intent.createChooser(intent, "分享方式"))
    }
}