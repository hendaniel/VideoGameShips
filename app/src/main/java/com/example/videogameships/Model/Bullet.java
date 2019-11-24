package com.example.videogameships.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.videogameships.R;

import java.util.Random;

public class Bullet {

    public static final float INIT_X =10;
    public static final float INIT_Y =10;
    public static  int SPRITE_SIZE_WIDTH =200;
    public static  int SPRITE_SIZE_HEIGTH=100;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;
    private float maxY;
    private float maxX;
    private float speed;
    private float positionX;
    private float positionY;
    private Bitmap spriteBullet;
    private boolean delete;


    public Bullet (Context context, float screenWidth, float screenHeigth, float inity, float initx){
        delete = false;
        Random randomGenerator = new Random();
        speed = 6;
        positionX = initx;
        positionY = inity;
        //Getting bitmap from resource
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.laserbueno);
        spriteBullet  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriteBullet.getWidth()/2);
        this.maxY = screenHeigth - spriteBullet.getHeight();
    }


    public static float getInitX() {
        return INIT_X;
    }

    public static float getInitY() {
        return INIT_Y;
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

    public Bitmap getSpriteCloud() {
        return spriteBullet;
    }

    public void setSpriteCloud(Bitmap spriteCloud) {
        this.spriteBullet = spriteCloud;
    }

    public boolean getBorrar(){
        return this.delete;
    }

    public void updateInfo () {


        this.positionX +=speed;
        if(this.positionX >= 2000) {
            this.delete = true;
        }
    }
}
