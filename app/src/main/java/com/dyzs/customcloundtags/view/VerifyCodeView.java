package com.dyzs.customcloundtags.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by maidou on 2016/7/8.
 */
public class VerifyCodeView extends View{
    private static final String TAG = VerifyCodeView.class.getSimpleName();
    private Context mContext;

    public VerifyCodeView(Context context) {
        this(context, null);
    }

    public VerifyCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 1);
    }

    public VerifyCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init () {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(false);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStrokeWidth(1f);
        mTextPaint.setTypeface(Typeface.MONOSPACE);
    }


    private String Code = "XLj234";
    private Paint mTextPaint;
    private float itemWidth;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (Code != null && Code.length() > 0) {
            for (int i = 0; i < Code.length(); i++) {

            }
        }



    }


    private float width, height;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }
}
