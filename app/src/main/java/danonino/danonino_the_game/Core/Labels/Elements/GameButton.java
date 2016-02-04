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

package danonino.danonino_the_game.Core.Labels.Elements;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.view.MotionEvent;

public class GameButton {
    public Matrix btn_matrix;
    public RectF btn_rect;
    public float width;
    public float height;
    private Bitmap bg;
    private Paint paint;

    public GameButton(Bitmap bg,int x,int y){
        this.btn_matrix = new Matrix();
        this.width = bg.getWidth();
        this.height = bg.getHeight();
        this.bg = bg;

        this.btn_rect = new RectF(0, 0, width, height);
        y+=this.bg.getHeight()/2;
        this.setPosition(x,y);
    }

    public void setPosition(float x, float y){
        btn_matrix.setTranslate(x, y);
        btn_matrix.mapRect(btn_rect);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(bg, btn_matrix, paint);
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                this.paint = new Paint();
                paint.setColorFilter(new PorterDuffColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP));
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {
                this.paint = null;
                break;
            }
        }
        return true;
    }
}
