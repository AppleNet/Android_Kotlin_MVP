package com.example.llcgs.android_kotlin.classandobject.nestclass.bean;

/**
 * com.example.llcgs.android_kotlin.classandobject.nestclass.bean.View
 *
 * @author liulongchao
 * @since 2017/10/19
 */


public interface View {

    State getCurrentState();
    void restoreState(State state);
}
