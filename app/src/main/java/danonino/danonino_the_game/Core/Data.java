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

package danonino.danonino_the_game.Core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import danonino.danonino_the_game.R;

import java.util.ArrayList;

public class Data {
    private static ArrayList<Bitmap> images;

    //font
    private static Typeface font;

    //values
    public static final int BACKGROUND = 0;
    public static final int FRONTGROUND = 1;
    public static final int JOYSTICK_INNER = 2;
    public static final int JOYSTICK_OUTER = 3;
    public static final int PROGRESS_FRAME = 4;
    public static final int PROGRESS_FILL = 5;
    public static final int PLAYER = 6;
    public static final int PLAY_AGAIN_BTN = 7;
    public static final int CONTINUE_BTN = 8;
    public static final int GAMEOVER_LABEL = 9;
    public static final int BUBBLE = 10;
    public static final int PATTERN = 11;
    public static final int POWER_UP_BTN = 12;

    public static void loadContent(Context context){
        images = new ArrayList<>();
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.background));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.frontground));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.inner));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.outer));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.frame));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.fillbar));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.player));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.againbtn));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.continuebtn));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.butterfly));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.pattern));
        images.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.powerupbtn));

        //load fonts;
        font = Typeface.createFromAsset(context.getAssets(), "fonts/Grandstander.ttf");
    }

    public static Bitmap getImage(int current){
        return images.get(current);
    }

    public static Typeface getTypeFace(){
        return font;
    }


}
