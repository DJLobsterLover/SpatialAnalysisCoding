import com.cl.pojo.DemPojo;
import com.cl.pojo.MyPoint;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static int a = 10;


    public static void getA(int i ) {
        if (i == 1) {
            System.out.println("a");
        } else {
            System.out.println("b");
        }
    }

    private static JexlEngine jexlEngine = new Engine();
    public static Object executeExpression(String jexlExpression, Map<String, Object> map) {
        JexlExpression expression = jexlEngine.createExpression(jexlExpression);
        JexlContext context = new MapContext();
        if (map!=null&&!map.isEmpty()) {
            map.forEach(context::set);
        }
        return expression.evaluate(context);
    }

    public static void main(String[] args) {
        DemPojo dp = new DemPojo(0);
        int[][] b = new int[2][2];
        b[1][1] = 20;
        int i = 1;
        double c = 2;
        MyPoint p = new MyPoint(1, 2);
        int temp = 0;
        Map<String, Object> map = new HashMap<>();
        map.put("p", p);
        map.put("b", b);//类方法需要map注入
        map.put("i", i);
        map.put("dp",dp);
        String expression = "dp.val = b[i][i]+ 10";
        Object bb = executeExpression(expression, map);
        System.out.println(dp.val);
    }
}
