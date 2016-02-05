/*
 * Copyright © 2015 Danonino The Game
 */

package danonino.danonino_the_game.Factory;

import java.util.Random;

import danonino.danonino_the_game.Core.Data;
import danonino.danonino_the_game.Core.GamePanel;
import danonino.danonino_the_game.GameObjects.ScreenObjects.Butterfly;

public class ButterflyFactory {

    public static Butterfly Create(){
        Butterfly butterfly = new Butterfly(Data.getImage(Data.BUBBLE),getRandomX(), GamePanel.getHEIGHT(), getRandomSpeed());
        return butterfly;
    }

    private static int getRandomX(){
        Random rnd = new Random();
        int x = rnd.nextInt(GamePanel.getWIDTH());
        return x;
    }
    private static int getRandomSpeed(){
        int speed;
        Random rnd = new Random();
        speed = rnd.nextInt(10)+5;
        return speed;
    }
}
