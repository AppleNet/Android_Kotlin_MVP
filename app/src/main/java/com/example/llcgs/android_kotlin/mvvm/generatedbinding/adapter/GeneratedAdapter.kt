package com.example.llcgs.android_kotlin.mvvm.generatedbinding.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.llcgs.android_kotlin.databinding.ViewGeneratedAdapterItemBinding
import com.example.llcgs.android_kotlin.mvvm.generatedbinding.model.GeneratedItemModel
import com.example.llcgs.android_kotlin.mvvm.generatedbinding.viewmodel.GeneratedAdapterViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.generatedbinding.adapter.GeneratedAdapter
 * @author liulongchao
 * @since 2017/11/17
 */
class GeneratedAdapter: RecyclerView.Adapter<GeneratedAdapter.GeneratedViewHolder>() {


    var list = ArrayList<GeneratedItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneratedViewHolder {
        // Creating R.layout.view_generated_adapter_item
        // LayoutInflater.from(parent.context).inflate(R.layout.view_generated_adapter_item, parent, false)
        // 自动找到R.layout.view_generated_adapter_item这个布局文件
        val inflate = ViewGeneratedAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GeneratedViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: GeneratedViewHolder, position: Int) {
        holder.binData(list[position])
    }

    override fun getItemCount(): Int = list.size


    class GeneratedViewHolder(private val bindingView: ViewGeneratedAdapterItemBinding): RecyclerView.ViewHolder(bindingView.root){

        fun binData(generatedItemModel: GeneratedItemModel){
            if (bindingView.generatedAdapterViewModel == null){
                bindingView.generatedAdapterViewModel = GeneratedAdapterViewModel().apply { this@apply.generatedItemModel = generatedItemModel }
            }else{
                bindingView.generatedAdapterViewModel.generatedItemModel = generatedItemModel
            }
        }

    }


}