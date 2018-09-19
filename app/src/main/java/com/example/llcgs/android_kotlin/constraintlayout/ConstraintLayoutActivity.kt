package com.example.llcgs.android_kotlin.constraintlayout

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.constraintlayout.presenter.impl.ConstraintLayoutPresenter
import com.example.llcgs.android_kotlin.constraintlayout.view.ConstraintLayoutView
import com.lzh.nonview.router.anno.RouterRule
import kotlinx.android.synthetic.main.activity_constraintlayout.*

/**
 * com.example.llcgs.android_kotlin.constraintlayout.ConstraintLayoutActivity
 * @author liulongchao
 * @since 2018/9/18
 */
@RouterRule("ConstraintLayout")
class ConstraintLayoutActivity: BaseActivity<ConstraintLayoutView, ConstraintLayoutPresenter>(), View.OnClickListener {

    // 相对位置的属性 这些属性的值即可以是parent，也可以是某个view的id。
    /***
     *    layout_constraintLeft_toLeftOf
          layout_constraintLeft_toRightOf
          layout_constraintRight_toLeftOf
          layout_constraintRight_toRightOf
          layout_constraintTop_toTopOf
          layout_constraintTop_toBottomOf
          layout_constraintBottom_toTopOf
          layout_constraintBottom_toBottomOf
          layout_constraintBaseline_toBaselineOf
          layout_constraintStart_toEndOf
          layout_constraintStart_toStartOf
          layout_constraintEnd_toStartOf
          layout_constraintEnd_toEndOf
     * */


    override fun createPresenter() = ConstraintLayoutPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraintlayout)

        vh.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.vh ->{

            }
        }
    }

}