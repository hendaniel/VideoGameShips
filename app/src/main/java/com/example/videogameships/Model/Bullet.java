package com.example.videogameships.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.videogameships.R;

public class Bullet {

    public static  int SPRITE_SIZE_WIDTH =200;
    public static  int SPRITE_SIZE_HEIGTH=100;
    private float speed;
    private float positionX;
    private float positionY;
    private Bitmap spriteBullet;
    private boolean delete;
    private float screenWidth;


    public Bullet (Context context, float screenWidth, float screenHeigth, float inity, float initx){
        this.screenWidth = screenWidth;
        delete = false;
        speed = 10;
        positionX = initx;
        positionY = inity;
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.laserbueno);
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
        if(this.positionX >= screenWidth) {
            this.delete = true;
        }
    }
}
