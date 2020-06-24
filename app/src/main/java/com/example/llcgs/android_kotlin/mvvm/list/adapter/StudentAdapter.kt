package com.example.llcgs.android_kotlin.mvvm.list.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ViewItemListBinding
import com.example.llcgs.android_kotlin.mvvm.list.model.Student
import com.example.llcgs.android_kotlin.mvvm.list.viewmodel.ItemListViewModel

/**
 * com.example.llcgs.android_kotlin.mvvm.list.adapter.StudentAdapter
 * @author liulongchao
 * @since 2017/10/30
 */


class StudentAdapter: RecyclerView.Adapter<StudentAdapter.StudentAdapterHolder>() {

    var list:List<Student> = ArrayList()

    override fun onBindViewHolder(holder: StudentAdapterHolder, position: Int) {
        holder.bindStudent(list[position])
    }

    override fun getItemCount()= list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= StudentAdapterHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_item_list, parent, false))


    class StudentAdapterHolder(private val viewItemListBinding: ViewItemListBinding) : RecyclerView.ViewHolder(viewItemListBinding.itemPeople){

        fun bindStudent(student: Student){
            if (viewItemListBinding.viewmodel == null){
                viewItemListBinding.viewmodel = ItemListViewModel().apply { this@apply.student = student }
            }else{
                viewItemListBinding.viewmodel?.student = student
            }
        }
    }
}