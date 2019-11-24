package com.example.videogameships;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.videogameships.Model.Bullet;
import com.example.videogameships.Model.BulletEnemy;
import com.example.videogameships.Model.EnemyShip;
import com.example.videogameships.Model.Rock;
import com.example.videogameships.Model.Ship;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private boolean isPlaying;
    private boolean isDead;
    private Ship ship;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder holder;
    private Thread gameplayThread = null;
    private ArrayList<EnemyShip> enemies;
    private ArrayList<Rock> rocks;
    private ArrayList<Bullet> bullets;
    private ArrayList<BulletEnemy> bulletsEnemies;
    public Context contextGlobal;
    public float screenWithGlobal;
    private int counter;
    private int counterBullet;

    public int life;
    public int aumentarDificultad;
    public int disminuirDificultad;
    public float screenHeightGlobal;


    /**
     * Contructor
     *
     * @param context
     */
    public GameView(Context context, float screenWith, float screenHeight) {
        super(context);
        // Inicializar contador y arreglos de objetos
        life = 100;
        counter = 0;
        counterBullet = 0;
        ship = new Ship(context, screenWith, screenHeight);
        rocks = new ArrayList<>();
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        bulletsEnemies = new ArrayList<>();

        // Asignar contexto y ancho y alto de pantalla
        contextGlobal = context;
        screenWithGlobal = screenWith;
        screenHeightGlobal = screenHeight;

        // Configuracion para canvas de juego
        paint = new Paint();
        holder = getHolder();
        isPlaying = true;
        isDead = false;
    }

    /**
     * Method implemented from runnable interface
     */
    @Override
    public void run() {
        while (isPlaying) {
            randomShipEnemy();
            randomRock();
            paintBullet();
            paintFrame();
            updateInfo();
            verifyCollition();

        }

    }
    private void verifyCollition(){
        for(int i = 0; i < rocks.size() ; i++){
            if(ship.getPositionX() + Ship.SPRITE_SIZE_WIDTH >= rocks.get(i).getPositionX() && Math.abs(ship.getPositionY() - rocks.get(i).getPositionY()) < Rock.SPRITE_SIZE_HEIGTH){
                isDead = true;
                rocks.remove(rocks.get(i));
            }
        }
        for(int i = 0; i < enemies.size() ; i++){
            if(ship.getPositionX() + Ship.SPRITE_SIZE_WIDTH >= enemies.get(i).getPositionX() && Math.abs(ship.getPositionY() - enemies.get(i).getPositionY()) < EnemyShip.SPRITE_SIZE_HEIGTH){
                isDead = true;
                enemies.remove(enemies.get(i));
            }
        }
        for(int i = 0; i < bulletsEnemies.size() ; i++){
            if(ship.getPositionX() + Ship.SPRITE_SIZE_WIDTH >= bulletsEnemies.get(i).getPositionX() && Math.abs(ship.getPositionY() - bulletsEnemies.get(i).getPositionY()) < BulletEnemy.SPRITE_SIZE_HEIGTH){
                life -= 15;
                if(life <= 0){
                    isDead = true;
                }
                bulletsEnemies.remove(bulletsEnemies.get(i));
            }
        }
        for(int i = 0; i < bullets.size() ; i++) {
            for (int j = 0; j < bulletsEnemies.size(); j++) {
                if (bullets.get(i).getPositionX() + Bullet.SPRITE_SIZE_WIDTH >= bulletsEnemies.get(j).getPositionX() && Math.abs(bullets.get(i).getPositionY() - bulletsEnemies.get(j).getPositionY()) < Bullet.SPRITE_SIZE_HEIGTH) {
                    bullets.remove(bullets.get(i));
                    bulletsEnemies.remove(bulletsEnemies.get(j));
                }
            }
        }
        for(int i = 0; i < bullets.size() ; i++) {
            for (int j = 0; j < rocks.size(); j++) {
                if (bullets.get(i).getPositionX() + Bullet.SPRITE_SIZE_WIDTH >= rocks.get(j).getPositionX() && Math.abs(bullets.get(i).getPositionY() - rocks.get(j).getPositionY()) < Bullet.SPRITE_SIZE_HEIGTH) {
                    counter++;
                    rocks.remove(rocks.get(j));
                    bullets.remove(bullets.get(i));
                }
            }
        }
        for(int i = 0; i < bullets.size() ; i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (bullets.get(i).getPositionX() + Bullet.SPRITE_SIZE_WIDTH >= enemies.get(j).getPositionX() && Math.abs(bullets.get(i).getPositionY() - enemies.get(j).getPositionY()) < Bullet.SPRITE_SIZE_HEIGTH) {
                    counter++;
                    enemies.remove(enemies.get(j));
                    bullets.remove(bullets.get(i));
                }
            }
        }

    }

    private void randomShipEnemy() {
        Random random = new Random();
        int ran = random.nextInt(1000);
        if (ran <= 2) {
            EnemyShip enemie = new EnemyShip(contextGlobal, screenWithGlobal, screenHeightGlobal);
            enemies.add(enemie);
        }
    }
    private void randomBulletEnemie() {
        Random random = new Random();
        int ran = random.nextInt(1000);
        if (ran <= 1) {
            BulletEnemy bulletEnemy = new BulletEnemy(contextGlobal, screenWithGlobal, screenHeightGlobal);
            bulletsEnemies.add(bulletEnemy);
        }
    }
    private void randomRock() {
        Random random = new Random();
        int ran = random.nextInt(1000);
        if (ran <= 5) {
            Rock rock = new Rock(contextGlobal, screenWithGlobal, screenHeightGlobal);
            rocks.add(rock);
        }
    }

    private void paintBullet() {
        counterBullet++;
        if(counterBullet == 150){
            Bullet bullet = new Bullet(contextGlobal, screenHeightGlobal, screenHeightGlobal, this.ship.getPositionY() , Ship.SPRITE_SIZE_WIDTH);
            bullets.add(bullet);
            counterBullet = 0;
        }
    }

    private void updateInfo() {
        ship.updateInfo();
        for(int i = 0; i < bulletsEnemies.size() ; i++){
            bulletsEnemies.get(i).updateInfo();
            if(bulletsEnemies.get(i).getBorrar()) {
                bulletsEnemies.remove(bulletsEnemies.get(i));
            }
        }
        for(int i = 0; i < rocks.size() ; i++){
            rocks.get(i).updateInfo();
            if(rocks.get(i).getBorrar()) {
                rocks.remove(rocks.get(i));
                counter--;
            }
        }
        for(int i = 0; i < bullets.size() ; i++){
            bullets.get(i).updateInfo();
            if(bullets.get(i).getBorrar()) {
                bullets.remove(bullets.get(i));
            }
        }
        for(int i = 0; i < enemies.size() ; i++){
            enemies.get(i).updateInfo();
            if(enemies.get(i).getBorrar()) {
                enemies.remove(enemies.get(i));
                counter--;
            }
        }
    }

    private void paintFrame() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(ship.getSpriteShip(), ship.getPositionX(), ship.getPositionY(), paint);
            for (Bullet bullet : bullets) {
                canvas.drawBitmap(bullet.getSpriteCloud(), bullet.getPositionX(), bullet.getPositionY(), paint);
            }
            for (EnemyShip enemie : enemies) {
                canvas.drawBitmap(enemie.getSpriteEnemie(), enemie.getPositionX(), enemie.getPositionY(), paint);
            }
            for (BulletEnemy bullet : bulletsEnemies) {
                canvas.drawBitmap(bullet.getSpriteBullet(), bullet.getPositionX(), bullet.getPositionY(), paint);
            }
            for (Rock rock : rocks) {
                canvas.drawBitmap(rock.getSpriteRock(), rock.getPositionX(), rock.getPositionY(), paint);
            }
            Paint paintText = new Paint();
            paintText.setColor(Color.WHITE);
            paintText.setTextSize(100);
            paintText.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(counter + "",1200, 100, paintText);
            holder.unlockCanvasAndPost(canvas);
        }

    }
    public void pause() {
        isPlaying = false;
        try {
            gameplayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {

        isPlaying = true;
        gameplayThread = new Thread(this);
        gameplayThread.start();
    }

    /**
     * Detect the action of the touch event
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                System.out.println("TOUCH UP - STOP JUMPING");
                ship.setJumping(false);
                break;
            case MotionEvent.ACTION_DOWN:
                System.out.println("TOUCH DOWN - JUMP");
                ship.setJumping(true);
                break;
        }
        return true;
    }

}
