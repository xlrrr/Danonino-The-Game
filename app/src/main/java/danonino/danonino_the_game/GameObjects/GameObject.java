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

package danonino.danonino_the_game.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.util.Random;

import danonino.danonino_the_game.Core.GamePanel;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected Bitmap spritesheet;
    protected int numRows;
    protected int numFrames;

    protected boolean turnedRight=true;
    protected int speedX = 0;

    public int getSpeedY() {
        return speedY;
    }

    protected int speedY = 0;
    protected int directionMultiplier;
    private boolean playing;

    public void setX(int x)
    {
        if(x<0){
           this.directionMultiplier = 1;
        }
        else {
            this.directionMultiplier = -1;
        }
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    public void setPlaying(boolean b){
        this.playing = b;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public void setSpeedX(int speedX) {
        if(speedX<0){
            this.turnedRight = false;
        }
        else if(speedX>0){
            this.turnedRight = true;
        }
        this.speedX = speedX;
    }

    public boolean isTurnedRight() {
        return turnedRight;
    }


    public boolean intersects(GameObject obj2, int percentX, int percentY){
        int playerCenterX = x+getWidth()/2;
        int playerCenterY = y+getHeight()/2;
        int enemyCenterX = obj2.x+obj2.getHeight()/2;
        int enemyCenterY = obj2.y+obj2.getHeight()/2;
        if((Math.abs(playerCenterX-enemyCenterX)<=percentX*getWidth()/100 && Math.abs(playerCenterY-enemyCenterY)<=percentY*getHeight()/100)){
            return true;
        }
        return false;
    }

    protected Bitmap[][] createBitmap(Bitmap res){
        Bitmap[][] image = new Bitmap[this.numRows][this.numFrames];
        this.spritesheet = res;
        for (int j = 0; j < this.numRows; j++) {
            for (int i = 0; i < this.numFrames; i++) {
                image[j][i] = Bitmap.createBitmap(this.spritesheet, i * this.width, j * this.height, this.width, this.height);
            }
        }
        return image;
    }

    protected Bitmap flipHorizontal(Bitmap d)
    {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap dst = Bitmap.createBitmap(d, 0, 0, d.getWidth(), d.getHeight(), m, false);
        return dst;
    }

    protected int getRandomX() {
        Random rand = new Random();
        int minNumber = this.getWidth();
        int maxNumber = GamePanel.getWIDTH()-this.getWidth();
        int generated = rand.nextInt((maxNumber-minNumber)+minNumber)+minNumber;
        return generated;
    }
}

