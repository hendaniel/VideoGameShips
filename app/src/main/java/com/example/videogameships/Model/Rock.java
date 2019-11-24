package com.example.videogameships.Model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.videogameships.R;

import java.util.Random;

public class Rock {

    public static  int SPRITE_SIZE_WIDTH =150;
    public static  int SPRITE_SIZE_HEIGTH=150;
    private float speed;
    private float positionX;
    private float positionY;
    private Bitmap spriteRock;
    private boolean delete;


    public Rock (Context context, float screenWidth, float screenHeigth){
        delete = false;
        Random randomGenerator = new Random();
        speed = randomGenerator.nextInt(8);
        positionX = screenWidth;
        positionY = randomGenerator.nextInt((int)screenHeigth - SPRITE_SIZE_HEIGTH);
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.rock);
        spriteRock  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

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

    public Bitmap getSpriteRock() {
        return spriteRock;
    }

    public void setSpriteRock(Bitmap spriteCloud) {
        this.spriteRock = spriteCloud;
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
