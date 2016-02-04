/*
 * Copyright © 2015 Danonino The Game
 */

package danonino.danonino_the_game.Entity;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Bubble {

    private Bitmap image;
    private int height;
    private int width;
    private int x,y;
    private int speedY;

    public Bubble(Bitmap bubble, int positionX, int positionY, int speed){
        this.image=bubble;
        this.width=image.getWidth();
        this.height=image.getHeight();
        this.speedY=speed;
        this.x=positionX;
        this.y=positionY;
    }

    public void update(){
        this.setY(y-speedY);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image, x, y, null);

    }

    public boolean isOutsideScreen(){
        boolean result=false;
        if(this.y+this.height<0){
            result = true;
        }
        return result;
    }
    private void setY(int y){
        this.y=y;
    }



}
