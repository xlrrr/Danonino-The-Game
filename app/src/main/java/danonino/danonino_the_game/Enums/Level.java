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

package danonino.danonino_the_game.Enums;

public enum  Level {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4);

    private Integer value;

    Level(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public boolean isBiggerThanOrEqual(Level otherLevel){
        if(this.value >= otherLevel.value){
            return true;
        }
        return false;
    }
}
