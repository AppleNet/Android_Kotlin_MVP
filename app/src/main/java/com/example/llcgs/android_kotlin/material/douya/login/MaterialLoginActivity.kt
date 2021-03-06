package com.example.llcgs.android_kotlin.material.douya.login

import android.content.Intent
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import com.example.llcgs.android_kotlin.material.douya.login.customtabs.WebViewFallback
import com.example.llcgs.android_kotlin.material.douya.login.presenter.IMaterialLoginPresenter
import com.example.llcgs.android_kotlin.material.douya.login.presenter.impl.MaterialLoginPresenter
import com.example.llcgs.android_kotlin.material.douya.login.view.MaterialLoginView
import com.example.llcgs.android_kotlin.material.douya.main.MaterialHomeActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_material_login.*

/**
 * com.example.llcgs.android_kotlin.material.login.MaterialLoginActivity
 * @author liulongchao
 * @since 2017/12/11
 */
@RouterRule("Material_Design")
class MaterialLoginActivity : BaseMaterialActivity<IMaterialLoginPresenter>(), MaterialLoginView {

    override fun createPresenter(): IMaterialLoginPresenter= MaterialLoginPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_material_login

    override fun initViews() {

        RxTextView.textChanges(userName).subscribe {
            //RxTextInputLayout.error(usernameLayout).accept(null)
        }

        RxTextView.textChanges(password).subscribe {
            //RxTextInputLayout.error(userPwdLayout).accept(null)
        }

        RxTextView.editorActions(password).subscribe {
            // 登录
            login()
        }

        RxView.clicks(signIn).subscribe {
            // 登录
            login()
        }

        RxView.clicks(signUp).subscribe {
            // 注册
            register()
        }
    }

    private fun login(){
        val name = userName.text.toString()
        val pwd = password.text.toString()
        if (name.isEmpty()){
            //RxTextInputLayout.error(usernameLayout).accept("请输入用户名")
            userName.requestFocus()
            return
        }

        if (pwd.isEmpty()){
            //RxTextInputLayout.error(userPwdLayout).accept("请输入密码")
            password.requestFocus()
            return
        }
        mPresenter.login(name, pwd)

    }

    private fun register(){
        //  注册
        val customTabsIntent = CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .enableUrlBarHiding()
                .setToolbarColor(ContextCompat.getColor(this, R.color.douya_primary))
                .setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.douya_primary_dark))
                .setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left)
                .setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .setShowTitle(true)
                .build()
        com.example.llcgs.android_kotlin.material.douya.login.customtabs.CustomTabActivityHelper.openCustomTab(
                this, customTabsIntent, Uri.parse("https://www.douban.com/accounts/register"), WebViewFallback())
    }

    override fun onLoginSuccess() {
        //
        startActivity(Intent(this@MaterialLoginActivity, MaterialHomeActivity::class.java))
        finish()
    }

}