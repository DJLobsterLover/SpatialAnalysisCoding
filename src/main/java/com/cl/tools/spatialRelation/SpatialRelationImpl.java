package com.cl.tools.spatialRelation;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

/**
 * @author DJLobster
 */
public class SpatialRelationImpl implements SpatialRelation{


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
}
