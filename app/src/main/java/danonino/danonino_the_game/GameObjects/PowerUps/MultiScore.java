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

package danonino.danonino_the_game.GameObjects.PowerUps;

import android.os.SystemClock;

import danonino.danonino_the_game.GameObjects.ScreenObjects.Player;

public class MultiScore extends PowerUp {
    private static final String MULTISCORE_ABOUT = "Multiplies all the score Ruffing gains for 10 seconds.";
    private static final int MULTISCORE_COST = 9;

    public MultiScore() {
        super(MULTISCORE_ABOUT, MULTISCORE_COST);
    }

    @Override
    public void applyEffect(final Player player) {
        player.setPowerUpActivated(true);
        player.setMultiScore(true);
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(10000);
                player.setMultiScore(false);
                player.setPowerUpActivated(false);
            }
        });
        thr.start();
    }
}
