package com.example.llcgs.android_kotlin.material.animation.activity_options

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.activity_options.adapter.ActivityOptionsAdapter
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.IActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.impl.ActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.view.ActivityOptionsView
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_activity_options.*
import kotlinx.android.synthetic.main.view_activity_options_footer.*
import kotlinx.android.synthetic.main.view_broadcast_layout.*
import kotlinx.android.synthetic.main.view_material_toolbar.*
import java.util.ArrayList

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.ActivityOptionsActivity
 * @author liulongchao
 * @since 2018/1/8
 */
class ActivityTransitionActivity : BaseMaterialActivity<IActivityOptionsPresenter>(), BaseQuickAdapter.OnItemClickListener, ActivityOptionsView {

    private lateinit var adapter: ActivityOptionsAdapter
    override fun createPresenter()= ActivityOptionsPresenter(this)

    override fun getLayoutId()= R.layout.activity_activity_options

    override fun initViews() {
        // 跳转下一个界面的动画
        setupWindowAnimations()

        toolbar.title = "ActivityTransitions"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)

        adapter = ActivityOptionsAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this).apply { orientation = LinearLayout.VERTICAL }
        recyclerView.setHasFixedSize(true)
        adapter.onItemClickListener = this

        mPresenter.getActivityTransition()
    }

    private fun setupWindowAnimations(){
        val slideTransition = Slide()
        slideTransition.slideEdge = Gravity.START
        slideTransition.duration = 500.toLong()
        window.reenterTransition = slideTransition
        window.exitTransition = slideTransition
    }

    override fun onGetActivityOptions(list: List<String>) {
        adapter.setNewData(list)
    }

    override fun onGetActivityTransition(list: List<String>) {
        adapter.setNewData(list)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
        val sharedElementList = ArrayList<Pair<View, String>>()
        val decor = window.decorView
        val statusBar = decor.findViewById<View>(android.R.id.statusBarBackground)
        val navBar = decor.findViewById<View>(android.R.id.navigationBarBackground)
        if (statusBar != null){
            sharedElementList.add(Pair.create(statusBar, statusBar.transitionName))
        }
        if (navBar != null){
            sharedElementList.add(Pair.create(navBar, navBar.transitionName))
        }
        when(position){
            0 ->{
                //
                val sharedElements = sharedElementList.toTypedArray<Pair<View, String>>()
                val makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *sharedElements)
                val intent = Intent(this, AnimationTransitionActivity::class.java).apply {
                    putExtra("AnimationName",  "Transitions")
                }
                startActivity(intent, makeSceneTransitionAnimation.toBundle())
            }
            1 ->{
                val image = view.findViewById<CircleImageView>(R.id.circleImageView)
                sharedElementList.add(Pair.create(image, image.transitionName))
                val textView = view.findViewById<TextView>(R.id.textView5)
                sharedElementList.add(Pair.create(textView, textView.transitionName))
                val sharedElements = sharedElementList.toTypedArray<Pair<View, String>>()
                val makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *sharedElements)
                val intent = Intent().apply {
                    putExtra("AnimationName",  textView.text)
                }
                startActivity(intent, makeSceneTransitionAnimation.toBundle())
            }
            2 ->{

            }
            3 ->{

            }
        }
    }

}