package com.example.llcgs.android_kotlin.material.setting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.llcgs.android_kotlin.material.setting.fragment.SettingActivityFragment

/**
 * com.example.llcgs.android_kotlin.material.setting.SettingActivity
 * @author liulongchao
 * @since 2017/12/20
 */
class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(android.R.id.content)
        if (savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .add(android.R.id.content, SettingActivityFragment(), null)
                    .commit()
        }
    }
}