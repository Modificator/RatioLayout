package cn.modificactor.ratiolayoutsimple;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class LineButton extends View {


    public LineButton(Context context) {
        super(context);
    }

    public LineButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                invalidate();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    postInvalidateOnAnimation();
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();


        Path path = PathUtil.rectangle(100,100,500,200);
//        Path path = new Path();
//        path = PathUtil.lineTo(100, 100, 500, 100, path);
        Paint paint = new Paint();
        paint.setColor(0xff555555);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);


    }


}
