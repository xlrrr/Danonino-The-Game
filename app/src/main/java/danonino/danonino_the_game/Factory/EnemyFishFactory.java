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

package danonino.danonino_the_game.Factory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import danonino.danonino_the_game.R;

import java.util.ArrayList;
import java.util.Random;

import danonino.danonino_the_game.Enums.Level;
import danonino.danonino_the_game.GameObjects.Fish.Enemy;

public class EnemyFishFactory {

    private static int currentNumRows=0;
    private static int currentNumFrames=0;
    private static ArrayList<Bitmap> enemyFish;

    //values
    public static final int LEVEL_1 = 0;
    public static final int LEVEL_2 = 1;
    public static final int LEVEL_3 = 2;
    public static final int LEVEL_4 = 3;

    public static void LoadImages(Context context){
        enemyFish = new ArrayList<>();
        enemyFish.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.level1_enemy));
        enemyFish.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.level2_enemy));
        enemyFish.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.level3_enemy));
        enemyFish.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.level4_enemy));
    }

    public static Enemy Create() {
        Level currentLevel = getLevel();
        Bitmap currentBitmap = getBitmap(currentLevel);
        return new Enemy(currentBitmap, currentLevel, currentNumRows, currentNumFrames);
    }

    private static Bitmap getBitmap(Level currentLevel) {
        Bitmap current = null;
        int numOfAnimations=0;
        switch (currentLevel){
            case ONE:
                current=enemyFish.get(LEVEL_1);
                numOfAnimations=4;
                currentNumRows = 1;
                currentNumFrames = 5;
                break;
            case TWO:
                current=enemyFish.get(LEVEL_2);
                numOfAnimations=1;
                currentNumRows=2;
                currentNumFrames=9;
                break;
            case THREE:
                current=enemyFish.get(LEVEL_3);
                numOfAnimations=1;
                currentNumRows=2;
                currentNumFrames=9;
                break;
            case FOUR:
                current=enemyFish.get(LEVEL_4);
                numOfAnimations=1;
                currentNumRows=2;
                currentNumFrames=7;
                break;
        }
        if(numOfAnimations>1) {
            current = getRandomStyle(current, numOfAnimations);
        }
        return current;
    }

    private static Bitmap getRandomStyle(Bitmap currentBitmap, int numOfAnimations){
        Random rand = new Random();
        int current = rand.nextInt(numOfAnimations);
        int height = currentBitmap.getHeight()/numOfAnimations;
        return Bitmap.createBitmap(currentBitmap, 0,current*height,currentBitmap.getWidth(), height);
    }

    private static Level getLevel(){
        Random rand = new Random();
        int current = rand.nextInt(10000);
        if(current%10==0){
            return Level.FOUR;
        }
        else if(current%8==0){
            return Level.THREE;
        }
        else if(current % 6==0){
            return Level.TWO;
        }
        return Level.ONE;
    }
}
