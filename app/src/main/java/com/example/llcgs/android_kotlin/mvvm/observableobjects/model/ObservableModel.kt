package com.example.llcgs.android_kotlin.mvvm.observableobjects.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.android.databinding.library.baseAdapters.BR

/**
 * com.example.llcgs.android_kotlin.mvvm.observableobjects.model.ObservableModel
 * @author liulongchao
 * @since 2017/11/15
 */
class ObservableModel: BaseObservable() {

    /**
     *  一个BaseObservable的基类为实现监听器注册机制而创建。
     *  Data实现类依然负责通知当属性改变时。
     *  这是通过指定一个Bindable注解给getter以及setter内通知来完成的
     * */
    @Bindable
    var firstName : String = "leBron"
    set(value) {
        field = value
        notifyPropertyChanged(BR.firstName)
    }

    @Bindable
    var lastName : String = "James"
    set(value) {
        field = value
        notifyPropertyChanged(BR.lastName)
    }
}