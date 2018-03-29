package com.example.llcgs.android_kotlin.algorithms.bubble.model

import com.example.llcgs.android_kotlin.base.model.BaseModel
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.algorithms.bubble.model.BubbleSortModel
 * @author liulongchao
 * @since 2018/3/28
 */
class BubbleSortModel: BaseModel{

    fun bubbleSort(array: Array<Int>): Observable<Array<Int>>{
        // 冒泡排序法
        // 假设有N个数据需要排序，则从第0个开始，依次比较第0个和第1个数据，如果第0个大于第1个则两者交换，
        // 否则什么动作也不做，继续比较第1个和第2个。。。。依次类推，直至所有数据冒泡到数据顶上
        for (i in 0 until array.size - 1) {
            for (j in 0 until array.size - i - 1) {
                // 对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
                if (array[j] > array[j + 1]) {
                    //把大的值交换到后面
                    val temp = array[j]
                    array[j] = array[j + 1]
                    array[j + 1] = temp
                }
            }
        }
        return Observable.just(array)
    }
}