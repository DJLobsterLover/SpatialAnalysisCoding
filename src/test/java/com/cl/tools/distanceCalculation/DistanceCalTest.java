package com.cl.tools.distanceCalculation;

import com.cl.pojo.Point;
import org.junit.Test;

public class DistanceCalTest {
    @Test
    public void Test() {
        DistanceCal dc = new DistanceCalImpl();
        Point p1 = new Point(0,90);
        Point p2 = new Point(0,45);
        System.out.println(dc.sphericalDistance(p1,p2,2));

    }
}
