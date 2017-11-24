package com.example.llcgs.android_kotlin.mvvm.menu.viewmodel

import android.content.Intent
import android.view.View
import com.example.llcgs.android_kotlin.mvvm.advancedbinding.AdvancedBindingActivity
import com.example.llcgs.android_kotlin.mvvm.attributesetters.AttributeSettersActivity
import com.example.llcgs.android_kotlin.mvvm.base.BaseViewModel
import com.example.llcgs.android_kotlin.mvvm.collections.CollectionsActivity
import com.example.llcgs.android_kotlin.mvvm.commfeatures.CommonFeaturesActivity
import com.example.llcgs.android_kotlin.mvvm.converters.ConvertersActivity
import com.example.llcgs.android_kotlin.mvvm.generatedbinding.GeneratedBindingActivity
import com.example.llcgs.android_kotlin.mvvm.includes.IncludesActivity
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.CustomBindingClassNameActivity
import com.example.llcgs.android_kotlin.mvvm.layoutdetails.LayoutDetailsActivity
import com.example.llcgs.android_kotlin.mvvm.list.MvvmListActivity
import com.example.llcgs.android_kotlin.mvvm.menu.model.Menu
import com.example.llcgs.android_kotlin.mvvm.observablecollections.ObservableCollectionsActivity
import com.example.llcgs.android_kotlin.mvvm.observablefields.FieldsActivity
import com.example.llcgs.android_kotlin.mvvm.observableobjects.ObservableActivity
import com.example.llcgs.android_kotlin.mvvm.resources.ResourcesActivity
import com.example.llcgs.android_kotlin.mvvm.show.PhotoShowActivity
import com.example.llcgs.android_kotlin.mvvm.stringliterals.StringLiteralsActivity
import com.example.llcgs.android_kotlin.mvvm.viewstubs.ViewStubActivity

/**
 * com.example.llcgs.android_kotlin.mvvm.menu.viewmodel.MenuItemViewModel
 * @author liulongchao
 * @since 2017/11/11
 */


class MenuItemViewModel:BaseViewModel() {

    var menu = Menu()

    fun onItemClickListener(view: View){
        when(menu.title){
            //
            "list" ->{
                view.context.startActivity(Intent(view.context, MvvmListActivity::class.java))
            }
            "includes" ->{
                view.context.startActivity(Intent(view.context, IncludesActivity::class.java))
            }
            "layoutdetails" ->{
                view.context.startActivity(Intent(view.context, LayoutDetailsActivity::class.java))
            }
            "customClassName" ->{
                view.context.startActivity(Intent(view.context, CustomBindingClassNameActivity::class.java))
            }
            "commonFeatures" ->{
                view.context.startActivity(Intent(view.context, CommonFeaturesActivity::class.java))
            }
            "collections" ->{
                view.context.startActivity(Intent(view.context, CollectionsActivity::class.java))
            }
            "stringLiterals" ->{
                view.context.startActivity(Intent(view.context, StringLiteralsActivity::class.java))
            }
            "resources" ->{
                view.context.startActivity(Intent(view.context, ResourcesActivity::class.java))
            }
            "observableObjects" ->{
                view.context.startActivity(Intent(view.context, ObservableActivity::class.java))
            }
            "observableFields" ->{
                view.context.startActivity(Intent(view.context, FieldsActivity::class.java))
            }
            "observableCollections" ->{
                view.context.startActivity(Intent(view.context, ObservableCollectionsActivity::class.java))
            }
            "generatedbinding" ->{
                view.context.startActivity(Intent(view.context, GeneratedBindingActivity::class.java))
            }
            "viewStubs" ->{
                view.context.startActivity(Intent(view.context, ViewStubActivity::class.java))
            }
            "advancedBinding" ->{
                view.context.startActivity(Intent(view.context, AdvancedBindingActivity::class.java))
            }
            "attributeSetters" ->{
                view.context.startActivity(Intent(view.context, AttributeSettersActivity::class.java))
            }
            "converters" ->{
                view.context.startActivity(Intent(view.context, ConvertersActivity::class.java))
            }
            "show" ->{
                view.context.startActivity(Intent(view.context, PhotoShowActivity::class.java).apply {
                    putExtra("imageUrl", "https://timgsa.baidu.com/timg?" +
                            "image&quality=80&" +
                            "size=b9999_10000&" +
                            "sec=1509790606182&di=df1422d4220d14ac87f5dc1a51ecd904&" +
                            "imgtype=0&" +
                            "src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%253D580%2Fsign%3D1c7184c0d243ad4ba62e46c8b2035a89%2F478ca4c27d1ed21bcbb65780ae6eddc451da3f74.jpg")
                })
            }
        }
    }

}