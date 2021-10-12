package com.cl.tools.spatialRelation;

import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

/**
 * @author DJLobster
 */
public class SpatialRelationImpl implements SpatialRelation{


    public double PointToLine(Point p, LineString l) {
        double rs = 0;
        rs = p.distance(l);
        return rs;
    }
}
