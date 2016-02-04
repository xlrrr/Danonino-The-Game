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

package danonino.danonino_the_game.GameObjects.ScreenObjects;

import android.graphics.Bitmap;

import danonino.danonino_the_game.Core.GamePanel;
import danonino.danonino_the_game.Entity.EnemySpeedGenerator;
import danonino.danonino_the_game.Enums.Level;

public class Fruit extends ScreenObject {
    private EnemySpeedGenerator speedGen = new EnemySpeedGenerator();

    public Fruit(Bitmap res, Level level, int numRows, int numFrames) {
        super(res, level, numRows, numFrames);
        this.setSpeedX(0);
        this.setSpeedY(speedGen.generateYspeed());
        this.setPlaying(true);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void setY(int y) {
        if(y-this.getHeight()>GamePanel.getHEIGHT()+10){
            this.setDead(true);
            return;
        }
        super.setY(y);
    }
}
