package com.example.videogameships.Model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.videogameships.R;

import java.util.Random;

public class EnemyShip {

    public static  int SPRITE_SIZE_WIDTH =200;
    public static  int SPRITE_SIZE_HEIGTH=200;
    private float speed;
    private float positionX;
    private float positionY;
    private Bitmap spriteEnemie;
    private boolean delete;


    public EnemyShip (Context context, float screenWidth, float screenHeigth){
        delete = false;
        Random randomGenerator = new Random();
        speed = randomGenerator.nextInt(8);
        positionX = screenWidth;
        positionY = randomGenerator.nextInt((int)screenHeigth - SPRITE_SIZE_HEIGTH);
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.naveenemiga);
        spriteEnemie  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public Bitmap getSpriteEnemie() {
        return spriteEnemie;
    }

    public void setSpriteEnemie(Bitmap spriteCloud) {
        this.spriteEnemie = spriteCloud;
    }

    public boolean getBorrar(){
        return this.delete;
    }

    public void updateInfo () {


        this.positionX -=speed;
        System.out.println(this.getPositionX());
        if(this.positionX <= 0) {
            this.delete = true;
        }
    }
}
