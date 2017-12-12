package com.example.llcgs.android_kotlin.material.login

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
import com.example.llcgs.android_kotlin.material.login.customtabs.CustomTabActivityHelper
import com.example.llcgs.android_kotlin.material.login.customtabs.WebViewFallback
import com.example.llcgs.android_kotlin.material.login.presenter.IMaterialLoginPresenter
import com.example.llcgs.android_kotlin.material.login.presenter.impl.MaterialLoginPresenter
import com.example.llcgs.android_kotlin.material.login.view.MaterialLoginView
import com.jakewharton.rxbinding2.support.design.widget.RxTextInputLayout
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

    val sPackageNameToUse:String? = null

    override fun createPresenter(): IMaterialLoginPresenter= MaterialLoginPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_material_login

    override fun initViews() {

        RxTextView.textChanges(userName).subscribe { RxTextInputLayout.error(usernameLayout).accept(null) }

        RxTextView.textChanges(password).subscribe { RxTextInputLayout.error(userPwdLayout).accept(null) }

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
            RxTextInputLayout.error(usernameLayout).accept("请输入用户名")
            userName.requestFocus()
            return
        }

        if (pwd.isEmpty()){
            RxTextInputLayout.error(userPwdLayout).accept("请输入密码")
            password.requestFocus()
            return
        }


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
        CustomTabActivityHelper.openCustomTab(
                this, customTabsIntent, Uri.parse("https://www.douban.com/accounts/register"), WebViewFallback())
    }

    override fun onLoginSuccess() {
        //

    }

}