package com.example.llcgs.android_kotlin.utils.retrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * com.example.llcgs.android_kotlin.utils.retrofit.Retrofit
 *
 * @author liulongchao
 * @since 2020-08-04
 * <p>
 * 手写 Retrofit
 */
public class Retrofit {

    HttpUrl baseUrl;
    Call.Factory callFactory;
    private final Map<Method, ServiceMethod> serviceMethodCache = new ConcurrentHashMap<>();

    private Retrofit(HttpUrl baseUrl, Call.Factory callFactory) {
        this.baseUrl = baseUrl;
        this.callFactory = callFactory;
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<T> service) {
        try {
            return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {

                @Override
                public @Nullable
                Object invoke(Object proxy, Method method, @Nullable Object[] args) throws Throwable {
                    // 做一下参数的拼接处理
                    // 解析方法上所有的注解信息
                    ServiceMethod serviceMethod = loadServiceMethod(method);
                    return serviceMethod.invoke(args);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result = serviceMethodCache.get(method);
        if (result != null) {
            return result;
        }

        synchronized (serviceMethodCache) {
            result = serviceMethodCache.get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(this, method).build();
                serviceMethodCache.put(method, result);
            }
        }
        return result;
    }


    public static final class Builder {

        private HttpUrl baseUrl;
        private Call.Factory callFactory;

        public Builder baseUrl(String httpUrl) {
            baseUrl = HttpUrl.get(httpUrl);
            return this;
        }

        public Builder callFactory(Call.Factory callFactory) {
            this.callFactory = callFactory;
            return this;
        }

        public Retrofit build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Bad URL required");
            }

            Call.Factory callFactory = this.callFactory;
            if (callFactory == null) {
                callFactory = new OkHttpClient();
            }

            return new Retrofit(baseUrl, callFactory);
        }
    }

}
