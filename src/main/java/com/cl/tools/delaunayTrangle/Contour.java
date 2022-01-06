package com.cl.tools.delaunayTrangle;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyTriangle;
import com.cl.tools.Transform;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import com.cl.tools.spatialRelation.SpatialRelation;
import com.cl.tools.spatialRelation.SpatialRelationImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Contour {
    Transform tf = new Transform();
    DistanceCal dc = new DistanceCalImpl();
    SpatialRelation sr = new SpatialRelationImpl();
    Random rand = new Random();


    public ArrayList<MyPoint> initContour(CreateDelaunay cd, double threshold) {
        ArrayList<MyTriangle> triangle_list = new ArrayList<MyTriangle>(cd.getTriangle_list());
        ArrayList<MyLine> line_list = new ArrayList<MyLine>();
        ArrayList<MyPoint> point_list = new ArrayList<MyPoint>();

        //将三角形数组的边存入边数组
        for (MyTriangle myTriangle : triangle_list) {
            ArrayList<MyPoint> points = myTriangle.getPoints();
            line_list.add(new MyLine(points.get(0), points.get(1)));
            line_list.add(new MyLine(points.get(1), points.get(2)));
            line_list.add(new MyLine(points.get(0), points.get(2)));
        }
        //除去重复的线
        line_list = getUniqueList(line_list);

        //判断每一条线上两点的高程差与阈值关系
        for (MyLine line : line_list) {
            MyPoint start = line.getStartPoint();
            MyPoint end = line.getEndPoint();
            start.setZ(rand.nextInt(200));
            end.setZ(rand.nextInt(200));
            boolean flag = false;
            double dH = start.getZ() - end.getZ();
            double dX = start.getX() - end.getX();
            double dY = start.getY() - end.getY();
            double h = start.getZ() - threshold;
            if (start.getZ() > end.getZ()) {
                if (threshold >= end.getZ() && threshold <= start.getZ()) {
                    flag = true;
                }
            } else if (start.getZ() < end.getZ()) {
                if(threshold >= start.getZ() && threshold <= end.getZ()) {
                    flag = true;
                }
            }
            if (flag) {
                //说明该线上有等值点
                //计算该点的X和Y
                double x = start.getX() - (dX * h) / dH;
                double y = start.getY() - (dY * h) / dH;
                point_list.add(new MyPoint(x, y, threshold));
            }
        }

        return point_list;
    }
    public static ArrayList getUniqueList(ArrayList al) {
        ArrayList tempAl = new ArrayList();

        Iterator it = al.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (!tempAl.contains(obj)) //不存在则添加
            {
                tempAl.add(obj);
            }
        }
        return tempAl;
    }
}
