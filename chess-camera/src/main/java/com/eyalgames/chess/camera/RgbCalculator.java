/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eyalgames.chess.camera;

/**
 * Carries out bitwise operations on rgb ints
 */
public class RgbCalculator {

    /**
     * Checks that a value is in range
     * @param value
     * @param x one boundary of the range
     * @param y second boundary of the range
     * @return 
     */
    static boolean inRange(int value, int x, int y) {
        return (value == x) || value == y || (value > x && value < y) || (value > y && value < x);
    }

    /**
     * Returns the color component from an rgb int
     * @param rgb the source color value
     * @param componnentNumber the number of the componnet to get (example: 1=green)
     * @return 
     */
    static public int getColorComponent(int rgb, int componnentNumber) {
        if (!inRange(componnentNumber, 0, 2)) {
            throw new IndexOutOfBoundsException("Component number" + componnentNumber + " not in range");
        }
        return (rgb >> (componnentNumber * 8)) & 0xff;
    }

    /**
     * Encode rgb as integer value
     * @param r
     * @param g
     * @param b
     * @return 
     */
    static public int createRgb(int r, int g, int b) {
        return r << 16 | g << 8 | b;
    }

    static public int setColorComponnent(int baseColor, int componnentNumber, int value) {
        if (!inRange(componnentNumber, 0, 2)) {
            throw new IndexOutOfBoundsException("Component number" + componnentNumber + " not in range");
        }
        if (!inRange(value, 0, 0xff)) {
            throw new IndexOutOfBoundsException("Value " + value + " not in range");
        }
        int mask = 0xff;
        mask <<= componnentNumber * 8;
        mask = (~mask)&0xffffff;
        //turn off all old bits of this componnent
        int ret = baseColor & mask ;
        //set only new bits
        ret |= value << (componnentNumber * 8);
        return ret;
    }

    /**
     * Return the absolute difference between two colors encoded visually as a third color
     * @param color1
     * @param color2
     * @return 
     */
    static public int absoluteDifference(int color1, int color2) {
        int ret = 0;
        for (int c = 0; c < 3; c++) {
            int c1 = getColorComponent(color1, c);
            int c2 = getColorComponent(color2, c);
            int absDiff = Math.abs(c1 - c2);
            ret = setColorComponnent(ret, c, absDiff);
        }
        return ret;
    }

}
