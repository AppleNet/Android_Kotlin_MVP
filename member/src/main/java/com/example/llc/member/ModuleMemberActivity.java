package com.example.llc.member;

import android.os.Bundle;

import com.example.llc.annotation.BindPath;
import com.llc.kotlin.example.basic.BaseModuleActivity;

import org.jetbrains.annotations.Nullable;

@BindPath("Member/member")
public class ModuleMemberActivity extends BaseModuleActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_member);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_module_member;
    }
}
