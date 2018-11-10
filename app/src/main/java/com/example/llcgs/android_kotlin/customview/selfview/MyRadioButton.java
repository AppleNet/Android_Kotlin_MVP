package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.example.llcgs.android_kotlin.R;

/**
 * com.example.llcgs.android_kotlin.customview.selfview.MyRadioButton
 *
 * @author liulongchao
 * @since 2018/11/10
 */
public class MyRadioButton extends AppCompatRadioButton implements CompoundButton.OnCheckedChangeListener {

    /**
     *  简易版自定义View
     *   继承已有控件 实现自定义View
     * */
    private String value;

    public MyRadioButton(Context context) {
        super(context);
    }

    public String getValue(){
        return value;
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);
        this.value = typedArray.getString(R.styleable.MyRadioButton_value);
        typedArray.recycle();
        setOnCheckedChangeListener(this);
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            setText(value);
        }else {
            setText(getResources().getString(R.string.app_name));
        }
    }

}
