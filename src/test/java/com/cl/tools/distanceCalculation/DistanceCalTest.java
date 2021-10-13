package com.cl.tools.distanceCalculation;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.Transform;
import com.vividsolutions.jts.geom.Point;
import org.junit.Test;

import java.util.ArrayList;

public class DistanceCalTest {
    private DistanceCal dc = new DistanceCalImpl();
    private Transform transform = new Transform();
    @Test
    public void Test() {
        MyPoint p1 = new MyPoint(0,90);
        MyPoint p2 = new MyPoint(0,45);
        System.out.println(dc.sphericalDistance(p1,p2,2));

    }

    @Test
    public void PointToLine() {
        MyPoint p = new MyPoint(0,0);
        MyLine line = new MyLine(new MyPoint(1, 1), new MyPoint(2, 0));
        System.out.println(dc.pointToLineDistance(p,line,null));
    }

    @Test
    public void PointToPolygon() {
        MyPoint p = new MyPoint(0,0);
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(1, 0));
        points.add(new MyPoint(2, 0));
        points.add(new MyPoint(2, 1));
        points.add(new MyPoint(1, 1));
        MyPolygon polygon = new MyPolygon(points);
        System.out.println(dc.pointToPolygon(p,polygon,"max"));
    }

}
