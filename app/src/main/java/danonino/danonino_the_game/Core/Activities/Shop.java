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
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import danonino.danonino_the_game.R;

import danonino.danonino_the_game.Core.Data;
import danonino.danonino_the_game.Core.GamePanel;
import danonino.danonino_the_game.Entity.ShardsContainer;
import danonino.danonino_the_game.GameObjects.Fish.Player;
import danonino.danonino_the_game.GameObjects.PowerUps.AquaShield;
import danonino.danonino_the_game.GameObjects.PowerUps.MultiScore;
import danonino.danonino_the_game.GameObjects.PowerUps.PowerUp;
import danonino.danonino_the_game.GameObjects.PowerUps.Whirlpool;

public class Shop extends Activity {

    private ImageButton watchAdd;
    private ImageButton shieldBtn;
    private ImageButton frenzyBtn;
    private ImageButton multiScoreBtn;
    private ImageButton buyBtn;
    //private InterstitialAd mInterstitialAd;
    private TextView infoTxt;
    private TextView shardText;

    private PowerUp selected;

    public void setSelected(int selected) {
        if(selected!=-1){
            this.buyBtn.setVisibility(View.VISIBLE);

            switch (selected){
                case 0:
                    this.selected = new AquaShield();
                    break;
                case 1:
                    this.selected = new Whirlpool();
                    break;
                case 2:
                    this.selected = new MultiScore();
                    break;
            }
            this.infoTxt.setText(this.selected.getAbout());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shop);

        this.infoTxt = (TextView)findViewById(R.id.info_txt);
        this.infoTxt.setTypeface(Data.getTypeFace());

        this.shardText = (TextView) findViewById(R.id.shardText);
        this.shardText.setTextSize(GamePanel.getHEIGHT() / 36);
        this.shardText.setTypeface(Data.getTypeFace());

        this.shieldBtn = (ImageButton) findViewById(R.id.shield_img);
        this.shieldBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.shield));
        this.shieldBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: {
                        clearMarkEffect();
                        ImageButton view = (ImageButton) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        setSelected(0);
                        break;
                    }
                }
                return true;
            }
        });

        this.frenzyBtn = (ImageButton) findViewById(R.id.frenzy_img);
        this.frenzyBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.teeth));
        this.frenzyBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: {
                        clearMarkEffect();
                        ImageButton view = (ImageButton) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        setSelected(1);
                        break;
                    }
                }
                return true;
            }
        });

        this.multiScoreBtn = (ImageButton) findViewById(R.id.multiScore_img);
        this.multiScoreBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.boots));
        this.multiScoreBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN: {
                        clearMarkEffect();
                        ImageButton view = (ImageButton) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        setSelected(2);
                        break;
                    }
                }
                return true;
            }
        });

        this.buyBtn = (ImageButton) findViewById(R.id.buy_btn);
        this.buyBtn.setOnTouchListener(new View.OnTouchListener() {
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
                        if(ShardsContainer.getShards()<selected.getCost()){
                            Toast.makeText(getBaseContext(), "Not enough shards", Toast.LENGTH_LONG).show();
                            break;
                        }
                        else if(Player.getPowerUp()!=null){
                            Toast.makeText(getBaseContext(), "You already have a power up", Toast.LENGTH_LONG).show();
                            break;
                        }
                        Player.setPowerUp(selected);
                        Toast.makeText(getBaseContext(), selected.getClass().getSimpleName()+" is successfully added", Toast.LENGTH_LONG).show();
                        ShardsContainer.remove(selected.getCost());
                        updateShardText();

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
/*
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                ShardsContainer.add(50);
            }
        });
        */

        this.watchAdd = (ImageButton)findViewById(R.id.watchAdd_btn);
        this.watchAdd.setOnTouchListener(new View.OnTouchListener() {
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
                        showInterstitial();

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
        super.onPause();
        clearMarkEffect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateShardText();
    }

    private void requestNewInterstitial() {
        /*
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
        */
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload ad.
        /*
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
            requestNewInterstitial();
        }
        */
    }

    private void clearMarkEffect(){
        this.shieldBtn.getBackground().clearColorFilter();
        this.shieldBtn.invalidate();

        this.frenzyBtn.getBackground().clearColorFilter();
        this.frenzyBtn.invalidate();

        this.multiScoreBtn.getBackground().clearColorFilter();
        this.multiScoreBtn.invalidate();
    }

    private void updateShardText(){
        this.shardText.setText(": " + ShardsContainer.getShards());
    }
}
