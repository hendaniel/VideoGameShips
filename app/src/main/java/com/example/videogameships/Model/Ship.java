package com.example.videogameships.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.videogameships.R;

public class Ship {

    public static final float INIT_X =100;
    public static final float INIT_Y =100;
    public static  int SPRITE_SIZE_WIDTH =300;
    public static  int SPRITE_SIZE_HEIGTH=300;
    public static final float GRAVITY_FORCE=10;
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private float maxY;
    private float maxX;

    private float speed = 0;
    private float positionX;
    private float positionY;
    private Bitmap spriteShip;
    private boolean isJumping;
    private Context context;
    private float screenWidth;
    private float screenHeigth;


    public Ship (Context context, float screenWidth, float screenHeigth){
        this.context = context;
        this.screenWidth = screenWidth;
        this.screenHeigth = screenHeigth;

        speed = 1;
        positionX = this.INIT_X;
        positionY = this.INIT_Y;
        isJumping = false;
        //Getting bitmap from resource
        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.navebuena);
        spriteShip  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);

        this.maxX = screenWidth - (spriteShip.getWidth()/2);
        this.maxY = screenHeigth - spriteShip.getHeight();
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

    public Bitmap getSpriteShip() {
        return spriteShip;
    }

    public void setSpriteShip(Bitmap spriteRock) {
        this.spriteShip = spriteRock;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        isJumping = jumping;
    }


    public void updateInfo () {


        Bitmap originalBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.navebuena);
        spriteShip  = Bitmap.createScaledBitmap(originalBitmap, SPRITE_SIZE_WIDTH, SPRITE_SIZE_HEIGTH, false);
        this.maxX = screenWidth - (spriteShip.getWidth()/2);
        this.maxY = screenHeigth - spriteShip.getHeight();

        if (isJumping) {
            speed += 5;
        } else {
            speed -= 5;
        }

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }
        this.positionY -= speed - GRAVITY_FORCE;

        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }
}
