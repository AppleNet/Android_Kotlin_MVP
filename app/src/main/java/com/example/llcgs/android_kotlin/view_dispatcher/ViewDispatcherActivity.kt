
package com.example.llcgs.android_kotlin.view_dispatcher

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.view_dispatcher.presenter.impl.ViewDispatcherPresenter
import com.example.llcgs.android_kotlin.view_dispatcher.view.ViewDispatcherView
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_view_dispatcher.*

/*
 * com.example.llcgs.android_kotlin.view_dispatcher.ViewDispatcherActivity
 * @author liulongchao
 * @since 2018/12/12
 */
@RouterRule("ViewDispatcher")
class ViewDispatcherActivity :BaseActivity<ViewDispatcherView, ViewDispatcherPresenter>(), ViewDispatcherView{

    override fun createPresenter()= ViewDispatcherPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_dispatcher)

//        myButton.setOnTouchListener { _, event ->
//            val action = event?.action
//            when(action){
//                MotionEvent.ACTION_DOWN ->{
//                    Log.d("EventActivity", "onTouchListener ACTION_DOWN")
//                }
//                MotionEvent.ACTION_MOVE ->{
//                    Log.d("EventActivity", "onTouchListener ACTION_MOVE")
//                }
//                MotionEvent.ACTION_UP ->{
//                    Log.d("EventActivity", "onTouchListener ACTION_UP")
//                }
//            }
//            // return false 说明不在本层消费，需要向下传递以及向上回传
//            false
//        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev?.action
        when (action) {
            MotionEvent.ACTION_DOWN -> Log.i("EventActivity", "ViewDispatcherActivity -----  dispatchTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.i("EventActivity", "ViewDispatcherActivity -----  dispatchTouchEvent ACTION_MOVE")
            MotionEvent.ACTION_UP -> Log.i("EventActivity", "ViewDispatcherActivity -----  dispatchTouchEvent ACTION_UP")
            else -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action
        when (action) {
            MotionEvent.ACTION_DOWN -> Log.i("EventActivity", "ViewDispatcherActivity -----  onTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.i("EventActivity", "ViewDispatcherActivity -----  onTouchEvent ACTION_MOVE")
            MotionEvent.ACTION_UP -> Log.i("EventActivity", "ViewDispatcherActivity -----  onTouchEvent ACTION_UP")
            else -> {
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onViewDispatcher() {
        //
    }

}
