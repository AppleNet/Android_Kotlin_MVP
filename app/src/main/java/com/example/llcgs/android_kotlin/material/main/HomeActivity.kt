package com.example.llcgs.android_kotlin.material.main

import android.content.Intent
import android.graphics.Color
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import com.example.llcgs.android_kotlin.material.main.adapter.TabFragmentPagerAdapter
import com.example.llcgs.android_kotlin.material.main.fragment.home.HomeBroadcastListFragment
import com.example.llcgs.android_kotlin.material.main.fragment.NotYetImplementedFragment
import com.example.llcgs.android_kotlin.material.main.presenter.IHomePresenter
import com.example.llcgs.android_kotlin.material.main.presenter.impl.HomePresenter
import com.example.llcgs.android_kotlin.material.webview.MaterialWebViewActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.view_navigation.*

/**
 * com.example.llcgs.android_kotlin.material.main.HomeActivity
 * @author liulongchao
 * @since 2017/12/12
 */
class HomeActivity : BaseMaterialActivity<IHomePresenter>(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private lateinit var adapter: TabFragmentPagerAdapter
    private var mNotificationMenuItem: MenuItem?= null

    override fun createPresenter() = HomePresenter()

    override fun getLayoutId() = R.layout.activity_home

    override fun initViews() {
        toolBar.setTitleTextColor(Color.parseColor("#ffffff")) //设置标题颜色
        setSupportActionBar(toolBar)
        // DrawerLayout Listener
        mDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.open, R.string.close) {}
        mDrawerToggle.syncState()
        drawerLayout.addDrawerListener(mDrawerToggle)
        drawerLayout.right = 200
        navigation.setNavigationItemSelectedListener(this)
        setupAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_main, menu)

        mNotificationMenuItem = menu.findItem(R.id.action_notification)
        val actionView = mNotificationMenuItem?.actionView
        actionView?.setOnClickListener { onMenuItemSelected(Window.FEATURE_OPTIONS_PANEL, mNotificationMenuItem) }
        val imageView = actionView?.findViewById<ImageView>(R.id.icon)
        imageView?.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.notifications_icon_white_24dp))
        val textView = actionView?.findViewById<TextView>(R.id.badge)
        textView?.text = "10"

        val mDouMailMenuItem = menu.findItem(R.id.action_doumail)
        val aView = mDouMailMenuItem.actionView
        aView.setOnClickListener { onMenuItemSelected(Window.FEATURE_OPTIONS_PANEL, mDouMailMenuItem) }
        val image = aView.findViewById<ImageView>(R.id.icon)
        image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.mail_icon_white_24dp))

        return super.onCreateOptionsMenu(menu)
    }


    /**
     *  菜单的点击事件
     *
     * */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                return true
            }
            R.id.action_notification ->{
                // 通知
                return true
            }
            R.id.action_doumail ->{
                val intent = Intent(this, MaterialWebViewActivity::class.java)
                intent.putExtra("EXTRA_URL", "https://www.douban.com/search")
                startActivity(intent)
            }
            R.id.action_search ->{
                val intent = Intent(this, MaterialWebViewActivity::class.java)
                intent.putExtra("EXTRA_URL", "https://www.douban.com/doumail/")
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    /**
     *  导航菜单的点击事件
     *
     * */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.navigation_home ->{
                //home
                drawerLayout.closeDrawer(Gravity.START)
            }
            R.id.navigation_book ->{
                // book
            }
            R.id.navigation_movie ->{
                // 电影
                val intent = Intent(this, MaterialWebViewActivity::class.java)
                intent.putExtra("EXTRA_URL", "https://movie.douban.com/")
                startActivity(intent)
            }
            R.id.navigation_music ->{
                //music
            }
            R.id.navigation_settings ->{
                // setting
            }
            R.id.navigation_feedback ->{
                // feedback
            }
        }
        return true
    }

    private fun setupAdapter(){

        adapter = TabFragmentPagerAdapter(supportFragmentManager)
        adapter.addTab(object : TabFragmentPagerAdapter.FragmentCreator {
            override fun createFragment(): Fragment = HomeBroadcastListFragment()
        },"友邻广播")

        adapter.addTab(object : TabFragmentPagerAdapter.FragmentCreator {
            override fun createFragment(): Fragment = NotYetImplementedFragment()
        },"浏览发现")

        adapter.addTab(object : TabFragmentPagerAdapter.FragmentCreator {
            override fun createFragment(): Fragment = NotYetImplementedFragment()
        },"话题广场")

        adapter.addTab(object : TabFragmentPagerAdapter.FragmentCreator {
            override fun createFragment(): Fragment = NotYetImplementedFragment()
        },"线上活动")

        viewPager.offscreenPageLimit= 0
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

}