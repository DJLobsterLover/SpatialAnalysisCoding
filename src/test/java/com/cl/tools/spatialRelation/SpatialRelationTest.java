package com.cl.tools.spatialRelation;

import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.GeometryBuilder;
import com.cl.tools.Transform;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.junit.Test;

import java.util.ArrayList;

public class SpatialRelationTest {
    private GeometryBuilder gb = new GeometryBuilder();
    private SpatialRelation sr = new SpatialRelationImpl();
    private Transform tf = new Transform();
    @Test
    public void test() {
//        Point p1 = gb.createPoint(0, 0);
//        LineString l = gb.createLine(gb.createPoint(0, 1), gb.createPoint(1, 0));
//        System.out.println(l.getLength());
        System.out.println(Math.acos(Math.pow(2,0.5)/2));
    }
    @Test
    public void test1() {
        Point p1 = gb.createPoint(9, 2);
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
}
