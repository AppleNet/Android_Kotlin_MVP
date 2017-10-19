package com.example.llcgs.android_kotlin.classandobject.nestclass.bean;


/**
 * com.example.llcgs.android_kotlin.classandobject.nestclass.bean.Button
 *
 * @author liulongchao
 * @since 2017/10/19
 */

public class Button implements View {
    @Override
    public State getCurrentState() {
        return new ButtonState();
    }

    @Override
    public void restoreState(State state) {
        //
    }

    // Java中默认为内部类，隐式地存储了它的外部Button类的引用
    public class ButtonState implements State{
        public String name = this.getClass().getSimpleName();
    }

}
