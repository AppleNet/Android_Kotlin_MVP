package com.example.llcgs.android_kotlin.window;

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Button
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.window.presenter.impl.WindowPresenter
import com.example.llcgs.android_kotlin.window.view.WindowView
import com.lzh.nonview.router.anno.RouterRule

/**
 * com.example.llcgs.android_kotlin.window.WindowActivity
 * @author liulongchao
 * @since 2018/12/15
 */
@RouterRule("Window")
class WindowActivity: BaseActivity<WindowView, WindowPresenter>(),WindowView, View.OnTouchListener {

    private lateinit var  mWindowManager: WindowManager
    private lateinit var button: Button
    private lateinit var mLayoutParams: WindowManager.LayoutParams

    override fun createPresenter()= WindowPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        //
        if (Build.VERSION.SDK_INT >= 23){
            // 6.0
            if (!Settings.canDrawOverlays(this)){
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivityForResult(intent, 10)
            }else{
                addView()
            }
        }else{
            addView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && Build.VERSION.SDK_INT >= 23 && Settings.canDrawOverlays(this)){
            addView()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addView() {
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        button = Button(this)
        button.text = resources.getString(R.string.app_name)
        button.setBackgroundColor(Color.BLUE)
        button.setOnTouchListener(this)
        mLayoutParams = WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT)
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL; WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        mLayoutParams.gravity = Gravity.START; Gravity.TOP
        mLayoutParams.width = 500
        mLayoutParams.height = 300
        mLayoutParams.x = 300
        mLayoutParams.y = 300
        mWindowManager.addView(button, mLayoutParams)
    }



    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        var lastX = 0
        var lastY = 0

        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE ->{
                val nowX = event.x.toInt()
                val nowY = event.y.toInt()
                mLayoutParams.x = (mLayoutParams.x + (nowX - lastX))
                mLayoutParams.y = (mLayoutParams.y + (nowY - lastY))
                mWindowManager.updateViewLayout(button, mLayoutParams)
                lastX = event.rawX.toInt()
                lastY = event.rawY.toInt()
            }
        }
        return true
    }
}
