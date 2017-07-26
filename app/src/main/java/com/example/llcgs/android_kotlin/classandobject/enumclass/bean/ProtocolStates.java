package com.example.llcgs.android_kotlin.classandobject.enumclass.bean;

/**
 * com.example.llcgs.android_kotlin.classandobject.enumclass.bean.ProtocolStates
 *
 * @author liulongchao
 * @since 2017/7/25
 */


public enum ProtocolStates {
    // Java中的枚举

    KOBE {
        @Override
        void getName() {

        }
    },

    JAMES {
        @Override
        void getName() {

        }
    };

    abstract void getName();
}
