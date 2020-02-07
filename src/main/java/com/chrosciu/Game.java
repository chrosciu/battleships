package com.chrosciu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static com.chrosciu.Result.FINISHED;

public class Game {
    public static void main(String[] args) {
        List<Integer> shipSizes = Collections.singletonList(1);
        int boardSize = 2;
        List<Ship> rv = new ArrayList<>();
        Putter.put(shipSizes, boardSize, rv);
        Shooter shooter = new Shooter(rv);
        Scanner keyboard = new Scanner(System.in);
        for (;;) {
            System.out.println("Enter field vertical coordinate (x)");
            int x = keyboard.nextInt();
            System.out.println("Enter field vertical coordinate (y)");
            int y = keyboard.nextInt();
            Result result = shooter.shoot(new Field(x, y));
            System.out.println(result);
            if (FINISHED == result) {
                break;
            }
        }
    }
}
