package com.cl.tools.vectorSpaceCal;

import com.cl.pojo.MyPoint;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DJLobster
 */
public interface VectorSpaceCal {
    /**
     * @param points
     * @return
     * 计算任意矢量的面积
     */
    double vectorSpaceCal(ArrayList<MyPoint> points);

    /**
     * @param p1
     * @param p2
     * @return
     * 球面任意两点围成四边形的面积
     */
    double terrestrialSufArea(MyPoint p1, MyPoint p2, double R);
}
