package com.example.videogameships;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
    }

    public void playGame (View view){
        startActivity(new Intent(this, GamePlay.class));
    }
    public void putEasy (View view){
        GameView.difficult = false;
        GameView.difficultRandom = 5;
    }
    public void putMedium (View view){
        GameView.difficult = true;
        GameView.difficultRandom = 10;
    }
    public void putHard (View view){
        GameView.difficult = true;
        GameView.difficultRandom = 20;
    }
}
