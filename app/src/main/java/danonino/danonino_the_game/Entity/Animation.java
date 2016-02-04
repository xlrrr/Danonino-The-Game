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

public class Animation {

    private Bitmap[][] frames;
    private int currentFrame;
    private int currentAction;
    private long startTime;
    private long delay;
    private boolean playedOnce;
    private int numOfFrames;

    public void setFrames(Bitmap[][] frames)
    {
        this.frames = frames;
        this.setFrame(0);
        currentAction=0;
        startTime = System.nanoTime();
        this.numOfFrames = this.frames[0].length;
    }
    public void setDelay(long d){delay = d;}
    public void setFrame(int i){currentFrame= i;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;

        if(elapsed>delay)
        {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame >= numOfFrames){
            currentFrame = 0;
            playedOnce = true;
        }
    }
    public Bitmap getImage(){
        return frames[currentAction][currentFrame];
    }

    public boolean playedOnce(){return playedOnce;}

    public void setCurrentAction(int currentAction) {
        this.currentFrame = 0;
        this.playedOnce = false;
        this.currentAction = currentAction;
    }
}
