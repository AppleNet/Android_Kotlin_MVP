package com.example.llcgs.android_kotlin.mvvm.stringliterals

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityStringLiteralsBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.stringliterals.viewmodel.StringLiteralsViewModel
import kotlinx.android.synthetic.main.view_title.*
import java.util.*

/**
 * com.example.llcgs.android_kotlin.mvvm.stringliterals.StringLiteralsActivity
 * @author liulongchao
 * @since 2017/11/13
 */


class StringLiteralsActivity:BaseActivity<StringLiteralsViewModel, ActivityStringLiteralsBinding>() {


    override fun createViewModel()= StringLiteralsViewModel()

    override fun createViewDataBinding(): ActivityStringLiteralsBinding= DataBindingUtil.setContentView(this, R.layout.activity_string_literals)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "stringLiterals"
    }

    private fun initData(){
        val map = mapOf("secondName" to "Jordon", "0" to "Wade", "1" to "McGrady", "2" to "James", "3" to "Kobe", "4" to "Paul", "5" to "Durant", "6" to "curry")
        binding.map = map
        binding.setVariable(BR.stringLiteralViewModel, viewModel)
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is StringLiteralsViewModel){
            when(o.index){
                "firstName" -> binding.show.text = binding.map?.get("secondName") ?: ""
                "secondName" -> binding.show.text = binding.map?.get("0") ?: ""
                "thirdName" -> binding.show.text = binding.map?.get("1") ?: ""
                "forthName" -> binding.show.text = binding.map?.get("2") ?: ""
                "fifthName" -> binding.show.text = binding.map?.get("3") ?: ""
                "sixthName" -> binding.show.text = binding.map?.get("4") ?: ""
                "seventhName" -> binding.show.text = binding.map?.get("5") ?: ""
                "eighthName" -> binding.show.text = binding.map?.get("6") ?: ""
                else -> binding.show.text = binding.map?.get("secondName") ?: ""
            }
        }
    }

}