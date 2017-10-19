package com.example.llcgs.android_kotlin.classandobject.interfaces.model;

import com.example.llcgs.android_kotlin.classandobject.interfaces.SevenInterface;
import com.example.llcgs.android_kotlin.classandobject.interfaces.function.SevenInterfaceKt;

import org.jetbrains.annotations.NotNull;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * com.example.llcgs.android_kotlin.classandobject.interfaces.model.SevenModelJava
 *
 * @author liulongchao
 * @since 2017/10/18
 */

// TODO Java中认为Unit就是Unit 不等于void
public class SevenModelJava implements Function1<SevenInterface, Unit>, SevenInterface {

    private void getInterface(){
        SevenInterfaceKt.setOnSevenInterfaceOne(this);
    }

    @Override
    public Unit invoke(SevenInterface sevenInterface) {

        return null;
    }

    @NotNull
    @Override
    public String one() {
        return null;
    }

    @NotNull
    @Override
    public String two() {
        return null;
    }

    @NotNull
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String value) {

    }
}
