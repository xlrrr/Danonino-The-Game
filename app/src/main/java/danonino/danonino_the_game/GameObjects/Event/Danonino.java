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

package danonino.danonino_the_game.GameObjects.Event;

import android.graphics.Bitmap;
import android.os.SystemClock;

import danonino.danonino_the_game.Entity.ScoreContainer;
import danonino.danonino_the_game.GameObjects.ScreenObjects.Player;
import danonino.danonino_the_game.Music.SoundManager;

public class Danonino extends Event {
    private static final int DANONINO_NUMROWS = 1;
    private static final int DANONINO_NUMFRAMES = 5;

    public Danonino(Bitmap res) {
        super(res, DANONINO_NUMROWS, DANONINO_NUMFRAMES);
    }

    @Override
    public void executeEvent(final Player player) {
        if(player.isPowerUpActivated()){
            return;
        }
        player.setIsEating(true);
        SoundManager.playSound(SoundManager.EAT_GOLD);
        ScoreContainer.addGlobalScore(517);

        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(8000);

            }
        });
        thr.start();
    }
}
