package com.cl.tools.distanceCalculation;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
//import com.vividsolutions.jts.geom.Point;

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
    double euclideanDistance(MyPoint startPoint, MyPoint endPoint);

    /**
     * @param startPoint
     * @param endPoint
     * @return
     * 曼哈顿距离
     */
    double manhattanDistance(MyPoint startPoint, MyPoint endPoint);

    /**
     * @param startPoint
     * @param endPoint
     * @return
     * 切比雪夫距离
     */
    double chebyshevDistance(MyPoint startPoint, MyPoint endPoint);

    /**
     * @param startPoint
     * @param endPoint
     * @return
     * 明氏距离
     */
    double minkowskiDistance(MyPoint startPoint, MyPoint endPoint, double m);

    /**
     * @param startPoint
     * @param endPoint
     * @return
     * 马氏距离
     */
    double mahalanobisDistance(MyPoint startPoint, MyPoint endPoint);

    /**
     * @param startPoint
     * @param endPoint
     * @return
     * 两点球面距离-余弦公式
     */
    double sphericalDistance(MyPoint startPoint, MyPoint endPoint, double R);

    /**
     * @param startPoint
     * @param endPoint
     * @param R
     * @return
     * Haversine 公式
     */
    double sphericalDistanceHaversine(MyPoint startPoint, MyPoint endPoint, double R);

    /**
     * @param point a
     * @param line a
     * @param type max,min
     * @return a
     * 计算点到线的距离
     */
    double  pointToLineDistance(MyPoint point, MyLine line, String type);

    /**
     * @param p
     * @param polygon
     * @param type
     * @return
     * 计算点到多边形的距离
     */
    double pointToPolygonDistance(MyPoint p, MyPolygon polygon ,String type);

    /**
     * @param l1
     * @param l2
     * @return
     * 计算线到线间的距离
     */
    double lineToLineDistance(MyLine l1, MyLine l2);

    /**
     * @param l1
     * @param polygon
     * @return
     * 计算线到面的距离
     */
    double lineToPolygonDistance(MyLine l1, MyPolygon polygon);

    /**
     * @param polygon1
     * @param polygon2
     * @return
     * 计算面到面的距离
     */
    double polygonToPolygonDistance(MyPolygon polygon1, MyPolygon polygon2, String type);
}
