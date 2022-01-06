package com.cl.pojo;

/**
 * @author DJLobster
 */
public class MyPoint {
    public double x;
    public double y;
    private double z;
    private int flage;

    public MyPoint(double x) {
        this.x = x;
        this.y = 0;
        this.z = 0;
        this.flage = -1;
    }

    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
        this.z = 0;
        this.flage = -1;
    }

    public MyPoint(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.flage = -1;
    }

    public MyPoint() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.flage = -1;
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

    public int getFlage() {
        return flage;
    }

    public void setFlage(int flage) {
        this.flage = flage;
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
