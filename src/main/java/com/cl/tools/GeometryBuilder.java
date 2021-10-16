package com.cl.tools;
import org.locationtech.jts.algorithm.MinimumDiameter;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

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
    public Point createPoint(double x, double y){
//        Coordinate coord = new Coordinate(109.013388, 32.715519);
        Coordinate coord = new Coordinate(x, y);
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
    /**
     * @author DJLobster
     * 创建面
     */
    public Polygon createPolygon(Coordinate[] coordinates) {
        Polygon polygon = geomFactory.createPolygon(coordinates);
        return polygon;
    }
}
