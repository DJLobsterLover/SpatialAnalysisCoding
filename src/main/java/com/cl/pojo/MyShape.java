package com.cl.pojo;

import lombok.Data;

import java.util.ArrayList;


public class MyShape {
    private double x;
    private double y;
    private String shapeType;//图形类型
    private MyPoint p;//点类型
    private ArrayList<MyPoint> points;//存储多边形或者线
    private double radius;//圆半径

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

    public String getShapeType() {
        return shapeType;
    }

    public void setShapeType(String shapeType) {
        this.shapeType = shapeType;
    }

    public MyPoint getP() {
        return p;
    }

    public void setP(MyPoint p) {
        this.p = p;
    }

    public ArrayList<MyPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<MyPoint> points) {
        this.points = points;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public MyShape() {
    }
}
