package com.cl.tools.spatialRelation;

import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.GeometryBuilder;
import com.cl.tools.Transform;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.geotools.geometry.jts.JTS;

import java.util.ArrayList;

/**
 * @author DJLobster
 */
public class SpatialRelationImpl implements SpatialRelation{
    private Transform tf = new Transform();
    private GeometryBuilder gb = new GeometryBuilder();
    DistanceCal dc = new DistanceCalImpl();

    public double pointToLine(Point p, LineString l) {
        double rs = 0;
        rs = p.distance(l);
        return rs;
    }

    public double pointToPolygon(Point p, Polygon pol) {
        double rs = 0;
        rs = p.distance(pol);
        return rs;
    }

    public boolean pointWithinPolygon(MyPoint p, MyPolygon pol) {
        boolean b = tf.PointTrans(p).within(tf.PolygonTrans(pol));
        return b;
    }

    public boolean pointWithinPolygonWinding(MyPoint p, MyPolygon pol) {
        double angle = 0;
        boolean rs = false;
        int sumPoints = pol.getPolygonPoints().size();
        for (int i = 0; i < sumPoints; i++) {
            double a = dc.euclideanDistance(pol.getPolygonPoints().get(i%sumPoints),pol.getPolygonPoints().get((i+1)%sumPoints));
            double b = dc.euclideanDistance(p,pol.getPolygonPoints().get(i%sumPoints));
            double c = dc.euclideanDistance(p,pol.getPolygonPoints().get((i+1)%sumPoints));
            if (b == 0 | c == 0) {
                rs = false;
                System.out.println("该点在多边形上");
                break;
            } else {
                angle += Math.acos((Math.pow(b, 2)+ Math.pow(c, 2) - Math.pow(a,2))/(2*b*c)) / Math.PI * 180;
            }
        }
        System.out.println(angle);
        if (Math.abs(angle - 360) <= 1) {
            rs = true;
        }
        return rs;
    }

    public ArrayList<MyPoint> pointWeedingDouglas(ArrayList<MyPoint> points, double threshold) {
        ArrayList<MyPoint> result = new ArrayList<MyPoint>();
        double maxH = 0;
        int index = 0;
        int end = points.size();
        for (int i = 1; i < end - 1; i++) {
            // 计算点到起点和终点组成线段的高
            double h = pointToLine(tf.PointTrans(points.get(i)), gb.createLine(tf.PointTrans(points.get(index)), tf.PointTrans(points.get(end - 1))));
            if (h > maxH) {
                maxH = h;
                index = i;
            }
        }
        return getPointsDouglas(points, threshold, maxH, index, end, result);
    }

    public ArrayList<MyPoint> getPointsDouglas(ArrayList<MyPoint> points, double epsilon, double maxH, int index, int end, ArrayList<MyPoint> result) {
        if (maxH > epsilon) {
            ArrayList<MyPoint> leftPoints = new ArrayList<MyPoint>();
            ArrayList<MyPoint> rightPoints = new ArrayList<MyPoint>();
            // 分成两半 继续找比阈值大的
            for (int i = 0; i < end; i++) {
                if (i < index) {
                    leftPoints.add(points.get(i));
                } else {
                    rightPoints.add(points.get(i));
                }
            }
            ArrayList<MyPoint> leftResult = pointWeedingDouglas(leftPoints, epsilon);
            ArrayList<MyPoint> rightResult = pointWeedingDouglas(rightPoints, epsilon);

            rightResult.remove(0);
            leftResult.addAll(rightResult);
            result = leftResult;
        } else {
            result.add(points.get(0));
            result.add(points.get(end - 1));
        }
        return result;
    }

    public double lineBending(ArrayList<MyPoint> points) {
        double rs = 0;
        double perimeter = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            perimeter += dc.euclideanDistance(points.get(i), points.get(i + 1));
        }
        double l = dc.euclideanDistance(points.get(0),points.get(points.size() - 1));
        rs = perimeter / l;
        return rs;
    }
}
