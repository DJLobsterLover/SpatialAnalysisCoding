import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.pojo.MyTriangle;
import com.cl.tools.Koch.KochLine;
import com.cl.tools.Transform;
import com.cl.tools.delaunayTrangle.CreateDelaunay2;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import org.locationtech.jts.algorithm.construct.MaximumInscribedCircle;
import org.locationtech.jts.geom.*;
import org.junit.Test;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TransformTest {
    @Test
    public void Transform() {
        Map<Character,MyPoint> Map = new HashMap();
        Map.put('A', new MyPoint(100, 100));
        System.out.println(Map.get('b'));
    }

    @Test
    public void t1() {
        Transform tf = new Transform();
        DistanceCal dc = new DistanceCalImpl();
        ArrayList points = new ArrayList();
        points.add(new MyPoint(0,0));
        points.add(new MyPoint(100,0));
        points.add(new MyPoint(100,50));
        MyPolygon mPolygon = new MyPolygon(points);
        MaximumInscribedCircle circle = new MaximumInscribedCircle(tf.PolygonTrans(mPolygon), 20);
        Point radiusPoint = circle.getRadiusPoint();
        System.out.println(radiusPoint.getX()+ " "+ radiusPoint.getY());

        MyPoint polygonInteriorPoint = dc.getPolygonCenter(new MyPolygon(points));
        System.out.println(polygonInteriorPoint.getX() + " " + polygonInteriorPoint.getY());

    }
    @Test
    public void test2(){
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(10, 10));
        points.add(new MyPoint(100, 100));
        points.add(new MyPoint(200, 50));
        points.add(new MyPoint(0, 100));
        points.add(new MyPoint(0, 200));
        points.add(new MyPoint(200, 220));
        points.add(new MyPoint(100, 220));
        MyPolygon polygon = new MyPolygon(points);
        Transform tf = new Transform();
        Polygon polygon1 = tf.PolygonTrans(polygon);

        DelaunayTriangulationBuilder dtb = new DelaunayTriangulationBuilder();
        GeometryFactory gf = new GeometryFactory();
        dtb.setSites(polygon1);
        dtb.setTolerance(1);
        GeometryCollection triangles = (GeometryCollection)dtb.getTriangles(gf);
        for (int i = 0; i < triangles.getNumGeometries(); i++) {
            System.out.println(triangles.getGeometryN(i));
        }
    }

    @Test
    public void test3() {
        ArrayList<MyPoint> points = new ArrayList<MyPoint>();
        points.add(new MyPoint(321, 251));
        points.add(new MyPoint(351, 576));
        points.add(new MyPoint(357, 395));
        points.add(new MyPoint(453, 195));
        points.add(new MyPoint(519, 244));
        points.add(new MyPoint(528, 278));
        points.add(new MyPoint(562, 261));
        points.add(new MyPoint(600, 496));
        points.add(new MyPoint(766, 456));
        points.add(new MyPoint(793, 680));
        points.add(new MyPoint(819, 262));
        points.add(new MyPoint(839, 477));
        points.add(new MyPoint(875, 419));
        points.add(new MyPoint(910, 296));
        points.add(new MyPoint(915, 459));
        points.add(new MyPoint(920, 344));
        points.add(new MyPoint(1006, 275));
        points.add(new MyPoint(1012, 465));
        points.add(new MyPoint(1044, 497));
        points.add(new MyPoint(1048, 645));
        MyPolygon polygon = new MyPolygon(points);
        CreateDelaunay2 cd = new CreateDelaunay2(points);
        cd.initDelaunay();
        ArrayList<MyLine> allLines = cd.getAllLines();
        for (MyLine allLine : allLines) {
            System.out.println(allLine);
        }

    }

}
