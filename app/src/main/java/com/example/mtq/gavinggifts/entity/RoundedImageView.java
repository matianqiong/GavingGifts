package com.example.mtq.gavinggifts.entity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 *
 * 这个是自定义的上面圆角 下面直角的ImageView控件
 *
 * by 邹远航
 */
public class RoundedImageView extends ImageView{
    private float[] rids = {20.0f,20.0f,20.0f,20.0f,0.0f,0.0f,0.0f,0.0f,};
    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 画图
     */
    protected void onDraw(Canvas canvas){
        Path path=new Path();
        int w=this.getWidth();
        int h=this.getHeight();
        /**
         * 向路径中添加圆形矩阵
         */
        path.addRoundRect(new RectF(0,0,w,h),rids,Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

}
