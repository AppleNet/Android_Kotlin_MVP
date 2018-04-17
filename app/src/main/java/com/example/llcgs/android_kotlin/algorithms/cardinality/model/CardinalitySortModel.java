package com.example.llcgs.android_kotlin.algorithms.cardinality.model;

import com.example.llcgs.android_kotlin.algorithms.bubble.base.BaseAlgorithmsModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType;

/**
 * com.example.llcgs.android_kotlin.algorithms.cardinality.model.CardinalitySortModel
 *
 * @author liulongchao
 * @since 2018/4/10
 */
public class CardinalitySortModel implements BaseAlgorithmsModel {

    public Observable<int[]> cardSort(int[] array) {
        int max = 0;
        for (int anArray : array) {
            if (max < anArray) {
                max = anArray;
            }
        }
        // 判断位数
        int times = 0;
        while (max > 0) {
            max = max / 10;
            times++;
        }
        // 建立十个队列
        List<ArrayList> queue = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ArrayList queue1 = new ArrayList();
            queue.add(queue1);
        }

        // 进行times分配和收集
        for (int i = 0; i < times; i++) {
            //分配
            for (int anArray : array) {
                int x = anArray % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                ArrayList queue2 = queue.get(x);
                queue2.add(anArray);
                queue.set(x, queue2);
            }
        }
        // 收集
        int count = 0;
        for (int j = 0; j < 9; j++) {
            while (queue.get(j).size() > 0) {
                ArrayList<Integer> queue3 = queue.get(j);
                array[count] = queue3.get(0);
                queue3.remove(0);
                count++;
            }
        }

        return Observable.just(array);
    }
}
