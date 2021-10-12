package com.cl.tools.vectorSpace;

import com.cl.pojo.Point;
import com.cl.tools.vectorSpaceCal.VectorSpaceCal;
import com.cl.tools.vectorSpaceCal.VectorSpaceCalImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class vectorSpaceTest {

    @Test
    public void vectorSpaceTest(){
        List<Point> points = new ArrayList<Point>();
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,2);
        Point p3 = new Point(2,2);
        Point p4 = new Point(1,1);
        Point p5 = new Point(2,0);
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
