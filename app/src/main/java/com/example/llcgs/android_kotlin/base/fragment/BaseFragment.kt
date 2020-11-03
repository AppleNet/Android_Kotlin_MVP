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
    private var isViewCreate = false;
    private var isVisibleState = false;

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
        isViewCreate = true
        if (mRootView != null) {
            val parent = mRootView?.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(mRootView)
            }
        }
        mPresenter = createPresenter()
        if (userVisibleHint) {
            userVisibleHint = true
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.CREATE_VIEW)
        if (firstInflate) {
            initViews()
            initData()
            firstInflate = false
        }
    }

    override fun onStart() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.START)
        super.onStart()
    }

    override fun onResume() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.RESUME)
        super.onResume()
        if (userVisibleHint && isVisibleState) {
            userVisibleHint = true
        }
    }

    override fun onPause() {
        lifecycleSubject.onNext(FragmentLifeCycleEvent.PAUSE)
        super.onPause()
        if (userVisibleHint && !isVisibleState) {
            userVisibleHint = false
        }
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

    override fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isViewCreate) {
            if (isVisibleToUser && !isVisibleState) {
                dispatchUserVisibleHint(true)
            } else if (!isVisibleToUser && isVisibleState){
                dispatchUserVisibleHint(false)
            }
        }
    }

    protected fun dispatchUserVisibleHint(isVisibleToUser: Boolean) {
        this.isVisibleState = isVisibleToUser

        if (isVisibleToUser && isParentInvisible()) {
            return
        }

        if (isVisibleToUser) {
            onFragmentDataLoad()
            // TODO 为了解决第二个问题，T1 到 T2 T2里面嵌套的ViewPager的Fragment默认不会分发执行
            // 手动 嵌套 分发执行
            // TODO 为了解决第二个问题，T1 到 T2 T2里面嵌套的ViewPager的Fragment默认不会分发执行
            // 在双重ViewPager嵌套的情况下，第一次滑到Frgment 嵌套ViewPager(fragment)的场景的时候
            // 此时只会加载外层Fragment的数据，而不会加载内嵌viewPager中的fragment的数据，因此，我们
            // 需要在此增加一个当外层Fragment可见的时候，分发可见事件给自己内嵌的所有Fragment显示
            dispatchChildVisibleState(true)
        } else {
            onFragmentDataStop()
            dispatchChildVisibleState(false)
        }
    }

    protected fun dispatchChildVisibleState(isVisibleToUser: Boolean) {
        val childFragmentManager = childFragmentManager
        val fragments = childFragmentManager.fragments
        for (fragment in fragments) {
            if (fragment is BaseFragment<*> && !fragment.isHidden && fragment.userVisibleHint) {
                fragment.dispatchUserVisibleHint(isVisibleToUser)
            }
        }
    }

    protected fun isParentInvisible(): Boolean {
        val parentFragment = parentFragment
        if (parentFragment is BaseFragment<*>) {
            return!parentFragment.isVisibleState
        }
        return false
    }

    override fun showLoadingDialog() {}

    override fun dismissLoadingDialog() {}

    override fun showException(imageRes: Int, message: String) {}

    override fun showContentView() {}

    override fun lifecycle(): Observable<LifeCycleEvent> = lifecycleSubject.hide()

    override fun <T : Any?> bindUntilEvent(event: LifeCycleEvent): LifecycleTransformer<T> = RxLifecycle.bindUntilEvent(lifecycleSubject, event)

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> = RxLifecycle.bind(lifecycleSubject, LifecycleHelper.fragmentLifecycle())

    abstract fun getLayoutId(): Int

    abstract fun createPresenter(): P

    abstract fun initViews()

    abstract fun initData()

    protected fun onFragmentDataLoad() {}

    protected fun onFragmentDataStop() {}

}