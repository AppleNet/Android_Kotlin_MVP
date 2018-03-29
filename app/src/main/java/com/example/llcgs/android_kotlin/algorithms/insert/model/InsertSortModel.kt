package com.example.llcgs.android_kotlin.algorithms.insert.model

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsModel
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.algorithms.insert.model.InsertSortModel
 * @author liulongchao
 * @since 2018/3/29
 */
class InsertSortModel: BaseAlgorithmsModel {

    fun insertSort(array: Array<Int>):Observable<Array<Int>>{
        // 每步将一个待排序的记录，
        // 按照其顺序码大小插入到前面已经排序子序列的合适位置，直到全部插入排序完为止。
        // 文件初态不同时，直接插入排序所耗费的时间有很大差异，若文件初态为正序，则每个待插入记录只需要比较一次就能够找到适合的位置插入。
        // 故算法的时间复杂度为O(N)，这是最好的情况。
        // 若初态为反序，则第i个待插入记录需要比较i+1次才能找到合适的位置插入，故时间复杂度为O(n的平方),这是最坏的情况
        for(i in 1 until array.size){
            val temp = array[i] // 待排序的值 记录为temp
            var j:Int = i - 1
            while (j >= 0) {
                // 将大于temp的往后移动一位
                if (array[j] > temp) {
                    array[j + 1] = array[j]
                } else {
                    break
                }
                j--// 角标-1
            }
            array[j+1] = temp
        }
        return Observable.just(array)
    }

    fun secondInsertSort(array: Array<Int>):Observable<Array<Int>>{
        // 按照二分法的方式 找到要插入的位置进行插入。基本思想和直接插入一样
        // 二分插入排序的比较次数与待排序记录的初始状态无关，仅依赖于记录的个数，当n较大时，比直接插入排序的最大比较次数少得多
        // 但大于直接插入排序的最小比较次数。算法的移动次数与直接插入排序算法的相同，最坏情况为二分之n方，平均移动次数为n方
        for(i in 1 until array.size){
            val temp = array[i]
            var start = 0
            var end = i-1
            var mid = 0 // 必须声明在while循环的外面 否则就成为了死循环
            // 按照二分法来查找
            while (start <= end){
                mid = (start + end)/2
                if (temp < array[mid]){
                    end = mid - 1
                }else{
                    start = mid + 1
                }
            }
            var j = i-1
            while(j >= start){
                array[j+1] = array[j]
                // 这一步尤为重要 否则会死循环
                j--
            }
            if (start != i){
                array[start] = temp
            }

        }
        return Observable.just(array)

    }
}