package com.example.llcgs.android_kotlin.butter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.llcgs.android_kotlin.R;
import com.example.llcgs.android_kotlin.base.activity.BaseActivity;
import com.example.llcgs.android_kotlin.butter.presenter.impl.ButterKnifePresenter;
import com.example.llcgs.android_kotlin.butter.view.ButterKnifeView;
import com.example.llcgs.android_kotlin.utils.butterknife.InjectClick;
import com.example.llcgs.android_kotlin.utils.butterknife.InjectUtils;
import com.example.llcgs.android_kotlin.utils.butterknife.InjectView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ButterKnifeActivity extends BaseActivity<ButterKnifeView, ButterKnifePresenter> {


    @InjectView(R.id.butterKnifeBtn)
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butter_knife);
        InjectUtils.injectViews(this);

        button.setText("findViewById");
    }

    @InjectClick({R.id.butterKnifeBtn})
    public void butterKnife(View view) {
        Toast.makeText(this, "onClick 注入成功！！！", Toast.LENGTH_LONG).show();
    }

    @NotNull
    @Override
    public ButterKnifePresenter createPresenter() {
        return new ButterKnifePresenter();
    }
}
