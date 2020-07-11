package com.example.llc.router;

import com.example.llc.arouter.ARouter;
import com.example.llc.arouter.IRouter;
import com.example.llc.member.ModuleMemberActivity;

public class ActivityUtil implements IRouter {

    @Override
    public void putActivity() {
        // 将当前模块的所有 Activity 的类对象加入路由表
        ARouter.getInstance().addActivity("member/member", ModuleMemberActivity.class);
    }
}
