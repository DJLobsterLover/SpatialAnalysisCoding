package com.cl.pojo;

import java.util.ArrayList;

/**
 * @author DJLobster
 */
public class MyRegularNet {
    private ArrayList<MyPoint> points;

    public MyRegularNet(ArrayList<MyPoint> points) {
        this.points = points;
    }

    public MyRegularNet() {
    }

    public ArrayList<MyPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<MyPoint> points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "MyRegularNet{" +
                "points=" + points +
                '}';
    }
}
