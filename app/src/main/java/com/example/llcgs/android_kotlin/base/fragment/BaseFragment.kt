package com.example.llcgs.android_kotlin.base.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.llcgs.android_kotlin.base.view.BaseView
import com.example.llcgs.android_kotlin.base.lifecycleevent.FragmentLifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.LifeCycleEvent
import com.example.llcgs.android_kotlin.base.lifecycleevent.LifecycleHelper
import com.example.llcgs.android_kotlin.base.presenter.SuperPresenter
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.RxLifecycle
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

/**
 * com.example.llcgs.android_kotlin.base.fragment.BaseFragment
 * @author liulongchao
 * @since 2017/12/1
 */
abstract class BaseFragment<P : SuperPresenter> : Fragment(), BaseView, LifecycleProvider<LifeCycleEvent> {

    private var compositeDisposable: CompositeDisposable? = null
    private val lifecycleSubject: BehaviorSubject<LifeCycleEvent> = BehaviorSubject.create()
    protected lateinit var mPresenter: P
    private var mRootView: View? = null
    private var firstInflate = true

    override fun onAttach(context: Context?) {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.ATTACH)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(FragmentLifeCycleEvent.CREATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), null, false)
        }
        if (mRootView != null) {
            val parent = mRootView?.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(mRootView)
            }
        }
        mPresenter = createPresenter()
        return mRootView
    }

    abstract fun getLayoutId(): Int

    abstract fun createPresenter(): P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.CREATE_VIEW)
        if (firstInflate) {
            initViews()
            initData()
            firstInflate = false
        }
    }

    abstract fun initViews()

    abstract fun initData()

    override fun onStart() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.START)
        super.onStart()
    }

    override fun onResume() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.RESUME)
        super.onResume()
    }

    override fun onPause() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.STOP)
        super.onStop()
    }


    override fun onDestroyView() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.DESTROY)
        if (compositeDisposable != null) {
            compositeDisposable?.clear()
        }
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        lifecycleSubject.onNext(FragmentLifeCycleEvent.DETACH)
    }

    override fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    override fun showLoadingDialog() {
    }

    override fun dismissLoadingDialog() {
    }

    override fun showContentView() {
    }

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun showException(imageRes: Int, message: String) {

    }

    override fun lifecycle(): Observable<LifeCycleEvent> = lifecycleSubject.hide()

    override fun <T : Any?> bindUntilEvent(event: LifeCycleEvent): LifecycleTransformer<T> = RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> = RxLifecycle.bind(lifecycleSubject, LifecycleHelper.fragmentLifecycle())

}