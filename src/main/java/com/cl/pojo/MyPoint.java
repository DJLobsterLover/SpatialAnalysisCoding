package com.cl.pojo;

/**
 * @author DJLobster
 */
public class MyPoint {
    private double x;
    private double y;
    private double z;

    public MyPoint(double x) {
        this.x = x;
        this.y = 0;
        this.z = 0;
    }

    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public MyPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MyPoint() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyPoint) {
            MyPoint s = (MyPoint) obj;
            return this.z == s.z && this.x == s.x && this.y == s.y;
        } else {
            return false;
        }
    }

}
