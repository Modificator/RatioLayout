package cn.modificactor.ratiolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import cn.modificactor.ratiolayoutlib.R;


/**
 * Created by Modificator on 2015/8/22.
 */
public class RatioFrameLayout extends FrameLayout {

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


    public RatioFrameLayout(Context context) {
        this(context, null);
    }

    public RatioFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取基准边的尺寸
        int childSpec = reference == ReferenceType.WIDTH ? getMeasuredWidth() : getMeasuredHeight();


        // 如果以宽为基准边则宽不变，高按比例得出具体数值，反之亦然
        setMeasuredDimension(
                (int) (reference == ReferenceType.WIDTH ? childSpec : childSpec * ratioWidth / ratioHeight),
                (int) (reference == ReferenceType.HEIGHT ? childSpec : childSpec * ratioHeight / ratioWidth)
        );
    }
}
