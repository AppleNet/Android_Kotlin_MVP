package com.example.llcgs.android_kotlin.customview.selfview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * com.example.llcgs.android_kotlin.customview.selfview.FlowLayout
 *
 * @author liulongchao
 * @since 2020-07-25
 *
 * */
public class FlowLayout extends ViewGroup {

    // 所有子控件的容器
    private List<List<View>> list = new ArrayList<>();
    // 把每一行最高的行高存起来
    private List<Integer> listLineHeight = new ArrayList<>();
    private boolean isMeasure = false;


    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *  获取到包含 margin 值的 layoutParams
     *
     *  重写了这个方法之后，子控件在getLayoutParams的时候，就能获取到 MarginLayoutParams
     * */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取到父容器，允许当前控件的大小
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 获取到自己的宽高模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // 测量子控件，根据子控件的大小，来决定自己的大小

        // 保存当前父控件的里面的子控件的总宽高
        int childCountWidth = 0;
        int childCountHeight = 0;
        if (!isMeasure) {
            isMeasure = true;
        } else {
            // 当前控件中 子控件一行使用的宽度值
            int lineCountWidth = 0;
            // 保存一行中最高的子控件的高度
            int lineMaxHeight = 0;

            // 存储每个子控件的宽高
            int iChildWidth  = 0;
            int iChildHeight = 0;
            // 创建一个一行的list
            List<View> viewList = new ArrayList<>();

            int childCount = getChildCount();
            // 遍历所有的子控件
            for (int i = 0; i < childCount; i++) {
                // 获取到每一个子控件
                View childAt = getChildAt(i);
                // 测量每个子控件
                measureChildWithMargins(childAt, widthMeasureSpec, 0, heightMeasureSpec, 0);
                // 获取每个子控件的 layoutParams
                // 考虑到每个子控件的 padding margin 值，需要重写容器的generateLayoutParams 这个接口，然你 new 一个 MarginLayoutParams
                MarginLayoutParams params = (MarginLayoutParams) childAt.getLayoutParams();
                // 获取到当前子控件要占用的宽度
                iChildWidth = childAt.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                // 获取到当前子控件要占用的高度
                iChildHeight = childAt.getMeasuredHeight() + params.topMargin + params.bottomMargin;

                // 判断当前子控件需不需要换行
                if (iChildWidth + lineCountWidth > widthSize - getPaddingLeft() - getPaddingRight()) { // 需要换行
                    // 出来换行的逻辑
                    // 1。保存上一行的数据 2。对比每一行保存者最宽的那行
                    childCountWidth = Math.max(lineCountWidth, iChildWidth);
                    // 累加行高
                    childCountHeight += lineMaxHeight;
                    // 把行高的记录存起来
                    listLineHeight.add(lineMaxHeight);
                    // 把这一行的控件集合放到嵌套集合中
                    list.add(viewList);
                    // 清空viewList  最好是new，因为clear的话 可能会把list中已经添加的数据给clear掉
                    viewList = new ArrayList<>();

                    lineCountWidth = iChildWidth;
                    //
                    lineMaxHeight = iChildHeight;
                    // 把不换行的view 收集起来
                    viewList.add(childAt);
                } else {
                    // 不需要换行
                    lineCountWidth += iChildWidth;
                    // 通过对比的方式 获取一行中最高的高度
                    lineMaxHeight = Math.max(lineMaxHeight, iChildHeight);
                    // 把不换行的view 收集起来
                    viewList.add(childAt);

                }

                // 这样做的原因是：之前的if else中不会把最后一行的高度加到listLineHeight中
                // 最后一
                if (i == childCount -1){
                    // 最后一个控件
                    childCountWidth = Math.max(childCountWidth, lineCountWidth);
                    childCountHeight += lineMaxHeight;
                    listLineHeight.add(lineMaxHeight);
                    list.add(viewList);
                }

            }
        }

        int measureWidth = widthMode == MeasureSpec.EXACTLY ? widthSize : childCountWidth;
        int measureHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : childCountHeight;
        // 第一种情况，当前控件宽高 设置的是 match_parent 就可以直接用它父亲的值
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 摆放子控件的位置
        int left, top, right, bottom;
        // 保存上一个控件的边距
        int countLeft = getPaddingLeft();
        // 保存上一行的高度的边距
        int countTop = getPaddingTop();

        // 遍历所有的子控件
        for (List<View> views : list) {
            // 遍历每一行的控件
            for (View view : views) {
                MarginLayoutParams layoutParams = (MarginLayoutParams) view.getLayoutParams();
                left = countLeft + layoutParams.leftMargin;
                top = countTop + layoutParams.topMargin;
                right = left + view.getMeasuredWidth();
                bottom = top + view.getMeasuredHeight();
                view.layout(left, top, right, bottom);

                countLeft += view.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;

            }
            // 换行了
            int i = list.indexOf(views);
            countLeft = getPaddingLeft();
            countTop += listLineHeight.get(i);
        }

        list.clear();
        listLineHeight.clear();
    }
}
