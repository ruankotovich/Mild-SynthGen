/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mild.lhi.bean;

import java.awt.Color;
import java.util.EnumSet;

/**
 *
 * @author dmitry
 */
public enum VertexType {
    // As a reminder: 0 = Floor 1 = Wall 2 = Starting position 3 = Route 4 =
    FLOOR('#', Color.WHITE.getRGB(), 0), OBSTACLE('@', Color.ORANGE.getRGB(), 1), START('O', Color.BLUE.getRGB(), 2), END('X', Color.GREEN.getRGB(), 4), WAYPOINT('W', Color.MAGENTA.getRGB(), 3);
    private final int SENSIBILITY = 1000;
    private final char symbol;
    private final int code;
    private final int RGB;

    private VertexType(char symbol, int RGB, int code) {
        this.symbol = symbol;
        this.RGB = RGB;
        this.code = code;
    }

    public int getRGB() {
        return RGB;
    }

    public char getSymbol() {
        return symbol;
    }

    private boolean isClose(int RGB) {
        return isCloserThat(RGB, getRGB(), SENSIBILITY);
    }

    public static boolean isCloserThat(int RGB, int comp, int sensibility) {
        return (comp - sensibility <= RGB && RGB <= comp + sensibility);
    }

    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }

    public int toCode() {
        return code;
    }

    public static VertexType detectByRGB(int RGB) {
        for (VertexType v : EnumSet.allOf(VertexType.class)) {
            if (v.isClose(RGB)) {
                return v;
            }
        }
        return VertexType.OBSTACLE;
    }
}
