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

import danonino.danonino_the_game.GameObjects.Event.Event;
import danonino.danonino_the_game.GameObjects.Event.Goldfish;
import danonino.danonino_the_game.GameObjects.Event.Jellyfish;

public class EventFactory {

    private static ArrayList<Bitmap> events;
    private static long spawnTimer;
    private static long nextSpawnIn;

    //values
    public static final int JELLYFISH = 0;
    public static final int GOLDFISH = 1;

    public static Event Create() {
        if(getIsReady()) {
            Event currentEvent = getRandomEvent();
            spawnTimer = System.nanoTime();
            nextSpawnIn = getNextSpawnTime();
            System.out.println("Next event in: "+nextSpawnIn);

            return currentEvent;
        }
        return null;
    }

    public static void loadContent(Context context){
        spawnTimer = System.nanoTime();
        nextSpawnIn = getNextSpawnTime();

        //load bitmaps
        events = new ArrayList<>();
        events.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.jelly));
        events.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.goldfish));
    }

    private static Event getRandomEvent(){
        Random rand = new Random();
        int current = rand.nextInt(10000);
        if(current%2==0){
            return new Jellyfish(events.get(JELLYFISH));
        }
        return new Goldfish(events.get(GOLDFISH));
    }

    private static boolean getIsReady() {
        long elapsedSpawnTime = (System.nanoTime() - spawnTimer) / 10000000;
        if(elapsedSpawnTime > nextSpawnIn)
        {
            return true;
        }
        return false;
    }

    private static long getNextSpawnTime(){
        Random rand = new Random();
        return rand.nextInt(6000)+1000;
    }

}
