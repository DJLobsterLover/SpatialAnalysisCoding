import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.pojo.MyTriangle;
import com.cl.tools.Koch.KochLine;
import com.cl.tools.Transform;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import org.locationtech.jts.algorithm.construct.MaximumInscribedCircle;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.junit.Test;

import java.util.ArrayList;

public class TransformTest {
    @Test
    public void Transform() {
        Transform tf = new Transform();
        ArrayList points = new ArrayList();
        points.add(new MyPoint(0,75));
        points.add(new MyPoint(200,75));
        points.add(new MyPoint(200,300));
        MyTriangle mPolygon = new MyTriangle(points);
        MyLine mLine = new MyLine(new MyPoint(100, 50), new MyPoint(100, 150));
        LineString lineString = tf.LineTrans(mLine);
        Polygon polygon = tf.PolygonTrans(mPolygon);
        System.out.println(lineString.crosses(polygon));
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
}
