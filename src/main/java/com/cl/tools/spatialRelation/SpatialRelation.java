package com.cl.tools.spatialRelation;
import com.vividsolutions.jts.algorithm.MinimumDiameter;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
/**
 * @author DJLobster
 */
public interface SpatialRelation {
    /**
     * @param p
     * @param l
     * @return
     * 点到线的距离
     */
    double pointToLine(Point p,LineString l);

    /**
     * @param p
     * @param pol
     * @return
     * 点到面的距离
     */
    double pointToPolygon(Point p,Polygon pol);
}
