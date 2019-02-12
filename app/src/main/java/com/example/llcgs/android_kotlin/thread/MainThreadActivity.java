package com.example.llcgs.android_kotlin.thread;

import android.os.Bundle;
import com.example.llcgs.android_kotlin.R;
import com.example.llcgs.android_kotlin.base.activity.BaseActivity;
import com.example.llcgs.android_kotlin.thread.presenter.impl.MainThreadPresenter;
import com.example.llcgs.android_kotlin.thread.view.MainThreadView;
import com.lzh.nonview.router.anno.RouterRule;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@RouterRule("Thread")
public class MainThreadActivity extends BaseActivity<MainThreadView, MainThreadPresenter> {


    @NotNull
    @Override
    public MainThreadPresenter createPresenter() {
        return new MainThreadPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thread);
        //initThread();
        //testWait();
        //testNotify();
        testJoin();
    }

    private void initThread(){
        mPresenter.initThread();
    }

    private void testWait(){
        mPresenter.testWait();
    }

    private void testNotify(){
        mPresenter.testNotify();
    }

    private void testJoin(){
        mPresenter.testJoin();
    }
}
