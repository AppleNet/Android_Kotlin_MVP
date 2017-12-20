
package com.example.llcgs.android_kotlin.material.setting.fragment.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter

import com.example.llcgs.android_kotlin.R


class DropDownPreference : android.support.v7.preference.DropDownPreference {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int,
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun createAdapter(): ArrayAdapter<String> =
            ArrayAdapter(context, R.layout.view_dropdown_preference_item)
}
