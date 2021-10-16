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
    public double vectorSpaceCal(ArrayList<MyPoint> points);
}
