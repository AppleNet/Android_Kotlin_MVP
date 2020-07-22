package com.example.llcgs.android_kotlin.ui.recyclerview.adapter;

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.ui.recyclerview.bean.Start

class RecyclerViewAdapter(private var context: Context, private val starList: ArrayList<Start>): RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        // View.inflate(context, R.layout.view_recycler_view_item, null) 这样写如果 width设置 match_parent，会导致 item 的宽度并不能充满屏幕 最终调用的是下面注释的这行代码
        // LayoutInflater.from(context).inflate(R.layout.view_recycler_view_item, null) 这样写如果 width设置 match_parent，会导致 item 的宽度并不能充满屏幕
        val view = LayoutInflater.from(context).inflate(R.layout.view_recycler_view_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return starList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.nameTv.text = starList[position].name
    }

    fun getGroupName(position: Int): String {
        return starList[position].groupName
    }

    fun isGroupHeader(position: Int): Boolean {
        if (position == 0) {
            return true
        }
        if (getGroupName(position) == getGroupName(position - 1)) {
            return false
        }
        return true
    }

    class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val nameTv: TextView = itemView.findViewById(R.id.textName)
    }
}
