package com.fourninetythreegroup.quarto.game;

import com.fourninetythreegroup.quarto.game.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameplayActivity extends Activity {

    private LinearLayout layout;
    private Bitmap background;

    private String lineColor = "#A82222";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        layout = (LinearLayout) findViewById(R.id.rect);
        background = Bitmap.createBitmap(960, 1600, Bitmap.Config.ARGB_8888);
        layout.setBackground(new BitmapDrawable(getResources(), background));

        drawGameboard();
    }

    private void drawGameboard(){
        Canvas canvas = new Canvas(background);

        // white out bg
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawPaint(paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(18f);
        paint.setColor(Color.parseColor(lineColor));

        // draw surrounding circle
        float radius = 70;
        float margin = 30;
        int initXOffset = 220;
        int initYOffset = 200;

        float circleWidth = 2 * radius;
        float centerX = 1.5f * circleWidth + 1.5f * margin + initXOffset;
        float centerY = 1.5f * circleWidth + 1.5f * margin + initYOffset;
        float bigR = 2.5f * (circleWidth + margin) + 30;
        canvas.drawCircle(centerX, centerY, bigR, paint);

        paint.setColor(Color.WHITE);
        canvas.drawCircle(centerX, centerY, 2, paint);
        paint.setColor(Color.parseColor("#A8A8A8"));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                float offset = 2 * radius + margin;
                float cx = (i * offset) + initXOffset;
                float cy = (j * offset) + initYOffset;
                PointF rotatedPoint = rotate(cx, cy, centerX, centerY, Math.toRadians(45));

                canvas.drawCircle(rotatedPoint.x, rotatedPoint.y, radius, paint);
            }
        }
    }
    private PointF rotate(float px, float py, float cx, float cy, double theta) {
        double newx = Math.cos(theta) * (px - cx) + Math.sin(theta) * (py - cy) + cx;
        double newy = -Math.sin(theta) * (px - cx) + Math.cos(theta) * (py - cy) + cy;
        return new PointF((float)newx, (float)newy);
    }
}
