package com.example.llcgs.android_kotlin.view_dispatcher.viewgroup;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class EventViewGroup extends LinearLayout {


    public EventViewGroup(Context context) {
        super(context);
    }

    public EventViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EventViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EventViewGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.i("EventActivity", "EventViewGroup -----  dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("EventActivity", "EventViewGroup -----  dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("EventActivity", "EventViewGroup -----  dispatchTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        // true false super.dispatchTouchEvent
        // 1 return super.dispatchTouchEvent 的时候
        /*
        *   此时向ViewGroup的子View中传递事件，执行子View的dispatchTouchEvent
        *   down move up 依次都会向下传递，知道找到消费的地方，如果找不到，向上回传
        *   找消费的地方
        * */


        // 2 return false 的时候 其他都是super
        /*
        *   此时ViewGroup return false，不向下传递，到此结束。
        *   up，down也就不在这里执行向下传递的操作。ViewGroup的 onTouchEvent方法不会执行，直接向上回溯到Activity，Activity的 move up在自己的dispatchTouchEvent分发 onTouchEvent中消费
        *
        *   返回false：当我们不再向下分发的时候（dispatchTouchEvent返回false），
        *   无论是ViewGroup还是View，都会从
        *   **
        *       该View的上一级 的onTouchEvent事件向上传递（ 注意：当一个View不在向下分发的时候，这个View是不会执行自己的onTouchEvent（）方法的，这也是和该View拦截后续事件的区别
        *   **）
        *
        *   EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: ViewDispatcherActivity -----  onTouchEvent ACTION_DOWN
            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: ViewDispatcherActivity -----  onTouchEvent ACTION_MOVE
            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_UP
            EventActivity: ViewDispatcherActivity -----  onTouchEvent ACTION_UP
        *
        * */


        // 3 return true 的时候 其他都是super
        /*
            若直接返回true，表示事件直接被消费，这个事件也就停止分发，ViewGroup中的子View的dispatcherTouchEvent onTouchEvent等都不会执行，且子View不会逆向向上传递，直接结束了

            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_UP
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_UP
         */
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.d("EventActivity", "EventViewGroup -----  onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("EventActivity", "EventViewGroup -----  onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("EventActivity", "EventViewGroup -----  onInterceptTouchEvent ACTION_UP");
                break;
            default:
                break;
        }
        // return super.onInterceptTouchEvent(event) true false
        // 注意onInterceptTouchEvent() 是ViewGroup特有的方法，View和Activity都不会拦截事件
        // 1. return super.onInterceptTouchEvent(event)
        /*
        *  向下传递，向上回溯正常执行，知道找到最终的事件消费View
        * */

        // 2. return false 其他都是super
        /*
        *   EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: EventViewGroup -----  onInterceptTouchEvent ACTION_DOWN
            EventActivity: myButton -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: myButton -----  onTouchEvent ACTION_DOWN
            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: EventViewGroup -----  onInterceptTouchEvent ACTION_MOVE
            EventActivity: myButton -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: myButton -----  onTouchEvent ACTION_MOVE
            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_UP
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_UP
            EventActivity: EventViewGroup -----  onInterceptTouchEvent ACTION_UP
            EventActivity: myButton -----  dispatchTouchEvent ACTION_UP
            EventActivity: myButton -----  onTouchEvent ACTION_UP

            逻辑上和super.onInterceptTouchEvent(event)一样，子View的dispatcherToucheEvent和onTouchEvent都回执行，且向上回溯
        * */

        // 3. return true 其他都是super
        /*
        *   返回true:表示ViewGroup容器拦截后续事件，
        *   **
        *      会执行该ViewGroup的onTouchEvent的Action_Down方法，然后停止向下分发转而通过onTouchEvent() 向上传递，直到最终被消费
        *   **
        *   ViewDispatcherActivity -----  dispatchTouchEvent ACTION_DOWN
            EventViewGroup -----  dispatchTouchEvent ACTION_DOWN
            EventViewGroup -----  onInterceptTouchEvent ACTION_DOWN
            EventViewGroup -----  onTouchEvent ACTION_DOWN
            ViewDispatcherActivity -----  onTouchEvent ACTION_DOWN
            ViewDispatcherActivity -----  dispatchTouchEvent ACTION_MOVE
            ViewDispatcherActivity -----  onTouchEvent ACTION_MOVE
            ViewDispatcherActivity -----  dispatchTouchEvent ACTION_UP
            ViewDispatcherActivity -----  onTouchEvent ACTION_UP
        * */

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.i("EventActivity", "EventViewGroup -----  onTouchEvent ACTION_DOWN");
                performClick();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("EventActivity", "EventViewGroup -----  onTouchEvent ACTION_MOVE");
                performClick();
                break;
            case MotionEvent.ACTION_UP:
                Log.i("EventActivity", "EventViewGroup -----  onTouchEvent ACTION_UP");
                performClick();
                break;
            default:
                break;
        }

        // return true false super.onTouchEvent(event)

        // 1. return super.onTouchEvent(event)
        /*
         *  向下传递，向上回溯正常执行，直到找到最终的事件消费View
         * */


        // 2. return false
        /*
        *   return false 等价与 super.onTouchEvent(event)
        *   向下传递，向上回溯正常执行，直到找到最终的事件消费View
        *
        *   EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: EventViewGroup -----  onInterceptTouchEvent ACTION_DOWN
            EventActivity: myButton -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: myButton -----  onTouchEvent ACTION_DOWN
            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: EventViewGroup -----  onInterceptTouchEvent ACTION_MOVE
            EventActivity: myButton -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: myButton -----  onTouchEvent ACTION_MOVE
            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_UP
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_UP
            EventActivity: EventViewGroup -----  onInterceptTouchEvent ACTION_UP
            EventActivity: myButton -----  dispatchTouchEvent ACTION_UP
            EventActivity: myButton -----  onTouchEvent ACTION_UP
        * */


        // return true 其他super 点击myButton
        /*
        /*
        *   return true 等价与 super.onTouchEvent(event) 等价于false
        *   向下传递，向上回溯正常执行，直到找到最终的事件消费View
        *
        *   EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: EventViewGroup -----  onInterceptTouchEvent ACTION_DOWN
            EventActivity: myButton -----  dispatchTouchEvent ACTION_DOWN
            EventActivity: myButton -----  onTouchEvent ACTION_DOWN
            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: EventViewGroup -----  onInterceptTouchEvent ACTION_MOVE
            EventActivity: myButton -----  dispatchTouchEvent ACTION_MOVE
            EventActivity: myButton -----  onTouchEvent ACTION_MOVE
            EventActivity: ViewDispatcherActivity -----  dispatchTouchEvent ACTION_UP
            EventActivity: EventViewGroup -----  dispatchTouchEvent ACTION_UP
            EventActivity: EventViewGroup -----  onInterceptTouchEvent ACTION_UP
            EventActivity: myButton -----  dispatchTouchEvent ACTION_UP
            EventActivity: myButton -----  onTouchEvent ACTION_UP
        * */
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
