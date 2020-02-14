package com.chrosciu.battleships;

import com.chrosciu.battleships.locator.BoardLocator;
import com.chrosciu.battleships.model.Field;
import com.chrosciu.battleships.model.Result;
import com.chrosciu.battleships.model.Ship;
import com.chrosciu.battleships.shooter.BoardShooter;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static com.chrosciu.battleships.model.Result.FINISHED;

public class BattleshipsGame {

    private static final List<Integer> SHIP_SIZES = Collections.singletonList(1);
    private static final int BOARD_SIZE = 2;

    private final BoardShooter boardShooter;
    private final Scanner keyboard;

    public static void main(String[] args) {
        new BattleshipsGame().playGame();
    }

    public BattleshipsGame() {
        List<Ship> ships = new BoardLocator(BOARD_SIZE).locateShipsWithGivenSizeOnBoard(SHIP_SIZES);
        this.boardShooter = new BoardShooter(ships);
        this.keyboard = new Scanner(System.in);
    }

    public void playGame() {
        while (true) {
            boolean gameFinished = handleUserMoveAndCheckIfFinished();
            if (gameFinished) {
                break;
            }
        }
    }

    private boolean handleUserMoveAndCheckIfFinished() {
        Field field = readFieldFromUserInput(keyboard);
        Result result = boardShooter.takeShot(field);
        handleShotResult(result);
        return FINISHED == result;
    }

    private Field readFieldFromUserInput(Scanner keyboard) {
        System.out.println("Enter field vertical coordinate (x)");
        int x = keyboard.nextInt();
        System.out.println("Enter field horizontal coordinate (y)");
        int y = keyboard.nextInt();
        return new Field(x, y);
    }

    private void handleShotResult(Result result) {
        System.out.println(result);
    }
}
