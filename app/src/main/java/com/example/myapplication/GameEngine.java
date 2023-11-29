package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import java.util.Random;

public class GameEngine extends Thread {
    private SurfaceHolder surfaceHolder;
    private boolean running;
    private GameEventListener gameEventListener;
    private int targetX, targetY;
    private int targetRadius;

    public interface GameEventListener {
        void onHit();
    }

    public GameEngine(SurfaceHolder surfaceHolder, GameEventListener gameEventListener) {
        this.surfaceHolder = surfaceHolder;
        this.gameEventListener = gameEventListener;
        this.running = false;
        this.targetRadius = 50;
    }

    public void start() {
        this.running = true;
        super.start();
    }

    public void stopRunning() {
        this.running = false;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public int getTargetRadius() {
        return targetRadius;
    }

    public GameEventListener getGameEventListener() {
        return gameEventListener;
    }

    @Override
    public void run() {
        while (running) {
            Canvas canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder) {
                    draw(canvas);
                }
            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void draw(Canvas canvas) {
        if (canvas == null) {
            return;
        }
        canvas.drawColor(Color.BLACK);

        Random random = new Random();
        targetX = random.nextInt(canvas.getWidth() - targetRadius * 2) + targetRadius;
        targetY = random.nextInt(canvas.getHeight() - targetRadius * 2) + targetRadius;

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawCircle(targetX, targetY, targetRadius, paint);
    }

    public void setHit(boolean hit) {
        if (hit) {
            gameEventListener.onHit();
        }
    }
}