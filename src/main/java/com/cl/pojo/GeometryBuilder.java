package com.cl.pojo;
import com.vividsolutions.jts.algorithm.MinimumDiameter;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 * @author DJLobster
 * 空间实体创建——点线面
 */
public class GeometryBuilder {
    private GeometryFactory geomFactory = new GeometryFactory();

    /**
     * @author DJLobster
     * 创建点
     */
    public Point createPoint(double x, double y, double z){
//        Coordinate coord = new Coordinate(109.013388, 32.715519);
        Coordinate coord = new Coordinate(x, y,z);
        Point point = geomFactory.createPoint( coord );
        return point;
    }
    /**
     * @author DJLobster
     * 创建线
     */
    public LineString createLine(Point startPoint, Point endPoint){
        Coordinate[] coords  = new Coordinate[] {new Coordinate(startPoint.getX(), startPoint.getY()),
                new Coordinate(endPoint.getX(), endPoint.getY())};
        LineString line = geomFactory.createLineString(coords);
        return line;
    }
}
