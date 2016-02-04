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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import danonino.danonino_the_game.R;

import danonino.danonino_the_game.Music.MusicManager;

public class Settings extends Activity {
    RadioButton rbleft;
    RadioButton rbright;
    RadioGroup rg;
    ToggleButton musciTbtn;
    ToggleButton soundTbtn;
    ToggleButton joystickTbtn;
    TextView joyposLbl;
   // private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);
/*
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
*/
        this.joyposLbl = (TextView)findViewById(R.id.joypos_lbl);
        this.rg = (RadioGroup) findViewById(R.id.radioGroup);
        this.joystickTbtn = (ToggleButton) findViewById(R.id.joy_tbtn);
        this.musciTbtn = (ToggleButton) findViewById(R.id.music_tbtn);
        this.soundTbtn = (ToggleButton) findViewById(R.id.sound_tbtn);
        this.rbleft = (RadioButton) findViewById(R.id.left);
        this.rbright = (RadioButton) findViewById(R.id.right);
        this.rbright.setChecked(!this.readSettings("left"));

        this.musciTbtn.setChecked(this.readSettings("music"));
        this.soundTbtn.setChecked(this.readSettings("sound"));
        this.joystickTbtn.setChecked(this.readSettings("joystick"));
        this.rbright.setEnabled(this.readSettings("joystick"));
        this.rbleft.setEnabled(this.readSettings("joystick"));
        this.joyposLbl.setEnabled(this.readSettings("joystick"));

        this.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbleft.getId()) {
                    saveSettings("left", true);
                } else {
                    saveSettings("left", false);
                }
            }
        });

        this.joystickTbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveSettings("joystick", isChecked);
                if (isChecked) {
                    rbleft.setEnabled(true);
                    rbright.setEnabled(true);
                    joyposLbl.setEnabled(true);
                } else {
                    rbleft.setEnabled(false);
                    rbright.setEnabled(false);
                    joyposLbl.setEnabled(false);
                }
            }
        });

        this.musciTbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveSettings("music", isChecked);
                if (isChecked) {
                    MusicManager.start(getBaseContext(), MusicManager.MUSIC_MENU);
                } else {
                    MusicManager.pause();
                }
            }
        });

        this.soundTbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveSettings("sound", isChecked);
            }
        });
    }

    private void saveSettings(String setting, boolean value){
        SharedPreferences keyValues = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = keyValues.edit();
        editor.putBoolean(setting, value);
        editor.commit();
    }

    private boolean readSettings(String setting) {
        SharedPreferences prefs = this.getSharedPreferences("settings", Context.MODE_PRIVATE);
        boolean current = prefs.getBoolean(setting, true); //true is the default value
        return current;
    }

    @Override
    protected void onPause() {
        /*
        if (mAdView != null) {
            mAdView.pause();
        }
                */
        super.onPause();


    }

    @Override
    protected void onResume() {

        super.onResume();
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
        }*/
        super.onDestroy();

    }
}
