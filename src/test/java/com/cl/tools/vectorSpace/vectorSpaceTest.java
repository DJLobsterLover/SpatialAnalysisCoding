package com.cl.tools.vectorSpace;

//import com.cl.pojo.Point;
import com.cl.pojo.MyPoint;
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
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        MyPoint p1 = new MyPoint(0, 0);
        MyPoint p2 = new MyPoint(0, 2);
        MyPoint p3 = new MyPoint(2, 2);
        MyPoint p4 = new MyPoint(1, 1);
        MyPoint p5 = new MyPoint(2, 0);
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
