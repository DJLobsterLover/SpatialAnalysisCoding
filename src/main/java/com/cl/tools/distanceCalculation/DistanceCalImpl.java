package com.cl.tools.distanceCalculation;

import com.cl.pojo.Point;
//import com.vividsolutions.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DJLobster
 */
public class DistanceCalImpl implements DistanceCal{
    public double euclideanDistance(Point startPoint, Point endPoint) {
        double rs = 0.0;
        rs += Math.pow(startPoint.getX()-endPoint.getX(),2);
        rs += Math.pow(startPoint.getY()-endPoint.getY(),2);
        rs += Math.pow(startPoint.getZ()-endPoint.getZ(),2);
        rs = Math.sqrt(rs);
        return rs;
    }

    public double manhattanDistance(Point startPoint, Point endPoint) {
        double rs = 0.0;
        rs += Math.abs(startPoint.getX() - endPoint.getX());
        rs += Math.abs(startPoint.getY() - endPoint.getY());
        rs += Math.abs(startPoint.getZ() - endPoint.getZ());
        return rs;
    }

    public double chebyshevDistance(Point startPoint, Point endPoint) {
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

    public double minkowskiDistance(Point startPoint, Point endPoint ,double m) {
        double rs = 0.0;
        rs += Math.pow(Math.abs(startPoint.getX() - endPoint.getX()),m);
        rs += Math.pow(Math.abs(startPoint.getY() - endPoint.getY()),m);
        rs += Math.pow(Math.abs(startPoint.getZ() - endPoint.getZ()),m);
        rs = Math.pow(rs, 1/m);
        return rs;
    }

    public double mahalanobisDistance(Point startPoint, Point endPoint) {
        double rs = 0.0;

        return rs;
    }

    public double sphericalDistance(Point startPoint, Point endPoint, double R) {
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

    public double sphericalDistanceHaversine(Point startPoint, Point endPoint, double R) {
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
}
