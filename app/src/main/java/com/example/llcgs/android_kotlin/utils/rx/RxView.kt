package com.example.llcgs.android_kotlin.utils.rx;

import android.view.View
import io.reactivex.Observable

// 自定义 RxView 操作符
class RxView {

    /**
     *  伴生对象，可静态访问
     * */
    companion object{

        fun click(view: View): Observable<Any> {
            return ViewClickObservable(view)
        }
    }
}
