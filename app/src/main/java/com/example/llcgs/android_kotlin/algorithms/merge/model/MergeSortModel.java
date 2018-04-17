package com.example.llcgs.android_kotlin.algorithms.merge.model;

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsModel;

import io.reactivex.Observable;

/**
 * com.example.llcgs.android_kotlin.algorithms.sort.model.MergeSortModel
 *
 * @author liulongchao
 * @since 2018/4/3
 */
public class MergeSortModel implements BaseAlgorithmsModel {

    /**
     *  合并两个已经排序的表。因为这两个表已经排序，所以若将输出放入到第三个表中，则该算法可以通过对输入数据一趟排序来完成
     *  原理：基本的合并算法是取两个输入数组A和B，一个输出数组C，以及3个计数器Actr、Bctr、Cctr，它们初始于对应数组的开始端。
     *       A[Actr]和B[Bctr]中较小者被拷贝到C中的下一个位置，相关的计数器向前推进一步，当两个输入表有一个用完的时候，则将另一个表中剩余部分拷贝到C中
     *  时间复杂度为O(nlogn)
     *  速度仅次于快速排序，为稳定排序算法，一般用于对总体无序，但是各子项相对有序的数列
     *
     * */

    public Observable<int[]> mergeSort(int[] array) {
        sort(array, 0, array.length - 1);
        return Observable.just(array);
    }

    private void sort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            // 对左边进行递归
            sort(array, left, middle);
            // 对右边进行排序
            sort(array, middle + 1, right);
            // 合并
            merge(array, left, middle, right);
        }
    }

    private static void merge(int[] a, int left, int middle, int right) {
        int[] tmpArr = new int[a.length];
        int mid = middle + 1;
        //右边的起始位置
        int tmp = left;
        int third = left;
        while (left <= middle && mid <= right) {
            //从两个数组中选取较小的数放入中间数组
            if (a[left] <= a[mid]) {
                tmpArr[third++] = a[left++];
            } else {
                tmpArr[third++] = a[mid++];
            }
        }
        //将剩余的部分放入中间数组
        while (left <= middle) {
            tmpArr[third++] = a[left++];
        }
        while (mid <= right) {
            tmpArr[third++] = a[mid++];
        }
        //将中间数组复制回原数组53
        while (tmp <= right) {
            a[tmp] = tmpArr[tmp++];
        }
    }

}
