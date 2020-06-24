package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.llcgs.android_kotlin.R;

/**
 * com.example.llcgs.android_kotlin.customview.selfview.MyAttrView
 *
 * @author liulongchao
 * @since 2018/11/12
 */
public class MyAttrView extends LinearLayout {

    /**
     *  简易版自定义View
     *      继承布局控件，添加系统控件，使用attrs.xml文件设置属性
     * */

    private Context context;
    private AttributeSet attrs;
    private String text;

    public MyAttrView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public MyAttrView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    public MyAttrView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    public MyAttrView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    private void initView(){
        int resourceId = -1;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyAttrView);
        TextView textView = new TextView(context);
        EditText editText = new EditText(context);
        this.setOrientation(LinearLayout.VERTICAL);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++){
            int attr = typedArray.getIndex(i);
            switch (attr){
                case R.styleable.MyAttrView_Oriental:
                    resourceId = typedArray.getInt(R.styleable.MyAttrView_Oriental, 0);
                    this.setOrientation(resourceId == 1 ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
                    break;
                case R.styleable.MyAttrView_Text:
                    resourceId = typedArray.getResourceId(R.styleable.MyAttrView_Text, 0);
                    textView.setText(resourceId > 0 ? typedArray.getResources().getText(resourceId) : typedArray.getString(R.styleable.MyAttrView_Text));
                    break;
            }
        }
        addView(textView);
        addView(editText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        typedArray.recycle();
    }
}
