package com.example.videogameships;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
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

    public static int difficultRandom;
    public static boolean difficult;
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
    private int counterBulletEnemy;
    public int life;
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
        counterBulletEnemy = 0;
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
        while (isPlaying && !isDead) {
            randomShipEnemy();
            randomRock();
            paintBullet();
            if (difficult) {
                paintBulletEnemy();
            }
            paintFrame();
            updateInfo();
            verifyCollition();
        }
        if (isDead) {
            gameOver();
        }

    }

    private void verifyCollition() {
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < rocks.size(); j++) {
                Bullet bull = bullets.get(i);
                Rock rock = rocks.get(j);
                if (bull.getPositionX() + Bullet.SPRITE_SIZE_WIDTH >= rock.getPositionX() && Math.abs(bull.getPositionY() - rock.getPositionY()) < Bullet.SPRITE_SIZE_HEIGTH) {
                    counter++;
                    rocks.remove(rock);
                    bullets.remove(bull);
                    if (i != 0) i--;
                    else break;
                    if (j != 0) j--;
                    else break;
                }
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                Bullet bull = bullets.get(i);
                EnemyShip enemy = enemies.get(j);
                if (bull.getPositionX() + Bullet.SPRITE_SIZE_WIDTH >= enemy.getPositionX() && Math.abs(bull.getPositionY() - enemy.getPositionY()) < Bullet.SPRITE_SIZE_HEIGTH) {
                    counter++;
                    enemies.remove(enemy);
                    bullets.remove(bull);
                    if (i != 0) i--;
                    else break;
                    if (j != 0) j--;
                    else break;
                }
            }
        }
        for (int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            if (ship.getPositionX() + Ship.SPRITE_SIZE_WIDTH >= rock.getPositionX() && Math.abs(ship.getPositionY() - rock.getPositionY()) < Rock.SPRITE_SIZE_HEIGTH) {
                isDead = true;
                rocks.remove(rock);
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            EnemyShip enemy = enemies.get(i);
            if (ship.getPositionX() + Ship.SPRITE_SIZE_WIDTH >= enemy.getPositionX() && Math.abs(ship.getPositionY() - enemy.getPositionY()) < EnemyShip.SPRITE_SIZE_HEIGTH) {
                isDead = true;
                enemies.remove(enemy);
            }
        }
        for (int i = 0; i < bulletsEnemies.size(); i++) {
            BulletEnemy bullet = bulletsEnemies.get(i);
            if (ship.getPositionX() + Ship.SPRITE_SIZE_WIDTH >= bullet.getPositionX() && Math.abs(ship.getPositionY() - bullet.getPositionY()) < BulletEnemy.SPRITE_SIZE_HEIGTH) {
                life -= 15;
                if (life <= 0) {
                    isDead = true;
                }
                bulletsEnemies.remove(bullet);
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < bulletsEnemies.size(); j++) {
                Bullet bull = bullets.get(i);
                BulletEnemy bulle = bulletsEnemies.get(j);
                if (bull.getPositionX() + Bullet.SPRITE_SIZE_WIDTH >= bulle.getPositionX() && Math.abs(bull.getPositionY() - bulle.getPositionY()) < Bullet.SPRITE_SIZE_HEIGTH) {
                    bullets.remove(bull);
                    bulletsEnemies.remove(bulle);
                    if (i != 0) i--;
                    else break;
                    if (j != 0) j--;
                    else break;
                }
            }
        }

    }

    private void randomShipEnemy() {
        Random random = new Random();
        int ran = random.nextInt(1000);
        if (ran <= difficultRandom) {
            EnemyShip enemie = new EnemyShip(contextGlobal, screenWithGlobal, screenHeightGlobal);
            enemies.add(enemie);
        }
    }

    private void randomRock() {
        Random random = new Random();
        int ran = random.nextInt(1000);
        if (ran <= difficultRandom) {
            Rock rock = new Rock(contextGlobal, screenWithGlobal, screenHeightGlobal);
            rocks.add(rock);
        }
    }

    private void paintBulletEnemy() {
        counterBulletEnemy++;
        if (counterBulletEnemy > 120 && counterBulletEnemy < 150) {
            if (enemies.size() > 0) {
                Random random = new Random();
                int ran = random.nextInt(enemies.size());
                EnemyShip pos = enemies.get(ran);
                BulletEnemy bulletEnemy = new BulletEnemy(contextGlobal, screenWithGlobal, screenHeightGlobal, pos.getPositionY() + EnemyShip.SPRITE_SIZE_HEIGTH / 2 - BulletEnemy.SPRITE_SIZE_HEIGTH / 2, pos.getPositionX() - EnemyShip.SPRITE_SIZE_WIDTH, pos.getSpeed() + 3);
                bulletsEnemies.add(bulletEnemy);
                counterBulletEnemy = 0;
            }
        }
    }


    private void paintBullet() {
        counterBullet++;
        if (counterBullet == 50) {
            Bullet bullet = new Bullet(contextGlobal, screenWithGlobal, screenHeightGlobal, this.ship.getPositionY() + Ship.SPRITE_SIZE_HEIGTH / 2 - Bullet.SPRITE_SIZE_HEIGTH / 2, Ship.SPRITE_SIZE_WIDTH);
            bullets.add(bullet);
            counterBullet = 0;
//            MediaPlayer mediaPlayer = MediaPlayer.create(contextGlobal, R.raw.laser);
//            mediaPlayer.setDisplay(holder);
//            mediaPlayer.start();
        }
    }

    private void updateInfo() {
        ship.updateInfo();
        for (int i = 0; i < bulletsEnemies.size(); i++) {
            bulletsEnemies.get(i).updateInfo();
            if (bulletsEnemies.get(i).getBorrar()) {
                bulletsEnemies.remove(bulletsEnemies.get(i));
            }
        }
        for (int i = 0; i < rocks.size(); i++) {
            rocks.get(i).updateInfo();
            if (rocks.get(i).getBorrar()) {
                rocks.remove(rocks.get(i));
                counter--;
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).updateInfo();
            if (bullets.get(i).getBorrar()) {
                bullets.remove(bullets.get(i));
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).updateInfo();
            if (enemies.get(i).getBorrar()) {
                enemies.remove(enemies.get(i));
                counter--;
            }
        }
    }

    private void gameOver() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            Bitmap originalBitmap = BitmapFactory.decodeResource(contextGlobal.getResources(), R.drawable.gameover);
            canvas.drawBitmap(originalBitmap, 200, 200, paint);
            holder.unlockCanvasAndPost(canvas);
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
            Paint paintCounter = new Paint();
            paintCounter.setColor(Color.WHITE);
            paintCounter.setTextSize(80);
            paintCounter.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Puntos: " + counter + "", screenWithGlobal - screenWithGlobal / 4, 100, paintCounter);
            Paint paintLife = new Paint();
            paintLife.setColor(Color.WHITE);
            paintLife.setTextSize(80);
            paintLife.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Vida: " + life + "", screenWithGlobal - screenWithGlobal / 2, 100, paintLife);
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
     *
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
