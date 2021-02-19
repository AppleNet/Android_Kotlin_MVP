package com.example.kotlin.plugin;

import android.content.Context;

/**
 * com.example.kotlin.plugin.IPluginHook
 *
 * @author liulongchao
 * @since 2021/2/18
 */
public interface IPluginHook {

    void hookPMS();

    void hookAMS();

    void hookHandler();

    void hookInstrumentation(Context context);

    void hookClassLoader();
}
