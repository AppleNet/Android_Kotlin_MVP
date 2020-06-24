package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.llcgs.android_kotlin.R;

/**
 * com.example.llcgs.android_kotlin.customview.selfview.MyCompositionView
 *
 * @author liulongchao
 * @since 2018/11/12
 */
public class MyCompositionView extends LinearLayout {

    private Context context;
    private AttributeSet attrs;
    private EditText loginNameEdt;
    private EditText loginPwdEdt;
    private FloatingActionButton fab;

    public MyCompositionView(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    public MyCompositionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        initViews();
    }

    public MyCompositionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        initViews();
    }

    public MyCompositionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        this.attrs = attrs;
        initViews();
    }

    private void initViews(){
        View view = LayoutInflater.from(context).inflate(R.layout.view_composition_view, this, true);
        loginNameEdt = view.findViewById(R.id.loginNameEdt);
        loginPwdEdt = view.findViewById(R.id.loginPwdEdt);
        fab = view.findViewById(R.id.fab);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCompositionView);
        // TODO
        typedArray.recycle();
    }


}
