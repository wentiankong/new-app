package com.example.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class GameViewFragment extends Fragment implements SurfaceHolder.Callback {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private GameEngine gameEngine;
    private TextView scoreTextView;
    private int score;
    private CountDownTimer timer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gameview, container, false);

        surfaceView = view.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        scoreTextView = view.findViewById(R.id.bookCountTextView);
        score = 0;

        Button resetButton = view.findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetScore();
                resetTimer();
            }
        });

        return view;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameEngine = new GameEngine(surfaceHolder, new GameEngine.GameEventListener() {
            public void onHit() {
                increaseScore();
            }
        });
        gameEngine.start();

        startTimer();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 处理SurfaceView尺寸变化的逻辑
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameEngine.stopRunning();
        try{
            gameEngine.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float touchX = event.getX();
            float touchY = event.getY();

            if (Math.sqrt(Math.pow(touchX - gameEngine.getTargetX(), 2) + Math.pow(touchY - gameEngine.getTargetY(), 2)) <= gameEngine.getTargetRadius()) {
                gameEngine.setHit(true);
                gameEngine.getGameEventListener().onHit();
            }
        }
        return true;
    }

    private void increaseScore() {
        score++;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTextView.setText("书本数量: " + score);
            }
        });
    }

    private void resetScore() {
        score = 0;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                scoreTextView.setText("书本数量: 0");
            }
        });
    }

    private void startTimer() {
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onFinish() {
                resetScore();
                resetGame();
            }
        };
        timer.start();
    }

    private void resetTimer() {
        if (timer != null) {
            timer.cancel();
        }
        startTimer();
    }

    private void resetGame() {
        gameEngine.stopRunning();
        try {
            gameEngine.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gameEngine = new GameEngine(surfaceHolder, new GameEngine.GameEventListener() {
            @Override
            public void onHit() {
                increaseScore();
            }
        });
        gameEngine.start();

        resetTimer();
    }
}