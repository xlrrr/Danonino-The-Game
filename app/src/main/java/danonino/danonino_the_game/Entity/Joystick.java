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

package danonino.danonino_the_game.Entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;

import danonino.danonino_the_game.Core.GamePanel;

public class Joystick {
    // coordinates
    private int zeroX;
    private int zeroY;
    private float clickedX=0;
    private float clickedY=0;
    private float dx=0;
    private float dy=0;
    private float radius;
    private float angle;
    private float distance;

    private boolean isEnabled;
    private boolean isLeft;

    // data
    private Bitmap outerCircle;
    private Bitmap innerCircle;

    public Joystick(Bitmap inner, Bitmap outer,boolean isLeft,boolean isEnabled){
        this.isEnabled = isEnabled;
        this.outerCircle = outer;
        this.innerCircle = inner;
        this.setIsLeft(isLeft);
        this.zeroY = GamePanel.getHEIGHT()-this.outerCircle.getHeight()/2-this.outerCircle.getHeight()/5;
        this.radius =outerCircle.getWidth()/2;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(outerCircle,zeroX-outerCircle.getWidth()/2,zeroY-outerCircle.getHeight()/2,null);
        if(dx==0&&dy==0){
            canvas.drawBitmap(innerCircle,
                    zeroX-innerCircle.getWidth()/2,
                    zeroY-innerCircle.getHeight()/2,null);
        }
        else{
            canvas.drawBitmap(innerCircle,
                    clickedX-innerCircle.getWidth()/2,
                    clickedY-innerCircle.getHeight()/2,null);
        }
    }

    public void update(){
        this.dx=this.clickedX-this.zeroX;
        this.dy=this.clickedY-this.zeroY;
        this.angle = (float)Math.atan(Math.abs(dy / dx));
        this.distance = (float)Math.sqrt(dx*dx+dy*dy);
        if(this.distance >this.radius){
            if(dx>0&&dy>0) {//bottom right
                this.clickedX = this.zeroX + (this.radius * (float) Math.cos(this.angle));
                this.clickedY = this.zeroY + (this.radius * (float) Math.sin(this.angle));
            }
            else if(dx>0&&dy<0){//top right
                this.clickedX = this.zeroX + (this.radius * (float) Math.cos(this.angle));
                this.clickedY = this.zeroY - (this.radius * (float) Math.sin(this.angle));
            }
            else if(dx<0&&dy<0){//top left
                this.clickedX = this.zeroX - (this.radius * (float) Math.cos(this.angle));
                this.clickedY = this.zeroY - (this.radius * (float) Math.sin(this.angle));
            }
            else{//bottom left
                this.clickedX = this.zeroX - (this.radius * (float) Math.cos(this.angle));
                this.clickedY = this.zeroY + (this.radius * (float) Math.sin(this.angle));
            }
        }
        else{
            this.clickedX = zeroX+dx;
            this.clickedY = zeroY+dy;
        }
    }

    public void onTouch(MotionEvent event,int x,int y){
        this.clickedX = event.getX();
        this.clickedY = event.getY();
        if(!isEnabled){
            this.zeroX = x;
            this.zeroY = y;
        }
        this.update();
    }

    public void resetPosition(){
        this.clickedX=0;
        this.clickedY=0;
        this.dx=0;
        this.dy=0;
    }

    public int calculateFishSpeedX(){
        return (int)(clickedX-zeroX)/5;
    }

    public int calculateFishSpeedY(){
        return (int)(clickedY-zeroY)/5;
    }

    private void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
        if(isLeft==false){
            this.zeroX = GamePanel.getWIDTH()-this.outerCircle.getWidth()+(this.outerCircle.getWidth()/3);
            return;
        }
        this.zeroX = this.outerCircle.getWidth()-(this.outerCircle.getWidth()/3);
    }
}
