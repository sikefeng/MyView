package myview.example.com.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class MyView extends View {//1、创建类继承View 或View的子类。
    /**
     * 文本
     */
    private String mTextValue;
    /**
     * 文本的颜色
     */
    private int mTextColor;
    /**
     * 文本的大小
     */
    private int mTextSize;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;
    //2、创建构造方法
    public MyView(Context context) {// 在代码中new 对象时调用此方法
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {// 在XML布局文件中声明此View，创建对象时，由系统自动调用
        super(context, attrs);
        initView(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {// 与方法2用法一样，只是多了一个参数：默认样式
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        //获得自定义的属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        try {
            mTextValue = ta.getString(R.styleable.MyView_textValue);
            mTextColor = ta.getColor(R.styleable.MyView_textColor, Color.BLACK);
            mTextSize = ta.getDimensionPixelSize(R.styleable.MyView_textSize, 10);
        } finally {
            ta.recycle();
        }
        /**
         * 获得绘制文本的宽和高
         */
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 抗锯齿
        mPaint.setDither(true); // 防抖动
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mBound = new Rect();
        Log.i("Tag", "TextLength:" + mTextValue.length());
        mPaint.getTextBounds(mTextValue, 0, mTextValue.length(), mBound);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i("Tag", "onMeasure():");
        int _WidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int _WidthSpec = MeasureSpec.getSize(widthMeasureSpec);
        int _HeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int _HeightSpec = MeasureSpec.getSize(heightMeasureSpec);
        int _Width;
        int _Height;
        //宽度
        if(_WidthMode == MeasureSpec.EXACTLY){
            _Width = _WidthSpec;
        }else{
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mTextValue, 0, mTextValue.length(), mBound);
            float _TextWidth = mBound.width();
            _Width = (int) (getPaddingLeft() + _TextWidth + getPaddingRight());
        }
        //高度
        if(_HeightMode == MeasureSpec.EXACTLY){
            _Height = _HeightSpec;
        }else{
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mTextValue, 0, mTextValue.length(), mBound);
            float _TextHeight = mBound.height();
            _Height = (int) (getPaddingTop() + _TextHeight + getPaddingBottom());
        }
        setMeasuredDimension(_Width, _Height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("Tag", "onDraw():");
        mPaint.setColor(Color.YELLOW);
        Log.i("Tag", "getMeasuredWidth():" + getMeasuredWidth() + "   " + getMeasuredHeight());
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(mTextColor);
        Log.i("Tag", "getWidth():" + getWidth() + "   " + getHeight());
        Log.i("Tag", "mBound.width():" + mBound.width() + "   " + mBound.height());
        canvas.drawText(mTextValue, getWidth() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
    }

}
