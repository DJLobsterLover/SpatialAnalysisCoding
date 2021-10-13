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
        Point p1 = gb.createPoint(0, 0);
        Point p2 = gb.createPoint(1,1);
        LineString line = gb.createLine(p1, p2);
        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(0, -1),
                new Coordinate(0, -2),
                new Coordinate(1, -2),
                new Coordinate(1, 0),
                new Coordinate(0, -1),
        };
        Polygon polygon = gb.createPolygon(coordinates);
        System.out.println(line.distance(polygon));
    }
}
