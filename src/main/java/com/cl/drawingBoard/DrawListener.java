package com.cl.drawingBoard;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
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
    VectorSpaceCal vs = new VectorSpaceCalImpl();
    ArrayList<MyPolygon> polygons;
    ArrayList<MyPoint> tempPoints;
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
//            g.clearRect(0,0,df.getWidth(),df.getHeight());
            g.setColor(Color.white);
            g.fillRect(0,0,df.getWidth(),df.getHeight());
            tempPoints.clear();
            polygons.clear();
        } else if (shape.equals("多边形面积")) {
            System.out.println(vs.vectorSpaceCal(polygons.get(0).getPolygonPoints()));
            //绘制多边形外接圆
            int r = (int)dc.getPolygonExternalCircle(polygons.get(0));
            MyPoint p = dc.getPolygonExternal(polygons.get(0));
            g.setColor(Color.red);
            g.fillOval((int)p.getX(), (int)p.getY(),  2, 2);
            g.drawOval((int)p.getX() - r, (int)p.getY() - r, 2 * r, 2 * r);
            System.out.println(tempPoints);
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
        } else if (shape.equals("弧线")) {
            g.drawArc(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1), 0, 180);
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
        } else if (shape.equals("圆角矩形")) {
            g.drawRoundRect(x1, y1, Math.abs(x2 - x1), Math.abs(y2 - y1), 2, 10);
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
        } else if (shape.equals("立体圆")) {
            // double a=-2,b=-2,c=-1.2,d=2;
            double a = 1.40, b = 1.56, c = 1.40, d = -6.56;
            double x = 0, xo = 0;
            double y = 0, yo = 0;
            Color[] Col = { Color.BLUE, Color.cyan, Color.green, Color.magenta,
                    Color.red, Color.yellow };
            for (int i = 0; i <= 90000; i++) {
                Random r = new Random();
                // 增加颜色
                int R = r.nextInt(Col.length);
                g.setColor(Col[R]);
                // x=Math.sin(a*yo)-Math.cos(b*xo);
                // y=Math.sin(c*xo)-Math.cos(d*yo);
                x = d * Math.sin(a * xo) - Math.sin(b * yo);
                y = c * Math.cos(a * xo) + Math.cos(b * yo);
                int temp_x = (int) (x * 50);
                int temp_y = (int) (y * 50);
                g.drawLine(temp_x + 500, temp_y + 300, temp_x + 500,
                        temp_y + 300);
                xo = x;
                yo = y;
            }
        } else if (shape.equals("三角形")) {
            double a = -2, b = -2, c = -1.2, d = 2;
            double x = 0, xo = 0;
            double y = 0, yo = 0;
            Color[] Col = { Color.BLUE, Color.cyan, Color.green, Color.magenta,
                    Color.red, Color.yellow };
            for (int i = 0; i <= 90000; i++) {
                Random r = new Random(); // 增加颜色
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
        }
    }
    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        if (shape.equals("曲线")) {
            // g.setStroke(new BasicStroke(10));
            // g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            // RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        } else if (shape.equals("橡皮擦")) {
            g.setStroke(new BasicStroke(80));
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.WHITE);
            g.drawLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        } else if (shape.equals("喷枪")) {
            // g.setStroke(new BasicStroke(2)); //不用加粗
            // g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            // RenderingHints.VALUE_ANTIALIAS_ON);
            for (int k = 0; k < 20; k++) {
                Random i = new Random();
                int a = i.nextInt(8);
                int b = i.nextInt(10);
                g.drawLine(x2 + a, y2 + b, x2 + a, y2 + b);
            }
        }
    }
}
