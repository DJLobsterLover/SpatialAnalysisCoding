package com.cl.drawingBoard;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.pojo.MyPolygon;
import com.cl.pojo.MyTriangle;
import com.cl.tools.Constants;
import com.cl.tools.Koch.KochLine;
import com.cl.tools.Koch.KochSnow;
import com.cl.tools.Transform;
import com.cl.tools.delaunayTrangle.CreateDelaunay;
import com.cl.tools.delaunayTrangle.CreateDelaunay2;
import com.cl.tools.delaunayTrangle.LineConstraintDelaunay;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;
import com.cl.tools.spatialRelation.SpatialRelation;
import com.cl.tools.spatialRelation.SpatialRelationImpl;
import com.cl.tools.vectorSpaceCal.VectorSpaceCal;
import com.cl.tools.vectorSpaceCal.VectorSpaceCalImpl;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class DrawListener extends MouseAdapter implements ActionListener{
    DistanceCal dc = new DistanceCalImpl();
    SpatialRelation sr = new SpatialRelationImpl();
    VectorSpaceCal vs = new VectorSpaceCalImpl();
    Transform tf = new Transform();
    Constants C = new Constants();
    ArrayList<MyPolygon> polygons;
    ArrayList<MyPoint> tempPoints;
    ArrayList<ArrayList> lines;
    ArrayList<MyPoint> points;
    ArrayList<MyPoint> constraintPoints;
    ArrayList<MyPoint> linePoints;

    CreateDelaunay cd;
    private int x1, y1, x2, y2;
    private int newx1, newy1, newx2, newy2;
    private Graphics2D g;
    private DrawMain df;
    private boolean flag = false;
    String shape = "直线";
    String content;
    Color color;
    private int[] arrx = new int[4];
    private int[] arry = new int[4];
    private int temp = 0;
    DrawListener(DrawMain d) {
        df = d;
        polygons = new ArrayList<MyPolygon>();
        tempPoints = new ArrayList<MyPoint>();
        constraintPoints = new ArrayList<MyPoint>();
        linePoints = new ArrayList<MyPoint>();
        lines = new ArrayList<ArrayList>();
        points = new ArrayList<MyPoint>();
    }

    public void itemStateChanged(ItemEvent e) {
//        System.out.println("ues");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("comboBoxChanged")) {
            shape = "";
            content = "";
            content = df.getSelectedComBox();
            if (content.equals("Koch曲线")) {
                KochLine a = new KochLine();
                a.draw(3);
            }
            else if(content.equals("生成三角网")){
                System.out.println("生成三角网");
//                ArrayList<MyPoint> points = new ArrayList<MyPoint>();
////            points.add(new MyPoint(10, 10));
////            points.add(new MyPoint(100, 100));
////            points.add(new MyPoint(200, 50));
////            points.add(new MyPoint(0, 100));
////            points.add(new MyPoint(0, 200));
////            points.add(new MyPoint(200, 220));
////            points.add(new MyPoint(100, 220));
////
//                points.add(new MyPoint(321, 251));
//                points.add(new MyPoint(351, 576));
//                points.add(new MyPoint(357, 395));
//                points.add(new MyPoint(453, 195));
//                points.add(new MyPoint(519, 244));
//                points.add(new MyPoint(528, 278));
//                points.add(new MyPoint(562, 261));
//                points.add(new MyPoint(600, 496));
//                points.add(new MyPoint(766, 456));
//                points.add(new MyPoint(793, 680));
//                points.add(new MyPoint(819, 262));
//                points.add(new MyPoint(839, 477));
//                points.add(new MyPoint(875, 419));
//                points.add(new MyPoint(910, 296));
//                points.add(new MyPoint(915, 459));
//                points.add(new MyPoint(920, 344));
//                points.add(new MyPoint(1006, 275));
//                points.add(new MyPoint(1012, 465));
//                points.add(new MyPoint(1044, 497));
//                points.add(new MyPoint(1048, 645));
                cd = new CreateDelaunay(points);
                cd.initDelaunay();

                //线限制
                ArrayList<MyPoint> constraints = new ArrayList<MyPoint>();
                constraints.add(new MyPoint(55, 85));
                constraints.add(new MyPoint(55, 125));
//            constraints.add(new MyPoint(55, 125));
                constraints.add(new MyPoint(55, 180));
//            constraints.add(new MyPoint(99, 100));
                g = (Graphics2D) df.getGraphics();
                g.setColor(color);
                for (MyPoint point : points) {
                    g.fillOval((int)point.getX(), (int)point.getY(),  3, 3);
                }
                for (MyTriangle myTriangle : cd.getTriangle_list()) {
                    ArrayList<MyPoint> points1 = myTriangle.getPoints();
                    g.drawLine((int)points1.get(0).getX(),(int)points1.get(0).getY(),(int)points1.get(1).getX(),(int)points1.get(1).getY());
                    g.drawLine((int)points1.get(1).getX(),(int)points1.get(1).getY(),(int)points1.get(2).getX(),(int)points1.get(2).getY());
                    g.drawLine((int)points1.get(2).getX(),(int)points1.get(2).getY(),(int)points1.get(0).getX(),(int)points1.get(0).getY());
                }
            } else if (content.equals("生成三角网2")) {
                System.out.println(content);
                ArrayList<MyPoint> points = new ArrayList<MyPoint>();
//            points.add(new MyPoint(10, 10));
//            points.add(new MyPoint(100, 100));
//            points.add(new MyPoint(200, 50));
//            points.add(new MyPoint(0, 100));
//            points.add(new MyPoint(0, 200));
//            points.add(new MyPoint(200, 220));
//            points.add(new MyPoint(100, 220));
//
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
                Transform tf = new Transform();
                Polygon polygon1 = tf.PolygonTrans(polygon);

                DelaunayTriangulationBuilder dtb = new DelaunayTriangulationBuilder();
                GeometryFactory gf = new GeometryFactory();
                dtb.setSites(polygon1);
                dtb.setTolerance(1);
                GeometryCollection triangles = (GeometryCollection)dtb.getTriangles(gf);
                g = (Graphics2D) df.getGraphics();
                g.setColor(color);
                for (int i = 0; i < triangles.getNumGeometries(); i++) {
                    Geometry geometryN = triangles.getGeometryN(i);
                    Coordinate[] coordinates = geometryN.getCoordinates();
                    g.drawLine((int)coordinates[0].getX(), (int)coordinates[0].getY(),(int)coordinates[1].getX(), (int)coordinates[1].getY());
                    g.drawLine((int)coordinates[1].getX(), (int)coordinates[1].getY(),(int)coordinates[2].getX(), (int)coordinates[2].getY());
                    g.drawLine((int)coordinates[2].getX(), (int)coordinates[2].getY(),(int)coordinates[3].getX(), (int)coordinates[3].getY());
                }
            } else if (content.equals("生成三角网3")) {
                System.out.println(content);
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
                //绘制
                g = (Graphics2D) df.getGraphics();
                g.setColor(color);
                for (MyLine allLine : allLines) {
                    g.drawLine((int)allLine.getStartPoint().getX(), (int)allLine.getStartPoint().getY(), (int)allLine.getEndPoint().getX(), (int)allLine.getEndPoint().getY());
                }

            } else if (content.equals("线约束")) {
                g = (Graphics2D) df.getGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, df.getWidth(), df.getHeight());
                g.setColor(Color.red);
                System.out.println("线约束");
                LineConstraintDelaunay LCD = new LineConstraintDelaunay();
                ArrayList<MyTriangle> myTriangles = LCD.LineConstraint(cd, constraintPoints);

                for (MyPoint point : cd.getPoints()) {
                    g.fillOval((int) point.getX(), (int) point.getY(), 4, 4);
                }
                for (MyTriangle myTriangle : myTriangles) {
                    ArrayList<MyPoint> points1 = myTriangle.getPoints();
                    g.drawLine((int) points1.get(0).getX(), (int) points1.get(0).getY(), (int) points1.get(1).getX(), (int) points1.get(1).getY());
                    g.drawLine((int) points1.get(1).getX(), (int) points1.get(1).getY(), (int) points1.get(2).getX(), (int) points1.get(2).getY());
                    g.drawLine((int) points1.get(2).getX(), (int) points1.get(2).getY(), (int) points1.get(0).getX(), (int) points1.get(0).getY());
                }
            } else if (content.equals("点抽稀")) {
                System.out.println("点抽稀");
                if (linePoints.size() != 0) {
                    ArrayList<MyPoint> myPoints = sr.pointWeedingDouglas(linePoints, 100);
                    //绘制结果
                    g = (Graphics2D) df.getGraphics();
                    g.setColor(Color.RED);
                    for (int i = 0; i < myPoints.size() - 1; i++) {
                        g.drawLine((int) myPoints.get(i).getX(), (int) myPoints.get(i).getY(), (int) myPoints.get(i + 1).getX(), (int) myPoints.get(i + 1).getY());
                    }
                } else {
                    System.out.println("请先输入一条线段");
                }
            } else if (content.equals("点平滑")) {
                if (linePoints.size() != 0) {
                    ArrayList<MyPoint> myPoints = sr.simpleGeometrySmooth(linePoints, 1);
                    g = (Graphics2D) df.getGraphics();
                    g.setColor(Color.RED);
                    for (int i = 0; i < myPoints.size() - 1; i++) {
                        g.drawLine((int) myPoints.get(i).getX(), (int) myPoints.get(i).getY(), (int) myPoints.get(i + 1).getX(), (int) myPoints.get(i + 1).getY());
                    }
                } else {
                    System.out.println("请先输入一条线段");
                }
            } else if (content.equals("多边形最小外接圆")) {
                //绘制多边形外接圆
                if (polygons.size() != 0) {
                    for (int i = 0; i < polygons.size(); i++) {
                        int r = (int) dc.getPolygonExternalCircle(polygons.get(i));
                        MyPoint p = dc.getPolygonExternal(polygons.get(i));
                        g.setColor(Color.red);
                        g.fillOval((int) p.getX(), (int) p.getY(), 2, 2);
                        g.drawOval((int) p.getX() - r, (int) p.getY() - r, 2 * r, 2 * r);
                    }
                } else {
                    System.out.println("请先输入一个多边形");
                }
            } else if (content.equals("多边形最大内切圆")) {
                if (polygons.size() != 0) {
                    for (int i = 0; i < polygons.size(); i++) {
                        int r = (int) dc.getMaximumInscribedCircle(polygons.get(i));
                        MyPoint p = dc.getMaxInscribed(polygons.get(i));
                        g.setColor(Color.BLUE);
                        g.fillOval((int) p.getX(), (int) p.getY(), 2, 2);
                        g.drawOval((int) p.getX() - r, (int) p.getY() - r, 2 * r, 2 * r);
                    }
                } else {
                    System.out.println("请先输入一个多边形");
                }
            } else if (content.equals("点在多边形内判断")) {
                if (polygons.size() == 0) {
                    System.out.println("请先创建一个多边形");
                } else if (points.size() == 0) {
                    System.out.println("请先拆创建一个点");
                } else {
                    for (int i = 0; i < points.size(); i++) {
                        for (int j = 0; j < polygons.size(); j++) {
                            if (sr.pointWithinPolygonRay(points.get(i), polygons.get(j))) {
                                System.out.println("点" + i + "该点在多边形内部");
                            } else {
                                System.out.println("该店不在多边形内部");
                            }
                        }
                    }
                }
            } else if (content.equals("两点距离")) {
                if (points.size() != 2) {
                    System.out.println("请先插入两个点");
                } else {
                    System.out.println("=====================================");
                    System.out.println("两点间欧式距离:" + dc.euclideanDistance(points.get(0), points.get(1)));
                    System.out.println("两点间曼哈顿距离:" + dc.manhattanDistance(points.get(0), points.get(1)));
                    System.out.println("两点间切比雪夫距离:" + dc.chebyshevDistance(points.get(0), points.get(1)));
                    System.out.println("两点间明氏距离:" + dc.minkowskiDistance(points.get(0), points.get(1), 3));
                }
            } else if (content.equals("点到线距离")) {
                if (points.size() != 1) {
                    System.out.println("请先插入一个点");
                } else if (linePoints.size() != 2) {
                    System.out.println("请先插入一条直线");
                } else {
                    System.out.println("=====================================");
                    System.out.println("点到直线的最大距离:" + dc.pointToLineDistance(points.get(0), new MyLine(linePoints.get(0), linePoints.get(1)), "max"));
                    System.out.println("点到直线的最小距离:" + dc.pointToLineDistance(points.get(0), new MyLine(linePoints.get(0), linePoints.get(1)), "min"));
                    System.out.println("点到直线的垂直距离:" + sr.pointToLine(tf.PointTrans(points.get(0)), tf.LineTrans(new MyLine(linePoints.get(0), linePoints.get(1)))));
                }
            } else if (content.equals("点与面距离")) {
                if (points.size() != 1) {
                    System.out.println("请先插入一个点");
                } else if (polygons.size() != 1) {
                    System.out.println("请先插入一个多边形");
                } else {
                    System.out.println("=====================================");
                    System.out.println("点到多边形的最大距离:" + dc.pointToPolygonDistance(points.get(0), polygons.get(0), "max"));
                    System.out.println("点到多边形的最小距离:" + dc.pointToPolygonDistance(points.get(0), polygons.get(0), "min"));
                }
            } else if (content.equals("线与线距离")) {
                if (lines.size() != 2) {
                    System.out.println("请先输入两条线");
                } else {
                    System.out.println("=====================================");
                    System.out.println("线与线之间的距离:" + dc.lineToLineDistance(new MyLine((MyPoint) lines.get(0).get(0), (MyPoint) lines.get(0).get(1)),
                            new MyLine((MyPoint) lines.get(1).get(0), (MyPoint) lines.get(1).get(1))));
                }
            } else if (content.equals("线与面距离")) {
                if (lines.size() < 0) {
                    System.out.println("请先输入一条线");
                } else if (polygons.size() < 0) {
                    System.out.println("请先输入一个多边形");
                } else {
                    System.out.println("=====================================");
                    System.out.println("线到多边形的距离是:" + dc.lineToPolygonDistance(new MyLine((MyPoint) lines.get(0).get(0), (MyPoint) lines.get(0).get(1)), polygons.get(0)));
                }
            } else if (content.equals("生成点击凸包")) {
                if (points.size() < 0) {
                    System.out.println("请先插入一个点集");
                } else {
                    //绘制凸包
                    g = (Graphics2D) df.getGraphics();
                    g.setColor(Color.GREEN);
                    ArrayList<MyPoint> polygonConvexHull = sr.getPolygonConvexHull(new MyPolygon(points));
                    if (polygonConvexHull.size() > 4) {
                        for (int i = 0; i < polygonConvexHull.size(); i++) {
                            g.fillOval((int) polygonConvexHull.get(i).getX(), (int) polygonConvexHull.get(i).getY(), 3, 3);
                        }
                        for (int i = 0; i < polygonConvexHull.size() - 1; i++) {
                            g.drawLine((int) polygonConvexHull.get(i).getX(), (int) polygonConvexHull.get(i).getY(), (int) polygonConvexHull.get(i + 1).getX(), (int) polygonConvexHull.get(i + 1).getY());
                        }
                    } else {
                        System.out.println("无法形成一个闭合多边形");
                    }
                }
            } else if (content.equals("面状地物量测")) {
                if (polygons.size() < 0) {
                    System.out.println("请先输入一个多边形");
                } else {
                    System.out.println("=====================================");
                    System.out.println("多边形的Perimeter/Area ratio:" + sr.getP1A(polygons.get(0)));
                    System.out.println("多边形Perimeter2/Area ratio:" + sr.getP2A(polygons.get(0)));
                    System.out.println("多边形最长轴:" + sr.getPolygonMaxAxis(polygons.get(0)));
                    System.out.println("多边形最短轴:" + sr.getPolygonMinAxis(polygons.get(0)));
                    System.out.println("多边形形状比:" + sr.getPolygonFormRatio(polygons.get(0)));
                    System.out.println("多边形伸延率:" + sr.getPolygonElongationRational(polygons.get(0)));
                    System.out.println("多边形紧凑性比率:" + sr.getPolygonCompactness(polygons.get(0)));
                    System.out.println("多边形形状指数:" + sr.getPolygonShapeIndex(polygons.get(0)));
                    System.out.println("多边形RBF相关外接圆:" + sr.getPolygonRelateBoundingFigure(polygons.get(0)));
                }
            } else if (content.equals("多边形各个心")) {
                if (polygons.size() < 0) {
                    System.out.println("请先输入一个多边形");
                } else {
                    System.out.println("多边形外心坐标:(" + dc.getPolygonExternal(polygons.get(0)).getX() + "," + dc.getPolygonExternal(polygons.get(0)).getY() + ")");
                    System.out.println("多边形质心坐标:(" + dc.getPolygonCentroid(polygons.get(0)).getX() + "," + dc.getPolygonCentroid(polygons.get(0)).getY() + ")");
                    System.out.println("多边形内心坐标:(" + dc.getPolygonInteriorPoint(polygons.get(0)).getX() + "," + dc.getPolygonInteriorPoint(polygons.get(0)).getY() + ")");
                    System.out.println("多边形中心坐标:(" + dc.getPolygonCenter(polygons.get(0)).getX() + "," + dc.getPolygonCenter(polygons.get(0)).getY() + ")");
                }
            } else if (content.equals("多边形面积")) {
                if (polygons.size() <= 0) {
                    System.out.println("请输入至少一个多边形");
                } else {
                    for (int i = 0; i < polygons.size(); i++) {
                        System.out.println("多边形" + i + "面积" + vs.vectorSpaceCal(polygons.get(i).getPolygonPoints()));
                    }
                }
            } else if (content.equals("栅格路径")) {
                g = (Graphics2D) df.getGraphics();
                g.fillRect(0, 0, 30, 30);
                //生成12*12的格网
                for (int i = 0; i < C.rasterSize; i++) {
                    for (int j = 0; j < C.rasterSize; j++) {
                        Random rd = new Random();
                        int col = rd.nextInt(255);
                        g.setColor(new Color(col, col, col));
                        g.fillRect(i * 30, j * 30, 30, 30);
                    }
                }
            } else {
                System.out.println("请选择");
            }

        }
        else if (e.getActionCommand().equals("")) {
            JButton button = (JButton) e.getSource();
            color = button.getBackground();
            System.out.println("color = " + color);
        }else {
            JButton button = (JButton) e.getSource();
            shape = button.getActionCommand();
            System.out.println("String = " + shape);
        }
        if (shape.equals("清空")) {
            g.setColor(Color.white);
            g.fillRect(0,0,df.getWidth(),df.getHeight());
            tempPoints.clear();
            polygons.clear();
            points.clear();
            content = "";
        }else if (shape.equals("点是否在多边形内")) {
            if (polygons == null) {
                System.out.println("请先创建一个多边形");
            } else if (points == null) {
                System.out.println("请先拆创建一个点");
            } else {
                for (int i = 0; i < points.size(); i++) {
                    for (int j = 0; j < polygons.size(); j++) {
                        if (sr.pointWithinPolygonRay(points.get(i), polygons.get(j))) {
                            System.out.println("该点在多边形内部");
                        } else {
                            System.out.println("该店不在多边形内部");
                        }
                    }
                }
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
        if ((shape.equals("多边形")||shape.equals("直线")) && !flag) {
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
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 1) {
            tempPoints.add(new MyPoint(e.getX(), e.getY()));
        }
        if ((shape.equals("多边形")|| shape.equals("直线")) && flag) {
            x2 = e.getX();
            y2 = e.getY();
            if (e.getClickCount() == 2) {
                if(shape.equals("多边形")){
//                    tempPoints.add(new MyPoint(e.getX(), e.getY()));
                    g.drawLine(newx1, newy1, newx2, newy2);
                    flag = false;
                    polygons.add(new MyPolygon(new ArrayList<MyPoint>(tempPoints)));
                    tempPoints.clear();
                }else{
                    flag = false;
                    constraintPoints = new ArrayList<MyPoint>(tempPoints);
                    linePoints = new ArrayList<MyPoint>(tempPoints);
                    lines.add(linePoints);
                    tempPoints.clear();

                }
//                tempPoints.clear();
            }
            g.drawLine(newx2, newy2, x2, y2);
            newx2 = x2;
            newy2 = y2;
        }else if (shape.equals("圆点")) {
            g = (Graphics2D) df.getGraphics();
            g.fillOval(e.getX(), e.getY(), 3, 3);
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
