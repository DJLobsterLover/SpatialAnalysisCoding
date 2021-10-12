package com.cl.tools.distanceCalculation;

import com.cl.pojo.Point;

/**
 * @author DJLobster
 */
public interface DistanceCal {
    /**
     * @param startPoint
     * @param endPoint
     * @return
     * @ 欧式距离
     */
    double euclideanDistance(Point startPoint, Point endPoint);

    /**
     * @param startPoint
     * @param endPoint
     * @return
     * 曼哈顿距离
     */
    double manhattanDistance(Point startPoint, Point endPoint);

    /**
     * @param startPoint
     * @param endPoint
     * @return
     * 切比雪夫距离
     */
    double chebyshevDistance(Point startPoint, Point endPoint);

    /**
     * @param startPoint
     * @param endPoint
     * @return
     * 明氏距离
     */
    double minkowskiDistance(Point startPoint, Point endPoint, double m);
}
