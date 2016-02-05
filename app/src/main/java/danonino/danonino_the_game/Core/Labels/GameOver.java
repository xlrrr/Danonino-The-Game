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
import android.view.MotionEvent;

import danonino.danonino_the_game.Core.Data;
import danonino.danonino_the_game.Core.GamePanel;
import danonino.danonino_the_game.Core.Labels.Elements.GameButton;

public class GameOver {
    private static GameButton playAgainBtn;
    private static GameButton continueBtn;
    private static Bitmap gameOverLbl;
    private static int x;
    private static int y;
    private static boolean againPressed = false;
    private static boolean continuePressed = false;

    public static void draw(Canvas canvas)
    {
        canvas.drawBitmap(gameOverLbl,x,y,null);
        playAgainBtn.draw(canvas);
        continueBtn.draw(canvas);
    }

    public static void loadGameOverContent(){
        gameOverLbl = Data.getImage(Data.GAMEOVER_LABEL);
        x = GamePanel.getWIDTH()/2-gameOverLbl.getWidth()/2;
        y = GamePanel.getHEIGHT()-gameOverLbl.getHeight();
        int btnY = GamePanel.getHEIGHT()-Data.getImage(Data.PLAY_AGAIN_BTN).getHeight();;
        playAgainBtn = new GameButton(Data.getImage(Data.PLAY_AGAIN_BTN),0,btnY);
        int btnX= GamePanel.getWIDTH()-Data.getImage(Data.CONTINUE_BTN).getWidth();
        continueBtn = new GameButton(Data.getImage(Data.CONTINUE_BTN),btnX,btnY);
    }

    public static int onTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (playAgainBtn.btn_rect.contains(x, y)||(event.getActionMasked()== MotionEvent.ACTION_UP&&againPressed))
        {
            againPressed = !againPressed;
            playAgainBtn.onTouch(event);
            return 1;
        }
        else if(continueBtn.btn_rect.contains(x, y)||(event.getActionMasked()== MotionEvent.ACTION_UP&&continuePressed)){
            continuePressed = !continuePressed;
            continueBtn.onTouch(event);
            return 2;
        }
        return 0;
    }
}
