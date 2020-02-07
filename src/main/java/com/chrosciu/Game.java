package com.chrosciu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        //one ship
        List<Integer> data = Arrays.asList(1);
        // board 2 x 2
        int s = 2;
        List<Ship> rv = new ArrayList<>();
        Putter.put(data, s, rv);
        //let's start the game
        Shooter shooter = new Shooter(rv);
        Scanner keyboard = new Scanner(System.in);
        //read user shots
        for (;;) {
            //read field coordinates...
            System.out.println("enter a");
            int a = keyboard.nextInt();
            System.out.println("enter b");
            int b = keyboard.nextInt();
            //... and take shot !
            int r = shooter.shoot(new Field(a, b));
            System.out.println(r);
            //if all ships sunk finish the game
            if (3 == r) {
                break;
            }
        }
    }
}
