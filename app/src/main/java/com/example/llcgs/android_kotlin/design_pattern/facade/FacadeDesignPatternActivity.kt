package com.example.llcgs.android_kotlin.design_pattern.facade

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Dough
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Fruit
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Vegetables
import com.example.llcgs.android_kotlin.design_pattern.facade.presenter.IFacadeDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.facade.presenter.impl.FacadeDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.facade.view.FacadeDesignPatternView
import kotlinx.android.synthetic.main.activity_design_pattern_facade.*

/**
 * com.example.llcgs.android_kotlin.design_pattern.facade.FacadeDesignPatternActivity
 * @author liulongchao
 * @since 2017/12/26
 */
class FacadeDesignPatternActivity: BaseDesignPatternActivity<IFacadeDesignPatternPresenter>(), FacadeDesignPatternView, View.OnClickListener {

    /**
     *  门面模式是对象的结构模式，外部与一个子系统的通信必须通过一个统一的门面对象进行。门面模式提供一个高层次的接口，使得子系统更易于使用
     *  门面模式可以理解为分而治之
     *
     *   松散耦合
     *   简单易用
     *   更好的划分访问层次
     * */

    override fun createPresenter(): IFacadeDesignPatternPresenter= FacadeDesignPatternPresenter(this)

    override fun getLayoutId()= R.layout.activity_design_pattern_facade

    override fun getUrl()= "http://www.cnblogs.com/java-my-life/archive/2012/05/02/2478101.html"

    override fun initViews() {
        setSupportActionBar(toolBar)
        confirm.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.confirm ->{
                val fruit = fruitEdt.text.toString()
                val dough = doughEdt.text.toString()
                val vegetables = vegetablesEdt.text.toString()
                mPresenter.calculate(Fruit(fruit), dough = Dough(dough), vegetables = Vegetables(vegetables))
            }
        }
    }

    override fun onGetResult(any: Any) {
        if (any is Fruit){
            fruitLayout.error = any.content
        }
        if (any is Vegetables){
            vegetablesLayout.error = any.content
        }
        if (any is Dough){
            doughLayout.error = any.content
        }
    }


}