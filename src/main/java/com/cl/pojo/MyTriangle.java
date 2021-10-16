package com.cl.pojo;

import java.util.ArrayList;

/**
 * @author DJLobster
 */
public class MyTriangle {
    private ArrayList<MyPoint> points;

    @Override
    public String toString() {
        return "MyTriangle{" +
                "points=" + points +
                '}';
    }

    public ArrayList<MyPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<MyPoint> points) {
        this.points = points;
    }

    public MyTriangle() {
    }

    public MyTriangle(ArrayList<MyPoint> points) {
        this.points = points;
    }
}
