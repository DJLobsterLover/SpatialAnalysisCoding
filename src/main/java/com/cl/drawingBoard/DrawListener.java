package com.cl.drawingBoard;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.pojo.MyTriangle;
import com.cl.tools.delaunayTrangle.CreateDelaunay;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import com.cl.tools.spatialRelation.SpatialRelation;
import com.cl.tools.spatialRelation.SpatialRelationImpl;
import com.cl.tools.vectorSpaceCal.VectorSpaceCal;
import com.cl.tools.vectorSpaceCal.VectorSpaceCalImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class DrawListener extends MouseAdapter implements ActionListener {
    DistanceCal dc = new DistanceCalImpl();
    SpatialRelation sp = new SpatialRelationImpl();
    VectorSpaceCal vs = new VectorSpaceCalImpl();
    ArrayList<MyPolygon> polygons;
    ArrayList<MyPoint> tempPoints;
    ArrayList<MyLine> lines;
    ArrayList<MyPoint> points;
    private int x1, y1, x2, y2;
    private int newx1, newy1, newx2, newy2;
    private Graphics2D g;
    private DrawMain df;
    private boolean flag = false;
    String shape = "直线";
    Color color;
    private int[] arrx = new int[4];
    private int[] arry = new int[4];
    private int temp = 0;
    DrawListener(DrawMain d) {
        df = d;
        polygons = new ArrayList<MyPolygon>();
        tempPoints = new ArrayList<MyPoint>();
        lines = new ArrayList<MyLine>();
        points = new ArrayList<MyPoint>();
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("")) {
            JButton button = (JButton) e.getSource();
            color = button.getBackground();
            System.out.println("color = " + color);
        } else {
            JButton button = (JButton) e.getSource();
            shape = button.getActionCommand();
            System.out.println("String = " + shape);
        }
        if (shape.equals("测试结果")) {
            g = (Graphics2D) df.getGraphics();
            g.setColor(color);
            MyLine l1 = new MyLine(new MyPoint(100,0),new MyPoint(0,100));
            MyPoint p1 = new MyPoint(0,0);
            MyPoint p2 = new MyPoint(200, 200);
            int x1 = (int) l1.getStartPoint().getX();
            int y1 = (int) l1.getStartPoint().getY();
            int x2 = (int) l1.getEndPoint().getX();
            int y2 = (int) l1.getEndPoint().getY();
            //设置线的粗细
            BasicStroke bs_1=new BasicStroke(2,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
            g.setStroke(bs_1);
//            g.drawLine(x1,y1,x2,y2);
            // 画圆
            int w = (int)p1.getX();
            int h = (int)p1.getY();
            int r = 10;
            g.setColor(Color.red);
            g.fillOval(w, h,  r, r);
            int w1 = (int)p2.getX();
            int h1 = (int)p2.getY();
            g.setColor(Color.yellow);
            g.fillOval(w1, h1, r, r);
            g.drawLine((int)p1.getX(), (int)p1.getY(),(int) p2.getX(),(int) p2.getY());
            df.resLabel.setText("结果:" + dc.euclideanDistance(p1,p2));
            System.out.println(dc.euclideanDistance(p1, p2));
        } else if (shape.equals("清空")) {
            g.setColor(Color.white);
            g.fillRect(0,0,df.getWidth(),df.getHeight());
            tempPoints.clear();
            polygons.clear();
            points.clear();
        } else if (shape.equals("多边形面积")) {
            System.out.println(vs.vectorSpaceCal(polygons.get(0).getPolygonPoints()));
            //绘制多边形外接圆
            int r = (int)dc.getPolygonExternalCircle(polygons.get(0));
            MyPoint p = dc.getPolygonExternal(polygons.get(0));
            g.setColor(Color.red);
            g.fillOval((int)p.getX(), (int)p.getY(),  2, 2);
            g.drawOval((int)p.getX() - r, (int)p.getY() - r, 2 * r, 2 * r);
            System.out.println(tempPoints);
        } else if (shape.equals("点是否在多边形内")) {
            if (polygons == null) {
                System.out.println("请先创建一个多边形");
            } else if (points == null) {
                System.out.println("请先拆创建一个点");
            } else {
                for (int i = 0; i < points.size(); i++) {
                    for (int j = 0; j < polygons.size(); j++) {
                        if (sp.pointWithinPolygonRay(points.get(i), polygons.get(j))) {
                            System.out.println("该点在多边形内部");
                        } else {
                            System.out.println("该店不在多边形内部");
                        }
                    }
                }
            }
        } else if (shape.equals("Delaunay")) {
            ArrayList<MyPoint> points = new ArrayList<MyPoint>();
//            points.add(new MyPoint(0, 0));
//            points.add(new MyPoint(100, 0));
//            points.add(new MyPoint(200, 0));
//            points.add(new MyPoint(200, 100));
//            points.add(new MyPoint(200, 200));
//            points.add(new MyPoint(0, 100));
//            points.add(new MyPoint(0, 200));
//
//            points.add(new MyPoint(321, 251));
//            points.add(new MyPoint(351, 576));
//            points.add(new MyPoint(357, 395));
//            points.add(new MyPoint(453, 195));
//            points.add(new MyPoint(519, 244));
//            points.add(new MyPoint(528, 278));
//            points.add(new MyPoint(562, 261));
//            points.add(new MyPoint(600, 496));
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
            points.add(new MyPoint(1200, 870));

            CreateDelaunay cd = new CreateDelaunay(points);
            cd.initDelaunay();
            g = (Graphics2D) df.getGraphics();
            g.setColor(color);
            for (MyPoint point : points) {
                g.fillOval((int)point.getX(), (int)point.getY(),  4, 4);
            }
            for (MyTriangle myTriangle : cd.getTriangle_list()) {
                ArrayList<MyPoint> points1 = myTriangle.getPoints();
                g.drawLine((int)points1.get(0).getX(),(int)points1.get(0).getY(),(int)points1.get(1).getX(),(int)points1.get(1).getY());
                g.drawLine((int)points1.get(1).getX(),(int)points1.get(1).getY(),(int)points1.get(2).getX(),(int)points1.get(2).getY());
                g.drawLine((int)points1.get(2).getX(),(int)points1.get(2).getY(),(int)points1.get(0).getX(),(int)points1.get(0).getY());

            }
        }
    }
    // 实现画笔
    @Override
    public void mousePressed(MouseEvent e) {
        g = (Graphics2D) df.getGraphics();
        g.setColor(color);
        x1 = e.getX();
        y1 = e.getY();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        if (shape.equals("直线")) {
            g.drawLine(x1, y1, x2, y2);
        } else if (shape.equals("多边形") && !flag) {
            g.drawLine(x1, y1, x2, y2);
            newx1 = x1;
            newy1 = y1;
            newx2 = x2;
            newy2 = y2;
            flag = true;
        } else if (shape.equals("圆")) {
            int r = (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
            g.drawOval(x1 - r, y1 - r, 2 * r, 2 * r);
        } else if (shape.equals("矩形")) {
            g.drawRect(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
        } else if (shape.equals("椭圆")) {
            g.drawOval(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1));
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            tempPoints.add(new MyPoint(e.getX(), e.getY()));
        }
        if (shape.equals("多边形") && flag) {
            x2 = e.getX();
            y2 = e.getY();
            if (e.getClickCount() == 2) {
                g.drawLine(newx1, newy1, newx2, newy2);
                flag = false;
                polygons.add(new MyPolygon(tempPoints));
//                tempPoints.clear();
            }
            g.drawLine(newx2, newy2, x2, y2);
            newx2 = x2;
            newy2 = y2;
        } else if (shape.equals("图形")) {
            arrx[temp] = e.getX();
            arry[temp] = e.getY();
            temp++;
            if (temp == 4) {
                int x = arrx[3];
                int y = arry[3];
                for (int i = 0; i <= 10000; i++) {
                    Random ran = new Random();
                    int k = ran.nextInt(3);
                    x = (x + arrx[k]) / 2;
                    y = (y + arry[k]) / 2;
                    g.drawLine(x, y, x, y);
                }
                temp = 0;
            }
        } else if (shape.equals("三角形")) {
            double a = -2, b = -2, c = -1.2, d = 2;
            double x = 0, xo = 0;
            double y = 0, yo = 0;
            Color[] Col = { Color.BLUE, Color.cyan, Color.green, Color.magenta,
                    Color.red, Color.yellow };
            for (int i = 0; i <= 90000; i++) {
                Random r = new Random();
                // 增加颜色
                int R = r.nextInt(Col.length);
                g.setColor(Col[R]);
                x = Math.sin(a * yo) - Math.cos(b * xo);
                y = Math.sin(c * xo) - Math.cos(d * yo);
                int temp_x = (int) (x * 50);
                int temp_y = (int) (y * 50);
                g.drawLine(temp_x + 500, temp_y + 300, temp_x + 500,
                        temp_y + 300);
                xo = x;
                yo = y;
            }
        } else if (shape.equals("测试")) {
            g = (Graphics2D) df.getGraphics();
            g.setColor(color);
            MyLine l1 = new MyLine(new MyPoint(0,0),new MyPoint(100,100));
            int x1 = (int) l1.getStartPoint().getX();
            int y1 = (int) l1.getStartPoint().getY();
            int x2 = (int) l1.getEndPoint().getX();
            int y2 = (int) l1.getEndPoint().getY();
            g.drawLine(x1,y1,x2,y2);
        } else if (shape.equals("圆点")) {
            g = (Graphics2D) df.getGraphics();
            g.fillOval(e.getX(), e.getY(), 5, 5);
            points.add(new MyPoint(e.getX(), e.getY()));
        }
    }
    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        if (shape.equals("橡皮擦")) {
            g.setStroke(new BasicStroke(80));
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.WHITE);
            g.drawLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        }
    }
}
