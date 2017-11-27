package com.example.llcgs.android_kotlin.home.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.llcgs.android_kotlin.R

/**
 * com.example.llcgs.android_kotlin.home.adapter.MainAdapter
 * @author liulongchao
 * @since 2017/11/24
 */
class MainAdapter : RecyclerView.Adapter<MainAdapter.MainAdapterHolder>() {

    var list = ArrayList<String>()

    override fun onBindViewHolder(holder: MainAdapterHolder, position: Int) {
        holder.textView.text = list[position]
        holder.textView.setOnClickListener {
            onItemClickListener?.let {
                it(list[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainAdapterHolder =
            MainAdapterHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_main_adapter_item, parent, false))

    override fun getItemCount(): Int = list.size


    class MainAdapterHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.menuItem)
    }


    private var onItemClickListener: ((string: String) -> Unit)? = null

    public fun setOnItemClickListener(listener: (string: String) -> Unit){
        this.onItemClickListener = listener
    }
}