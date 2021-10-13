package com.cl.pojo;

/**
 * @author DJLobster
 */
public class MyLine {
    private MyPoint startPoint;
    private MyPoint endPoint;

    public MyLine() {
    }

    public MyLine(MyPoint startPoint, MyPoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public MyPoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(MyPoint startPoint) {
        this.startPoint = startPoint;
    }

    public MyPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(MyPoint endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return "MyLine{" +
                "startPoint=" + startPoint +
                ", endPoint=" + endPoint +
                '}';
    }
}
