package com.example.videogameships.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.videogameships.R;

import java.util.Random;

public class BulletEnemy {

    public static  int SPRITE_SIZE_WIDTH =200;
    public static  int SPRITE_SIZE_HEIGTH=100;
    private float speed;
    private float positionX;
    private float positionY;
    private Bitmap spriteBullet;
    private boolean delete;


    public BulletEnemy (Context context, float screenWidth, float screenHeigth, float inity, float initx, float initspeed){
        delete = false;
        speed = initspeed;
        positionX = initx;
        positionY = inity;
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.laser);
        spriteBullet  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);
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

    public Bitmap getSpriteBullet() {
        return spriteBullet;
    }

    public void setSpriteBullet(Bitmap spriteCloud) {
        this.spriteBullet = spriteCloud;
    }

    public boolean getBorrar(){
        return this.delete;
    }

    public void updateInfo () {


        this.positionX -=speed;
        if(this.positionX <= 0) {
            this.delete = true;
        }
    }
}
