package com.example.llcgs.android_kotlin.design_pattern.wrapper

import android.view.View
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.wrapper.presenter.IWrapperDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.wrapper.presenter.impl.WrapperDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.wrapper.view.WrapperDesignPatternView
import kotlinx.android.synthetic.main.activity_design_pattern_wrapper.*

/**
 * com.example.llcgs.android_kotlin.design_pattern.wrapper.WrapperDesignPatternActivity
 * @author liulongchao
 * @since 2017/12/27
 */
class WrapperDesignPatternActivity : BaseDesignPatternActivity<IWrapperDesignPatternPresenter>(), WrapperDesignPatternView, View.OnClickListener {

    /**
     *  包装模式，扩展对象的功能，继承关系的另一种替代方案
     *  抽象构件(Component)角色：给出一个抽象接口，以规范准备接收附加责任的对象。
    　  具体构件(ConcreteComponent)角色：定义一个将要接收附加责任的类。
    　  装饰(Decorator)角色：持有一个构件(Component)对象的实例，并定义一个与抽象构件接口一致的接口。
    　  具体装饰(ConcreteDecorator)角色：负责给构件对象“贴上”附加的责任

     *  创造不同行为的组合
     *     // demo 不同方式的登录
     *       1.微信登录
     *       2.QQ登录
     *       3.微博登录
     *       4.支付宝登录
     * */
    override fun createPresenter() = WrapperDesignPatternPresenter(this)

    override fun getLayoutId()= R.layout.activity_design_pattern_wrapper

    override fun getUrl() = "http://www.cnblogs.com/java-my-life/archive/2012/04/20/2455726.html"

    override fun initViews() {
        setSupportActionBar(toolBar)
        weChat.setOnClickListener(this)
        qq.setOnClickListener(this)
        sina.setOnClickListener(this)
        alipay.setOnClickListener(this)
    }

    override fun onGetLoginResult(boolean: Boolean) {
        //

    }

    override fun onClick(v: View) {
        val name = userName.text.toString()
        val pwd = userPwd.text.toString()
        when(v.id){
            R.id.weChat ->{
                mPresenter.login(name, pwd, "weChat")
            }
            R.id.qq ->{
                mPresenter.login(name, pwd, "QQ")
            }
            R.id.sina ->{
                mPresenter.login(name, pwd, "sina")
            }
            R.id.alipay ->{
                mPresenter.login(name, pwd, "alipay")
            }
        }
    }
}