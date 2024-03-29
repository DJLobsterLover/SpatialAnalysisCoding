package com.cl.tools.distanceCalculation;

import Jama.Matrix;
import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.Transform;
import org.locationtech.jts.geom.Point;
import org.junit.Test;

import java.util.ArrayList;

public class DistanceCalTest {
    private DistanceCal dc = new DistanceCalImpl();
    private Transform transform = new Transform();

    @Test
    public void Test() {
        MyPoint p1 = new MyPoint(0, 90);
        MyPoint p2 = new MyPoint(0, 45);
        System.out.println(dc.sphericalDistance(p1, p2, 2));
    }

    @Test
    public void PointToLine() {
        MyPoint p = new MyPoint(0, 0);
        MyLine line = new MyLine(new MyPoint(1, 1), new MyPoint(2, 0));
        System.out.println(dc.pointToLineDistance(p, line, null));
    }

    @Test
    public void PointToPolygon() {
        MyPoint p = new MyPoint(0, 0);
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        MyLine l1 = new MyLine(new MyPoint(0,0),new MyPoint(-1,-1));
        points.add(new MyPoint(1, 0));
        points.add(new MyPoint(2, 0));
        points.add(new MyPoint(2, 1));
        points.add(new MyPoint(1, 1));
        MyPolygon polygon = new MyPolygon(points);
        System.out.println(dc.lineToPolygonDistance(l1,polygon));
    }

    @Test
    public void LineToLine() {
        MyLine l1 = new MyLine(new MyPoint(0,0),new MyPoint(1,1));
        MyLine l2 = new MyLine(new MyPoint(-1,-1), new MyPoint(-1,0));
        System.out.println(dc.lineToLineDistance(l1,l2));
    }
    @Test
    public void PolyToPolygon() {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(1, 0));
        points.add(new MyPoint(2, 0));
        points.add(new MyPoint(2, 1));
        points.add(new MyPoint(1, 1));

        ArrayList<MyPoint> points1 = new ArrayList<MyPoint>();
        points1.add(new MyPoint(0.5, 0));
        points1.add(new MyPoint(0.5, 1));
        points1.add(new MyPoint(-0.5, 1));
        points1.add(new MyPoint(-0.5, 0));
        MyPolygon polygon = new MyPolygon(points);
        MyPolygon polygon1 = new MyPolygon(points1);
        System.out.println(dc.polygonToPolygonDistance(polygon,polygon1,"min"));

    }
    @Test
    public void getPolygonCentroid() {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(0, 1));
        points.add(new MyPoint(1, 0));
        points.add(new MyPoint(2, 1));
        points.add(new MyPoint(1, 3));
        MyPolygon polygon = new MyPolygon(points);
        System.out.println(dc.getPolygonExternal(polygon).getX());
        System.out.println(dc.getPolygonExternal(polygon).getY());

    }

    @Test
    public void mahalanobisDistance() {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(0, 14));
        points.add(new MyPoint(1, 0));
        points.add(new MyPoint(2, 1));
        points.add(new MyPoint(1, 2));
//        points.add(new MyPoint(1, 3));
//        System.out.println(dc.mahalanobisDistance(points));
//        System.out.println(inverse.getArray()[2][1]);
        System.out.println(dc.mahalanobisDistance(points));
    }
    @Test
    public void hausdorffDistance() {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(0, 1));
        points.add(new MyPoint(1, 1));
        points.add(new MyPoint(1, 0));
        points.add(new MyPoint(0, 0));
        MyPolygon p1 = new MyPolygon(points);
        ArrayList<MyPoint> points1 = new ArrayList<MyPoint>();
        points1.add(new MyPoint(-1, 0));
        points1.add(new MyPoint(-1, 0));
        points1.add(new MyPoint(0, -1));
        MyPolygon p2 = new MyPolygon(points1);
        System.out.println(dc.hausdorffDistance(p1,p2));
    }

}