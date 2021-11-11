package com.cl.tools.spatialRelation;

import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.GeometryBuilder;
import com.cl.tools.Transform;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.geotools.geometry.jts.JTS;
import org.junit.Test;

import java.util.ArrayList;

public class SpatialRelationTest {
    private GeometryBuilder gb = new GeometryBuilder();
    private SpatialRelation sr = new SpatialRelationImpl();
    private Transform tf = new Transform();
    @Test
    public void test() {

        Point p1 = gb.createPoint(0, 0);
        LineString l = gb.createLine(gb.createPoint(0, 1), gb.createPoint(1, 0));
        System.out.println(sr.pointToLine(p1,l));
//        System.out.println(Math.acos(Math.pow(2,0.5)/2));

    }
    @Test
    public void test1() {
        Point p1 = gb.createPoint(1, 2);
        Point p2 = gb.createPoint(1,1);
        LineString line = gb.createLine(p1, p2);
        MyPoint myPoint = tf.PointTransBack(p1);
        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(0, -1),
                new Coordinate(0, -2),
                new Coordinate(1, -2),
                new Coordinate(1, 0),
                new Coordinate(0, -1),
        };
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(0, 1));
        points.add(new MyPoint(1, 0));
        points.add(new MyPoint(2, 1));
        points.add(new MyPoint(1, 3));
        MyPolygon polygon = new MyPolygon(points);
//        Polygon polygon = gb.createPolygon(coordinates);
        System.out.println(sr.pointWithinPolygonWinding(myPoint,polygon));
    }

    @Test
    public void testBending() {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(0,0));
        points.add(new MyPoint(0, 1));
        points.add(new MyPoint(1, 1));
        points.add(new MyPoint(1, 0));
        MyPolygon p = new MyPolygon(points);
//        System.out.println(tf.PolygonTrans(p).getLength());
//        System.out.println(sr.lineBending(points));
    }

    @Test
    public void Dogulas() {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(1, 2));
        points.add(new MyPoint(2, 2));
        points.add(new MyPoint(3, 4));
        points.add(new MyPoint(4, -5));
        points.add(new MyPoint(5, 3));
        points.add(new MyPoint(6, 3));
        points.add(new MyPoint(7, 5));
        points.add(new MyPoint(8, 2));
        points.add(new MyPoint(9, 0));
        points.add(new MyPoint(10, 9));
        points.add(new MyPoint(11, 5));

        MyPolygon polygon = new MyPolygon(points);

        ArrayList<MyPoint> polygonConvexHull = sr.getPolygonConvexHull(polygon);
        for (MyPoint myPoint : polygonConvexHull) {
            System.out.println(myPoint.getX() + " " + myPoint.getY());
        }

//        for (MyPoint p : sr.simpleGeometrySmooth(points, 6)) {
//            System.out.println("(" + p.getX() + "," + p.getY() + ") ");
//        }

    }

    @Test
    public void PointWithPolygon() {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(0,0));
        points.add(new MyPoint(0, 1));
        points.add(new MyPoint(1, 1));
        points.add(new MyPoint(1, 0));
        MyPolygon pol = new MyPolygon(points);
        MyPoint p = new MyPoint(0.5, 0.5);
        System.out.println(sr.pointWithinPolygonRay(p,pol));
    }

    @Test
    public void convexHull() {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(0,0));
        points.add(new MyPoint(0, 1));
        points.add(new MyPoint(1, 1));
        points.add(new MyPoint(1, 0));
        points.add(new MyPoint(0.5, 0.5));
        ArrayList<MyPoint> polygonConvexHull2 = sr.getPolygonConvexHull2(points);
        for (MyPoint myPoint : polygonConvexHull2) {
            System.out.println(myPoint.getX() + "  "  + myPoint.getY());
        }
    }
}
