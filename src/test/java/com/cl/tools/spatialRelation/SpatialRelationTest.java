package com.cl.tools.spatialRelation;

import com.cl.tools.GeometryBuilder;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import org.junit.Test;

public class SpatialRelationTest {
    private GeometryBuilder gb = new GeometryBuilder();
    private SpatialRelation sr = new SpatialRelationImpl();
    @Test
    public void test() {
        Point p1 = gb.createPoint(0, 0);
        LineString l = gb.createLine(gb.createPoint(0, 1), gb.createPoint(1, 0));
        System.out.println(l.getLength());
    }
    @Test
    public void test1() {
        Point p1 = gb.createPoint(-1, -1);
        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(0, 0),
                new Coordinate(0, 2),
                new Coordinate(2, 2),
                new Coordinate(2, 0),
                new Coordinate(0, 0),
        };
        Polygon polygon = gb.createPolygon(coordinates);
        System.out.println(sr.pointToPolygon(p1,polygon));
    }
}
