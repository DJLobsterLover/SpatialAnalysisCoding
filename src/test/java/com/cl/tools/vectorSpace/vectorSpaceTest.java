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
    private VectorSpaceCal vs = new VectorSpaceCalImpl();
    @Test
    public void vectorSpaceTest(){
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        MyPoint p1 = new MyPoint(46, 46);
        MyPoint p2 = new MyPoint(49, 199);
        MyPoint p3 = new MyPoint(219, 186);
//        MyPoint p4 = new MyPoint(1, 1);
        MyPoint p5 = new MyPoint(207, 0);
        points.add(p1);
        points.add(p2);
        points.add(p3);
//        points.add(p4);
        points.add(p5);
        double r = vs.vectorSpaceCal(points);
        System.out.println(r);
    }

    @Test
    public void Test() {
        System.out.println(vs.terrestrialSufArea(new MyPoint(0, 0),new MyPoint(90,90),2));
    }

}
