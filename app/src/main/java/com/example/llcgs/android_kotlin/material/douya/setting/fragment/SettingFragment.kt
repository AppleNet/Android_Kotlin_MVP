package com.example.llcgs.android_kotlin.material.douya.setting.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatDelegate
import android.text.TextUtils
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.utils.BaseUtil
import com.example.llcgs.android_kotlin.utils.log.logD
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompatDividers

/**
 * com.example.llcgs.android_kotlin.material.setting.fragment.SettingFragment
 * @author liulongchao
 * @since 2017/12/20
 */
class SettingFragment: PreferenceFragmentCompatDividers(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferencesFix(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }

    override fun onStart() {
        super.onStart()
        PreferenceManager.getDefaultSharedPreferences(BaseUtil.context()).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onStop() {
        super.onStop()
        PreferenceManager.getDefaultSharedPreferences(BaseUtil.context()).unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String) {
        key.logD()
        if (!TextUtils.equals(key, "key_night_mode")) {
            return
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

}