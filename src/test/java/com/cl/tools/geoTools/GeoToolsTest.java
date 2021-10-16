package com.cl.tools.geoTools;


import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

public class GeoToolsTest {
    @Test
    public void Test() {
        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory();
        Coordinate[] coords  = new Coordinate[] {new Coordinate(0, 2), new Coordinate(2, 0), new Coordinate(8, 6)
        ,new Coordinate(10,7)};
        Geometry line = geometryFactory.createLineString(coords);
        Geometry smooth = JTS.smooth(line, 2);
        System.out.println(smooth.getCoordinates()[1].x);
    }
}
