package com.example.llc.member

import android.os.Bundle
import com.example.llc.annotation.BindPath
import com.llc.kotlin.example.basic.BaseModuleActivity

@BindPath("Member/member")
class ModuleMemberActivity : BaseModuleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module_member)
    }

    override fun getLayoutId(): Int = R.layout.activity_module_member
}
