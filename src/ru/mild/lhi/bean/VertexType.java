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
    FLOOR('#', Color.WHITE.getRGB()), OBSTACLE('@', Color.ORANGE.getRGB()), START('O', Color.BLUE.getRGB()), END('X', Color.GREEN.getRGB());
    private final int SENSIBILITY = 1000;
    private final char symbol;
    private final int RGB;

    private VertexType(char symbol, int RGB) {
        this.symbol = symbol;
        this.RGB = RGB;
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

    public static VertexType detectByRGB(int RGB) {
        for (VertexType v : EnumSet.allOf(VertexType.class)) {
            if (v.isClose(RGB)) {
                //System.out.println(RGB + " with" + v.getRGB());
                return v;
            }
        }
        return VertexType.OBSTACLE;
    }
}
