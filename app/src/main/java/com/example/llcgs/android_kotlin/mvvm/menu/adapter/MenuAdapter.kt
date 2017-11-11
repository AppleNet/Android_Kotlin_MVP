package com.example.llcgs.android_kotlin.mvvm.menu.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityMenuItemBinding
import com.example.llcgs.android_kotlin.mvvm.menu.model.Menu
import com.example.llcgs.android_kotlin.mvvm.menu.viewmodel.MenuItemViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.menu.adapter.MenuAdapter
 * @author liulongchao
 * @since 2017/11/11
 */


class MenuAdapter:RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {


    var list = ArrayList<Menu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= MenuViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.activity_menu_item, parent, false))

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.setMenu(list[position])
    }


    class MenuViewHolder(private val binding : ActivityMenuItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun setMenu(menu: Menu){
            if (binding.menuItemViewModel == null){
                binding.menuItemViewModel = MenuItemViewModel().apply { this@apply.menu = menu }
            }else{
                binding.menuItemViewModel.menu = menu
            }

        }
    }
}