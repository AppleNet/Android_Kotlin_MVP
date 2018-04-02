package com.example.llcgs.android_kotlin.algorithms.heap.model

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsModel
import io.reactivex.Observable

/**
 * com.example.llcgs.android_kotlin.algorithms.heap.model.HeapSortModel
 * @author liulongchao
 * @since 2018/3/30
 */
class HeapSortModel : BaseAlgorithmsModel {

    /**
     *  堆排序也是一种不稳定的排序算法
     *  堆排序优于简单选择排序的原因：直接选择排序中，为了从R[1..n]中选出关键字最小的记录，必须进行n-1次比较，
     *  然后在R[n-2]中选出关键字最小的记录，又需要做n-2次比较。事实上，后面的n-2次比较中，有许多比较可能在前面的n-1次比较中已经做过，
     *  但由于前一趟排序时保留这些比较结果，所以后一趟排序又重复执行了这些比较操作。
     *  堆排序可通过树形结构保存部分比较结果，可减少比较次数
     *  堆排序的最坏时间复杂度为O(nlogn)。堆排序的平均性能比较接近于最坏性能。由于建初始堆所需要的比较次数较多，所以堆排序不适宜记录数较少的文件
     * */
    fun heapSort(array: Array<Int>): Observable<Array<Int>> {
        for (i in 0 until array.size - 1) {
            // 建堆
            buildMaxHeap(array, array.size - 1 - i)
            // 交换堆顶和最后一个元素
            swap(array, 0, array.size - 1 - i)
        }
        return Observable.just(array)
    }

    //对data数组从0到lastIndex建大顶堆
    private fun buildMaxHeap(data: Array<Int>, lastIndex: Int) {
        // 从lastIndex处节点(最后一个节点)的父节点开始
        var i = (lastIndex - 1) / 2
        while (i >= 0) {
            // k保存正在判断的节点
            var k = i
            // 如果当前k节点的子节点存在
            while (k * 2 + 1 <= lastIndex) {
                //k节点的左子节点的索引
                var biggerIndex = 2 * k + 1
                //如果biggerIndex小于lastIndex，即biggerIndex代表的k节点的右子节点存在
                if (biggerIndex < lastIndex) {
                    //如果右子节点的值较大
                    if (data[biggerIndex] < data[biggerIndex + 1]) {
                        biggerIndex++
                    }
                }
                // 如果k节点的值小于其较大的子节点的值
                if (data[k] < data[biggerIndex]) {
                    // 交换她们
                    swap(data, k, biggerIndex)
                    // 将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k = biggerIndex
                } else {
                    break
                }
            }
            i--
        }
    }

    private fun swap(data: Array<Int>, i: Int, j: Int) {
        val tmp = data[i]
        data[i] = data[j]
        data[j] = tmp
    }
}