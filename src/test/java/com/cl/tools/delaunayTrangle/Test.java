package com.cl.tools.delaunayTrangle;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyTriangle;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiLineString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Test {
    public static void main(String[] args) {
//        CreateDelaunay cd = new CreateDelaunay();
//        GeometryFactory gf = new GeometryFactory();
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(0, 1));
        points.add(new MyPoint(1, 0));
        points.add(new MyPoint(2, 1));
        points.add(new MyPoint(1, 3));
        points.add(new MyPoint(0, 1));
        points.add(new MyPoint(1, 4));
        //        Geometry delaunay = cd.createDelaunay(points);
//        MultiLineString ms = (MultiLineString) delaunay;
//        System.out.println(ms.getGeometryType());
        points = getUniqueList(points);
        ArrayList<MyPoint> point1 = new ArrayList<MyPoint>(points);
//        for (MyPoint point : point1) {
//            points.remove(point);
//        }
        for (int i = 0; i < points.size(); i++) {
            System.out.println(points.get(i).getX());
        }
    }

    @org.junit.Test
    public void test() {
        MyLine l1 = new MyLine(new MyPoint(0, 1), new MyPoint(0, 0));
        MyLine l2 = new MyLine(new MyPoint(0, 2), new MyPoint(0, 0));
        System.out.println(l1.equals(l2));
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

    @org.junit.Test
    public void Test1() {
//        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
//        points.add(new MyPoint(0, 1));
//        points.add(new MyPoint(1, 0));
//        points.add(new MyPoint(2, 1));
//        points.add(new MyPoint(1, 3));
//        points.add(new MyPoint(1, 1));
//        points.add(new MyPoint(2, 0));
//        points.add(new MyPoint(1, 0.5));
//        CreateDelaunay cd = new CreateDelaunay(points);
//        cd.initDelaunay();
//        System.out.println(cd.getHullPoints().size());
//        System.out.println(cd.getTriangle_list().size());
////        for (MyTriangle myTriangle : cd.getTriangle_list()) {
////            System.out.println(myTriangle.getPoints().get(0).getX()+" "+myTriangle.getPoints().get(0).getY());
////            System.out.println(myTriangle.getPoints().get(1).getX()+" "+myTriangle.getPoints().get(1).getY());
////            System.out.println(myTriangle.getPoints().get(2).getX() + " " + myTriangle.getPoints().get(2).getY());
////            System.out.println("=========================");
////        }
//        for (MyPoint hullPoint : cd.getHullPoints()) {
//            System.out.println(hullPoint.getX() + "  " + hullPoint.getY());
//        }
        double a = 3;
        System.out.println(Math.toDegrees(3));
    }
}


