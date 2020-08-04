package com.example.llcgs.android_kotlin.utils.retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * com.example.llcgs.android_kotlin.utils.retrofit.ServiceMethod
 * <p>
 * 解析注解信息
 * 记录请求类型，参数，完整地址
 */
public class ServiceMethod {


    private ParametersHandler[] parametersHandlers;
    private HttpUrl baseUrl;
    private Call.Factory callFactory;
    private String httpMethod;
    private String relativeUrl;
    private boolean hasBody;
    private FormBody.Builder formBuilder;
    private HttpUrl.Builder httpUrlBuilder;

    private ServiceMethod(Builder builder) {
        baseUrl = builder.retrofit.baseUrl;
        callFactory = builder.retrofit.callFactory;
        httpMethod = builder.httpMethod;
        relativeUrl = builder.relativeUrl;
        hasBody = builder.hasBody;
        parametersHandlers = builder.parametersHandlers;

        if (hasBody) {
            formBuilder = new FormBody.Builder();
        }
    }

    public Object invoke(Object[] args) {
        /*
         *  1. 处理请求的地址与参数
         *
         * */
        for (int i = 0; i < parametersHandlers.length; i++) {
            ParametersHandler parametersHandler = parametersHandlers[i];
            parametersHandler.apply(this, args[i].toString());
        }

        HttpUrl url;
        if (httpUrlBuilder == null) {
            httpUrlBuilder = baseUrl.newBuilder(relativeUrl);
        }

        url = httpUrlBuilder.build();

        FormBody formBody = null;
        if (formBuilder != null) {
            formBody = formBuilder.build();
        }

        Request build = new Request.Builder().url(url).method(httpMethod, formBody).build();
        return callFactory.newCall(build);
    }

    // get 请求  把 key - value 拼接起来
    void addQueryParameters(String key, String value) {
        if (httpUrlBuilder == null) {
            httpUrlBuilder = baseUrl.newBuilder(relativeUrl);
        }
        if (httpUrlBuilder != null) {
            httpUrlBuilder.addQueryParameter(key, value);
        }
    }

    // post 请求 把 key - value 放到 body 中
    void addFieldParameters(String key, String value) {
        if (formBuilder == null) {
            formBuilder = new FormBody.Builder();
        }
        formBuilder.add(key, value);
    }

    public static class Builder {

        private Retrofit retrofit;
        private Annotation[] methodAnnotations;
        private Annotation[][] parameterAnnotations;
        private String httpMethod;
        private String relativeUrl;
        private boolean hasBody;
        private ParametersHandler[] parametersHandlers;


        public Builder(Retrofit retrofit, Method method) {
            // 解析注解
            this.retrofit = retrofit;
            methodAnnotations = method.getAnnotations();
            parameterAnnotations = method.getParameterAnnotations();
        }


        public ServiceMethod build() {
            /*
             *  进行具体的解析
             *
             *   1. 解析方法上的注解，只处理 POST 和 GET
             *   2. 解析方法参数的注解
             *
             * */
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }

            // 2. 解析方法参数的注解
            int length = parameterAnnotations.length;
            parametersHandlers = new ParametersHandler[length];
            for (int i = 0; i < parameterAnnotations.length; i++) {
                // 解析一个参数上的所有注解
                for (Annotation annotation : parameterAnnotations[i]) {
                    // 只处理 Field 和 Query 注解
                    // 可以加一个判断 如果 httpMethod 是 get 请求，现在解析到 Field 注解，可以提示开发者实用 Query 注解
                    if (annotation instanceof Field) {
                        // 得到注解上的 value，请求参数的 key
                        String key = ((Field) annotation).value();
                        parametersHandlers[i] = new ParametersHandler.FieldParametersHandler(key);
                    } else if (annotation instanceof Query) {
                        String key = ((Query) annotation).value();
                        parametersHandlers[i] = new ParametersHandler.QueryParametersHandler(key);
                    }
                }
            }
            return new ServiceMethod(this);
        }

        private void parseMethodAnnotation(Annotation annotation) {
            if (annotation instanceof GET) {
                // 记录当前请求方式
                this.httpMethod = "GET";
                this.relativeUrl = ((GET) annotation).value();
                this.hasBody = false;
            } else if (annotation instanceof POST) {
                // 记录当前请求方式
                this.httpMethod = "POST";
                this.relativeUrl = ((POST) annotation).value();
                this.hasBody = true;
            }
        }

    }

}
