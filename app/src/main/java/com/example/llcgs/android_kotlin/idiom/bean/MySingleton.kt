package com.example.llcgs.android_kotlin.idiom.bean

/**
 * com.example.llcgs.android_kotlin.third.bean.MySingleton
 * @author liulongchao
 * @since 2017/7/20
 */


object MySingleton {

    // 直接声明一些变量
    var name: String? = null
    var age: Int = 0
    var hobby: String? = null
    var height: Double = 0.0
    var sex: String? = null
    var address: String? = null


    /**
     * public static final MySingleton INSTANCE;

        private MySingleton() {
            INSTANCE = (MySingleton)this;
        }

        static {
            new MySingleton();
        }
     *
     *
     * */
}