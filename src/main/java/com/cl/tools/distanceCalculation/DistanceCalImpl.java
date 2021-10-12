package com.cl.tools.distanceCalculation;

import com.cl.pojo.Point;

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
}
