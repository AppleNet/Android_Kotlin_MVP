package com.example.llcgs.android_kotlin.material.detail

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.example.llcgs.android_kotlin.material.detail.fragment.BroadcastFragment
import com.example.llcgs.android_kotlin.material.main.fragment.home.bean.BroadListContent

/**
 * com.example.llcgs.android_kotlin.material.detail.DetailActivity
 * @author liulongchao
 * @since 2017/12/14
 */
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.sharedElementsUseOverlay = false
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        findViewById<View>(android.R.id.content)
        ActivityCompat.postponeEnterTransition(this)
        if (savedInstanceState == null){
            val broadcastId = intent.getIntExtra("id", -1)
            val broadcast = intent.getParcelableExtra<BroadListContent>("broadcast")
            val showComment = intent.getBooleanExtra("show_send_comment", false)
            val title = intent.getStringExtra("title")
            supportFragmentManager.beginTransaction()
                    .add(android.R.id.content, BroadcastFragment.setData(broadcastId, broadcast, showComment, title), null)
                    .commit()
        }
    }

}