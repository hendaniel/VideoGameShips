package com.example.videogameships;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class GamePlay extends AppCompatActivity {

    private GameView gameSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        // Force the screen to use the landscape orintation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display display = getWindowManager().getDefaultDisplay();
        Point screenSize = new Point();
        display.getRealSize(screenSize);

        gameSurfaceView = new GameView(this, screenSize.x, screenSize.y);
        setContentView(gameSurfaceView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        gameSurfaceView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameSurfaceView.resume();
    }
}

