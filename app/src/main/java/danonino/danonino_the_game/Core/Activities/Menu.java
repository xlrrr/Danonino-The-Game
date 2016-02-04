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
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import danonino.danonino_the_game.R;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

import danonino.danonino_the_game.Core.Data;
import danonino.danonino_the_game.Core.GamePanel;
import danonino.danonino_the_game.Entity.ShardsContainer;
import danonino.danonino_the_game.Music.MusicManager;
import danonino.danonino_the_game.Music.SoundManager;

public class Menu extends Activity {
    private RelativeLayout layout;
    private ImageButton startBtn;
    private Button settingsBtn;
    private ImageButton shardBtn;
    private TextView shardText;
//    private AdView mAdView;

    private boolean continuePlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        this.continuePlaying = false;
        MusicManager.start(getApplicationContext(),MusicManager.MUSIC_MENU);
        ShardsContainer.load(getBaseContext());

        /*mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        */

        this.startBtn = (ImageButton) findViewById(R.id.start_btn);
        this.settingsBtn = (Button) findViewById(R.id.settings_btn);
        this.layout = (RelativeLayout) findViewById(R.id.layout);
        this.shardBtn =(ImageButton)findViewById(R.id.shardIcon);
        this.shardBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.shard));

        this.shardText = (TextView) findViewById(R.id.shardText);
        this.shardText.setText(": " + ShardsContainer.getShards());
        this.shardText.setTextSize(GamePanel.getHEIGHT() / 36);
        this.shardText.setTypeface(Data.getTypeFace());

        Drawable bg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.menubackground);
        this.layout.setBackground(bg);
        Drawable setbg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.settingicon);
        this.settingsBtn.setBackground(setbg);
        Drawable playbg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.woodenlabel);
        this.startBtn.setBackground(playbg);

        this.startBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        Intent i = new Intent(getBaseContext(), Game.class);
                        startActivity(i);

                    case MotionEvent.ACTION_CANCEL: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        this.settingsBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        continuePlaying = true;
                        Intent i = new Intent(getBaseContext(), Settings.class);
                        startActivity(i);

                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });

        this.shardBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                        continuePlaying = true;
                        Intent i = new Intent(getBaseContext(), Shop.class);
                        startActivity(i);

                    case MotionEvent.ACTION_CANCEL: {
                        ImageButton view = (ImageButton) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
         if(!continuePlaying) {
            MusicManager.pause();
        }
        /*
        if (mAdView != null) {
            mAdView.pause();
        }*/
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.continuePlaying=false;
        MusicManager.start(this, MusicManager.MUSIC_MENU);
        this.shardText.setText(": " + ShardsContainer.getShards());
        /*
        if (mAdView != null) {
            mAdView.resume();
        }*/
    }

    @Override
    protected void onDestroy() {
        /*
        if (mAdView != null) {
            mAdView.destroy();
        }
        */
        SoundManager.release();
        MusicManager.release();
        super.onDestroy();
    }
}
