package com.cjt2325.misports;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by cjt on 2017/10/16.
 */

public class Dot {
    //角度范围
    public static final int ANGLE_RANGE = 45;
    //颜色
    public static final int COLOR = 0xffffffff;
    //时间
    public int DURATION = 1000;

    public float start_x;
    public float start_y;

    public float target_x;
    public float target_y;

    public float distance;
    public float radius;
    public int startAlpha;
    public int alpha;
    public int alphaGradient;

    public int pointAngle;

    public Dot(float distance, float radius, int alpha, float x, float y, float tangentAngle) {
        Random random = new Random();
        DURATION = random.nextInt(1000) + 500;

        this.distance = distance;
        this.radius = radius;

        this.startAlpha = alpha;
        this.alpha = alpha;
        this.start_x = x;
        this.start_y = y;
        target_x = x;
        target_y = y;

        pointAngle = getPointAngle(tangentAngle);
        alphaGradient = alpha * 60 / DURATION;
//        Log.i("CJT", "Dot new !");
    }

    public void reset(float x, float y, float tangentAngle) {
        alpha = startAlpha;
        this.start_x = x;
        this.start_y = y;
        target_x = x;
        target_y = y;
        pointAngle = getPointAngle(tangentAngle);
        alphaGradient = alpha * 60 / DURATION;
//        Log.i("CJT", "Dot reset !");
    }

    public boolean draw(Canvas canvas, Paint paint) {
        if (alpha <= 0) {
//            Log.i("CJT", " ========= ");
            return false;
        }
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(COLOR);
        paint.setAlpha(alpha);
        canvas.drawCircle(target_x, target_y, radius, paint);
        alpha -= alphaGradient;
        getCoordinate(1 - ((float) (alpha * 1.0 / startAlpha)));
        return true;
    }

    public int getPointAngle(float angle) {
        Random random = new Random();
        int randomAngle = random.nextInt(ANGLE_RANGE);
        return (int) ((angle + 270 - ANGLE_RANGE / 2 + randomAngle) % 360);
    }

    public void getCoordinate(float proportion) {
//        Log.i("CJT", " = " + pointAngle + " prop = " + proportion);
        switch (pointAngle) {
            case 0:
                target_x = start_x + distance * proportion;
                break;
            case 180:
                target_x = start_x - distance * proportion;
                break;
            case 90:
                target_y = start_y + distance * proportion;
                break;
            case 270:
                target_y = start_y - distance * proportion;
                break;
            default:
                if (pointAngle > 0 && pointAngle < 90) {
                    target_x = start_x + distance * proportion * (float) Math.cos(getRadian(pointAngle));
                    target_y = start_y + distance * proportion * (float) Math.sin(getRadian(pointAngle));
                } else if (pointAngle > 90 && pointAngle < 180) {
                    target_x = start_x - distance * proportion * (float) Math.cos(getRadian(180 - pointAngle));
                    target_y = start_y + distance * proportion * (float) Math.sin(getRadian(180 - pointAngle));
                } else if (pointAngle > 180 && pointAngle < 270) {
                    target_x = start_x - distance * proportion * (float) Math.cos(getRadian(pointAngle - 180));
                    target_y = start_y - distance * proportion * (float) Math.sin(getRadian(pointAngle - 180));
                } else if (pointAngle > 270 && pointAngle < 360) {
                    target_x = start_x + distance * proportion * (float) Math.cos(getRadian(360 - pointAngle));
                    target_y = start_y - distance * proportion * (float) Math.sin(getRadian(360 - pointAngle));
                }
                break;
        }
    }

    private double getRadian(int angle) {
        return Math.PI * angle / 180;
    }
}
