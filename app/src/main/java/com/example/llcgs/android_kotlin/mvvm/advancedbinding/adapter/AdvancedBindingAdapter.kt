package com.example.llcgs.android_kotlin.mvvm.advancedbinding.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.android.databinding.library.baseAdapters.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ViewAdvancedBindingItemBinding
import com.example.llcgs.android_kotlin.mvvm.advancedbinding.model.AdvancedBindingModel
import com.example.llcgs.android_kotlin.mvvm.advancedbinding.viewmodel.AdvancedBindingItemViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.advancedbinding.adapter.AdvancedBindingAdapter
 * @author liulongchao
 * @since 2017/11/20
 */
class AdvancedBindingAdapter:RecyclerView.Adapter<AdvancedBindingAdapter.AdvancedBindingViewHolder>() {

    var list = ArrayList<AdvancedBindingModel>()

    override fun getItemCount(): Int= list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvancedBindingViewHolder =
            AdvancedBindingViewHolder(ViewAdvancedBindingItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: AdvancedBindingViewHolder, position: Int) {
        // Dynamic Variables
        holder.bindData(list[position])
        holder.bindingItem.setVariable(BR.advancedBindingItemViewModel, holder.bindingItem.advancedBindingItemViewModel)
        holder.bindingItem.executePendingBindings() //立即更新UI
    }

    class AdvancedBindingViewHolder(binding: ViewAdvancedBindingItemBinding): RecyclerView.ViewHolder(binding.root), (AdvancedBindingModel) -> Unit {

        var bindingItem = binding

        fun bindData(advancedBindingModel: AdvancedBindingModel){
            if (bindingItem.advancedBindingItemViewModel == null){
                bindingItem.advancedBindingItemViewModel = AdvancedBindingItemViewModel().apply { this@apply.advancedBindingModel = advancedBindingModel }
            }else{
                bindingItem.advancedBindingItemViewModel.advancedBindingModel = advancedBindingModel
            }
            bindingItem.advancedBindingItemViewModel.setItemClickListener(this)
        }

        var inflate: View? = null
        override fun invoke(advancedBindingModel: AdvancedBindingModel) {
            if (!bindingItem.viewStub.isInflated){
                inflate = bindingItem.viewStub.viewStub.inflate()
                inflate?.findViewById<TextView>(R.id.sub1)?.text = advancedBindingModel.title
            }else{
                val linearLayout = inflate?.findViewById<LinearLayout>(R.id.subLayout)
                if (linearLayout?.visibility == View.GONE) {
                    linearLayout.visibility = View.VISIBLE
                } else {
                    linearLayout?.visibility = View.GONE
                }
            }

        }
    }


}