package com.cjt2325.misports;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by cjt on 2017/10/16.
 */


public class Dot {
    //角度范围
    public static final int ANGLE_RANGE = 60;
    //颜色
    public static final int COLOR = 0xffffffff;
    //时间
    public int DURATION = 1000;

    //点初始做标
    public float start_x;
    public float start_y;

    //点移动的坐标
    public float target_x;
    public float target_y;

    //从初始点到结束点的距离
    public float distance;
    //点半径
    public float radius;
    //起始透明度
    public int startAlpha;
    //当前透明度
    public int alpha;
    //透明度递减梯度
    public int alphaGradient;
    //移动角度
    public int pointAngle;

    //创建一个点
    public Dot(float distance, float radius, int alpha, float x, float y, float tangentAngle) {
        Random random = new Random();
        //随机生成500～1500毫秒的显示时间
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
        //计算透明度递减梯度
        alphaGradient = alpha * 60 / DURATION;
    }

    //重复使用点
    public void reset(float x, float y, float tangentAngle) {
        alpha = startAlpha;
        this.start_x = x;
        this.start_y = y;
        target_x = x;
        target_y = y;
        pointAngle = getPointAngle(tangentAngle);
        alphaGradient = alpha * 60 / DURATION;
    }

    //绘制点
    public boolean draw(Canvas canvas, Paint paint) {
        if (alpha <= 0) {
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

    //根据切线角度与角度范围获取点的移动角度
    public int getPointAngle(float angle) {
        Random random = new Random();
        int randomAngle = random.nextInt(ANGLE_RANGE);
        return (int) ((angle + 270 - ANGLE_RANGE / 2 + randomAngle) % 360);
    }

    //根据切线角度与角度范围或者坐标
    public void getCoordinate(float proportion) {
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

    //角度转弧度
    private double getRadian(int angle) {
        return Math.PI * angle / 180;
    }
}
