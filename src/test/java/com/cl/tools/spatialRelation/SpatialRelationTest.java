package com.cl.tools.spatialRelation;

import com.cl.pojo.GeometryBuilder;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import org.junit.Test;

public class SpatialRelationTest {
    @Test
    public void test() {
        GeometryBuilder gb = new GeometryBuilder();
        SpatialRelation sr = new SpatialRelationImpl();
        Point p1 = gb.createPoint(0, 0,0);
        LineString l = gb.createLine(gb.createPoint(0, 1, 0), gb.createPoint(1, 0, 0));
        System.out.println(sr.PointToLine(p1,l));
    }
}
