package com.cl.tools.spatialRelation;

import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.GeometryBuilder;
import com.cl.tools.Transform;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

/**
 * @author DJLobster
 */
public class SpatialRelationImpl implements SpatialRelation{
    private Transform tf = new Transform();
    private GeometryBuilder gb = new GeometryBuilder();

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
}
