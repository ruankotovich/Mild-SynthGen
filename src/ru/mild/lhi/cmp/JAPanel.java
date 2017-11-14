/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mild.lhi.cmp;

import javax.swing.JPanel;

/**
 *
 * @author dmitry
 */
public class JAPanel extends JPanel {

    private final int x;
    private final int y;

    @Override
    public String toString() {
        return x + ";" + y;
    }

    public JAPanel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getXpos() {
        return x;
    }

    public int getYpos() {
        return y;
    }

}
