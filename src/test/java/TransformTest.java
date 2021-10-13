import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.Transform;
import com.vividsolutions.jts.geom.Polygon;
import org.junit.Test;

import java.util.ArrayList;

public class TransformTest {
    @Test
    public void Transform() {
        Transform tf = new Transform();
        ArrayList points = new ArrayList();
        points.add(new MyPoint(0,0));
        points.add(new MyPoint(1,0));
        points.add(new MyPoint(1,1));
        points.add(new MyPoint(0,1));
        MyPolygon mPolygon = new MyPolygon(points);
        Polygon polygon = tf.PolygonTrans(mPolygon);
        System.out.println(polygon.getArea());
    }
}
