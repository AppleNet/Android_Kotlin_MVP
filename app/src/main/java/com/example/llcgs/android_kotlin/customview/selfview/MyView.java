package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * com.example.llcgs.android_kotlin.customview.selfview.MyView
 *
 * @author liulongchao
 * @since 2018/11/10
 */
public class MyView extends LinearLayout {

    /**
     *  简易版自定义View
     *      继承布局控件，添加系统控件，不适用attrs.xml文件设置属性
     * */

    private Context context;
    private AttributeSet attrs;
    private String text;


    public MyView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        this.attrs = attrs;
        initView();
    }

    private void initView(){
        int resourceId = -1;
        TextView textView = new TextView(context);
        EditText editText = new EditText(context);
        this.setOrientation(LinearLayout.VERTICAL);
        // 使用getAttributeResourceValue，此时在xml中不需要声明命名空间，直接使用“Text”属性。
        resourceId = attrs.getAttributeResourceValue(null, "Text", 0);
        if (resourceId > 0){
            text = context.getResources().getText(resourceId).toString();
        }else{
            text = "";
        }
        textView.setText(text);
        addView(textView);
        addView(editText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
