package com.example.llcgs.android_kotlin.algorithms.merge.presenter.impl

import com.example.llcgs.android_kotlin.algorithms.merge.model.MergeSortModel
import com.example.llcgs.android_kotlin.algorithms.merge.presenter.IMergeSortPresenter
import com.example.llcgs.android_kotlin.algorithms.merge.view.MergeSortView
import com.example.llcgs.android_kotlin.base.lifecycleevent.ActivityLifeCycleEvent
import com.trello.rxlifecycle2.RxLifecycle.bindUntilEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * com.example.llcgs.android_kotlin.algorithms.merge.presenter.impl.MergeSortPresenter
 * @author liulongchao
 * @since 2018/4/2
 */
class MergeSortPresenter(private var view: MergeSortView): IMergeSortPresenter {

    private val model = MergeSortModel()

    override fun mergeSort(array: Array<Int>) {
        model.mergeSort(array.toIntArray())
            .doOnSubscribe {
                view.addDisposable(it)
            }
            .compose(view.bindUntilEvent<IntArray>(ActivityLifeCycleEvent.DESTROY))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.onGetMergeSort(it.toTypedArray())
            }
    }

    /*
     * unicode编码转中文
     */
    fun decodeUnicode(dataStr: String): String {
        var start = 0
        var end = 0
        val buffer = StringBuffer()
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2)
            var charStr = ""
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length)
            } else {
                charStr = dataStr.substring(start + 2, end)
            }
            val letter = Integer.parseInt(charStr, 16).toChar() // 16进制parse整形字符串。
            buffer.append(letter.toString())
            start = end
        }
        return buffer.toString()
    }
}