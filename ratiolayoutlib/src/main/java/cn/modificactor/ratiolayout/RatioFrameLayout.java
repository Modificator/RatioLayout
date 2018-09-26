package cn.modificactor.ratiolayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import cn.modificactor.ratiolayoutlib.R;


/**
 * Created by Modificator on 2015/8/22.
 * update on 2017-01-15
 */
public class RatioFrameLayout extends FrameLayout {

    public static final int REFERENCE_TYPE_WIDTH = 0;
    public static final int REFERENCE_TYPE_HEIGHT = 1;

    /**
     * 以哪边为参考，默认为宽
     */
    int reference = REFERENCE_TYPE_WIDTH;
    /**
     * 宽的比例
     */
    float ratioWidth = 1;
    /**
     * 高的比例
     */
    float ratioHeight = 1;

    public RatioFrameLayout(Context context) {
        this(context, null);
    }

    public RatioFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatioFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
        ratioWidth = a.getFloat(R.styleable.RatioLayout_w, 0);
        ratioHeight = a.getFloat(R.styleable.RatioLayout_w, 0);
        reference = a.getInt(R.styleable.RatioLayout_by, 0);
        a.recycle();
        if (ratioWidth == 0 || ratioHeight == 0) {
            throw new IllegalArgumentException("ViewGroup ratio witdh/ratio height can't unset,can't set zore");
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取基准边的尺寸
        int childSpec = reference == REFERENCE_TYPE_WIDTH ? getMeasuredWidth() : getMeasuredHeight();

        int parentWidth;
        int parentHeight;
        // 如果以宽为基准边则宽不变，高按比例得出具体数值，反之亦然
        setMeasuredDimension(
                parentWidth = (int) (reference == REFERENCE_TYPE_WIDTH ? childSpec : childSpec * ratioWidth / ratioHeight),
                parentHeight = (int) (reference == REFERENCE_TYPE_HEIGHT ? childSpec : childSpec * ratioHeight / ratioWidth)
        );
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                lp.calcChildSize(parentWidth, parentHeight, ratioWidth, ratioHeight);
                child.measure(MeasureSpec.makeMeasureSpec(lp.width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY));
            }
        }

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.leftMargin, lp.topMargin, lp.leftMargin + lp.width, lp.topMargin + lp.height);
        }
    }

    @Override
    protected FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    @Override
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {

        private float x;
        private float y;
        private float w;
        private float h;
        private int leftMargin;
        private int topMargin;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.RatioLayout_Layout);
            x = a.getFloat(R.styleable.RatioLayout_Layout_x, 0);
            y = a.getFloat(R.styleable.RatioLayout_Layout_y, 0);
            w = a.getFloat(R.styleable.RatioLayout_Layout_w, 0);
            h = a.getFloat(R.styleable.RatioLayout_Layout_h, 0);
            a.recycle();
        }

        public void calcChildSize(float parentWidth, float parentHeight, float parentRatioWidth, float parentRatioHeight) {
            width = (int) (parentWidth * w / parentRatioWidth);
            height = (int) (parentHeight * h / parentRatioHeight);
            leftMargin = (int) (parentWidth * x / parentRatioWidth);
            topMargin = (int) (parentHeight * y / parentRatioHeight);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }


        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        @Override
        public String toString() {
            return "LayoutParams{" +
                    "x=" + x +
                    ", y=" + y +
                    ", w=" + w +
                    ", h=" + h +
                    ", leftMargin=" + leftMargin +
                    ", topMargin=" + topMargin +
                    ", height=" + height +
                    ", width=" + width +
                    '}';
        }
    }
}
