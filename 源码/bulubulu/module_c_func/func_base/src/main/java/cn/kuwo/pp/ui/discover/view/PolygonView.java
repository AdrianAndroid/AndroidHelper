package cn.kuwo.pp.ui.discover.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class PolygonView extends View {
    private float widthWeight = 0.9f;
    private int defaultColor = Color.parseColor("#404040");
    private int highLightColor = Color.parseColor("#FFDF1F");

    private boolean isHighLight = false;

    public PolygonView(Context context) {
        super(context);
    }

    public PolygonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PolygonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PolygonView setWidthWeight(float widthWeight) {
        this.widthWeight = widthWeight;
        return this;
    }

    public PolygonView setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
        return this;
    }

    public PolygonView setHighLightColor(int highLightColor) {
        this.highLightColor = highLightColor;
        return this;
    }

    public void setHighLight(boolean highLight){
        isHighLight = highLight;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.TRANSPARENT);//把整张画布绘制成透明

        Paint paint = new Paint();
        paint.setAntiAlias(true);//去锯齿
        if (isHighLight) {//设置paint的颜色
            paint.setColor(highLightColor);
        } else {
            paint.setColor(defaultColor);
        }
        paint.setStyle(Paint.Style.FILL);//设置paint的风格

        int width = this.getWidth();//获取到view的宽高
        int height = this.getHeight();

        Path path = new Path();    //通过path绘制平行四边形
        path.moveTo(width * (1 - widthWeight), 0);
        path.lineTo(width, 0);
        path.lineTo(width * widthWeight, height);
        path.lineTo(0, height);
        path.close();
        canvas.drawPath(path, paint);
    }
}
