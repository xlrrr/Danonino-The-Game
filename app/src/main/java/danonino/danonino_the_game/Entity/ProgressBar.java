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
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;

import danonino.danonino_the_game.Core.Data;
import danonino.danonino_the_game.Core.GamePanel;
import danonino.danonino_the_game.GameObjects.Fish.Player;

public class ProgressBar {

    private Bitmap frame;
    private Bitmap barImage;
    private Bitmap outputBar;
    private Bitmap pattern;
    private double scoreWidth;
    private Player player;
    private int scoreToLevelUp;
    private Paint fillPnt;
    private Paint strokePnt;
    private int textSize;

    public ProgressBar(Bitmap frame, Bitmap bar,Player player)
    {
        this.frame = Bitmap.createScaledBitmap(frame, GamePanel.getWIDTH(),frame.getHeight(),true);
        this.barImage= bar;
        this.player=player;
        this.textSize = GamePanel.getWIDTH()/32;
        this.pattern= Data.getImage(Data.PATTERN);
        this.fillPnt = this.getFillPaint();
        this.strokePnt = this.getStrokePaint();

        this.fillPnt.setTypeface(Data.getTypeFace());
        this.strokePnt.setTypeface(Data.getTypeFace());
    }

    public void update(int score)
    {
        if(score==0){
            this.scoreToLevelUp = 10+this.player.getCurrentLevel().getValue()*10;
            this.scoreWidth = GamePanel.getWIDTH()/this.scoreToLevelUp;
        }
        int width = 1+(int) (score*this.scoreWidth);
        outputBar = Bitmap.createBitmap(barImage, 0, 0,
                width, barImage.getHeight());
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(outputBar, 0, 0, null);
        canvas.drawBitmap(frame, 0, 0, null);
        drawStrokedText(""+ScoreContainer.getCurrentScore(), canvas);

    }

    private Paint getFillPaint(){
        Paint pnt = new Paint();
        pnt.setTextSize(this.textSize);

        Shader shader = new BitmapShader(pattern,
                Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        pnt.setShader(shader);

        return pnt;
    }

    private Paint getStrokePaint(){
        Paint pnt = new Paint();
        pnt.setTextSize(this.textSize);
        pnt.setStyle(Paint.Style.STROKE);
        pnt.setStrokeWidth(2);
        pnt.setColor(Color.BLACK);

        return pnt;
    }


    private void drawStrokedText(String text, Canvas canvas){

        Rect r = new Rect();
        this.strokePnt.setTextAlign(Paint.Align.LEFT);
        this.strokePnt.getTextBounds(text, 0, text.length(), r);
        this.fillPnt.setTextAlign(Paint.Align.LEFT);
        this.fillPnt.getTextBounds(text, 0, text.length(), r);
        float x = GamePanel.getWIDTH() / 2f - r.width() / 2f - r.left;
        float y = this.barImage.getHeight()/1.5f;
        canvas.drawText(text, x, y, this.strokePnt);
        canvas.drawText(text, x, y, this.fillPnt);
    }

}


