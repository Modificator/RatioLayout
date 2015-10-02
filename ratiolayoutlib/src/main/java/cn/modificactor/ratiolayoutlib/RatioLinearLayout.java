package cn.modificactor.ratiolayoutlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/**
 * Created by Modificator on 2015/8/22.
 */
public class RatioLinearLayout extends LinearLayout {

    /**
     * 以哪边为参考，默认为宽
     */
    ReferenceType reference = ReferenceType.WIDTH;
    /**
     * 宽的比例
     */
    double ratioWidth = 1;
    /**
     * 高的比例
     */
    double ratioHeight = 1;


    public RatioLinearLayout(Context context) {
        this(context, null);
    }

    public RatioLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioRelativeLayout, defStyleAttr, 0);
        //获取参考边
        reference = typedArray.getInt(R.styleable.RatioRelativeLayout_reference, 0) == 0 ? ReferenceType.WIDTH : ReferenceType.HEIGHT;
        //获取高比例
        ratioHeight = typedArray.getFloat(R.styleable.RatioRelativeLayout_ratioHeight, 1);
        //获取宽比例
        ratioWidth = typedArray.getFloat(R.styleable.RatioRelativeLayout_ratioWidth, 1);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 如果以宽慰基准边则宽不变，高按比例得出具体数值，反之亦然
         */
        setMeasuredDimension(View.getDefaultSize(0, reference == ReferenceType.WIDTH ? widthMeasureSpec :
                        (int) (heightMeasureSpec / ratioHeight * ratioWidth)),
                View.getDefaultSize(0, reference == ReferenceType.HEIGHT ? heightMeasureSpec :
                        (int) (widthMeasureSpec / ratioWidth * ratioHeight)));

        int childSpec = reference == ReferenceType.WIDTH ? getMeasuredWidth() : getMeasuredHeight();
        /**
         * 获取非基准边的尺寸
         */
        int measureSpec = reference == ReferenceType.HEIGHT ? MeasureSpec.makeMeasureSpec(
                (int) (childSpec / ratioHeight * ratioWidth), MeasureSpec.EXACTLY) :
                MeasureSpec.makeMeasureSpec(
                        (int) (childSpec / ratioWidth * ratioHeight), MeasureSpec.EXACTLY);

        super.onMeasure(reference == ReferenceType.WIDTH ? widthMeasureSpec : measureSpec, reference == ReferenceType.HEIGHT ? heightMeasureSpec : measureSpec);
    }
}
