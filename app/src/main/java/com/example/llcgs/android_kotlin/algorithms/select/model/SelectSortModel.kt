package com.example.llcgs.android_kotlin.algorithms.select.model

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsModel
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.algorithms.select.model.SelectSortModel
 * @author liulongchao
 * @since 2018/3/30
 */
class SelectSortModel: BaseAlgorithmsModel {

    fun selectSort(array: Array<Int>): Observable<Array<Int>>{
        // 在要排序的一组数组中，选出最小的一个数与第一个位置的数交换；
        // 然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个和最后一个数为止
        // 时间复杂度为 n方 O(n2)
        for(i in 0 until array.size){
            var min = array[i] // 最小值
            var n = i// 最小值索引
            var j = n+1
            while (j < array.size){
                if (array[j] < min){
                    min = array[j]// 最小值赋值给min
                    n = j
                }
                j++
            }
            // 交换位置，第i项赋值给第i+1项
            array[n] = array[i]
            // 最小值赋值给第i项
            array[i] = min
        }
        return Observable.just(array)
    }
}