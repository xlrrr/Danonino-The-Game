/*
 * Copyright © 2015 Danonino The Game
<<<<<<< Updated upstream
 *
 * This file is part of "Danonino The Game".
 *
 * "Danonino The Game" is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * "Danonino The Game" is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with "Danonino The Game".  If not, see <http://www.gnu.org/licenses/>.
=======
>>>>>>> Stashed changes
 */

package danonino.danonino_the_game.Core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;

import danonino.danonino_the_game.Core.Activities.Game;
import danonino.danonino_the_game.Core.Activities.MoreGamesMenu;
import danonino.danonino_the_game.Core.Labels.GameOver;
import danonino.danonino_the_game.Core.Labels.PowerUp;
import danonino.danonino_the_game.Entity.Background;
import danonino.danonino_the_game.GameObjects.ScreenObjects.Butterfly;
import danonino.danonino_the_game.Entity.Joystick;
import danonino.danonino_the_game.Entity.ProgressBar;
import danonino.danonino_the_game.Entity.ScoreContainer;
import danonino.danonino_the_game.Entity.ShardsContainer;
import danonino.danonino_the_game.Factory.ButterflyFactory;
import danonino.danonino_the_game.Factory.EnemyFishFactory;
import danonino.danonino_the_game.Factory.EventFactory;
import danonino.danonino_the_game.GameObjects.Event.Event;
import danonino.danonino_the_game.GameObjects.Event.Danonino;
import danonino.danonino_the_game.GameObjects.ScreenObjects.Fruit;
import danonino.danonino_the_game.GameObjects.ScreenObjects.Player;
import danonino.danonino_the_game.Music.SoundManager;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    private static int WIDTH;
    private static int HEIGHT;

    private MainThread thread;
    private Background bg;
    private Background bgFront;
    private Player player;
    private ProgressBar progress;
    private ArrayList<Fruit> enemies;
    private ArrayList<Butterfly> butterflies;
    private Joystick joystick;
    private Event event;
    private boolean joystickEnabled;
    private boolean joystickLeft;
    private boolean gameOver;
    private boolean alreadyEnded;
    private PowerUp powerUpLabel = null;

    public GamePanel(Context context)
    {
        super(context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry)
        {
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        this.bg = new Background(Data.getImage(Data.BACKGROUND));
        this.bg.setVector(0);
        this.bgFront = new Background(Data.getImage(Data.FRONTGROUND));
        this.bgFront.setVector(0);
        this.joystickEnabled = this.readSettings("joystick");
        this.joystickLeft = this.readSettings("left");
        this.joystick = new Joystick(Data.getImage(Data.JOYSTICK_INNER),
                Data.getImage(Data.JOYSTICK_OUTER),this.joystickLeft,this.joystickEnabled);
        this.enemies = new ArrayList<>();
        this.butterflies = new ArrayList<>();
        this.initPlayerFeatures();
        this.initFish();
        if(Player.getPowerUp()!=null){
            this.powerUpLabel = new PowerUp(!this.joystickLeft);
        }

        //we can safely start the game loop
        this.thread = new MainThread(getHolder(), this);
        this.thread.setRunning(true);
        this.thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN: {
                if(this.gameOver){
                    GameOver.onTouch(event);
                }
                else if(this.powerUpLabel!=null&&this.powerUpLabel.onTouch(event)==1){
                    return true;
                }
                if(!this.joystickEnabled&&action==MotionEvent.ACTION_DOWN) {
                    this.movePlayer(event);
                }
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                this.movePlayer(event);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:{
                if(this.gameOver){
                    if(GameOver.onTouch(event)==1){
                        this.initPlayerFeatures();
                        Player.setPowerUp(null);
                        this.powerUpLabel=null;
                    }
                    else if(GameOver.onTouch(event)==2){
                        Intent i = new Intent(getContext(), MoreGamesMenu.class);
                        getContext().startActivity(i);
                    }
                }
                else if(this.powerUpLabel!=null){
                    if(this.powerUpLabel.onTouch(event)==1){
                        Player.getPowerUp().applyEffect(this.player);
                    }
                }
                this.player.setSpeedX(0);
                this.player.setSpeedY(0);
                joystick.resetPosition();
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    public void update() {
        this.bg.update();
        this.bgFront.update();
        this.player.update();
        this.progress.update(this.player.getScore());

        if(this.event != null) {
            if (!this.event.isOnScreen()) {
                this.event =null;
            }
            else {
                this.event.update();
                if(this.event.intersects(this.player,60,60)){
                    this.event.executeEvent(this.player);
                    if(this.event.getClass()== Danonino.class){
                        this.event=null;
                    }
                }
            }
        }
        else{
            this.event=EventFactory.Create();
        }

        for (int i = 0; i < this.butterflies.size(); i++) {
            Butterfly currentButterfly = this.butterflies.get(i);
            if(currentButterfly.isOutsideScreen()){
                this.butterflies.remove(i);
            }
            currentButterfly.update();
        }
        for (int i = 0; i < this.enemies.size(); i++) {
            Fruit currentEnemy = this.enemies.get(i);
            if (currentEnemy.isDead()) {
                this.enemies.remove(i);
                continue;
            }

            currentEnemy.update();

            if (this.player.intersects(currentEnemy,40,(int) (currentEnemy.getSpeedY()*6.6666666666666666666666666666667))) {
                this.player.tryEat(currentEnemy);
            }
        }
        if(!this.player.isDead()) {
            if (this.enemies.size() <= 10) {
                initFish();
            }
        }

        if(this.player.isDead()&&!this.alreadyEnded){
            this.alreadyEnded=true;
            Thread thr = new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                    setGameOver(true);
                    SoundManager.playSound(SoundManager.GAME_OVER);
                }
            });
            thr.start();
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            this.bg.draw(canvas);
            this.progress.draw(canvas);

            //draw butterflies
            for(Butterfly b: this.butterflies){
                b.draw(canvas);
            }
            //draw enemies
            for(Fruit e: this.enemies)
            {
                e.draw(canvas);
            }
            this.player.draw(canvas);

            if(this.event != null) {
                this.event.draw(canvas);
            }

            if(!this.gameOver){
                if(this.joystickEnabled) {
                    this.joystick.draw(canvas);
                }

                if(this.powerUpLabel!=null){
                    this.powerUpLabel.draw(canvas);
                }
            }
            else {
                GameOver.draw(canvas);
            }
            this.bgFront.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    public void initFish() {
        this.enemies.add(EnemyFishFactory.Create());
    }

    public void initBubbles() {
        this.butterflies.add(ButterflyFactory.Create());
    }

    public static void setProportions(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        WIDTH = metrics.widthPixels;
        HEIGHT = metrics.heightPixels;
    }

    private boolean readSettings(String setting) {
        SharedPreferences prefs = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean current = prefs.getBoolean(setting, true); //true is the default value
        return current;
    }

    private void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    private void initPlayerFeatures(){
        this.player = new Player(Data.getImage(Data.PLAYER));
        this.progress = new ProgressBar(Data.getImage(Data.PROGRESS_FRAME),
                Data.getImage(Data.PROGRESS_FILL),this.player);
        this.setGameOver(false);
        this.alreadyEnded=false;
        ScoreContainer.resetGlobalScore();
    }

    private void movePlayer(MotionEvent event){
        joystick.onTouch(event,this.player.getX(),this.player.getY());
        this.player.setSpeedX(joystick.calculateFishSpeedX());
        this.player.setSpeedY(joystick.calculateFishSpeedY());
    }
}
