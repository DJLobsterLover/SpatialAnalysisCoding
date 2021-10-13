package com.cl.pojo;

import java.util.ArrayList;

/**
 * @author DJLobster
 */
public class MyPolygon {
    private ArrayList<MyPoint> PolygonPoints;

    public MyPolygon() {
    }

    public MyPolygon(ArrayList<MyPoint> polygonPoints) {
        PolygonPoints = polygonPoints;
    }

    public ArrayList<MyPoint> getPolygonPoints() {
        return PolygonPoints;
    }

    public void setPolygonPoints(ArrayList<MyPoint> polygonPoints) {
        PolygonPoints = polygonPoints;
    }

    @Override
    public String toString() {
        return "MyPolygon{" +
                "PolygonPoints=" + PolygonPoints +
                '}';
    }
}
