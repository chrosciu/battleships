package com.chrosciu;

import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Random;

public class Putter {

    /**
     * Put ships on board according to following rules
     * <ul>
     *     <li>Ships cannot be placed outside board</li>
     *     <li>Ships cannot collide with each other</li>
     *     <li>Ships cannot adjoin with each other even by corner</li>
     * </ul>
     *
     * @param data - sizes of ships to are to be put on board
     * @param s - board size (board is a square)
     * @param rv - list where ships are added after successful put
     */
    public static void put(List<Integer> data, int s, List<Triple<Field, Integer, Boolean>> rv) {
        //array representing already occupied fields
        boolean[][] arr = new boolean[s][s];
        //try to place each ship
        for (int i = 0; i < data.size(); ++i) {
            //infinite loop - as this is random process, we cannot predict how many iterations it will take
            for (; ; ) {
                //get random orientation and first field coordinates
                //true - horizontal, false - vertical
                boolean f = new Random().nextBoolean();
                //constant coordinate may be in whole size range
                int a = new Random().nextInt(s);
                //variable coordinate must be limited as ship cannot exceed the board
                int b = new Random().nextInt(s - data.get(i));
                //collision flag
                boolean c = false;
                //check collisions with other ships
                for (int k = -1; k <= data.get(i); ++k) {
                    //if field is outside board - skip check, there cannot be a collision
                    if (b + k < 0) {
                        continue;
                    }
                    if (b + k >= s) {
                        continue;
                    }
                    //otherwise check all ship fields for collision and adjoining other ships
                    if (f) {
                        //column before ship
                        if (a - 1 >= 0) {
                            if (arr[a - 1][b + k]) {
                                c = true;
                                break;
                            }
                        }
                        if (arr[a][b + k]) {
                            c = true;
                            break;
                        }
                        //column before ship
                        if (a + 1 < s) {
                            if (arr[a + 1][b + k]) {
                                c = true;
                                break;
                            }
                        }
                    } else {
                        //row above ship
                        if (a - 1 >= 0) {
                            if (arr[b + k][a - 1]) {
                                c = true;
                                break;
                            }
                        }
                        if (arr[b + k][a]) {
                            c = true;
                            break;
                        }
                        //row below ship
                        if (a + 1 < s) {
                            if (arr[b + k][a + 1]) {
                                c = true;
                                break;
                            }
                        }
                    }
                }
                //if no collision...
                if (!c) {
                    for (int k = 0; k < data.get(i); ++k) {
                        //... mark ship fields as occupied
                        if (f) {
                            arr[a][b + k] = true;
                        } else {
                            arr[b + k][a] = true;
                        }
                    }
                    //add ship to list
                    rv.add(Triple.of(f ? new Field(a, b) : new Field(b, a), data.get(i), f));
                    break;
                }
            }
        }
    }
}
