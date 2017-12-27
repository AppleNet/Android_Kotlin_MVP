package com.example.llcgs.android_kotlin.design_pattern.flyweight

import android.view.View
import android.widget.Toast
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.design_pattern.base.BaseDesignPatternActivity
import com.example.llcgs.android_kotlin.design_pattern.flyweight.bean.DesignPatternUser
import com.example.llcgs.android_kotlin.design_pattern.flyweight.presenter.IFlyweightDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.flyweight.presenter.impl.FlyweightDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.flyweight.view.FlyweightDesignPatternView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_design_pattern_flyweight.*

/**
 * com.example.llcgs.android_kotlin.design_pattern.flyweight.FlyweightDesignPatternActivity
 * @author liulongchao
 * @since 2017/12/26
 */
class FlyweightDesignPatternActivity : BaseDesignPatternActivity<IFlyweightDesignPatternPresenter>(), FlyweightDesignPatternView, View.OnClickListener {

    /**
     *  共享相同内容的对象，减少开销
     *  享元模式可以分成单纯享元模式和复合享元模式两种形式
     *  在单纯的享元模式中，所有的享元对象都是可以共享的
     *
     *    保存登录状态为例
     * */
    override fun createPresenter() = FlyweightDesignPatternPresenter(this)

    override fun getLayoutId() = R.layout.activity_design_pattern_flyweight

    override fun initViews() {
        setSupportActionBar(toolBar)
        signIn.setOnClickListener(this)
        RxTextView.textChanges(userName).subscribe {
            mPresenter.getPwd(it.toString())
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.signIn -> {
                mPresenter.doLogin(DesignPatternUser().apply {
                    name = userName.text.toString()
                    pwd = userPwd.text.toString()
                })
            }
        }
    }

    override fun onGetPwd(string: String) {
        userPwd.setText(string)
    }

    override fun onGetLogin(boolean: Boolean) {
        Toast.makeText(this, if (boolean) {
            // 用户存在
            "用户存在"
        } else {
            // 用户不存在
            "用户不存在"
        }, Toast.LENGTH_SHORT).show()
    }
}



