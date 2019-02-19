package com.example.llcgs.android_kotlin.design_pattern.simplefactory.factory;

/**
 * com.example.llcgs.android_kotlin.design_pattern.simplefactory.factory.WeChatLogin
 * @author liulongchao
 * @since 2019/2/18
 */
class SinaLogin: ILogin {

    override fun login(type: String): Boolean {
        return true
    }
}
