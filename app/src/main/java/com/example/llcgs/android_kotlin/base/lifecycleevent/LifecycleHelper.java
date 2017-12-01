package com.example.llcgs.android_kotlin.base.lifecycleevent;

import com.trello.rxlifecycle2.OutsideLifecycleException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 作者：Alex
 * 时间：2017/4/9 20:06
 * 简述：
 */
public class LifecycleHelper {
    public static Function<LifeCycleEvent, LifeCycleEvent> activityLifecycle() {
        return ACTIVITY_LIFECYCLE;
    }

    /**
     * FragmentEvent提供
     *
     * @return
     */
    public static Function<LifeCycleEvent, LifeCycleEvent> fragmentLifecycle() {
        return FRAGMENT_LIFECYCLE;
    }

    private static final Function<LifeCycleEvent, LifeCycleEvent> ACTIVITY_LIFECYCLE =
            new Function<LifeCycleEvent, LifeCycleEvent>() {
                @Override
                public LifeCycleEvent apply(@NonNull LifeCycleEvent lastEvent) throws Exception {
                    if (lastEvent.equals(ActivityLifeCycleEvent.CREATE) || lastEvent.equals(ActivityLifeCycleEvent.STOP)) {
                        return ActivityLifeCycleEvent.DESTROY;
                    } else if (lastEvent.equals(ActivityLifeCycleEvent.START) || lastEvent.equals(ActivityLifeCycleEvent.PAUSE)) {
                        return ActivityLifeCycleEvent.STOP;
                    } else if (lastEvent.equals(ActivityLifeCycleEvent.RESUME)) {
                        return ActivityLifeCycleEvent.PAUSE;
                    } else if (lastEvent.equals(OtherLifeCycleEvent.START)) {
                        return OtherLifeCycleEvent.STOP;
                    } else if (lastEvent.equals(ActivityLifeCycleEvent.DESTROY)) {
                        throw new OutsideLifecycleException("Cannot bind to Activity lifecycle when outside of it.");
                    } else {
                        throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
                    }
                }
            };
    /**
     * 仿RxLifecycle对FragmentEvent的定义
     */
    private static final Function<LifeCycleEvent, LifeCycleEvent> FRAGMENT_LIFECYCLE =
            new Function<LifeCycleEvent, LifeCycleEvent>() {
                @Override
                public LifeCycleEvent apply(LifeCycleEvent lastEvent) throws Exception {

                    if (lastEvent.equals(FragmentLifeCycleEvent.ATTACH) || lastEvent.equals(FragmentLifeCycleEvent.DESTROY)) {
                        return FragmentLifeCycleEvent.DETACH;
                    } else if (lastEvent.equals(FragmentLifeCycleEvent.CREATE) || lastEvent.equals(FragmentLifeCycleEvent.DESTROY_VIEW)) {
                        return FragmentLifeCycleEvent.DESTROY;
                    } else if (lastEvent.equals(FragmentLifeCycleEvent.CREATE_VIEW) || lastEvent.equals(FragmentLifeCycleEvent.STOP)) {
                        return FragmentLifeCycleEvent.DESTROY_VIEW;
                    } else if (lastEvent.equals(FragmentLifeCycleEvent.START) || lastEvent.equals(FragmentLifeCycleEvent.PAUSE)) {
                        return FragmentLifeCycleEvent.STOP;
                    } else if (lastEvent.equals(FragmentLifeCycleEvent.RESUME)) {
                        return FragmentLifeCycleEvent.PAUSE;
                    } else if (lastEvent.equals(OtherLifeCycleEvent.START)) {
                        return OtherLifeCycleEvent.STOP;
                    } else if (lastEvent.equals(FragmentLifeCycleEvent.DETACH)) {
                        throw new OutsideLifecycleException("Cannot bind to Fragment lifecycle when outside of it.");
                    } else {
                        throw new UnsupportedOperationException("Binding to " + lastEvent + " not yet implemented");
                    }
                }
            };
}
