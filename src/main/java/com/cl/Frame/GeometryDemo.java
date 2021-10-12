package com.cl.Frame;

import com.vividsolutions.jts.algorithm.MinimumDiameter;
import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/**
 *
 * @author DJLobster
 */
public class GeometryDemo {
    private GeometryFactory geomFactory = new GeometryFactory();

    public Point createPoint(double x, double y,double z){
//        Coordinate coord = new Coordinate(109.013388, 32.715519);
        Coordinate coord = new Coordinate(x, y,z);
        Point point = geomFactory.createPoint( coord );

        return point;
    }
    public Polygon createCircle(double x, double y, final double RADIUS){
        final int SIDES = 32;
        //圆上面的点个数
        Coordinate coords[] = new Coordinate[SIDES+1];
        for( int i = 0; i < SIDES; i++){
            double angle = ((double) i / (double) SIDES) * Math.PI * 2.0;
            double dx = Math.cos( angle ) * RADIUS;
            double dy = Math.sin( angle ) * RADIUS;
            coords[i] = new Coordinate( (double) x + dx, (double) y + dy );
        }
        coords[SIDES] = coords[0];
        LinearRing ring = geomFactory.createLinearRing( coords );
        Polygon polygon = geomFactory.createPolygon( ring, null );
        return polygon;
    }
    public LineString createLine(){
        Coordinate[] coords  = new Coordinate[] {new Coordinate(0, 0), new Coordinate(1, 1)};
        LineString line = geomFactory.createLineString(coords);
        line.getStartPoint().getX();
        return line;
    }
    public Polygon createPolygonByWKT() throws ParseException{

        Coordinate[] coordinates = new Coordinate[]{
                new Coordinate(0, 0),
                new Coordinate(0, 2),
                new Coordinate(2, 2),
                new Coordinate(2, 0),
                new Coordinate(0, 0),
        };
        WKTReader reader = new WKTReader( geomFactory );
        Polygon polygon = geomFactory.createPolygon(coordinates);
//        Polygon polygon = (Polygon) reader.read("POLYGON((50 10 , 30 0 , 40 10 , 30 20 , 50 10 ))");
        return polygon;
    }

    public static void main(String[] args) throws ParseException {
        GeometryDemo gt = new GeometryDemo();
        Polygon p = gt.createCircle(0, 1, 2);

//        //圆上所有的坐标(32个)
//        Coordinate coords[] = p.getCoordinates();
//        for(Coordinate coord:coords){
//            System.out.println(coord.x+","+coord.y);
//        }
        Point p1 = gt.createPoint(0,0,0);
        Point p2 = gt.createPoint(1,1,1);
//        LineString p2 = gt.createLine();
        Polygon p3 = gt.createPolygonByWKT();
        System.out.println(p1.distance(p2));
    }
}
