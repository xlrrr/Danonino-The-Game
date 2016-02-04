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

package danonino.danonino_the_game.Core.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import danonino.danonino_the_game.R;

import danonino.danonino_the_game.Core.Data;
import danonino.danonino_the_game.Core.GamePanel;
import danonino.danonino_the_game.Entity.ScoreContainer;
import danonino.danonino_the_game.Entity.Vibration;
import danonino.danonino_the_game.Factory.EnemyFishFactory;
import danonino.danonino_the_game.Factory.EventFactory;
import danonino.danonino_the_game.Music.SoundManager;

public class Splash extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        TextView tv = (TextView)findViewById(R.id.textView);

        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.blink_animation);
        tv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Context base = getBaseContext();
                EnemyFishFactory.LoadImages(base);
                SoundManager.loadSoundManager();
                SoundManager.loadSounds(base);
                EventFactory.loadContent(base);
                Data.loadContent(base);
                Vibration.loadVibrator(base);
                GamePanel.setProportions(base);
                ScoreContainer.loadHighestScore(base);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent i = new Intent(getBaseContext(),Menu.class);
                startActivity(i);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
