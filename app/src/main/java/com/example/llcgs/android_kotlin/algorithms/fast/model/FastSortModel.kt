package com.example.llcgs.android_kotlin.algorithms.fast.model

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsModel
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.algorithms.fast.model.FastSortModel
 * @author liulongchao
 * @since 2018/4/2
 */
class FastSortModel : BaseAlgorithmsModel {

    /**
     *  选择一个基准，通常选择第一个元素或者最后一个元素，通过一趟扫描，将待排序列分成两部分，
     *  一部分比基准元素小，一部分大于等于基准元素，此时基准元素在其排好序后的正确位置，然后
     *  再用同样的方法递归地排序划分的两部分
     *
     *  时间复杂度为O(nlogn)
     *
     * */
    fun fastSort(array: Array<Int>): Observable<Array<Int>> {
        if(array.isNotEmpty()){
            quickSort(array, 0, array.size - 1)
        }
        return Observable.just(array)
    }

    private fun quickSort(array: Array<Int>, low: Int, high: Int) {
        if (low < high) {
            val tmp = array[low]//基准元素
            var tmpLow = low
            var tmpHigh = high
            while (tmpLow < tmpHigh) {
                // 找到比基准元素小的位置
                while (tmpLow < tmpHigh && array[high] >= tmp) {
                    tmpHigh--
                }
                array[tmpLow] = array[tmpHigh]
                while (tmpLow < tmpHigh && array[tmpLow] < tmp) {
                    tmpLow++
                }
                array[tmpHigh] = array[tmpLow]
            }
            array[tmpLow] = tmp
            quickSort(array, 0, tmpLow - 1)
            quickSort(array, tmpLow + 1, tmpHigh)
        }
    }

}