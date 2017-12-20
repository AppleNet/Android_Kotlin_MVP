package com.example.llcgs.android_kotlin.material.setting.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.llcgs.android_kotlin.R
import kotlinx.android.synthetic.main.fragment_setting_activity.*

/**
 * com.example.llcgs.android_kotlin.material.setting.fragment.SettingActivityFragment
 * @author liulongchao
 * @since 2017/12/20
 */
class SettingActivityFragment: Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_setting_activity, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home ->{
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}