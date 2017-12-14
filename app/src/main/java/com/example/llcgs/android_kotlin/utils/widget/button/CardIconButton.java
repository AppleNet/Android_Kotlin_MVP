/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package com.example.llcgs.android_kotlin.utils.widget.button;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.llcgs.android_kotlin.R;


public class CardIconButton extends LinearLayout {

    ImageView mImage;
    TextView mText;

    public CardIconButton(Context context) {
        super(context);

        init(null, 0, 0);
    }

    public CardIconButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs, 0, 0);
    }

    public CardIconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CardIconButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs, defStyleAttr, defStyleRes);
    }

    @SuppressWarnings("RestrictedApi")
    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        setClickable(true);
        setFocusable(true);
        setGravity(Gravity.CENTER_VERTICAL);
        setOrientation(HORIZONTAL);

        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.card_icon_button, this);
        mImage = (ImageView) view.findViewById(R.id.cardiconbutton_image);
        mText = (TextView) view.findViewById(R.id.cardiconbutton_text);


        TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.CardIconButton, defStyleAttr, defStyleRes);
        Drawable src = a.getDrawable(R.styleable.CardIconButton_android_src);
        if (src != null) {
            mImage.setImageDrawable(src);
        }
        CharSequence text = a.getText(R.styleable.CardIconButton_android_text);
        setText(text);
        a.recycle();
    }

    public ImageView getImageView() {
        return mImage;
    }

    public TextView getTextView() {
        return mText;
    }

    public void setIcon(Drawable icon) {
        mImage.setImageDrawable(icon);
    }

    public void setText(CharSequence text) {
        mText.setText(text);
    }
}
