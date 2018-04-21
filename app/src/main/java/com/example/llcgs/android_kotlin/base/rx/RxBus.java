package com.example.llcgs.android_kotlin.base.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * com.example.llcgs.android_rx.rxbus.RxBus
 *
 * @author liulongchao
 * @since 2017/6/9
 */


public class RxBus {

    public static volatile RxBus bus;
    private PublishSubject<Object> subject;

    private RxBus() {
        subject = PublishSubject.create();
    }

    public static RxBus getInstance() {
        RxBus rxBus2 = bus;
        if (rxBus2 == null) {
            synchronized (RxBus.class) {
                rxBus2 = bus;
                if (rxBus2 == null) {
                    bus = new RxBus();
                    rxBus2 = bus;
                }
            }
        }
        return rxBus2;
    }

    /**
     * 发送消息
     *
     * */
    public void post(Object object){
        subject.onNext(object);
    }

    /**
     * 接受消息
     * */
    public <T>Observable<T> tObservable(Class<T> tClass){
        return subject.hide().ofType(tClass);
    }

}
