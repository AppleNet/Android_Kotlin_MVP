package com.example.llcgs.android_kotlin.material.login

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.material.base.BaseMaterialActivity
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
        // TODO
    }

    override fun onLoginSuccess() {
        //

    }


}