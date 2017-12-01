package com.example.llcgs.android_kotlin.base.lifecycleevent

/**
 * 作者：Alex
 * 时间：2017/4/9 18:55
 * 简述：
 */
enum class FragmentLifeCycleEvent : LifeCycleEvent {
    ATTACH,
    CREATE,
    CREATE_VIEW,
    START,
    RESUME,
    PAUSE,
    STOP,
    DESTROY_VIEW,
    DESTROY,
    DETACH
}
