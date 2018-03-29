package com.example.llcgs.android_kotlin.algorithms.shell.model

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsModel
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.algorithms.shell.model.ShellSortModel
 * @author liulongchao
 * @since 2018/3/29
 */
class ShellSortModel: BaseAlgorithmsModel {

    // 先取一个小于n的整数d1作为第一个增量，把文件的内部记录分成d1个组。所有距离d1的倍数的记录放在同一个组中。
    // 先在各组内进行直接插入排序；然后，取第二个增量d2<d1重复上述的分组和排序，直至所取的增量dt=1(dt<dt-1<...<d2<d1)
    // 即所有记录放在同一组中进行直接插入排序为止。
    // 平均时间复杂度为log n
    fun shellSort(array: Array<Int>): Observable<Array<Int>>{
        var d = array.size
        while (true){
            d /= 2 // 第一个增量
            for(x in 0 until d){ // 分成d1个组
                var i = x
                while (i < array.size){ //开始插入排序
                    val temp = array[i]
                    var j = i-d
                    while (j >= 0 && array[j] > temp){
                        array[j+d] = array[j]
                        j -= d
                    }
                    array[j+d] = temp
                    i += d
                }
            }
            if (d == 1){
                break
            }
        }
        return Observable.just(array)
    }
}