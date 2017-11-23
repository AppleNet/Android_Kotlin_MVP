package com.example.llcgs.android_kotlin.mvvm.attributesetters.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.llcgs.android_kotlin.databinding.ViewMenuAdapterItemBinding
import com.example.llcgs.android_kotlin.mvvm.attributesetters.model.MenuAdapterModel
import com.example.llcgs.android_kotlin.mvvm.attributesetters.viewmodel.MenuAdapterViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.attributesetters.adapter.MenuAdapter
 * @author liulongchao
 * @since 2017/11/22
 */
class MenuAdapter: RecyclerView.Adapter<MenuAdapter.MenuAdapterHolder>(), (MenuAdapterModel) -> Unit {

    var list = ArrayList<MenuAdapterModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapterHolder =
            MenuAdapterHolder(ViewMenuAdapterItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: MenuAdapterHolder?, position: Int) {
        holder?.bindData(list[position])
        holder?.binding?.menuAdapterViewModel?.setListener(this)
    }

    override fun getItemCount(): Int = list.size

    class MenuAdapterHolder(val binding:ViewMenuAdapterItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(model: MenuAdapterModel){
            if (binding.menuAdapterViewModel== null){
                binding.menuAdapterViewModel = MenuAdapterViewModel().apply { this@apply.model = model }
            }else{
                binding.menuAdapterViewModel.model = model
            }
        }
    }

    override fun invoke(model: MenuAdapterModel) {
        listener?.let {
            it(model)
        }
    }

    private var listener: ((model: MenuAdapterModel) -> Unit)? = null

    fun setAdapterListener(listener: ((model: MenuAdapterModel) -> Unit)){
        this.listener = listener
    }
}