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

package danonino.danonino_the_game.Music;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import danonino.danonino_the_game.R;

import java.util.ArrayList;

public class SoundManager {
    //values
    public static int EAT_SOUND = 0;
    public static int EAT_GOLD = 1;
    public static int LEVELUP = 2;
    public static int GAME_OVER = 3;

    private static boolean soundOn;
    private static ArrayList<Integer> sounds = new ArrayList<>();
    private static SoundPool sp;

    public static void loadSounds(Context context){
        sounds.add(sp.load(context, R.raw.eat, 1));
        sounds.add(sp.load(context, R.raw.eatgold, 1));
        sounds.add(sp.load(context, R.raw.levelup, 1));
        sounds.add(sp.load(context, R.raw.gameover, 1));
    }

    public static void playSound(int sound){
        if(soundOn){
            int currentId = sounds.get(sound);
            sp.play(currentId, 1, 1, 1, 0, 1.0f);
        }
    }

    public static void release(){
        sp.release();
    }
    
    public static void loadSoundManager(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        }else{
            createOldSoundPool();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected static void createNewSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        sp = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    protected static void createOldSoundPool(){
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
    }

    public static void setSoundOn(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean current = prefs.getBoolean("sound", true); //true is the default value
        soundOn = current;
    }


}
