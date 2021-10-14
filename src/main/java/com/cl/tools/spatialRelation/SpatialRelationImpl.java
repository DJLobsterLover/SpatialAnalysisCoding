package com.cl.tools.spatialRelation;

import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.GeometryBuilder;
import com.cl.tools.Transform;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

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
}
