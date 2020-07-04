package com.example.llcgs.android_kotlin.base.livedata;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class LiveDataBus {


    private final Map<String, BusMutableLiveData<Object>> bus;

    private LiveDataBus() {
        bus = new HashMap<>();
    }

    private static class SingletonHolder {
        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
    }

    public static LiveDataBus get() {
        return SingletonHolder.DEFAULT_BUS;
    }

    /**
     * 注册订阅的方法
     *
     * @param key  key
     * @param type type 用来泛型限定
     * @return MutableLiveData
     */
    public synchronized <T> BusMutableLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusMutableLiveData<>());
        }
        return (BusMutableLiveData<T>) bus.get(key);
    }


    public static class BusMutableLiveData<T> extends MutableLiveData<T> {

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<T> observer) {
            super.observe(owner, observer);
            // 拦截 onChanged 方法
            hook(observer);
        }

        private void hook(Observer<T> observer) {
            //
            Class<LiveData> liveDataClass = LiveData.class;
            try {
                // 反射获取 mVersion 属性
                Field mVersionField = liveDataClass.getDeclaredField("mVersion");
                // 设置作用域
                mVersionField.setAccessible(true);
                // 反射获取 mObservers 属性
                Field mObserversField = liveDataClass.getDeclaredField("mObservers");
                // 设置作用域
                mObserversField.setAccessible(true);

                // 获取 SafeIterableMap<Observer<T>, LifecycleBoundObserver> mObservers 对象
                Object mObservers = mObserversField.get(this);
                // 反射获取 SafeIterableMap 的 get 方法
                Method getMethod = mObservers.getClass().getDeclaredMethod("get", Object.class);
                // 设置作用域
                getMethod.setAccessible(true);
                // 反射调用 get 方法，获取 LifecycleBoundObserver
                Object lifecycleBoundObserver = getMethod.invoke(mObservers, observer);
                // 获取 LifecycleBoundObserver 中的 lastVersion 属性
                Field mLastVersionField = lifecycleBoundObserver.getClass().getDeclaredField("lastVersion");
                mLastVersionField.setAccessible(true);
                // 获取 mVersion 的值
                Object o = mVersionField.get(mObservers);
                // 将 mVersion 值 赋值给 lastVersion
                mLastVersionField.set(lifecycleBoundObserver, o);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
