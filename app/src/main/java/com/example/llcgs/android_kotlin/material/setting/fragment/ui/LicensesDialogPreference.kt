
package com.example.llcgs.android_kotlin.material.setting.fragment.ui

import android.content.Context
import android.support.v7.preference.DialogPreference
import android.util.AttributeSet

import com.example.llcgs.android_kotlin.material.setting.fragment.LicensesDialogFragment
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat

class LicensesDialogPreference : DialogPreference {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int,
                defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    companion object {

        init {
            PreferenceFragmentCompat.registerPreferenceFragment(LicensesDialogPreference::class.java,
                    LicensesDialogFragment::class.java)
        }
    }
}
