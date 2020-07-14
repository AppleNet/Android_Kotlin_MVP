package com.example.llc.member

import android.os.Bundle

import com.example.llc.annotation.BindPath
import com.example.llc.kotlin.basic.BaseModuleActivity
import com.lzh.nonview.router.anno.RouteConfig
import com.lzh.nonview.router.anno.RouterRule

@BindPath("Member/member")
@RouterRule("Member/member")
class ModuleMemberActivity : BaseModuleMemberActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_member)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_module_member
    }
}
