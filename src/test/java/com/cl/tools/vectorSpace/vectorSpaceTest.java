package com.cl.tools.vectorSpace;

//import com.cl.pojo.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import com.cl.tools.vectorSpaceCal.VectorSpaceCal;
import com.cl.tools.vectorSpaceCal.VectorSpaceCalImpl;
import org.junit.Test;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.ArrayList;
import java.util.List;

public class vectorSpaceTest {
    private GeometryFactory geomFactory = new GeometryFactory();
    @Test
    public void vectorSpaceTest(){
        List<Point> points = new ArrayList<Point>();
        Point p1 = geomFactory.createPoint(new Coordinate(0,0 ,0));
        Point p2 = geomFactory.createPoint(new Coordinate(0, 2, 0));
        Point p3 = geomFactory.createPoint(new Coordinate(2 ,2 ,0));
        Point p4 = geomFactory.createPoint(new Coordinate(1, 1, 0));
        Point p5 = geomFactory.createPoint(new Coordinate(2 ,0,0));
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);
        VectorSpaceCal vs = new VectorSpaceCalImpl();
        double r = vs.vectorSpaceCal(points);
        System.out.println(r);
    }

}
