package com.example.llcgs.android_kotlin.design_pattern.facade.presenter.impl

import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Dough
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Fruit
import com.example.llcgs.android_kotlin.design_pattern.facade.bean.Vegetables
import com.example.llcgs.android_kotlin.design_pattern.facade.model.FacadeDesignPatternModel
import com.example.llcgs.android_kotlin.design_pattern.facade.presenter.IFacadeDesignPatternPresenter
import com.example.llcgs.android_kotlin.design_pattern.facade.view.FacadeDesignPatternView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.design_pattern.facade.presenter.impl.FacadeDesignPatternPresenter
 * @author liulongchao
 * @since 2017/12/26
 */
class FacadeDesignPatternPresenter(private val view: FacadeDesignPatternView) : IFacadeDesignPatternPresenter {

    private val model = FacadeDesignPatternModel()

    override fun calculate(fruit: Fruit, vegetables: Vegetables, dough: Dough) {
        model.calculate(fruit, vegetables, dough)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.addDisposable(it)
                }
                .doOnTerminate {
                    view.dismissLoadingDialog()
                }
                .compose(view.bindUntilEvent(ActivityLifeCycleEvent.DESTROY))
                .subscribe {
                    view.onGetResult(it)
                }
    }
}