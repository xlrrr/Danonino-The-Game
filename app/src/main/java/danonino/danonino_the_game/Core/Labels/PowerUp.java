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

package danonino.danonino_the_game.Core.Labels;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;

import danonino.danonino_the_game.Core.Data;
import danonino.danonino_the_game.Core.GamePanel;
import danonino.danonino_the_game.Core.Labels.Elements.GameButton;

public class PowerUp {
    private GameButton usePowerUpBtn;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isLeft;
    private boolean usePowerUpBtnPressed;

    public PowerUp(boolean left) {
        this.usePowerUpBtnPressed = false;
        Bitmap currentBtnImage = Data.getImage(Data.POWER_UP_BTN);
        this.width = currentBtnImage.getWidth();
        this.height = currentBtnImage.getHeight();
        this.y = GamePanel.getHEIGHT()-this.height*2;
        this.setIsLeft(left);
        this.usePowerUpBtn = new GameButton(currentBtnImage,this.x,this.y);

    }

    public void draw(Canvas canvas)
    {
        this.usePowerUpBtn.draw(canvas);
    }

    public int onTouch(MotionEvent event) {
        int index = MotionEventCompat.getActionIndex(event);
        int x;
        int y;

        if (event.getPointerCount() > 1) {
            // The coordinates of the current screen contact, relative to
            // the responding View or Activity.
            x = (int) MotionEventCompat.getX(event, index);
            y = (int) MotionEventCompat.getY(event, index);

        } else {
            // Single touch event
            x = (int) MotionEventCompat.getX(event, index);
            y = (int) MotionEventCompat.getY(event, index);
        }

        if (this.usePowerUpBtn.btn_rect.contains(x, y)||((event.getActionMasked()== MotionEvent.ACTION_UP||event.getActionMasked()== MotionEvent.ACTION_POINTER_UP)&&usePowerUpBtnPressed))
        {
            this.usePowerUpBtnPressed = !this.usePowerUpBtnPressed;
            this.usePowerUpBtn.onTouch(event);
            return 1;
        }
        return 0;
    }

    private void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
        if(isLeft==false){
            //right
            this.x =GamePanel.getWIDTH()-this.width-this.width/2;
            return;
        }
        //left
        this.x = this.width/2;
    }
}
