package com.cjt2325.misports;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by cjt on 2017/10/16.
 */

public class ConnectView extends View {
    private Resources mResources;
    Paint mPaint;

    List<Dot> dotList;

    RectF oval, oval2, oval3, oval4, oval5, oval6, oval7;
    RectF textRect, textRect2;
    Rect bitmapRect;
    RectF progressRectF;
    int[] colors = {0x00ffffff, 0xffffffff, 0xffffffff};
    int[] colors2 = {0x00ffffff, 0x00ffffff, 0xffffffff, 0xffffffff};
    int[] colors3 = {0x00ffffff, 0x00ffffff, 0x00ffffff, 0xffffffff, 0xffffffff};
    int[] colors4 = {0xbbffffff, 0x88ffffff, 0x44ffffff, 0x88ffffff, 0xbbffffff};

//    int[] colors5 = {0x00ffffff, 0x00ffffff, 0x00ffffff, 0xffffffff, 0x00ffffff, 0x00ffffff, 0x00ffffff};
        int[] colors5 = {0x44ffffff, 0x77ffffff, 0xaaffffff, 0xaaffffff, 0x77ffffff, 0x44ffffff};
    SweepGradient sweepGradient;
    SweepGradient sweepGradient2;
    SweepGradient sweepGradient3;

    SweepGradient sweepGradient4;
    SweepGradient sweepGradient5, sweepGradient6, sweepGradient7;

    private float center_viewX;
    private float center_viewY;

    private int radius = 200;
    private int radius_circle = 200;
    private int centerX_circle;
    private int centerY_circle;

    private int angle = 0;
    Random random = new Random();

    private String text = "2325";
    private String text2 = "1.5公里 | 34千卡";

    PathEffect effects = new DashPathEffect(new float[]{4, 4}, 10);
    private Bitmap mBitmap;
    private int mBitmapWidth, mBitmapHeight;

    public ConnectView(Context context) {
        this(context, null);
    }

    public ConnectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConnectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mResources = getResources();
        initBitmap();
        initView();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        center_viewX = getWidth() / 2;
        center_viewY = getHeight() / 2;

        centerX_circle = getWidth() / 2;
        centerY_circle = getHeight() / 2;

//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        bitmapRect = new Rect(
                (int) (center_viewX - mBitmapWidth / 2),
                (int) (center_viewY + radius * 0.6 - mBitmapHeight / 2),
                (int) (center_viewX + mBitmapWidth / 2),
                (int) (center_viewY + radius * 0.6 + mBitmapHeight / 2));

        textRect = new RectF(
                (int) (center_viewX - radius),
                (int) (center_viewY - radius),
                (int) (center_viewX + radius),
                (int) (center_viewY + radius));
        textRect2 = new RectF(
                (int) (center_viewX - radius),
                (int) (center_viewY + radius * 0.1),
                (int) (center_viewX + radius),
                (int) (center_viewY + radius * 0.6));

        oval = new RectF(center_viewX - radius, center_viewY - radius, center_viewX + radius, center_viewY + radius);
        oval2 = new RectF(center_viewX - radius + getRandom(9), center_viewY - radius - getRandom(9), center_viewX +
                radius +
                getRandom(9), center_viewY + radius + getRandom(9));
        oval4 = new RectF(center_viewX - radius + getRandom(9), center_viewY - radius + getRandom(9), center_viewX +
                radius -
                getRandom(9), center_viewY + radius -
                getRandom(9));
        oval3 = new RectF(center_viewX - radius - getRandom(9), center_viewY - radius - getRandom(9), center_viewX +
                radius +
                getRandom(9), center_viewY + radius + getRandom(9));
        oval5 = new RectF(center_viewX - radius - getRandom(9), center_viewY - radius - getRandom(9), center_viewX +
                radius +
                getRandom(9), center_viewY + radius +
                getRandom(9));
        oval6 = new RectF(center_viewX - radius - getRandom(9), center_viewY - radius - getRandom(9), center_viewX +
                radius +
                getRandom(9), center_viewY + radius +
                getRandom(9));
        oval7 = new RectF(center_viewX - radius - getRandom(9), center_viewY - radius - getRandom(9), center_viewX +
                radius +
                getRandom(9), center_viewY + radius +
                getRandom(9));
        sweepGradient = new SweepGradient(center_viewX, center_viewY, colors, null);
        sweepGradient2 = new SweepGradient(center_viewX, center_viewY, colors2, null);
        sweepGradient3 = new SweepGradient(center_viewX, center_viewY, colors3, null);
        sweepGradient4 = new SweepGradient(center_viewX, center_viewY, colors4, null);

        sweepGradient5 = new SweepGradient(centerX_circle, center_viewY - 15, colors5, null);
        sweepGradient6 = new SweepGradient(centerX_circle, center_viewY - 18, colors5, null);
        sweepGradient7 = new SweepGradient(centerX_circle, center_viewY - 21, colors5, null);

        progressRectF = new RectF(center_viewX - radius + 40, center_viewY - radius + 40, center_viewX +
                radius - 40,
                center_viewY +
                        radius - 40);

        initAnim();
    }

    public int getRandom(int n) {
        return random.nextInt(n * 2) - n;
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        dotList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            dotList.add(new Dot(random.nextInt(250) + 20, random.nextInt(10) + 6, random.nextInt(100) + 155,
                    center_viewX
                            + random.nextInt(4) - 2, center_viewY + random.nextInt(4) - 2, angle));
        }
        dotList.add(new Dot(30, random.nextInt(10) + 6, 255, center_viewX + random.nextInt(4) - 2, center_viewY + random
                .nextInt(4) - 2, angle));

        dotList.add(new Dot(20, random.nextInt(10) + 6, 255, center_viewX + random.nextInt(4) - 2, center_viewY + random
                .nextInt(4) - 2, angle));
    }

    boolean running = true;
    boolean showCircle = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawBitmap(mBitmap, getWidth() / 2 - mBitmapWidth / 2, (int)(getHeight()/2+radius*0.6), mPaint);


        if (running) {
            mPaint.setAlpha(255);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(0xddffffff);
            canvas.drawCircle(center_viewX, center_viewY, 13, mPaint);

            mPaint.setStyle(Paint.Style.STROKE);
            canvas.save();

            canvas.rotate(angle, getWidth() / 2, getHeight() / 2);
            mPaint.setStrokeWidth(3);
            mPaint.setShader(sweepGradient);
            canvas.drawArc(oval, 0, -360, false, mPaint);
            mPaint.setStrokeWidth(2);
            canvas.drawArc(oval2, 0, -360, false, mPaint);
            mPaint.setStrokeWidth(1);
            mPaint.setShader(sweepGradient2);
            canvas.drawArc(oval3, 0, -360, false, mPaint);
            mPaint.setStrokeWidth(3);
            canvas.drawArc(oval4, 0, -360, false, mPaint);
            mPaint.setStrokeWidth(2);
            mPaint.setShader(sweepGradient3);
            canvas.drawArc(oval5, 0, -360, false, mPaint);
            mPaint.setStrokeWidth(2);
            mPaint.setShader(sweepGradient2);
            canvas.drawArc(oval6, 0, -360, false, mPaint);
            mPaint.setStrokeWidth(2);
            mPaint.setShader(sweepGradient);
            canvas.drawArc(oval7, 0, -360, false, mPaint);

            canvas.restore();

            for (Dot dot : dotList) {
                if (!dot.draw(canvas, mPaint)) {
                    dot.reset(center_viewX + random.nextInt(16) - 8, center_viewY + random.nextInt(16) - 8, angle);
                }
            }
        } else {
            canvas.save();

            canvas.rotate(angle, centerX_circle, centerY_circle);

            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(20);

            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setShader(sweepGradient5);
            canvas.drawCircle(centerX_circle, centerY_circle - 2, radius_circle - 2, mPaint);
            mPaint.setShader(sweepGradient6);
            canvas.drawCircle(centerX_circle, centerY_circle - 4, radius_circle - 4, mPaint);
//            mPaint.setShader(sweepGradient7);
            canvas.drawCircle(centerX_circle, centerY_circle - 5, radius_circle - 6, mPaint);

            mPaint.reset();
            mPaint.setAntiAlias(true);
            mPaint.setShader(sweepGradient4);
            mPaint.setStrokeWidth(24);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(centerX_circle, centerY_circle, radius_circle, mPaint);

            canvas.restore();

            if (showCircle) {
                mPaint.reset();
                mPaint.setAntiAlias(true);
                mPaint.setColor(0xffffffff);
                mPaint.setStyle(Paint.Style.STROKE);
                mPaint.setPathEffect(effects);
                canvas.drawCircle(getWidth() / 2, centerY_circle, radius - 40, mPaint);

                mPaint.setPathEffect(null);
                mPaint.setStrokeWidth(3);
                canvas.drawArc(progressRectF, -90, 270, false, mPaint);
            }
        }
        mPaint.setShader(null);
        mPaint.setAlpha(255);
        mPaint.setStyle(Paint.Style.FILL);
        drawRectText(canvas, mPaint, textRect, text, 0xffffffff);
        drawRectText(canvas, mPaint, textRect2, text2, 0x99ffffff);

        angle++;
        if (angle > 360) {
            angle = 0;
        }
        getCoordinate();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startAnimation();
//                radius_circle += 20;
//                centerY_circle -= 40;
//                a = true;
                break;
            case MotionEvent.ACTION_MOVE:
//                oval.top--;
//                oval.bottom--;
                break;
            case MotionEvent.ACTION_UP:
                set.cancel();
                running = true;
                showCircle = false;
                progressRectF.top = getHeight() / 2 - radius + 40;
                progressRectF.bottom = getHeight() / 2 + radius - 40;

                textRect.top = getHeight() / 2 - radius;
                textRect.bottom = getHeight() / 2 + radius;

                textRect2.top = getHeight() / 2 + radius * 0.1f;
                textRect2.bottom = getHeight() / 2 + radius * 0.6f;

                break;
        }
        return true;
    }

    private double getRadian(float angle) {
        return Math.PI * angle / 180;
    }

    public void getCoordinate() {
//        Log.i("CJT", " = " + pointAngle + " prop = " + proportion);
        switch (angle) {
            case 0:
                center_viewX = getWidth() / 2 + radius;
                break;
            case 180:
                center_viewX = getWidth() / 2 - radius;
                break;
            case 90:
                center_viewY = getHeight() / 2 + radius;
                break;
            case 270:
                center_viewY = getHeight() / 2 - radius;
                break;
            default:
                if (angle > 0 && angle < 90) {
                    center_viewX = getWidth() / 2 + radius * (float) Math.cos(getRadian(angle));
                    center_viewY = getHeight() / 2 + radius * (float) Math.sin(getRadian(angle));
                } else if (angle > 90 && angle < 180) {
                    center_viewX = getWidth() / 2 - radius * (float) Math.cos(getRadian(180 - angle));
                    center_viewY = getHeight() / 2 + radius * (float) Math.sin(getRadian(180 - angle));
                } else if (angle > 180 && angle < 270) {
                    center_viewX = getWidth() / 2 - radius * (float) Math.cos(getRadian(angle - 180));
                    center_viewY = getHeight() / 2 - radius * (float) Math.sin(getRadian(angle - 180));
                } else if (angle > 270 && angle < 360) {
                    center_viewX = getWidth() / 2 + radius * (float) Math.cos(getRadian(360 - angle));
                    center_viewY = getHeight() / 2 - radius * (float) Math.sin(getRadian(360 - angle));
                }
                break;
        }
    }


    //绘制文字
    private void drawRectText(Canvas canvas, Paint mPaint, RectF rect, String text, int bgcolor) {
        mPaint.setTextSize(rect.height() / 5);
        mPaint.setFlags(Paint.FAKE_BOLD_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();

        int baseline = (int) ((rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2);
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(bgcolor);
        canvas.drawText(text, rect.centerX(), baseline, mPaint);
    }

    private void initBitmap() {
//        mBitmap = ((BitmapDrawable) mResources.getDrawable(R.drawable.ic_watch)).getBitmap();
//        mBitmapWidth = mBitmap.getWidth();
//        mBitmapHeight = mBitmap.getHeight();
    }


    private ValueAnimator radiusAnim;
    private ValueAnimator verticalAnim;

    private AnimatorSet set;

    private void initAnim() {
        set = new AnimatorSet();
        radiusAnim = ValueAnimator.ofInt(200, 240, 200);
        radiusAnim.setDuration(300);
        radiusAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius_circle = (int) animation.getAnimatedValue();
            }
        });
        verticalAnim = ValueAnimator.ofInt(getHeight() / 2, getHeight() / 2 - 40, getHeight() / 2, getHeight() / 2 -
                40);
        verticalAnim.setDuration(400);
        verticalAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                centerY_circle = (int) animation.getAnimatedValue();
                float height = textRect.height();
                textRect.top = (int) animation.getAnimatedValue() - height / 2;
                textRect.bottom = (int) animation.getAnimatedValue() + height / 2;
                textRect2.top = (int) animation.getAnimatedValue() + radius * 0.1f;
                textRect2.bottom = (int) animation.getAnimatedValue() + radius * 0.6f;

                if (animation.getCurrentPlayTime() >= 300) {
                    showCircle = true;
                    progressRectF.top = (int) animation.getAnimatedValue() - radius + 40;
                    progressRectF.bottom = (int) animation.getAnimatedValue() + radius - 40;
                }
            }
        });
        set.playTogether(radiusAnim, verticalAnim);
    }

    public void startAnimation() {
        running = false;
        set.start();
    }
}
