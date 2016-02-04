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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;

public class ScoreContainer {
    private static int highestScore;
    private static int currentScore;
    private static int newScore;
    private static Context context;

    public static void loadHighestScore(Context ctx) {
        context=ctx;
        currentScore = 0;
        newScore = 0;
        SharedPreferences prefs = context.getSharedPreferences("highestScore", Context.MODE_PRIVATE);
        int score = prefs.getInt("highscore", 0); //0 is the default value
        highestScore = score;
    }

    public static void saveNewHighestScore(){
        if(currentScore<=highestScore){
            return;
        }
        highestScore = currentScore;
        SharedPreferences keyValues = context.getSharedPreferences("highestScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = keyValues.edit();
        editor.putInt("highscore", highestScore);
        editor.commit();
    }

    public static void addGlobalScore(int score){
        //we can use system clock to make it count up;
        if((newScore+score)<0){
            score=-newScore;
        }
        newScore+=score;
        animateScore(score);
    }

    public static int getCurrentScore(){
        return currentScore;
    }

    public static void resetGlobalScore(){
        currentScore=0;
        newScore = 0;
    }

    private static void animateScore(int added) {
        final int scorePerTick = added/100;
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                while((currentScore<newScore&&scorePerTick>0)||(currentScore>newScore&&scorePerTick<0)) {
                    SystemClock.sleep(10);
                    currentScore+=scorePerTick;
                }
            }
        });
        thr.start();

    }
}
