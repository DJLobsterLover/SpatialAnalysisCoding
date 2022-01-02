package com.cl.tools.vectorSpaceCal;

import com.cl.pojo.MyPoint;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DJLobster
 */
public class VectorSpaceCalImpl implements VectorSpaceCal{
    public  double vectorSpaceCal(ArrayList<MyPoint> points){
        double rs = 0;
        int n = points.size();
        for (int i = 1; i < n - 1; i++) {
            rs += 1.0/2 * ((points.get(i+1).getX() * points.get(i).getY())
                    - (points.get(i).getX() * points.get(i+1).getY()));
        }
        rs = Math.abs(rs);
        return rs;

    }

    public double terrestrialSufArea(MyPoint p1, MyPoint p2, double R) {
        double rs = 0;
        double lat1 = p1.getY() * Math.PI / 180;
        double lat2 = p2.getY() * Math.PI / 180;
        double A1 = 2 * Math.PI * (1 - Math.sin(lat1)) * Math.pow(R, 2);
        double A2 = 2 * Math.PI * (1 - Math.sin(lat2)) * Math.pow(R, 2);
        rs = Math.abs(A1 - A2) * Math.abs(p1.getX() - p2.getX()) / 360;
        return rs;
    }

    public double triangleArea(ArrayList<MyPoint> tempList) {
        double rs = 0;
        DistanceCal dc = new DistanceCalImpl();
        double a = dc.euclideanDistance(tempList.get(0), tempList.get(1));
        double b = dc.euclideanDistance(tempList.get(1), tempList.get(2));
        double c = dc.euclideanDistance(tempList.get(0), tempList.get(2));
        double p = (a + b + c) / 2;
        rs = Math.sqrt(p * (p - a) * (p - b) * (p - c));
        return rs;
    }
}
