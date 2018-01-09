package com.example.llcgs.android_kotlin.material.animation.activity_options

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.IActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.animation.activity_options.presenter.impl.ActivityOptionsPresenter
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import kotlinx.android.synthetic.main.activity_activity_options.*

/**
 * com.example.llcgs.android_kotlin.material.animation.activity_options.ActivityOptionsActivity
 * @author liulongchao
 * @since 2018/1/8
 */
class ActivityOptionsActivity : BaseMaterialActivity<IActivityOptionsPresenter>() {

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
    override fun createPresenter()= ActivityOptionsPresenter()

    override fun getLayoutId()= R.layout.activity_activity_options

    override fun initViews() {
        /**
         *  makeCustomAnimation这个很简单，从参数中我们可以发现，这个和overridePendingTransition非常类似，确实，在实现效果上和overridePendingTransition也是相同的
         * */
        makeCustomAnimation.setOnClickListener {
            //

        }
        /**
         * makeScaleUpAnimation这种方式个人感觉用途还是很广的，效果就是不断的放大一个view，进而进行activity的过度
         * */
    }
}