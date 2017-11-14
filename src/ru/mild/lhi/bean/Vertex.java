/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mild.lhi.bean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dmitry
 */
public class Vertex {

    private final VertexType type;
    private final List<Vertex> neighbors;
    private final int x, y;

    public Vertex(VertexType t, int x, int y) {
        this.type = t;
        this.x = x;
        this.y = y;
        neighbors = new ArrayList<>();
    }

    public List<Vertex> getNeighbors() {
        return neighbors;
    }

    public VertexType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    public String stringCoordinates() {
        return "[[" + x + "];[" + y + "]]";
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
