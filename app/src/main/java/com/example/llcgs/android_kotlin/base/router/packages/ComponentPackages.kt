package com.example.llcgs.android_kotlin.base.router.packages

/**
 * 对各个模块用于生成路由映射表的生成类包名定义。让各自的组件中生成的映射表都对应使用各自的package。
 *
 *
 * 对Activity使用[com.lzh.nonview.router.anno.RouterRule] 注解。指定pack报名。即可
 *
 */
interface ComponentPackages {
    companion object {
        val JAMES = "com.example.llc.base"
        val HOST = "com.example.llcgs.android_kotlin.base.app"

        /**
         * JMRouteManager中使用此数组数据来加载所有的路由映射表。
         */
        val Packages = arrayOf(HOST, JAMES)
    }
}
