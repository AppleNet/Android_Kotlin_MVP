package com.example.llcgs.android_kotlin.utils.retrofit;

public abstract class ParametersHandler {

    abstract void apply(ServiceMethod serviceMethod, String value);

    static class QueryParametersHandler extends ParametersHandler{

        String key;

        QueryParametersHandler(String key) {
            this.key = key;
        }

        @Override
        void apply(ServiceMethod serviceMethod, String value) {
            serviceMethod.addQueryParameters(key, value);
        }
    }

    static class FieldParametersHandler extends ParametersHandler{

        String key;

        FieldParametersHandler(String key) {
            this.key = key;
        }

        @Override
        void apply(ServiceMethod serviceMethod, String value) {
            serviceMethod.addFieldParameters(key, value);
        }
    }
}
