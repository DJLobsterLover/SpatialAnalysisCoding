package com.cl.tools.delaunayTrangle;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.pojo.MyTriangle;
import com.cl.tools.Transform;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import com.cl.tools.spatialRelation.SpatialRelation;
import com.cl.tools.spatialRelation.SpatialRelationImpl;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;

import java.util.ArrayList;
import java.util.Iterator;

public class LineConstraintDelaunay {
    Transform tf = new Transform();
    DistanceCal dc = new DistanceCalImpl();
    SpatialRelation sr = new SpatialRelationImpl();
    public ArrayList<MyTriangle> LineConstraint(CreateDelaunay cd, ArrayList<MyPoint> points) {
        ArrayList<MyTriangle> lineConstraintTriangles = new ArrayList<MyTriangle>(cd.getTriangle_list());
        ArrayList<MyTriangle> crossTriangles = new ArrayList<MyTriangle>();
        ArrayList<MyPoint> reDelaunayPoints = new ArrayList<MyPoint>();
        //狄洛尼线约束
        if (cd.getTriangle_list() == null) {
            return lineConstraintTriangles;
        } else {
            //寻找与线相交的三角形
            for (int i = 0; i < points.size() - 1; i++) {
                MyLine tempLine = new MyLine(points.get(i), points.get(i + 1));
                LineString lineString = tf.LineTrans(tempLine);
                for (int j = 0; j < cd.getTriangle_list().size(); j++) {
                    //判断三角形是否与直线相交
                    Polygon tempTriangle = tf.PolygonTrans(cd.getTriangle_list().get(j));
                    if (tempTriangle.crosses(lineString)) {
                        System.out.println(lineString.crosses(tempTriangle));
                        //与线相交
                        crossTriangles.add(cd.getTriangle_list().get(j));
                    } else {
                        System.out.println(false);
                    }
                }
            }
            //原三角网移除掉相交的三角形
            for (int i = 0; i < crossTriangles.size(); i++) {
                lineConstraintTriangles.remove(crossTriangles.get(i));
            }
            //将移除掉的三角形的点添加至reDelaunayPoints，重新生成delaunay三角网
            for(int i = 0; i < crossTriangles.size(); i++) {
                for (int j = 0; j < crossTriangles.get(i).getPoints().size(); j++) {
                    reDelaunayPoints.add(crossTriangles.get(i).getPoints().get(j));
                }
            }
            //将重复的点去掉
            ArrayList<MyPoint> uniqueList = getUniqueList(reDelaunayPoints);
            uniqueList = sr.getPolygonConvexHull(new MyPolygon(uniqueList));

            if(uniqueList.size() > 0) {
                int tempP = 0;
                double min = 999999999;
                for (int i = 0; i < uniqueList.size() - 1; i++) {
                    min = 999999999;
                    for (int j = 0; j < points.size(); j++) {
                        //getUniqueList两点组成线
//                        double tempMin = sr.pointToLine(tf.PointTrans(points.get(j)),tf.LineTrans(new MyLine(uniqueList.get(i), uniqueList.get((i + 1)))));
                        double tempMin = dc.pointToLineDistance(points.get(j), new MyLine(uniqueList.get(i), uniqueList.get(i + 1)), "max");
                        if (tempMin < min) {
                            min = tempMin;
                            tempP = j;
                        }
                    }
                    ArrayList<MyPoint> tempPoints = new ArrayList<MyPoint>();
                    tempPoints.add(points.get(tempP));
                    tempPoints.add(uniqueList.get(i));
                    tempPoints.add(uniqueList.get(i + 1));
                    lineConstraintTriangles.add(new MyTriangle(tempPoints));
                }

                tempP = 0;
                min = 999999999;
                for (int i = 0; i < points.size() - 1; i++) {
                    min = 999999999;
                    for (int j = 0; j < uniqueList.size(); j++) {
//                            double tempMin = sr.pointToLine(tf.PointTrans(uniqueList.get(j)),tf.LineTrans(new MyLine(points.get(i), points.get((i + 1)))));
                            double tempMin = dc.pointToLineDistance(uniqueList.get(j), new MyLine(points.get(i), points.get((i + 1))), "max");
                        if (tempMin < min) {
                            min = tempMin;
                            tempP = j;
                        }
                    }
                    ArrayList<MyPoint> tempPoints = new ArrayList<MyPoint>();
                    tempPoints.add(uniqueList.get(tempP));
                    tempPoints.add(points.get(i));
                    tempPoints.add(points.get(i + 1));
                    lineConstraintTriangles.add(new MyTriangle(tempPoints));
                }
            }



            return lineConstraintTriangles;
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
}
