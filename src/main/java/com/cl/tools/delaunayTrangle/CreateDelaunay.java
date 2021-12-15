package com.cl.tools.delaunayTrangle;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.pojo.MyTriangle;
import com.cl.tools.GeometryBuilder;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import com.cl.tools.spatialRelation.SpatialRelation;
import com.cl.tools.spatialRelation.SpatialRelationImpl;
import com.cl.tools.vectorSpaceCal.VectorSpaceCal;
import com.cl.tools.vectorSpaceCal.VectorSpaceCalImpl;
import org.locationtech.jts.algorithm.MinimumBoundingCircle;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.triangulate.ConformingDelaunayTriangulationBuilder;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author DJLobster
 */
public class CreateDelaunay {
    private ArrayList<MyPoint> points;
    private ArrayList<MyPoint> hullPoints;
    private ArrayList<MyTriangle> triangle_list;
    SpatialRelation sr = new SpatialRelationImpl();
    public CreateDelaunay(ArrayList<MyPoint> points) {
        this.points = points;
        hullPoints = new ArrayList<MyPoint>();
        triangle_list = new ArrayList<MyTriangle>();
    }

    public CreateDelaunay() {
    }

    public ArrayList<MyPoint> getPoints() {
        return points;
    }

    public ArrayList<MyPoint> getHullPoints() {
        return hullPoints;
    }

    public ArrayList<MyTriangle> getTriangle_list() {
        return triangle_list;
    }

    public void initDelaunay() {
        GeometryBuilder gb =new GeometryBuilder();
        DistanceCal dc = new DistanceCalImpl();
        ArrayList<MyTriangle> _tempTriangles;
        ArrayList<MyLine> edge_buffer = new ArrayList<MyLine>();
        if (points != null) {
            //对点集进行冒泡排序x
            maoPaoSort(points);
            //获得最大凸包的点
            MyPolygon tempPolygon = new MyPolygon(points);
            hullPoints = sr.getPolygonConvexHull(tempPolygon);
            hullPoints = getUniqueList(hullPoints);
            //初始化三角网
// TODO: 2021/11/3  在points移除掉最大凸包点
            ArrayList<MyPoint> _points = new ArrayList<MyPoint>(points);
            for (int i = 0; i < hullPoints.size(); i++) {
                for(int j = _points.size() - 1; j >= 0; j--) {
                    if (hullPoints.get(i).getX() == _points.get(j).getX()
                            && hullPoints.get(i).getY() == _points.get(j).getY()) {
                        _points.remove(j);
                    }
                }
            }
            //
            ArrayList<MyTriangle> tempTriangles = new ArrayList<MyTriangle>();
            for (MyPoint point : _points) {
                if (tempTriangles.size() == 0) {
                    for (int i = 0; i < hullPoints.size(); i++) {
                        int k = i + 1;
                        if (k >= hullPoints.size()) {
                            k = 0;
                        }
                        //将三角网添加
                        ArrayList<MyPoint> tempPoints = new ArrayList<MyPoint>();
                        tempPoints.add(hullPoints.get(i));
                        tempPoints.add(hullPoints.get(k));
                        tempPoints.add(point);
                        MyTriangle triangle = new MyTriangle(tempPoints);
                        tempTriangles.add(triangle);
                    }
                    continue;
                }
                _tempTriangles = new ArrayList<MyTriangle>(tempTriangles);
                //清空edge_buffer
                edge_buffer.clear();
                for (MyTriangle tempTriangle : _tempTriangles) {
                    double[] circle1 = get_circle(tempTriangle.getPoints().get(0), tempTriangle.getPoints().get(1), tempTriangle.getPoints().get(2));
                    Geometry geometry = gb.createGeometry(tempTriangle.getPoints());
                    MinimumBoundingCircle circle = new MinimumBoundingCircle(geometry);
                    double distance = dc.euclideanDistance(point,new MyPoint(circle1[0],circle1[1]));
                    //点在圆内
                    if (distance <= circle1[2]) {
                        //将三边添加进edge_buffer
                        edge_buffer.add(new MyLine(tempTriangle.getPoints().get(0), tempTriangle.getPoints().get(1)));
                        edge_buffer.add(new MyLine(tempTriangle.getPoints().get(1), tempTriangle.getPoints().get(2)));
                        edge_buffer.add(new MyLine(tempTriangle.getPoints().get(2), tempTriangle.getPoints().get(0)));
                        //在tem_triangle 中移除
                        tempTriangles.remove(tempTriangle);
                        continue;
                    }
                    if (distance > circle1[2] && (circle1[2] + circle1[0]) > point.getX())
                        continue;
                    //点在圆的右侧
                    if (distance > circle1[2] && (circle1[2] + circle1[0]) <= point.getX()) {
                        //添加到 delaunay triangl
                        triangle_list.add(tempTriangle);
                        //在 tem_triangle 中移除
                        tempTriangles.remove(tempTriangle);
                        continue;
                    }
                }
                //处理重复边
//                ArrayList<MyLine> duplicated = new ArrayList<MyLine>();
//                //每条边的坐标点集
//                ArrayList<MyPoint> buffer_points = new ArrayList<MyPoint>();
//                for (MyLine edge : edge_buffer) {
//                    buffer_points.add(edge.getStartPoint());
//                    buffer_points.add(edge.getEndPoint());
//                }
                // TODO: 2021/11/4 找到重复边，去掉重复的边，重复的一条也不留
//                edge_buffer = getUniqueList(edge_buffer);
                ArrayList<MyLine> duplicated = new ArrayList<MyLine>();
                for (int i = 0; i < edge_buffer.size(); i++) {
                    for (int j = i + 1; j < edge_buffer.size(); j++) {
                        if(edge_buffer.get(i).equals(edge_buffer.get(j))) {
                            duplicated.add(edge_buffer.get(i));
                        }
                    }
                }
                for (int i = 0; i < duplicated.size(); i++) {
                    for (int j = edge_buffer.size() - 1; j >= 0; j--) {
                        if(edge_buffer.get(j).equals(duplicated.get(i))) {
                            edge_buffer.remove(j);
                        }
                    }
                }
                // 组成三个三角形，加入到temp triangles中
                for (MyLine line : edge_buffer) {
                    ArrayList<MyPoint> tempPoints = new ArrayList<MyPoint>();
                    tempPoints.add(line.getStartPoint());
                    tempPoints.add(line.getEndPoint());
                    tempPoints.add(point);
                    MyTriangle triangle = new MyTriangle(tempPoints);
                    tempTriangles.add(triangle);
                }
            }
            //temp_triangle_list 和 triangle_list 合并
            for (MyTriangle tempTriangle : tempTriangles) {
                triangle_list.add(tempTriangle);
            }
        }
    }

    public void maoPaoSort(ArrayList<MyPoint> points){
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = 0; j < points.size() - i - 1; j++) {
                if(points.get(j).getX()>points.get(j+1).getX())
                {
                    MyPoint tempPoint = points.get(j);
                    points.set(j,points.get(j + 1));
                    points.set(j + 1, tempPoint);
                }
            }
        }
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

    double[] get_circle(MyPoint p1, MyPoint p2, MyPoint p3) {
        double x1 = p1.getX();
        double x2 = p2.getX();
        double x3 = p3.getX();
        double y1 = p1.getY();
        double y2 = p2.getY();
        double y3 = p3.getY();
        double a = ((y2 - y1) * (y3 * y3 - y1 * y1 + x3 * x3 - x1 * x1) - (y3 - y1) * (y2 * y2 - y1 * y1 + x2 * x2 - x1 * x1)) / (
                2.0 * ((x3 - x1) * (y2 - y1) - (x2 - x1) * (y3 - y1)));
        double b = ((x2 - x1) * (x3 * x3 - x1 * x1 + y3 * y3 - y1 * y1) - (x3 - x1) * (x2 * x2 - x1 * x1 + y2 * y2 - y1 * y1)) / (
                2.0 * ((y3 - y1) * (x2 - x1) - (y2 - y1) * (x3 - x1)));
        double r = Math.sqrt((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b));
        double[] rs = {a, b, r};
        return rs;
    }

    public double getDemArea() {
        double rs = 0;
        VectorSpaceCal vs = new VectorSpaceCalImpl();
        if (triangle_list != null) {
            for (MyTriangle myTriangle : triangle_list) {
                rs += vs.vectorSpaceCal(myTriangle.getPoints());
            }
        }
        return rs;
    }

}
