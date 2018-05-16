package com.example.llcgs.android_kotlin.mvvm.collections

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.util.SparseArray
import com.example.llcgs.android_kotlin.BR
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.databinding.ActivityCollectionsBinding
import com.example.llcgs.android_kotlin.mvvm.base.BaseActivity
import com.example.llcgs.android_kotlin.mvvm.collections.textwatcher.OnlyOneTextWatcher
import com.example.llcgs.android_kotlin.mvvm.collections.viewmodel.CollectionsViewModel
import com.example.llcgs.android_kotlin.mvvm.commfeatures.filters.NoEmptyFilters
import com.example.llcgs.android_kotlin.utils.log.logD
import kotlinx.android.synthetic.main.activity_collections.*
import kotlinx.android.synthetic.main.view_title.*
import java.util.*

/**
 * com.example.llcgs.android_kotlin.mvvm.collections.CollectionsActivity
 * @author liulongchao
 * @since 2017/11/11
 */


class CollectionsActivity: BaseActivity<CollectionsViewModel, ActivityCollectionsBinding>() {

    override fun createViewModel()= CollectionsViewModel()

    override fun createViewDataBinding(): ActivityCollectionsBinding= DataBindingUtil.setContentView(this, R.layout.activity_collections)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
    }

    private fun initViews(){
        pluginTitleTV.text = "Collections"
        indexEdt.filters = arrayOf(NoEmptyFilters())
        indexEdt.addTextChangedListener(OnlyOneTextWatcher(indexEdt))
    }

    private fun initData(){
        /**
         *  给集合赋值
         * */
        val list = arrayListOf("Kobe", "James", "McGrady", "Wade")
        binding.list = list
        val sparse = SparseArray<String>().apply { put(0, "Kobe"); put(1, "James"); put(2, "McGrady"); put(3, "Wade") }
        binding.sparse = sparse
        val map = mapOf("0" to "Wade", "1" to "McGrady", "2" to "James", "3" to "Kobe")
        binding.map = map

        binding.key = "0"
        binding.setVariable(BR.collectionViewModel, viewModel)
    }

    override fun update(o: Observable?, arg: Any?) {
        if (o is CollectionsViewModel){
            "indexString: ${o.indexString}".logD()
            try {
                binding.index = o.indexString.toInt()
            } catch (e: Exception) {

            }
            binding.key = o.indexString
        }
    }
}