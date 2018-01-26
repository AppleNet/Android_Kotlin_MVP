package com.example.llcgs.android_kotlin.material.mediaplayer.base

import android.app.ActivityOptions
import android.app.FragmentManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.MediaRouteButton
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import com.example.llcgs.android_kotlin.material.base.BaseMaterialPresenter
import com.example.llcgs.android_kotlin.material.mediaplayer.player.MusicPlayerActivity
import com.example.llcgs.android_kotlin.material.mediaplayer.placeholder.PlaceHolderActivity
import com.google.android.gms.cast.framework.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_media_toolbar.*
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.material.mediaplayer.base.ActionBarCastActivity
 * @author liulongchao
 * @since 2018/1/19
 */
abstract class ActionBarCastActivity<P: BaseMaterialPresenter>: BaseMaterialActivity<P>() {

    private val TAG = ActionBarCastActivity::class.java.simpleName
    private var mCastContext: CastContext? = null
    private var mMediaRouteMenuItem: MenuItem? = null
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var mToolbarInitialized: Boolean = false
    private var mItemToOpenWhenDrawerCloses: Int = -1

    private val mCastStateListener = CastStateListener {
        if (it != CastState.NO_DEVICES_AVAILABLE){
            Observable.just(mMediaRouteMenuItem)
                    .delay(1, TimeUnit.SECONDS)
                    .flatMap {
                        var overlay: IntroductoryOverlay? = null
                        if (it.isVisible){
                            overlay = showFtu()
                        }
                        Observable.just(overlay)

                    }.subscribe {
                        it.show()
                    }
        }
    }

    private val mBackStackChangedListener = FragmentManager.OnBackStackChangedListener { updateDrawerToggle() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if (playServicesAvailable == ConnectionResult.SUCCESS){
            mCastContext= CastContext.getSharedInstance(this)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (mDrawerToggle != null){
            mDrawerToggle?.syncState()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mCastContext != null){
            mCastContext?.addCastStateListener(mCastStateListener)
        }
        fragmentManager.addOnBackStackChangedListener(mBackStackChangedListener)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (mDrawerToggle != null){
            mDrawerToggle?.onConfigurationChanged(newConfig)
        }
    }

    override fun onPause() {
        super.onPause()
        if (mCastContext != null){
            mCastContext?.removeCastStateListener(mCastStateListener)
        }
        fragmentManager.removeOnBackStackChangedListener(mBackStackChangedListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        if (mCastContext != null){
            mMediaRouteMenuItem = CastButtonFactory.setUpMediaRouteButton(applicationContext, menu, R.id.media_route_menu_item)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (mDrawerToggle != null && mDrawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        }
        if (item != null && item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            mDrawerLayout?.closeDrawers()
            return
        }
        if (fragmentManager.backStackEntryCount > 0){
            fragmentManager.popBackStack()
        }else{
            super.onBackPressed()
        }
    }

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        mToolbar?.setTitle(titleId)
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        mToolbar?.title = title
    }

    protected fun initializeToolbar(){
        mToolbar.inflateMenu(R.menu.main)
        mDrawerLayout = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (mDrawerLayout != null){
            val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
            mDrawerToggle = ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_content_drawer, R.string.close_content_drawer)
            mDrawerLayout?.setDrawerListener(object : DrawerLayout.DrawerListener{
                override fun onDrawerStateChanged(newState: Int) {
                    if (mDrawerToggle != null) mDrawerToggle?.onDrawerStateChanged(newState)
                }

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    if (mDrawerToggle != null) mDrawerToggle?.onDrawerSlide(drawerView, slideOffset)
                }

                override fun onDrawerClosed(drawerView: View) {
                    if (mDrawerToggle != null) mDrawerToggle?.onDrawerClosed(drawerView)
                    if (mItemToOpenWhenDrawerCloses >= 0){
                        val extras = ActivityOptions.makeCustomAnimation(this@ActionBarCastActivity, R.anim.anim_fade_in, R.anim.anim_fade_out).toBundle()
                        var activityClass: Class<*>? = null
                        when(mItemToOpenWhenDrawerCloses){
                            R.id.navigation_allmusic ->{
                                activityClass = MusicPlayerActivity::class.java
                            }
                            R.id.navigation_playlists ->{
                                activityClass = PlaceHolderActivity::class.java
                            }
                        }
                        if (activityClass != null){
                            startActivity(Intent(this@ActionBarCastActivity, activityClass), extras)
                            finish()
                        }
                    }
                }

                override fun onDrawerOpened(drawerView: View) {
                    if (mDrawerToggle != null) mDrawerToggle?.onDrawerOpened(drawerView)
                    supportActionBar?.title = resources.getString(R.string.app_name)
                }
            })
            populateDrawerItems(navigationView)
            setSupportActionBar(mToolbar)
            updateDrawerToggle()
        }else{
            setSupportActionBar(mToolbar)
        }
        mToolbarInitialized = true
    }

    private fun populateDrawerItems(navigationView: NavigationView){
        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            mItemToOpenWhenDrawerCloses = menuItem.itemId
            mDrawerLayout?.closeDrawers()
            true
        }
        if (MusicPlayerActivity::class.java.isAssignableFrom(javaClass)) {
            navigationView.setCheckedItem(R.id.navigation_allmusic)
        } else if (PlaceHolderActivity::class.java.isAssignableFrom(javaClass)) {
            navigationView.setCheckedItem(R.id.navigation_playlists)
        }
    }

    private fun updateDrawerToggle(){
        if (mDrawerToggle == null) {
            return
        }
        val isRoot = fragmentManager.backStackEntryCount == 0
        mDrawerToggle?.isDrawerIndicatorEnabled = isRoot
        supportActionBar?.setDisplayHomeAsUpEnabled(!isRoot)
        supportActionBar?.setDisplayShowHomeEnabled(!isRoot)
        supportActionBar?.setHomeButtonEnabled(!isRoot)
        if(isRoot){
            mDrawerToggle?.syncState()
        }
    }

    private fun showFtu(): IntroductoryOverlay?{
        val menu = mToolbar?.menu
        val view = menu?.findItem(R.id.media_route_menu_item)?.actionView
        var overlay: IntroductoryOverlay? = null
        if (view != null && view is MediaRouteButton){
            overlay = IntroductoryOverlay.Builder(this, mMediaRouteMenuItem)
                    .setTitleText("Touch to connect to a Google Cast device")
                    .setSingleTime()
                    .build()
        }
        return overlay
    }
}