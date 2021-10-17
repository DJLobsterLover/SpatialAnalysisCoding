package com.cl.tools.spatialRelation;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import org.locationtech.jts.algorithm.MinimumDiameter;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.util.ArrayList;

/**
 * @author DJLobster
 */
public interface SpatialRelation {
    /**
     * @param p
     * @param l
     * @return 点到线的距离
     */
    double pointToLine(Point p, LineString l);

    /**
     * @param p
     * @param pol
     * @return 点到面的距离
     */
    double pointToPolygon(Point p, Polygon pol);

    /**
     * @param p
     * @param pol
     * @return 判断点是否子啊多边形内部
     */
    boolean pointWithinPolygon(MyPoint p, MyPolygon pol);

    /**
     * @param p
     * @param pol
     * @return
     * 点在多边形内部，旋转数法
     */
    boolean pointWithinPolygonWinding(MyPoint p, MyPolygon pol);

    /**
     * @param
     * @return
     * 道格拉斯扑克——点抽稀
     * max 最大阈值
     */
    ArrayList<MyPoint> pointWeedingDouglas(ArrayList<MyPoint> points, double threshold);

    ArrayList<MyPoint> getPointsDouglas(ArrayList<MyPoint> points,double epsilon, double maxH, int index, int end, ArrayList<MyPoint> result);

    /**
     * @param points
     * @param threshold
     * @return 几何图像平滑
     */
    ArrayList<MyPoint> simpleGeometrySmooth(ArrayList<MyPoint> points, double threshold);

    /**
     * @param points
     * @return
     * 获得一段曲线的弯曲度
     *
     */
    double lineBending(ArrayList<MyPoint> points);

    /**
     * @param polygon
     * @return
     * 返回一个多边形的凸包
     */
    ArrayList<MyPoint> getPolygonConvexHull(MyPolygon polygon);

    /**
     * @param polygon
     * @return
     * 得到多边形的Perimeter/Area ratio
     */
    double getP1A(MyPolygon polygon);

    /**
     * @param polygon
     * @return
     * 获得多边形Perimeter2/Area ratio
     */
    double getP2A(MyPolygon polygon);

    /**
     * @param polygon
     * @return
     * 获得多边形最长轴
     */
    double getPolygonMaxAxis(MyPolygon polygon);

    /**
     * @param polygon
     * @return
     * 获得多边形最短轴
     */
    double getPolygonMinAxis(MyPolygon polygon);

    /**
     * @param polygon
     * @return
     * 形状比
     */
    double getPolygonFormRatio(MyPolygon polygon);

    /**
     * @param polygon
     * @return
     * 伸延率
     */
    double getPolygonElongationRational(MyPolygon polygon);

    /**
     * @param polygon
     * @return
     * 紧凑性比率
     */
    double getPolygonCompactness(MyPolygon polygon);

    /**
     * @param polygon
     * @return
     * 形状指数
     */
    double getPolygonShapeIndex(MyPolygon polygon);

    /**
     * @param polygon
     * @return
     * RBF相关外接圆
     */
    double getPolygonRelateBoundingFigure(MyPolygon polygon);

}
