package com.cl.tools.vectorSpaceCal;

import com.cl.pojo.Point;

import java.util.List;

/**
 * @author DJLobster
 */
public class VectorSpaceCalImpl implements VectorSpaceCal{
    public  double vectorSpaceCal(List<Point> points){
        double rs = 0;
        int n = points.size();
        for (int i = 1; i < n - 1; i++) {
            rs += 1.0/2 * ((points.get(i+1).getX() * points.get(i).getY())
                    - (points.get(i).getX() * points.get(i+1).getY()));
        }
        return rs;

    }
}
