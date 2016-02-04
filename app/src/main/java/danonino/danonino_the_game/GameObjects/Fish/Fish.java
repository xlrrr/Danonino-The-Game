/*
 * Copyright © 2015 Danonino The Game
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
 */

package danonino.danonino_the_game.GameObjects.Fish;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import danonino.danonino_the_game.Entity.Animation;
import danonino.danonino_the_game.Enums.Level;
import danonino.danonino_the_game.GameObjects.GameObject;

public abstract class Fish extends GameObject {
    //animation
    private int currentAction;
    private boolean isEating;
    private boolean stunned;
    private Animation animation = new Animation();
    private Bitmap fishImage;
    private Bitmap[][] image;
    private int gold = 0;
    private int aquaShield = 0;
    private int multiScore = 1;
    private boolean inWhirlpool = false;

    // Animation actions
    private static final int SWIMMING = 0;
    private static final int EATING = 1;
    private static final int STUNNED = 4;
    private static final int WHIRLPOOL = 7;

    //stats
    private Level currentLevel;
    private boolean dead;
    private long startTime;

    public Fish(Bitmap res, Level level, int numRows, int numFrames) {
        this.numRows = numRows;
        this.numFrames = numFrames;
        this.setCurrentLevel(level);
        this.fishImage=res;
        this.getFrameDimensions(fishImage);
        this.setX(this.getRandomX());
        this.setY(this.getRandomY());

        this.setPlaying(true);
        this.setDead(false);

        this.image = this.createBitmap(res);
        this.animation.setFrames(this.image);
        this.animation.setDelay(100);
        this.startTime = System.nanoTime();
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return this.dead;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Animation getAnimation() {
        return animation;
    }

    public boolean isEating() {
        return this.isEating;
    }

    public boolean isStunned() { return this.stunned; }

    public void setIsEating(boolean isEating) {
        this.isEating = isEating;
    }

    public void setStunned(boolean stunned) {
        this.stunned = stunned;
        this.update();
    }

    public void setGold(boolean gold) {
        if(gold) {
            this.gold = 2;
            return;
        }
        this.gold = 0;
    }

    public void setMultiScore(boolean multiScore) {
        if(multiScore) {
            this.multiScore = 4;
            return;
        }
        this.multiScore = 1;
    }

    public void setAquaShielded(boolean aquaShield) {
        if(aquaShield) {
            this.aquaShield = 5;
            this.setGold(false);
            return;
        }
        this.aquaShield = 0;
    }

    public boolean getGold(){
        return this.gold==2;
    }

    public int getMultiScore(){
        return this.multiScore;
    }

    public boolean isInAquaShielded(){
        return this.aquaShield == 5;
    }

    public void setInWhirlpool(boolean inWhirlpool){
        this.inWhirlpool = inWhirlpool;
    }

    public boolean isInWhirlpool(){
        return this.inWhirlpool;
    }

    @Override
    public void setSpeedX(int speedX) {
        if(this.getGold()||this.isInWhirlpool()){
            speedX*=2;
        }
        super.setSpeedX(speedX);
    }

    public int getCurrentAction() {
        return this.currentAction;
    }

    public void setCurrentAction(int currentAction) {
        this.currentAction = currentAction;
    }

    public void update()
    {
        if(this.isDead()){
            return;
        }

        if(this.isStunned()){
            if(this.getCurrentAction() != this.STUNNED) {
                this.setCurrentAction((this.STUNNED));
                this.getAnimation().setCurrentAction(this.STUNNED);
            }
            this.animation.update();
            return;
        }

        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            startTime = System.nanoTime();
        }
        animation.update();

        this.setX(x + this.speedX);
        this.setY(y + this.speedY);

        // check attack has stopped
        if(this.getCurrentAction() == this.EATING) {
            if (this.getAnimation().playedOnce()) {
                this.setIsEating(false);
            }
        }

        if(this.isInWhirlpool()){
            if(this.getCurrentAction() != this.WHIRLPOOL) {
                this.setCurrentAction((this.WHIRLPOOL));
                this.getAnimation().setCurrentAction(this.WHIRLPOOL);
            }
        }else if(this.isEating()){
            if(this.getCurrentAction() != this.EATING){
                this.setCurrentAction(this.EATING);
                this.getAnimation().setCurrentAction(this.EATING+this.gold+this.aquaShield);
            }
        }else {
            if(this.getCurrentAction() != this.SWIMMING) {
                this.setCurrentAction(this.SWIMMING);
                this.getAnimation().setCurrentAction(this.SWIMMING+this.gold+this.aquaShield);
            }
        }
    }

    public void updateBitmap(){
        Bitmap resized = Bitmap.createScaledBitmap(fishImage,
                (int)(fishImage.getWidth()*0.25*(this.getCurrentLevel().getValue()+1)),
                (int)(fishImage.getHeight()*0.25*(this.getCurrentLevel().getValue()+1)),
                true);
        this.getFrameDimensions(resized);
        this.image = this.createBitmap(resized);
        this.animation.setFrames(this.image);
    }

    public void draw(Canvas canvas)
    {
        if(!this.isDead()) {
            Bitmap currentFrame = animation.getImage();
            if (!this.isTurnedRight()) {
                currentFrame = flipHorizontal(currentFrame);
            }
            canvas.drawBitmap(currentFrame, x, y, null);
        }
    }

    private void getFrameDimensions(Bitmap frame){
        this.height = frame.getHeight()/this.numRows;
        this.width = frame.getWidth()/this.numFrames;
    }
}
