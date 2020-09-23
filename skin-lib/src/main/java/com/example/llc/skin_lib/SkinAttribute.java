package com.example.llc.skin_lib;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.llc.skin_lib.utils.SkinResources;
import com.example.llc.skin_lib.utils.SkinThemeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  com.example.llc.skin_lib.SkinAttribute
 *
 * @author liulongchao
 * @since 2020-09-23
 *
 * 存放了所有需要换肤的 view 的属性
 * */
public class SkinAttribute {

    private List<SkinView> mSkinViews = new ArrayList<>();
    private static final List<String> mAttributes = new ArrayList<>();

    static {
        mAttributes.add("background");
        mAttributes.add("src");
        mAttributes.add("textColor");
        mAttributes.add("drawableLeft");
        mAttributes.add("drawableTop");
        mAttributes.add("drawableRight");
        mAttributes.add("drawableBottom");
    }

    /**
     * 寻找所有可以换肤的 view
     * 以 ？开头的 和 以 @ 开头的属性值
     * */
    public void look(View view, AttributeSet attrs) {
        List<SkinPair> skinPairs =  new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            // 获取属性名
            String attributeName = attrs.getAttributeName(i);
            if (mAttributes.contains(attributeName)) {
                // 获取属性值
                String attributeValue = attrs.getAttributeValue(i);
                // 比如 color，以#号写死的颜色，不用换肤
                if (attributeValue.startsWith("#")) {
                    continue;
                }
                int resId;
                if (attributeValue.startsWith("?")) {
                    // 以 ？开头的属性值
                    int attrId = Integer.parseInt(attributeValue.substring(1));
                    resId = SkinThemeUtils.getResId(view.getContext(), new int[]{attrId})[0];
                } else {
                    // 正常以 @ 开头的属性值
                    resId = Integer.parseInt(attributeValue.substring(1));
                }

                SkinPair skinPair = new SkinPair(attributeName, resId);
                skinPairs.add(skinPair);
            }
        }
        if (!skinPairs.isEmpty() || view instanceof SkinViewSupport) {
            SkinView skinView = new SkinView(view, skinPairs);
            mSkinViews.add(skinView);
        }
    }

    /**
     *   对所有的 View 中的所有属性进行皮肤修改
     * */
    public void applySkin() {
        for (SkinView mSkinView : mSkinViews) {
            mSkinView.applySkin();
        }
    }

    public static class SkinView{
        public View view;
        // 这个 View  换肤的属性与它对应的 id 的集合
        public List<SkinPair> skinPairs;

        public SkinView(View view, List<SkinPair> skinPairs) {
            this.view = view;
            this.skinPairs = skinPairs;
        }

        public void applySkin() {
            applySkinSupport();
            for (SkinPair skinPair : skinPairs) {
                Drawable left = null, top = null, right = null, bottom = null;
                switch (skinPair.attributeName) {
                    case "background":
                        Object background = SkinResources.getInstance().getBackground(skinPair.resId);
                        if (background instanceof Integer) {
                            view.setBackgroundColor((Integer) background);
                        } else {
                            ViewCompat.setBackground(view, (Drawable) background);
                        }
                        break;
                    case "src":
                        background = SkinResources.getInstance().getBackground(skinPair.resId);
                        if (background instanceof Integer) {
                            ((ImageView)view).setImageDrawable(new ColorDrawable((Integer) background));
                        } else {
                            ((ImageView)view).setImageDrawable((Drawable) background);
                        }
                        break;
                    case "textColor":
                        ((TextView)view).setTextColor(SkinResources.getInstance().getColorStateList(skinPair.resId));
                        break;
                    case "drawableLeft":
                        left = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableTop":
                        top = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableRight":
                        right = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                    case "drawableBottom":
                        bottom = SkinResources.getInstance().getDrawable(skinPair.resId);
                        break;
                }

                if (left != null || null != top || right != null || null != bottom) {
                    ((TextView)view).setCompoundDrawablesWithIntrinsicBounds(left,top,right,bottom);
                }
            }
        }

        private void applySkinSupport() {
           if (view instanceof SkinViewSupport) {
               ((SkinViewSupport) view).applySkin();
           }
        }
    }

    static class SkinPair {

        // 属性名
        public String attributeName;
        // 对应资源 id
        public int resId;

        public SkinPair(String attributeName, int resId) {
            this.attributeName = attributeName;
            this.resId = resId;
        }
    }
}
