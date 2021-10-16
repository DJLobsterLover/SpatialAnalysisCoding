package com.cl.tools.volumeCalculation;

import com.cl.pojo.MyPoint;
import com.cl.pojo.MyRegularNet;
import com.cl.pojo.MyTriangle;
import org.junit.Test;

import java.util.ArrayList;

public class volumeCalTest {
    private VolumeCal vc = new VolumeCalImpl();
    @Test
    public void TriangleTest() {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(0, 0, 1));
        points.add(new MyPoint(0, 2, 1));
        points.add(new MyPoint(2, 2, 1));
        points.add(new MyPoint(2, 0, 1));
        MyRegularNet triangle = new MyRegularNet(points);
        System.out.println(vc.volumeCalculationBaseRegularNet(triangle));
    }
}
