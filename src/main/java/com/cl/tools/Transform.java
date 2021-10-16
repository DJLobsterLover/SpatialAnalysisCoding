package com.cl.tools;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import org.locationtech.jts.geom.*;

/**
 * @author DJLobster
 * 用pojo类与Jts类种的对象相互转换
 */
public class Transform {
    private GeometryBuilder gf = new GeometryBuilder();

    public Point PointTrans(MyPoint p){
        Point point = gf.createPoint(p.getX(), p.getY());
        return point;
    }

    public LineString LineTrans(MyLine line) {
        Point startPoint = PointTrans(line.getStartPoint());
        Point endPoint = PointTrans(line.getEndPoint());
        LineString line1 = gf.createLine(startPoint, endPoint);
        return line1;
    }

    public Polygon PolygonTrans(MyPolygon polygon) {
        Coordinate[] coordinates = new Coordinate[polygon.getPolygonPoints().size() + 1];
        for (int i = 0; i < polygon.getPolygonPoints().size(); i++) {
            coordinates[i] = new Coordinate(polygon.getPolygonPoints().get(i).getX(), polygon.getPolygonPoints().get(i).getY());
        }
        coordinates[coordinates.length - 1] = new Coordinate(polygon.getPolygonPoints().get(0).getX(), polygon.getPolygonPoints().get(0).getY());
        Polygon polygon1 = gf.createPolygon(coordinates);
        return polygon1;
    }

    public MyPoint PointTransBack(Point p) {
        MyPoint p1 = new MyPoint(p.getX(), p.getY());
        return p1;
    }

}