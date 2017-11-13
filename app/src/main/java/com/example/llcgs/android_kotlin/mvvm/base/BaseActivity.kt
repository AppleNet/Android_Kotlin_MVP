package com.example.llcgs.android_kotlin.mvvm.base

import android.app.Activity
import android.app.Fragment
import android.databinding.ViewDataBinding
import android.os.Bundle
import java.util.*

/**
 * Base [Activity] class for every Activity in this application.
 */
abstract class BaseActivity<VM : BaseViewModel, B : ViewDataBinding> : Activity(), Observer {

    protected lateinit var viewModel: VM
    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel()
        viewModel.addObserver(this)
        binding = createViewDataBinding()
    }

    abstract fun createViewModel(): VM
    abstract fun createViewDataBinding(): B

    /**
     * Adds a [Fragment] to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment The fragment to be added.
     */
    protected fun addFragment(containerViewId: Int, fragment: Fragment, tag: String) {
        val fragmentTransaction = this.fragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment, tag)
        fragmentTransaction.commit()
    }

    fun <T : Fragment> getFragment(tag: String): T = fragmentManager.findFragmentByTag(tag) as T

    override fun update(o: Observable?, arg: Any?) {

    }

}
