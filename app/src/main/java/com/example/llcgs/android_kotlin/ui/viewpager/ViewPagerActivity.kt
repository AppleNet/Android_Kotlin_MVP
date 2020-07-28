package com.example.llcgs.android_kotlin.ui.viewpager;

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.ui.viewpager.adapter.ParentViewPagerAdapter
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.anchor.AnchorFragment
import com.example.llcgs.android_kotlin.ui.viewpager.fragment.kinganchor.KingAnchorFragment
import com.example.llcgs.android_kotlin.ui.viewpager.presenter.impl.ViewPagerPresenter
import com.example.llcgs.android_kotlin.ui.viewpager.transformer.ScaleInTransformer
import com.example.llcgs.android_kotlin.ui.viewpager.view.ViewPagerView
import kotlinx.android.synthetic.main.activity_viewpager.*

/**
 * com.example.llcgs.android_kotlin.ui.viewpager.ViewPagerActivity
 * @author liulongchao
 * @since 2018/12/25
 */
class ViewPagerActivity : BaseActivity<ViewPagerView, ViewPagerPresenter>(){

    /**
     *
     *  private List<View> mViews = new ArrayList<View>();
        private ActivityAdapter adapter = new ActivityAdapter(mViews);
        private LocalActivityManager manager;
        private Intent intentMain,intentCircle,intentMy;

        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);

        intentMain = new Intent(StartActivity.this, MainActivity.class);
        View tab01 = manager.startActivity("viewID", intentMain).getDecorView();
        intentCircle = new Intent(StartActivity.this, CircleActivity.class);
        View tab02 = manager.startActivity("viewID", intentCircle).getDecorView();
        intentMy = new Intent(StartActivity.this, MyActivity.class);
        View tab03 = manager.startActivity("viewID", intentMy).getDecorView();

        mViews.add(tab01);//将页面添加到View集合
        mViews.add(tab02);
        mViews.add(tab03);
        id_ViewPager.setAdapter(adapter);// 配置适配器
     *
     *
     *
     * */

    override fun createPresenter()= ViewPagerPresenter ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)
        initViewPagerAndTabLayout()
    }

    private fun initViewPagerAndTabLayout(){

        val mViewPagerAdapterAdapter = ParentViewPagerAdapter(supportFragmentManager)
        mViewPagerAdapterAdapter.addTab(object : ParentViewPagerAdapter.FragmentCreator{
            override fun createFragment(): Fragment = AnchorFragment()
        }, "主播榜")
        mViewPagerAdapterAdapter.addTab(object : ParentViewPagerAdapter.FragmentCreator{
            override fun createFragment(): Fragment = KingAnchorFragment()
        }, "金主榜")
        viewPager.adapter = mViewPagerAdapterAdapter
        viewPager.pageMargin = 20
        viewPager.offscreenPageLimit = 3
        viewPager.setPageTransformer(true, ScaleInTransformer())
        // tab的字体选择器,默认黑色,选择时红色
        // tabLayout.setTabTextColors(Color.BLACK, Color.RED)
        tabLayout.setupWithViewPager(viewPager)
    }
}
