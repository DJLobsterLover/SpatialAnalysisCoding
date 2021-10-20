package com.cl.tools.distanceCalculation;

import Jama.Matrix;
import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.GeometryBuilder;
import com.cl.tools.Transform;
import com.mysql.jdbc.StringUtils;
import org.locationtech.jts.algorithm.MinimumBoundingCircle;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
//import org.locationtech.jts.geom.Point;

import java.util.ArrayList;

/**
 * @author DJLobster
 */
public class DistanceCalImpl implements DistanceCal{
    private Transform tf = new Transform();
    private GeometryBuilder gb = new GeometryBuilder();

    public double euclideanDistance(MyPoint startPoint, MyPoint endPoint) {
        double rs = 0.0;
        rs += Math.pow(startPoint.getX()-endPoint.getX(),2);
        rs += Math.pow(startPoint.getY()-endPoint.getY(),2);
        rs += Math.pow(startPoint.getZ()-endPoint.getZ(),2);
        rs = Math.sqrt(rs);
        return rs;
    }

    public double manhattanDistance(MyPoint startPoint, MyPoint endPoint) {
        double rs = 0.0;
        rs += Math.abs(startPoint.getX() - endPoint.getX());
        rs += Math.abs(startPoint.getY() - endPoint.getY());
        rs += Math.abs(startPoint.getZ() - endPoint.getZ());
        return rs;
    }

    public double chebyshevDistance(MyPoint startPoint, MyPoint endPoint) {
        double rs = 0.0;
        ArrayList list = new ArrayList();
        list.add(Math.abs(startPoint.getX() - endPoint.getX()));
        list.add(Math.abs(startPoint.getY() - endPoint.getY()));
        list.add(Math.abs(startPoint.getZ() - endPoint.getZ()));
        for (int i = 0; i < list.size(); i++) {
            rs = Math.max(Double.parseDouble(String.valueOf(list.get(i))),rs);
        }
        return rs;
    }

    public double minkowskiDistance(MyPoint startPoint, MyPoint endPoint , double m) {
        double rs = 0.0;
        rs += Math.pow(Math.abs(startPoint.getX() - endPoint.getX()),m);
        rs += Math.pow(Math.abs(startPoint.getY() - endPoint.getY()),m);
        rs += Math.pow(Math.abs(startPoint.getZ() - endPoint.getZ()),m);
        rs = Math.pow(rs, 1/m);
        return rs;
    }

    public double mahalanobisDistance(ArrayList<MyPoint> points) {
        double[] His1 = new double[points.size()];
        double[] His2 = new double[points.size()];
        double[] u = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            His1[i] = points.get(i).getX();
            His2[i] = points.get(i).getY();
            u[i] = (His1[i] + His2[i]) / 2;
        }
        //计算协方差矩阵
        int temp = points.size();
        double[][] S = new double[temp][temp];
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                S[i][j] = ((His1[i] - u[i]) * (His1[j] - u[j]) + (His2[i] - u[i]) * (His2[j] - u[j])) / 2 ;
            }
        }
        //求S的逆矩阵
        Matrix S_mat = new Matrix(S);
        System.out.println(S_mat.rank());
//        S_mat.rank
        Matrix S_inverse = S_mat.inverse();
//        double[][] S_inverse = inverse.getArray();
//        Hist1 - Hist2
        double[][] H1rH2 = new double[1][points.size()];
        for (int i = 0; i < points.size(); i++) {
            H1rH2[0][i] = His1[i] - His2[i];
        }
        Matrix H1rH2_mat = new Matrix(H1rH2);
        //Hist1 - Hist2倒置
        Matrix H1rH2_mat_transpose = H1rH2_mat.transpose();
        double v = H1rH2_mat.times(S_inverse).times(H1rH2_mat_transpose).get(0, 0);
        return v;
    }

    public double sphericalDistance(MyPoint startPoint, MyPoint endPoint, double R) {
        //设x为经度，y为纬度
        double rs = 0.0;
        //经纬度转换弧度制
        double long1 = startPoint.getX() * Math.PI / 180 ;
        double long2 = endPoint.getX() * Math.PI / 180 ;
        double lat1 = startPoint.getY() * Math.PI / 180;
        double lat2 = endPoint.getY() * Math.PI / 180;
        rs = R * Math.acos(Math.cos(lat1) * Math.cos(lat2) *
                Math.cos(long1 - long2) + Math.sin(lat1) * Math.sin(lat2));
        return rs;
    }

    public double sphericalDistanceHaversine(MyPoint startPoint, MyPoint endPoint, double R) {
        double rs = 0.0;
        //经纬度转换弧度制
        double long1 = startPoint.getX() * Math.PI / 180 ;
        double long2 = endPoint.getX() * Math.PI / 180 ;
        double lat1 = startPoint.getY() * Math.PI / 180;
        double lat2 = endPoint.getY() * Math.PI / 180;
        double A = (lat1 - lat2) / 2;
        double B = (long1 - long2) / 2;
        rs = 2 * R * Math.asin(Math.sqrt(Math.pow(Math.sin(A), 2) + Math.pow(Math.sin(B), 2) *
                Math.cos(lat1) * Math.cos(lat2)));
        return rs;
    }

    public double pointToLineDistance(MyPoint point, MyLine line, String type) {
        double rs = 0;
        if ((StringUtils.isNullOrEmpty(type))) {
            //求点到线的最大距离
            rs = Math.min(euclideanDistance(point, line.getStartPoint()),euclideanDistance(point,line.getEndPoint()));
        } else if (type.equals("max")) {
            //求点到直线的最小距离
            rs = Math.max(euclideanDistance(point, line.getStartPoint()), euclideanDistance(point, line.getEndPoint()));
        } else {
            rs = Math.min(euclideanDistance(point, line.getStartPoint()),euclideanDistance(point,line.getEndPoint()));
        }
        return rs;
    }

    public double pointToPolygonDistance(MyPoint p, MyPolygon polygon, String type) {
        double rs = 0;
        try {
            rs = euclideanDistance(p, polygon.getPolygonPoints().get(0));
            if (StringUtils.isNullOrEmpty(type) || type.equals("min")) {
                //如果为空，默认为最小距离
                for (int i = 1; i < polygon.getPolygonPoints().size(); i++) {
                    rs = Math.min(rs, euclideanDistance(p, polygon.getPolygonPoints().get(i)));
                }
            } else if (type.equals("max")) {
                for (int i = 1; i < polygon.getPolygonPoints().size(); i++) {
                    rs = Math.max(rs, euclideanDistance(p, polygon.getPolygonPoints().get(i)));
                }
            } else if (type.equals("center")){
                Polygon tempPolygon = tf.PolygonTrans(polygon);
                MyPoint tempPoint = tf.PointTransBack(tempPolygon.getCentroid());
                rs = euclideanDistance(p,tempPoint);
            } else {
                System.out.println("请输入max Or min Or center");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public double lineToLineDistance(MyLine l1, MyLine l2) {
        double rs = 0;
        rs = euclideanDistance(l1.getStartPoint(), l2.getStartPoint());
        rs = Math.min(rs , euclideanDistance(l1.getStartPoint(),l2.getEndPoint()));
        rs = Math.min(rs , euclideanDistance(l1.getEndPoint(),l2.getStartPoint()));
        rs = Math.min(rs , euclideanDistance(l1.getEndPoint(),l2.getEndPoint()));
        return rs;
    }

    public double lineToPolygonDistance(MyLine l1, MyPolygon polygon) {
        double rs = 0;
        rs = euclideanDistance(l1.getStartPoint(),polygon.getPolygonPoints().get(0));
        for (int i = 1; i < polygon.getPolygonPoints().size(); i++) {
            rs = Math.min(rs, euclideanDistance(l1.getStartPoint(),polygon.getPolygonPoints().get(i)));
        }
        for (int i = 0; i < polygon.getPolygonPoints().size(); i++) {
            rs = Math.min(rs, euclideanDistance(l1.getEndPoint(),polygon.getPolygonPoints().get(i)));
        }
        return rs;
    }

    public double polygonToPolygonDistance(MyPolygon polygon1, MyPolygon polygon2, String type) {
        double rs = 0;
        if (StringUtils.isNullOrEmpty(type)) {
            System.out.println("请输入一种类型[min、max、center】");
        } else if (type.equals("center")) {
            Polygon tempPolygon1 = tf.PolygonTrans(polygon1);
            Polygon tempPolygon2 = tf.PolygonTrans(polygon2);
            rs = euclideanDistance(tf.PointTransBack(tempPolygon1.getCentroid()),tf.PointTransBack(tempPolygon2.getCentroid()));
        } else if (type.equals("max")) {
            for (int i = 0; i < polygon1.getPolygonPoints().size(); i++) {
                for (int j = 0; j < polygon2.getPolygonPoints().size(); j++) {
                    rs = Math.max(rs, euclideanDistance(polygon1.getPolygonPoints().get(i), polygon2.getPolygonPoints().get(j)));
                }
            }
        } else if (type.equals("min")) {
            rs = euclideanDistance(polygon1.getPolygonPoints().get(0), polygon2.getPolygonPoints().get(0));
            for (int i = 0; i < polygon1.getPolygonPoints().size(); i++) {
                for (int j = 0; j < polygon2.getPolygonPoints().size(); j++) {
                    rs = Math.min(rs, euclideanDistance(polygon1.getPolygonPoints().get(i), polygon2.getPolygonPoints().get(j)));
                }
            }
        } else {
            System.out.println("请输入一种类型[min、max、center】");
        }
        return rs;
    }

    public MyPoint getPolygonCentroid(MyPolygon polygon) {
        Polygon tempPolygon = tf.PolygonTrans(polygon);
        return tf.PointTransBack(tempPolygon.getCentroid());
    }

    public MyPoint getPolygonInteriorPoint(MyPolygon polygon) {
        Polygon tempPolygon = tf.PolygonTrans(polygon);
        return tf.PointTransBack(tempPolygon.getInteriorPoint());
    }

    public MyPoint getPolygonCenter(MyPolygon polygon) {
        double x = 0;
        double y = 0;
        for (int i = 0; i < polygon.getPolygonPoints().size(); i++) {
            x += polygon.getPolygonPoints().get(i).getX();
        }
        for (int i = 0; i < polygon.getPolygonPoints().size(); i++) {
            y += polygon.getPolygonPoints().get(i).getY();
        }
        x = x / polygon.getPolygonPoints().size();
        y = y / polygon.getPolygonPoints().size();
        MyPoint p = new MyPoint(x, y);
        return p;
    }

    public MyPoint getPolygonExternal(MyPolygon polygon) {
        MinimumBoundingCircle circle = new MinimumBoundingCircle(tf.PolygonTrans(polygon));
        Coordinate co = circle.getCentre();
        MyPoint myPoint = tf.PointTransBack(gb.createPoint(co.x, co.y));
        return myPoint;
    }

    public double getPolygonExternalCircle(MyPolygon polygon) {
        MinimumBoundingCircle circle = new MinimumBoundingCircle(tf.PolygonTrans(polygon));
        return circle.getRadius();
    }
}
