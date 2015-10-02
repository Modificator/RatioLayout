package cn.modificactor.ratiolayoutlib.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import cn.modificactor.ratiolayoutlib.R;

/**
 * Created by Modificator
 * Date: 2015/10/2
 * Time: 13:26
 * Connect: yunshangcn@gmail.com
 */
public class RatioUtil<T extends View> {
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
    int widthMeasureSpec, heightMeasureSpec;

    public enum ReferenceType {
        WIDTH,
        HEIGHT
    }

    public RatioUtil init(Context context) {
        return init(context, null, 0);
    }

    public RatioUtil init(Context context, AttributeSet attrs) {
        return init(context, attrs, 0);
    }

    public RatioUtil init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioRelativeLayout, defStyleAttr, 0);
        //获取参考边
        reference = typedArray.getInt(R.styleable.RatioRelativeLayout_reference, 0) == 0 ? ReferenceType.WIDTH : ReferenceType.HEIGHT;
        //获取高比例
        ratioHeight = typedArray.getFloat(R.styleable.RatioRelativeLayout_ratioHeight, 1);
        //获取宽比例
        ratioWidth = typedArray.getFloat(R.styleable.RatioRelativeLayout_ratioWidth, 1);
        typedArray.recycle();
        return new RatioUtil();
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.widthMeasureSpec = widthMeasureSpec;
        this.heightMeasureSpec = heightMeasureSpec;
    }

    public int getRatioHeightMeasureSpec() {
        return heightMeasureSpec;
    }

    public int getRatioWidthMeasureSpec() {
        return reference == ReferenceType.WIDTH ? widthMeasureSpec : (int) (heightMeasureSpec / ratioHeight * ratioWidth);
    }
}
