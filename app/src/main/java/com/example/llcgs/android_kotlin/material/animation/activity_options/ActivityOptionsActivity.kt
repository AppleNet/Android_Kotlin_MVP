package com.example.llcgs.android_kotlin.material.animation.activity_options

import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.activity_options.adapter.ActivityOptionsAdapter
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.IActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.impl.ActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.view.ActivityOptionsView
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.synthetic.main.activity_activity_options.*
import kotlinx.android.synthetic.main.view_material_toolbar.*

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.ActivityOptionsActivity
 * @author liulongchao
 * @since 2018/1/8
 */
class ActivityOptionsActivity : BaseMaterialActivity<IActivityOptionsPresenter>(), BaseQuickAdapter.OnItemClickListener, ActivityOptionsView {

    /**
     *  google在新的sdk中给我们提供了另外一种Activity的过度动画——ActivityOptions。并且提供了兼容包——ActivityOptionsCompat
     *
     *  1.ActivityOptionsCompat.makeCustomAnimation(Context context, int enterResId, int exitResId)
     *  2.ActivityOptionsCompat.makeScaleUpAnimation(View source,int startX, int startY, int startWidth, int startHeight)
     *  3.ActivityOptionsCompat.makeThumbnailScaleUpAnimation(View source,Bitmap thumbnail, int startX, int startY)
     *  4.ActivityOptionsCompat.makeSceneTransitionAnimation(Activity activity, View sharedElement, String sharedElementName)
     *  5.ActivityOptionsCompat.makeSceneTransitionAnimation(Activity activity,Pair<View, String>… sharedElements)
     *
     * */

    private lateinit var adapter: ActivityOptionsAdapter

    override fun createPresenter()= ActivityOptionsPresenter(this)

    override fun getLayoutId()= R.layout.activity_activity_options

    override fun initViews() {
        toolbar.title = "ActivityOptions"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)

        adapter = ActivityOptionsAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        adapter.onItemClickListener = this

        mPresenter.getActivityOptions()
    }

    override fun onGetActivityOptions(list: List<String>) {
        adapter.setNewData(list)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View, position: Int) {
        when(position){
            0 ->{
                /**
                 *  makeCustomAnimation这个很简单，从参数中我们可以发现，这个和overridePendingTransition非常类似，确实，在实现效果上和overridePendingTransition也是相同的
                 * */
                // 自定义转场动画
                val customAnimation = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.anim_fade_in, R.anim.anim_fade_out)
                ActivityCompat.startActivity(this, Intent(this, AnimationActivity::class.java).apply {
                    putExtra("AnimationName", "makeCustomAnimation")
                }, customAnimation.toBundle())
            }
            1 ->{
                /**
                 * makeScaleUpAnimation这种方式个人感觉用途还是很广的，效果就是不断的放大一个view，进而进行activity的过度
                 * */
                //  不断放大或者缩小一个view 进而进行Activity的过度
                // View source, scale哪个view的大小
                // int startX, 以view 为基点从X轴哪里开始动画
                // int startY, 以view 为基点从Y轴哪里开始动画
                // int startWidth, 新的Activity 宽度从多大开始放大
                // int startHeight 新的Activity 高度从多大开始放大
                val scaleUpAnimation = ActivityOptionsCompat.makeScaleUpAnimation(view, view.width / 2, view.height / 2, 0, 0)
                ActivityCompat.startActivity(this, Intent(this, AnimationActivity::class.java).apply {
                    putExtra("AnimationName", "makeScaleUpAnimation")
                }, scaleUpAnimation.toBundle())
            }
            2 ->{

            }
        }
    }
}