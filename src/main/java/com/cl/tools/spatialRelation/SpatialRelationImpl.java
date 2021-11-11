package com.cl.tools.spatialRelation;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.Constants;
import com.cl.tools.GeometryBuilder;
import com.cl.tools.Transform;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import com.cl.tools.vectorSpaceCal.VectorSpaceCal;
import com.cl.tools.vectorSpaceCal.VectorSpaceCalImpl;
import org.locationtech.jts.geom.*;
import org.geotools.geometry.jts.JTS;

import java.util.*;

/**
 * @author DJLobster
 */
public class SpatialRelationImpl implements SpatialRelation{
    private Transform tf = new Transform();
    private GeometryBuilder gb = new GeometryBuilder();
    private DistanceCal dc = new DistanceCalImpl();
    VectorSpaceCal vc = new VectorSpaceCalImpl();

    public double pointToLine(Point p, LineString l) {
        double rs = 0;
        rs = p.distance(l);
        return rs;
    }

    public double pointToPolygon(Point p, Polygon pol) {
        double rs = 0;
        rs = p.distance(pol);
        return rs;
    }

    public boolean pointWithinPolygon(MyPoint p, MyPolygon pol) {
        boolean b = tf.PointTrans(p).within(tf.PolygonTrans(pol));
        return b;
    }

    public boolean pointWithinPolygonWinding(MyPoint p, MyPolygon pol) {
        double angle = 0;
        boolean rs = false;
        int sumPoints = pol.getPolygonPoints().size();
        for (int i = 0; i < sumPoints; i++) {
            double a = dc.euclideanDistance(pol.getPolygonPoints().get(i%sumPoints),pol.getPolygonPoints().get((i+1)%sumPoints));
            double b = dc.euclideanDistance(p,pol.getPolygonPoints().get(i%sumPoints));
            double c = dc.euclideanDistance(p,pol.getPolygonPoints().get((i+1)%sumPoints));
            if (b == 0 | c == 0) {
                rs = false;
                System.out.println("该点在多边形上");
                break;
            } else {
                angle += Math.acos((Math.pow(b, 2)+ Math.pow(c, 2) - Math.pow(a,2))/(2*b*c)) / Math.PI * 180;
            }
        }
        System.out.println(angle);
        if (Math.abs(angle - 360) <= 1) {
            rs = true;
        }
        return rs;
    }

    public boolean pointWithinPolygonRay(MyPoint p, MyPolygon pol) {
        boolean rs = false;
        int count = 0;
        MyLine l = new MyLine(p,new MyPoint(p.getX(), Constants.YMax));
        LineString lineString = tf.LineTrans(l);
        int pointsCount = pol.getPolygonPoints().size();
        for (int i = 0; i < pointsCount; i++) {
            boolean crosses = tf.LineTrans(new MyLine(pol.getPolygonPoints().get(i), pol.getPolygonPoints().get((i + 1) % pointsCount))).crosses(lineString);
            if (crosses) {
                count += 1;
            }
        }
        if ((count % 2) == 0) {
            rs = false;
        } else {
            rs = true;
        }
        return rs;
    }

    public ArrayList<MyPoint> pointWeedingDouglas(ArrayList<MyPoint> points, double threshold) {
        ArrayList<MyPoint> result = new ArrayList<MyPoint>();
        double maxH = 0;
        int index = 0;
        int end = points.size();
        for (int i = 1; i < end - 1; i++) {
            // 计算点到起点和终点组成线段的高
            double h = pointToLine(tf.PointTrans(points.get(i)), gb.createLine(tf.PointTrans(points.get(index)), tf.PointTrans(points.get(end - 1))));
            if (h > maxH) {
                maxH = h;
                index = i;
            }
        }
        return getPointsDouglas(points, threshold, maxH, index, end, result);
    }

    public ArrayList<MyPoint> getPointsDouglas(ArrayList<MyPoint> points, double epsilon, double maxH, int index, int end, ArrayList<MyPoint> result) {
        if (maxH > epsilon) {
            ArrayList<MyPoint> leftPoints = new ArrayList<MyPoint>();
            ArrayList<MyPoint> rightPoints = new ArrayList<MyPoint>();
            // 分成两半 继续找比阈值大的
            for (int i = 0; i < end; i++) {
                if (i < index) {
                    leftPoints.add(points.get(i));
                } else {
                    rightPoints.add(points.get(i));
                }
            }
            ArrayList<MyPoint> leftResult = pointWeedingDouglas(leftPoints, epsilon);
            ArrayList<MyPoint> rightResult = pointWeedingDouglas(rightPoints, epsilon);

            rightResult.remove(0);
            leftResult.addAll(rightResult);
            result = leftResult;
        } else {
            result.add(points.get(0));
            result.add(points.get(end - 1));
        }
        return result;
    }

    public ArrayList<MyPoint> simpleGeometrySmooth(ArrayList<MyPoint> points, double threshold) {
        ArrayList<MyPoint> rs = new ArrayList<MyPoint>();
        Geometry geometry = gb.createGeometry(points);
        Geometry smooth = JTS.smooth(geometry, threshold);
        for (int i = 0; i < smooth.getCoordinates().length; i++) {
            rs.add(new MyPoint(smooth.getCoordinates()[i].x, smooth.getCoordinates()[i].y));
        }
        return rs;
    }

    public double lineBending(ArrayList<MyPoint> points) {
        double rs = 0;
        double perimeter = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            perimeter += dc.euclideanDistance(points.get(i), points.get(i + 1));
        }
        double l = dc.euclideanDistance(points.get(0),points.get(points.size() - 1));
        rs = perimeter / l;
        return rs;
    }

    public ArrayList<MyPoint> getPolygonConvexHull(MyPolygon polygon) {
        Geometry geometry = gb.createGeometry(polygon.getPolygonPoints());
        Geometry convexHull = geometry.convexHull();
        ArrayList<MyPoint> rs = new ArrayList<MyPoint>();
        for (int i = 0; i < convexHull.getCoordinates().length; i++) {
            rs.add(new MyPoint(convexHull.getCoordinates()[i].x, convexHull.getCoordinates()[i].y));
        }
        return rs;
    }

    public ArrayList<MyPoint> getPolygonConvexHull2(ArrayList<MyPoint> points) {
        ArrayList<MyPoint> rs = new ArrayList<MyPoint>();
        //最大y的点
        MyPoint maxYPoint = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).getY() > maxYPoint.getY()) {
                maxYPoint = points.get(i);
            }
        }
        ArrayList<MyPoint> _points = new ArrayList<MyPoint>(points);
        _points.remove(maxYPoint);
        //坐标点余弦值点集
        Map<Double, MyPoint> cosMap = new HashMap<Double, MyPoint>();
        List<Double> tempCos = new ArrayList<Double>();
        for (MyPoint point : _points) {
            double c = dc.euclideanDistance(maxYPoint, point);
            double b = maxYPoint.getY() - point.getY();
            double a = Math.sqrt(c * c - b * b);
            double t = (a * a + c * c - b * b) / (2 * a * c);
            double cos = Math.toDegrees(Math.acos(t));
            if (point.getX() < maxYPoint.getX()) {
                cos = 180 - cos;
            }
            cosMap.put(cos, point);
            tempCos.add(cos);
        }
        //采用冒泡排序对余弦值cos排序
        for (int i = 0; i < tempCos.size() - 1; i++) {
            for (int j = 0; j < tempCos.size() - i - 1; j++) {
                if (tempCos.get(j) > tempCos.get(j + 1)) {
                    Collections.swap(tempCos, j, j + 1);
                }
            }
        }
        //排序后的点集
        ArrayList<MyPoint> sortPoints = new ArrayList<MyPoint>();
        for (Double tempCo : tempCos) {
            sortPoints.add(cosMap.get(tempCo));
        }
        rs.add(maxYPoint);
        //Graham扫描法
        for (int i = 0; i < sortPoints.size(); i++) {
            if (i == 0 || i == 1) {
                rs.add(sortPoints.get(i));
            } else {
                MyPoint point = sortPoints.get(i);
                ArrayList<MyPoint> tempList = new ArrayList<MyPoint>();
                tempList.add(point);
                tempList.add(rs.get(rs.size()-2));
                tempList.add(rs.get(rs.size()-1));
                double area = vc.vectorSpaceCal(tempList);
                if (area > 0) {
                    rs.remove(rs.get(rs.size() - 1));
                    rs.add(point);
                } else {
                    rs.add(point);
                }
            }
            while (rs.size()>=3) {
                ArrayList<MyPoint> tempList = new ArrayList<MyPoint>();
                tempList.add(rs.get(rs.size() - 1));
                tempList.add(rs.get(rs.size() - 2));
                tempList.add(rs.get(rs.size() - 3));
                double area = vc.vectorSpaceCal(tempList);
                if (area > 0) {
                    rs.remove(rs.get(rs.size() - 2));
                } else {
                    break;
                }
            }

        }
        return rs;
    }

    public double getP1A(MyPolygon polygon) {
        double rs = 0;
        Polygon polygon1 = tf.PolygonTrans(polygon);
        rs = polygon1.getLength() / polygon1.getArea();
        return rs;
    }

    public double getP2A(MyPolygon polygon) {
        double rs = 0;
        Polygon polygon1 = tf.PolygonTrans(polygon);
        rs = Math.pow(polygon1.getLength(), 2) / polygon1.getArea();
        return rs;
    }

    public double getPolygonMaxAxis(MyPolygon polygon) {
        double max = 0;
        for(int i =0;i<polygon.getPolygonPoints().size();i++) {
            for(int j = i+1;j<polygon.getPolygonPoints().size();j++) {
                max = Math.max(max, dc.euclideanDistance(polygon.getPolygonPoints().get(i), polygon.getPolygonPoints().get(j)));
            }
        }
        return max;
    }

    public double getPolygonMinAxis(MyPolygon polygon) {
        double min = 0;
        min = dc.euclideanDistance(polygon.getPolygonPoints().get(0), polygon.getPolygonPoints().get(1));
        for(int i =0;i<polygon.getPolygonPoints().size();i++) {
            for(int j = i+1;j<polygon.getPolygonPoints().size();j++) {
                min = Math.min(min, dc.euclideanDistance(polygon.getPolygonPoints().get(i), polygon.getPolygonPoints().get(j)));
            }
        }
        return min;
    }

    public double getPolygonFormRatio(MyPolygon polygon) {
        double rs = 0;
        Polygon polygon1 = tf.PolygonTrans(polygon);
        rs = polygon1.getArea() / getPolygonMaxAxis(polygon);
        return rs;
    }

    public double getPolygonElongationRational(MyPolygon polygon) {
        return getPolygonMaxAxis(polygon) / getPolygonMinAxis(polygon);
    }

    public double getPolygonCompactness(MyPolygon polygon) {
        double rs = 0;
        Polygon polygon1 = tf.PolygonTrans(polygon);
        rs = polygon1.getLength() / (2 * Math.sqrt(Math.PI * polygon1.getArea()));
        return rs;
    }

    public double getPolygonShapeIndex(MyPolygon polygon) {
        double rs = 0;
        Polygon polygon1 = tf.PolygonTrans(polygon);
        double r = polygon1.getLength() / (2 * Math.PI);
        rs = Math.sqrt(polygon1.getArea() / (Math.PI * Math.pow(r, 2)));
        return rs;
    }

    public double getPolygonRelateBoundingFigure(MyPolygon polygon) {
        double rs = 0;
        Polygon polygon1 = tf.PolygonTrans(polygon);
        double r = polygon1.getLength() / (2 * Math.PI);
        rs = 1 - polygon1.getArea() / (Math.PI * Math.pow(r, 2));
        return rs;
    }
}
