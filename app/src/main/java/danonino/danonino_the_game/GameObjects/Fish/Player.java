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

import danonino.danonino_the_game.Core.GamePanel;
import danonino.danonino_the_game.Entity.ScoreContainer;
import danonino.danonino_the_game.Entity.ShardsContainer;
import danonino.danonino_the_game.Entity.Vibration;
import danonino.danonino_the_game.Enums.Level;
import danonino.danonino_the_game.GameObjects.PowerUps.PowerUp;
import danonino.danonino_the_game.Music.SoundManager;

public class Player extends Fish {
    private static final int PLAYER_NUMROWS = 8;
    private static final int PLAYER_NUMFRAMES = 8;
    private static final int STARTING_PLAYER_SCORE = 0;
    private static final Level STARTING_PLAYER_LEVEL = Level.ONE;
    private static PowerUp powerUp;
    private int score;
    private boolean powerUpActivated = false;

    public Player(Bitmap res) {
        super(res, STARTING_PLAYER_LEVEL, PLAYER_NUMROWS, PLAYER_NUMFRAMES);
        this.updateBitmap();
        this.setX(GamePanel.getWIDTH() / 2);
        this.setY(GamePanel.getHEIGHT() / 2);
        this.setScore(STARTING_PLAYER_SCORE);
    }

    @Override
    public void setX(int xCurrent) {
        if(xCurrent<0){
            xCurrent=0;
        }
        else if(xCurrent+this.getWidth()>GamePanel.getWIDTH()){
            xCurrent=GamePanel.getWIDTH()-this.getWidth();
        }
        super.setX(xCurrent);
    }

    @Override
    public void setY(int yCurrent) {
        if(yCurrent<0){
            yCurrent=0;
        }
        else if(yCurrent+this.getHeight()>GamePanel.getHEIGHT()){
            yCurrent=GamePanel.getHEIGHT()-this.getHeight();
        }
        super.setY(yCurrent);
    }

    public int getScore() {
        return score;
    }

    public boolean isPowerUpActivated() {
        return powerUpActivated;
    }

    public void setPowerUpActivated(boolean powerUpActivated) {
        this.powerUpActivated = powerUpActivated;
    }

    @Override
    public void setDead(boolean dead) {
        if(dead){
            ScoreContainer.saveNewHighestScore();
        }
        super.setDead(dead);
    }

    public void tryEat(Fish enemy) {
        if(this.isDead()){
            return;
        }
        if (this.getCurrentLevel().isBiggerThanOrEqual(enemy.getCurrentLevel())||this.isInWhirlpool()) {
            this.setIsEating(true);
            if(this.intersects(enemy,40,50)) {
                enemy.setDead(true);
                int currentEnemyLevel = enemy.getCurrentLevel().getValue();
                this.addScore(currentEnemyLevel);
                int currentGoldMultiplier = this.getGold()?2:1;
                ScoreContainer.addGlobalScore(currentEnemyLevel*103*this.getMultiScore()*currentGoldMultiplier);
                SoundManager.playSound(SoundManager.EAT_SOUND);
            }
        }
        else {
            if(this.isInAquaShielded()){
                return;
            }
            if(this.intersects(enemy, 150, 70)) {
                enemy.setIsEating(true);
                if(this.intersects(enemy,40,50)) {
                    this.setDead(true);
                    Vibration.vibrate(200);
                    SoundManager.playSound(SoundManager.EAT_SOUND);
                }
            }
        }
    }

    public static void setPowerUp(PowerUp currentUp){
        powerUp = currentUp;
    }

    public static PowerUp getPowerUp(){
       return powerUp;
    }

    protected void addScore(int score){
        if(this.getGold()){
            score*=2;
        }
        score*= this.getMultiScore();
        this.setScore(this.getScore() + score);
    }

    private void setScore(int score) {
        if(score>=10+this.getCurrentLevel().getValue()*10){
            this.levelUp();
            this.updateBitmap();
            this.setCurrentAction(-1);
            score=0;
        }
        this.score = score;
    }

    private void levelUp(){
        SoundManager.playSound(SoundManager.LEVELUP);
        int currentLevel = this.getCurrentLevel().getValue();
        currentLevel+=1;
        Level newLevel;
        switch (currentLevel){
            case 2:
                newLevel=Level.TWO;
                break;

            case 3:
                newLevel=Level.THREE;
                break;

            case 4:
                newLevel=Level.FOUR;
                break;
            default:
                ShardsContainer.add(1);
                newLevel=Level.ONE;
                break;
        }
        this.setCurrentLevel(newLevel);
    }

}
