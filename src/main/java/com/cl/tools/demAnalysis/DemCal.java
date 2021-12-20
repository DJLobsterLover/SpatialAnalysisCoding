package com.cl.tools.demAnalysis;

import com.cl.pojo.DemPojo;
import com.cl.pojo.MyDEM;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DemCal {

    private MyDEM dem1;
    private MyDEM dem2;

    public DemCal() {
    }

    public MyDEM getDem1() {
        return dem1;
    }

    public void setDem1(MyDEM dem1) {
        this.dem1 = dem1;
    }

    public MyDEM getDem2() {
        return dem2;
    }

    public void setDem2(MyDEM dem2) {
        this.dem2 = dem2;
    }

    public int[][] oneDemCal(String expression) {
//        String dem1_val = expression.replace("Dem1_Val", "dem1.points[i][j].getZ()");
        System.out.println(expression);
        int row = dem1.getNrows();
        int col = dem1.getNcols();
        int[][] rs = new int[row][col];
        int Dem2_Val = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                String exp = "dp.val = " + expression;
//                exp = "dp.val = 2 + Dem1_Val";
                int Dem1_Val = (int) dem1.points[i][j].getZ();
                if (dem2 != null) {
                    Dem2_Val = (int) dem2.points[i][j].getZ();
                }

                if (Dem1_Val != dem1.getNODATA_value()) {
                    DemPojo dp = new DemPojo(0);

                    Map<String, Object> map = new HashMap<>();
                    map.put("Dem1_Val", Dem1_Val);
                    if (dem2 != null) {
                        map.put("Dem2_Val", Dem2_Val);
                    }
                    map.put("dp", dp);

                    Object bb = executeExpression(exp, map);
                    rs[i][j] = dp.val;
                } else {
                    rs[i][j] = (int) dem1.getNODATA_value();
                }
            }
        }
//        String rs = expression.replace("Dem1_Val", "dem1.height[i][j]");
        return rs;
    }


    //执行字符串代码封装
    public static Object executeExpression(String jexlExpression, Map<String, Object> map) {
        JexlEngine jexlEngine = new Engine();
        JexlExpression expression = jexlEngine.createExpression(jexlExpression);
        JexlContext context = new MapContext();
        if (map!=null&&!map.isEmpty()) {
            map.forEach(context::set);
        }
        return expression.evaluate(context);
    }

    public void executeString(String expression) {
//        demStringReplace(expression);
        Map<String, Object> map = new HashMap<>();
        map.put("dem1", dem1);//变量需要通过map注入
        map.put("dem2", dem2);//类方法需要map注入
        Object bb = executeExpression(expression, map);
    }

    public static void main(String[] args) {
        String exp = "dp.val = 2 + 10";
        DemPojo dp = new DemPojo(0);

        Map<String, Object> map = new HashMap<>();
        map.put("dp", dp);

        System.out.println(exp);

        System.out.println(dp.val);
        Object bb = executeExpression(exp, map);

        System.out.println(dp.val);

    }
}
