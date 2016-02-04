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

import danonino.danonino_the_game.GameObjects.Fish.Player;

public abstract class PowerUp {

    private String about;
    private int cost;

    public PowerUp(String about, int cost) {
        this.about = about;
        this.cost=cost;
    }

    public int getCost() {
        return this.cost;
    }

    public String getAbout() {
        return this.about;
    }

    public abstract void applyEffect(Player player);
}
