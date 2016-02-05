/*
 * Copyright © 2015 Danonino The Game
 */

package danonino.danonino_the_game.GameObjects.ScreenObjects;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import danonino.danonino_the_game.Core.GamePanel;
import danonino.danonino_the_game.Entity.EnemySpeedGenerator;
import danonino.danonino_the_game.Enums.Level;

public class Butterfly extends ScreenObject {


    private EnemySpeedGenerator speedGen = new EnemySpeedGenerator();

    public Butterfly(Bitmap bubble, int positionX, int positionY, int speed){
        super(bubble, Level.ONE, 1, 9);
        this.setSpeedX(speedGen.generateXspeed());
        this.setSpeedY(-(speedGen.generateYspeed() - 5));
        this.setPlaying(true);
        this.setX(positionX);
        this.setY(positionY);
    }

    public void update(){
        this.setY(y+speedY);
        this.setX(x+speedX);
        super.update();
    }


    public boolean isOutsideScreen(){
        boolean result=false;
        if(this.y+this.height<0){
            result = true;
        }
        return result;
    }
}
