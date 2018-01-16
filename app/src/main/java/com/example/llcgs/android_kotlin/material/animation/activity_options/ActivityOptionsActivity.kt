package com.example.llcgs.android_kotlin.material.animation.activity_options

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.activity_options.adapter.ActivityOptionsAdapter
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.IActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.impl.ActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.view.ActivityOptionsView
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_activity_options.*
import kotlinx.android.synthetic.main.view_activity_options_footer.*
import kotlinx.android.synthetic.main.view_material_toolbar.*
import java.util.ArrayList

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
    private val headerView by lazy { HeaderView(LayoutInflater.from(this).inflate(R.layout.view_activity_options_header, null)) }
    private val footerView by lazy { FooterView(LayoutInflater.from(this).inflate(R.layout.view_activity_options_footer, null)) }
    override fun createPresenter()= ActivityOptionsPresenter(this)

    override fun getLayoutId()= R.layout.activity_activity_options

    override fun initViews() {
        toolbar.title = "ActivityOptions"
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        setSupportActionBar(toolbar)

        adapter = ActivityOptionsAdapter()
        adapter.addHeaderView(headerView.containerView)
        adapter.addFooterView(footerView.containerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this).apply { orientation = LinearLayout.VERTICAL }
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
                /**
                 *  makeThumbnailScaleUpAnimation
                 *   该方法和上面的makeScaleUpAnimation非常相似，只不过，
                 *   这里是通过放大一个图片，
                 *   最后过度到一个新的activity，
                 *   第2个参数是指那个图片要放大，
                 *   3和4参数表示从哪开始动画。
                 * */
                val makeThumbnailScaleUpAnimation = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view,
                        (footerView.footerImg.drawable as BitmapDrawable).bitmap,
                        footerView.footerImg.width/2, footerView.footerImg.height/2)
                ActivityCompat.startActivity(this, Intent(this, AnimationActivity::class.java).apply {
                    putExtra("AnimationName", "makeThumbnailScaleUpAnimation")
                }, makeThumbnailScaleUpAnimation.toBundle())
            }
            3 ->{
                /**
                 *  makeSceneTransitionAnimation
                 *
                 * */
                footerView.footerImg.transitionName = "footerImg"
                val makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(this, footerView.footerImg, footerView.footerImg.transitionName)
                ActivityCompat.startActivity(this, Intent(this, AnimationActivity::class.java).apply {
                    putExtra("AnimationName", "makeSceneTransitionAnimation")
                    putExtra("transitionName", footerView.footerImg.transitionName)
                }, makeSceneTransitionAnimation.toBundle())
            }
            4 ->{
                /**
                 *  多个view的协作 makeSceneTransitionAnimation
                 *  给多个View指定transitionName 保持一致
                 * */
                footerView.footerImg.transitionName = "footerImg"
                val sharedElementList = ArrayList<Pair<View, String>>()
                // 添加多个元素
                sharedElementList.add(Pair.create(footerView.footerImg, footerView.footerImg.transitionName))
                // convert to Pair<View, String>[]
                val sharedElements = sharedElementList.toTypedArray<Pair<View, String>>()
                val makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *sharedElements)
                ActivityCompat.startActivity(this, Intent(this, AnimationActivity::class.java).apply {
                    putExtra("AnimationName", "makeSceneTransitionAnimation")
                    putExtra("transitionName", footerView.footerImg.transitionName)
                }, makeSceneTransitionAnimation.toBundle())
            }
        }
    }

    class HeaderView(override val containerView: View?): LayoutContainer
    class FooterView(override val containerView: View?): LayoutContainer

}