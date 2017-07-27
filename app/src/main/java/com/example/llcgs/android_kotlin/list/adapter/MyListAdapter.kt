package com.example.llcgs.android_kotlin.list.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.llcgs.android_kotlin.R

/**
 * com.example.llcgs.android_kotlin.list.adapter.MyListAdapter
 * @author liulongchao
 * @since 2017/7/26
 */


class MyListAdapter(list: List<String>): RecyclerView.Adapter<MyListAdapter.ListHolder>() {

    private var context: Context? = null
    private lateinit var view: View
    private var list: List<String> = ArrayList()

    init {
        this.list = list
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.textView.text = list.get(position)
        view.setOnClickListener { onItemClickListener.onItemClick(position) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        context = parent.context
        view = LayoutInflater.from(context).inflate(R.layout.view_item_ry, parent, false)
        return ListHolder(view)
    }


    class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(R.id.textView4) as TextView
    }

    interface OnItemClickListener{
        fun onItemClick(text: Int)
    }

    lateinit var onItemClickListener: OnItemClickListener

    fun setOnClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }
}