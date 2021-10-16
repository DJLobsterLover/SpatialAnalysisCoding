package com.cl.tools.vectorSpaceCal;

import org.locationtech.jts.geom.Point;

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
    public double vectorSpaceCal(List<Point> points);
}
