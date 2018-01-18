package com.example.llcgs.android_kotlin.material.douya.main.adapter

import android.support.design.widget.NavigationView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.llcgs.android_kotlin.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_item_home.*

/**
 * com.example.llcgs.android_kotlin.material.main.adapter.NavigationViewAdapter
 * @author liulongchao
 * @since 2017/12/20
 */
class NavigationViewAdapter(private val navigationView: NavigationView) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    private val VIEW_TYPE_ACCOUNT_LIST = -1
    private lateinit var mMenuAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    private var mShowingAccountList = false

    constructor(navigationView: NavigationView, menuAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) : this(navigationView) {
        this.mMenuAdapter = menuAdapter
        mMenuAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {

            override fun onChanged() {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                onChanged()
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                onChanged()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                onChanged()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                onChanged()
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                onChanged()
            }

        })
    }

    companion object {

        fun init(navigationView: NavigationView): NavigationViewAdapter {
            val recyclerView: RecyclerView = navigationView.getChildAt(navigationView.childCount - 1) as RecyclerView
            val adapter = NavigationViewAdapter(navigationView = navigationView, menuAdapter = recyclerView.adapter)
            recyclerView.adapter = adapter
            return adapter
        }
    }

    fun showAccountList(show: Boolean) {
        if (mShowingAccountList == show){
            return
        }
        val headerCount = navigationView.headerCount
        val menuCount = mMenuAdapter.itemCount - headerCount
        if (show){
            notifyItemRangeRemoved(headerCount, menuCount)
            notifyItemInserted(headerCount)
        }else{
            notifyItemRemoved(headerCount)
            notifyItemRangeInserted(headerCount, menuCount)
        }
        mShowingAccountList = show
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ACCOUNT_LIST) {
            if (holder is AccountListViewHolder) {
                holder.add_account.setOnClickListener(this)
                holder.remove_current_account.setOnClickListener(this)
                holder.manage_accounts.setOnClickListener(this)
            }
        } else {
            mMenuAdapter.onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ACCOUNT_LIST) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_home, parent, false)
            AccountListViewHolder(view, view)
        } else {
            mMenuAdapter.onCreateViewHolder(parent, viewType)
        }
    }

    override fun getItemCount(): Int {
        return if (mShowingAccountList) {
            navigationView.headerCount + 1
        } else {
            mMenuAdapter.itemCount
        }
    }

    override fun getItemId(position: Int): Long {
        return if (mShowingAccountList && position >= navigationView.headerCount) {
            position.toLong()
        } else {
            mMenuAdapter.getItemId(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mShowingAccountList && position >= navigationView.headerCount) {
            VIEW_TYPE_ACCOUNT_LIST
        } else {
            mMenuAdapter.getItemViewType(position)
        }
    }

    override fun onClick(v: View) {
        onClickListener?.let {
            it(v)
        }
    }

    private var onClickListener: ((view: View)->Unit)?= null
    fun setOnClickListener(listener: ((view: View)->Unit)){
        onClickListener = listener
    }

    class AccountListViewHolder(var view: View, override val containerView: View?) : RecyclerView.ViewHolder(view), LayoutContainer
}