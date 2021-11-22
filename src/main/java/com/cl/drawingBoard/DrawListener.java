package com.cl.drawingBoard;

import com.cl.pojo.*;
import com.cl.tools.Constants;
import com.cl.tools.Koch.KochLine;
import com.cl.tools.Koch.KochSnow;
import com.cl.tools.Transform;
import com.cl.tools.clustering.KCluster;
import com.cl.tools.delaunayTrangle.CreateDelaunay;
import com.cl.tools.delaunayTrangle.CreateDelaunay2;
import com.cl.tools.delaunayTrangle.LineConstraintDelaunay;
import com.cl.tools.distanceCalculation.DijstraAlgorithm;
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
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
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
    ArrayList<MyClusterPoint> clusterList;
    int [][] matrixWeight;
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
        clusterList = new ArrayList<MyClusterPoint>();
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
                    JOptionPane.showMessageDialog(null, "请先输入一条线段");
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
                    JOptionPane.showMessageDialog(null, "请先输入一条线段");
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
                    JOptionPane.showMessageDialog(null, "请先输入一个多边形");
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
                    JOptionPane.showMessageDialog(null, "请先输入一个多边形");
                }
            } else if (content.equals("点在多边形内判断")) {
                if (polygons.size() == 0) {
                    JOptionPane.showMessageDialog(null, "请先输入一个多边形");
                } else if (points.size() == 0) {
                    JOptionPane.showMessageDialog(null, "请先拆创建一个点");
                } else {
                    String res = "";
                    for (int i = 0; i < points.size(); i++) {
                        for (int j = 0; j < polygons.size(); j++) {
                            if (sr.pointWithinPolygonRay(points.get(i), polygons.get(j))) {
                                res += "点" + String.valueOf(i) + "在多边形内部\n";
                            } else {
                                res += "点" + String.valueOf(i) + "不在多边形内部\n";
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, res);
                }
            } else if (content.equals("两点距离")) {
                if (points.size() != 2) {
                    JOptionPane.showMessageDialog(null, "请先插入两个点");
                } else {
                    String res = "";
                    res += "两点间欧式距离:" + String.valueOf(dc.euclideanDistance(points.get(0), points.get(1))) + "\n";
                    res += "两点间曼哈顿距离:" + String.valueOf(dc.manhattanDistance(points.get(0), points.get(1))) + "\n";
                    res += "两点间切比雪夫距离:" + String.valueOf(dc.chebyshevDistance(points.get(0), points.get(1))) + "\n";
                    res += "两点间明氏距离:" + String.valueOf(dc.minkowskiDistance(points.get(0), points.get(1),3)) + "\n";
                    JOptionPane.showMessageDialog(null, res);
                }
            } else if (content.equals("点到线距离")) {
                if (points.size() != 1) {
                    JOptionPane.showMessageDialog(null, "请先插入一个点");
                } else if (linePoints.size() != 2) {
                    JOptionPane.showMessageDialog(null, "请先插入一条直线");
                } else {
                    String res = "";
                    res += "点到直线的最大距离:" + String.valueOf(dc.pointToLineDistance(points.get(0), new MyLine(linePoints.get(0), linePoints.get(1)), "max")) + "\n";
                    res += "点到直线的最小距离:" + String.valueOf(dc.pointToLineDistance(points.get(0), new MyLine(linePoints.get(0), linePoints.get(1)), "min")) + "\n";
                    res += "点到直线的垂直距离:" + String.valueOf(sr.pointToLine(tf.PointTrans(points.get(0)), tf.LineTrans(new MyLine(linePoints.get(0), linePoints.get(1))))) + "\n";
                    JOptionPane.showMessageDialog(null, res);
                }
            } else if (content.equals("点与面距离")) {
                if (points.size() != 1) {
                    JOptionPane.showMessageDialog(null, "请先插入一个点");
                } else if (polygons.size() != 1) {
                    JOptionPane.showMessageDialog(null, "请先插入一个多边形");
                } else {
                    String res = "";
                    res += "点到多边形的最大距离:" + String.valueOf(dc.pointToPolygonDistance(points.get(0), polygons.get(0), "max")) + "\n";
                    res += "点到多边形的最小距离" + String.valueOf(dc.pointToPolygonDistance(points.get(0), polygons.get(0), "min"));
                    JOptionPane.showMessageDialog(null, res);
                }
            } else if (content.equals("线与线距离")) {
                if (lines.size() != 2) {
                    JOptionPane.showMessageDialog(null, "请先输入两条线");
                } else {
                    String res = "";
                    res += "线与线之间的距离:" + String.valueOf(dc.lineToLineDistance(new MyLine((MyPoint) lines.get(0).get(0), (MyPoint) lines.get(0).get(1)),
                            new MyLine((MyPoint) lines.get(1).get(0), (MyPoint) lines.get(1).get(1))));
                    JOptionPane.showMessageDialog(null, res);
                }
            } else if (content.equals("线与面距离")) {
                if (lines.size() < 0) {
                    JOptionPane.showMessageDialog(null, "请先输入一条线");
                } else if (polygons.size() < 0) {
                    JOptionPane.showMessageDialog(null, "请先输入一个多边形");
                } else {
                    String res = "线到多边形的距离是:" + String.valueOf(dc.lineToPolygonDistance(new MyLine((MyPoint) lines.get(0).get(0), (MyPoint) lines.get(0).get(1)), polygons.get(0)));
                    JOptionPane.showMessageDialog(null, res);
                }
            } else if (content.equals("生成点击凸包")) {
                if (points.size() < 0) {
                    JOptionPane.showMessageDialog(null, "请先插入一个点集");
                } else {
                    //绘制凸包
                    g = (Graphics2D) df.getGraphics();
                    g.setColor(Color.GREEN);
                    ArrayList<MyPoint> polygonConvexHull = sr.getPolygonConvexHull(new MyPolygon(points));
                    if (polygonConvexHull.size() > 4) {
                        for (int i = 0; i < polygonConvexHull.size(); i++) {
                            g.setColor(Color.RED);
                            g.fillOval((int) polygonConvexHull.get(i).getX(), (int) polygonConvexHull.get(i).getY(), 4, 4);
                        }
                        g.setColor(Color.YELLOW);
                        for (int i = 0; i < polygonConvexHull.size() - 1; i++) {
                            g.drawLine((int) polygonConvexHull.get(i).getX(), (int) polygonConvexHull.get(i).getY(), (int) polygonConvexHull.get(i + 1).getX(), (int) polygonConvexHull.get(i + 1).getY());
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "无法形成一个闭合多边形");
                    }
                }
            } else if (content.equals("面状地物量测")) {
                if (polygons.size() < 0) {
                    JOptionPane.showMessageDialog(null, "请先输入一个多边形");
                } else {
                    String res = "";
                    res += "多边形的Perimeter/Area ratio:" + String.valueOf(sr.getP1A(polygons.get(0))) + "\n";
                    res += "多边形Perimeter2/Area ratio:" + String.valueOf(sr.getP2A(polygons.get(0))) + "\n";
                    res += "多边形最长轴:" + String.valueOf(sr.getPolygonMaxAxis(polygons.get(0))) + "\n";
                    res += "多边形最短轴:" + String.valueOf(sr.getPolygonMinAxis(polygons.get(0))) + "\n";
                    res += "多边形形状比:" + String.valueOf(sr.getPolygonFormRatio(polygons.get(0))) + "\n";
                    res += "多边形伸延率:" + String.valueOf(sr.getPolygonElongationRational(polygons.get(0))) + "\n";
                    res += "多边形紧凑性比率:" + String.valueOf(sr.getPolygonCompactness(polygons.get(0))) + "\n";
                    res += "多边形形状指数:" + String.valueOf(sr.getPolygonShapeIndex(polygons.get(0))) + "\n";
                    res += "多边形RBF相关外接圆:" + String.valueOf(sr.getPolygonRelateBoundingFigure(polygons.get(0)));
                    JOptionPane.showMessageDialog(null, res);
                }
            } else if (content.equals("多边形各个心")) {
                if (polygons.size() < 0) {
                    JOptionPane.showMessageDialog(null, "请先输入一个多边形");
                } else {
                    String res = "";
                    res += "多边形外心坐标:(" + String.valueOf(dc.getPolygonExternal(polygons.get(0)).getX()) + "," + String.valueOf(dc.getPolygonExternal(polygons.get(0)).getY()) + ")" + "\n";
                    res += "多边形质心坐标:(" + String.valueOf(dc.getPolygonCentroid(polygons.get(0)).getX()) + "," + String.valueOf(dc.getPolygonCentroid(polygons.get(0)).getY()) + ")" + "\n";
                    res += "多边形内心坐标:(" + String.valueOf(dc.getPolygonInteriorPoint(polygons.get(0)).getX()) + "," + String.valueOf(dc.getPolygonInteriorPoint(polygons.get(0)).getY()) + ")" + "\n";
                    res += "多边形中心坐标:(" + String.valueOf(dc.getPolygonCenter(polygons.get(0)).getX()) + "," + String.valueOf(dc.getPolygonCenter(polygons.get(0)).getY()) + ")" + "\n";
                    JOptionPane.showMessageDialog(null, res);
                }
            } else if (content.equals("多边形面积")) {
                if (polygons.size() <= 0) {
                    JOptionPane.showMessageDialog(null, "请输入至少一个多边形");
                } else {
                    String res = "";
                    for (int i = 0; i < polygons.size(); i++) {
                        res += "多边形" + String.valueOf(i) + "面积" + String.valueOf(vs.vectorSpaceCal(polygons.get(i).getPolygonPoints())) + "\n";
                    }
                    JOptionPane.showMessageDialog(null, res);
                }
            } else if (content.equals("生成栅格")) {
                matrixWeight = new int[C.rasterSize][C.rasterSize];
                g = (Graphics2D) df.getGraphics();
                g.fillRect(0, 0, 30, 30);
                //生成12*12的格网
                for (int i = 0; i < C.rasterSize; i++) {
                    for (int j = 0; j < C.rasterSize; j++) {
                        Random rd = new Random();
                        int col = rd.nextInt(255);
                        matrixWeight[i][j] = col;
                        g.setColor(new Color(col, col, col));
                        g.fillRect(i * C.netSize, j * C.netSize, C.netSize, C.netSize);
                    }
                }
            } else if (content.equals("最短路径")) {
                if (matrixWeight != null) {
                    int size = C.rasterSize;
                    //顶点数
                    int vertex = size * size;
                    int[][] matrix = new int[vertex][vertex];
                    //初始化邻接矩阵
                    for (int i = 0; i < vertex; i++) {
                        for (int j = 0; j < vertex; j++) {
                            matrix[i][j] = 999999999;
                        }
                    }
                    //初始化邻接矩阵
                    //1、从四个顶点开始
                    //左上
                    matrix[0][1] = matrixWeight[0][0] + matrixWeight[0][1];
                    matrix[0][size] = matrixWeight[0][0] + matrixWeight[1][0];
                    //左下
                    matrix[size * (size - 1)][size * (size - 1) + 1] = matrixWeight[size - 1][0] + matrixWeight[size - 1][1];
                    matrix[size * (size - 1)][size * (size - 2)] = matrixWeight[size - 1][0] + matrixWeight[size - 2][0];
                    //右上
                    matrix[size - 1][size - 2] = matrixWeight[0][size - 1] + matrixWeight[0][size - 2];
                    matrix[size - 1][2 * size - 1] = matrixWeight[0][size - 1] + matrixWeight[1][size - 1];
                    //右下
                    matrix[size * size - 1][size * size - 2] = matrixWeight[size - 1][size - 1] + matrixWeight[size - 1][size - 2];
                    matrix[size * size - 1][size * (size - 1)] = matrixWeight[size - 1][size - 1] + matrixWeight[size - 2][size - 1];
                    //2、四条边
                    //上边
                    for (int i = 1; i < size - 1; i++) {
                        matrix[i][i - 1] = matrixWeight[0][i] + matrixWeight[0][i - 1];
                        matrix[i][i + 1] = matrixWeight[0][i] + matrixWeight[0][i + 1];
                        matrix[i][i + size] = matrixWeight[0][i] + matrixWeight[1][i];
                    }
                    //下边
                    for (int i = 1; i<size -1; i++) {
                        matrix[(size - 1) * size + i][(size - 1) * size + i - 1] = matrixWeight[size - 1][i] + matrixWeight[size - 1][i - 1];
                        matrix[(size - 1) * size + i][(size - 1) * size + i + 1] = matrixWeight[size - 1][i] + matrixWeight[size - 1][i + 1];
                        matrix[(size - 1) * size + i][(size - 1) * size + i] = matrixWeight[size - 1][i] + matrixWeight[size - 2][i];
                    }
                    //左边
                    for (int i = 1; i < size - 1; i++) {
                        matrix[i * size][(i - 1) * size] = matrixWeight[i][0] + matrixWeight[i - 1][0];
                        matrix[i * size][(i + 1) * size] = matrixWeight[i][0] + matrixWeight[i + 1][0];
                        matrix[i * size][i * size + 1] = matrixWeight[i][0] + matrixWeight[i][1];
                    }
                    //右边
                    for (int i = 1; i < size - 1; i++) {
                        matrix[(i + 1) * size - 1][i * size - 1] = matrixWeight[i][size - 1] + matrixWeight[i - 1][size - 1];
                        matrix[(i + 1) * size - 1][(i + 2) * size - 1] = matrixWeight[i][size - 1] + matrixWeight[i + 1][size - 1];
                        matrix[(i + 1) * size - 1][(i + 1) * size - 2] = matrixWeight[i][size - 1] + matrixWeight[i][size - 2];
                    }
                    //内部
                    for (int i = 1; i < size - 1; i++) {
                        for (int j = 1; j < size - 1; j++) {
                            matrix[i * size + j][(i - 1) * size + j] = matrixWeight[i][j] + matrixWeight[i - 1][j];
                            matrix[i * size + j][(i + 1) * size + j] = matrixWeight[i][j] + matrixWeight[i + 1][j];
                            matrix[i * size + j][i * size + j - 1] = matrixWeight[i][j] + matrixWeight[i][j - 1];
                            matrix[i * size + j][i * size + j + 1] = matrixWeight[i][j] + matrixWeight[i][j + 1];

                        }
                    }
                    //拆分字符串，将数字提取出来
                    if (points.size() >= 2) {
                        int source = (int) points.get(0).getX() / C.netSize + (int) points.get(0).getY() / C.netSize * C.rasterSize;
                        int end = (int) points.get(1).getX() / C.netSize + (int) points.get(1).getY() / C.netSize * C.rasterSize;
                        DijstraAlgorithm dj =new DijstraAlgorithm();
                        dj.dijstra(matrix, source);
                        String s = dj.getPath()[end];
                        String regex = "\\D+";
                        String[] words = s.split(regex);
                        //绘制最短路线网格
                        g = (Graphics2D) df.getGraphics();
                        g.setColor(Color.RED);
                        for (String word : words) {
                            int i = Integer.parseInt(word);
                            int row = i / size;
                            int col = i % size;
                            g.fillRect(col * C.netSize, row * C.netSize, C.netSize, C.netSize);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "请先输入两个点");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "请先生成矩阵");
                }
            } else if (content.equals("聚类生成")) {

                if (points.size() == 0) {
                    String s = String.valueOf(points.size());
                    JOptionPane.showMessageDialog(null, s);
                }
                else{
                    KCluster kc = new KCluster(points, C.clusterNum);
                    kc.productPoint();
                    kc.moveCore();
                    //将同类的点添加进同一数组
                    for (int i = 0; i < C.clusterNum; i++) {
                        ArrayList<MyPoint>tempPoints = new ArrayList<MyPoint>();
                        for (int j = 0; j < points.size(); j++) {
                            if (points.get(j).getFlage() == (i + 1)) {
                                tempPoints.add(points.get(j));
                            }
                        }
                        clusterList.add(new MyClusterPoint(tempPoints));
                    }
                    //绘制
                    Color[] colors = new Color[]{Color.RED,Color.CYAN,Color.YELLOW,Color.pink};

                    for (int i = 0; i < clusterList.size(); i++) {
                        g = (Graphics2D) df.getGraphics();
                        g.setColor(colors[i]);
                        for (int j = 0; j < clusterList.get(i).getClusterPoints().size(); j++) {
                            g.fillOval((int) clusterList.get(i).getClusterPoints().get(j).getX(), (int) clusterList.get(i).getClusterPoints().get(j).getY(), 5, 5);
                        }
                    }
                }
            } else if (content.equals("聚类距离")) {
                String res = "";
                if (clusterList.size() == 0) {
                    JOptionPane.showMessageDialog(null, "请先生成聚类");
                }else{
                    for (int i = 0; i < clusterList.size() - 1; i++) {
                        for (int j = i + 1; j < clusterList.size(); j++) {
                            res += "聚类" + String.valueOf(i) + "到聚类" + String.valueOf(j) + "的最小距离:" + String.valueOf(clusterList.get(i).MinDistance(clusterList.get(j))) + "\n";
                            res += "聚类" + String.valueOf(i) + "到聚类" + String.valueOf(j) + "的最大距离:" + String.valueOf(clusterList.get(i).MinDistance(clusterList.get(j))) + "\n";
                            res += "聚类" + String.valueOf(i) + "到聚类" + String.valueOf(j) + "的中间距离:" + String.valueOf(clusterList.get(i).CenterDistance(clusterList.get(j))) + "\n";
                            res += "聚类" + String.valueOf(i) + "到聚类" + String.valueOf(j) + "的重心距离:" + String.valueOf(clusterList.get(i).CentroidDistance(clusterList.get(j))) + "\n";
                            res += "聚类" + String.valueOf(i) + "到聚类" + String.valueOf(j) + "的平均距离:" + String.valueOf(clusterList.get(i).AverageDistance(clusterList.get(j))) + "\n";
                            res += "聚类" + String.valueOf(i) + "到聚类" + String.valueOf(j) + "的Ward距离:" + String.valueOf(clusterList.get(i).WardDistance(clusterList.get(j))) + "\n";
                            res += "=========================================\n";
                        }

                    }
                    JOptionPane.showMessageDialog(null, res);
                }
            } else if (content.equals("导入DEM")) {
                String filePath = "";
                //选择文件
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "DEM_text", "txt", "gif");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(chooser);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("你选择的路径是: " +
                            chooser.getSelectedFile().getPath());
                    filePath = chooser.getSelectedFile().getPath();
                }
                //文件流载入DEM数据
                MyDEM dem = new MyDEM();
                File file = new File(filePath);
                BufferedReader reader = null;
                String tempString = null;
                int line =1;
                try {
                    System.out.println("以行为单位读取文件内容，一次读一整行：");
                    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
                    while ((tempString = reader.readLine()) != null) {
                        String regex = "\\D+";
                        if (line == 1) {
                            //字符拆分
                            String[] words = tempString.split(regex);
                            //写入列
                            dem.setNcols(Integer.parseInt(words[1]));
                        }else if(line == 2) {
                            String[] words = tempString.split(regex);
                            //写入行
                            dem.setNrows(Integer.parseInt(words[1]));
                        }else if(line == 3) {
                            String[] words = tempString.split(regex);
                            dem.setXllcorner(Double.parseDouble(words[1]));
                        }else if(line == 4) {
                            String[] words = tempString.split(regex);
                            dem.setYllcorner(Double.parseDouble(words[1]));
                        }else if(line == 5) {
                            String[] words = tempString.split(regex);
                            dem.setCellsize(Double.parseDouble(words[1]));
                        } else if (line == 6) {
                            String[] words = tempString.split(regex);
                            dem.setNODATA_value(Double.parseDouble(words[1]));
                            dem.points = new MyPoint[dem.getNrows()][dem.getNcols()];
                        } else {
                            String[] words = tempString.split(regex);
                            for (int i = 0; i < dem.getNcols(); i++) {
                                dem.points[line - 7][i] = new MyPoint(dem.getXllcorner() + (line - 7) * dem.getCellsize(), dem.getYllcorner() + i * dem.getCellsize(), Double.parseDouble(words[i]));
                            }
                        }
                        line ++ ;
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //高度赋值
                dem.SetHeight();
                dem.setSlopeAndAspect();
                System.out.println(dem.height.toString());

                //绘制点集
                g = (Graphics2D) df.getGraphics();
                g.setColor(Color.RED);
                for (int i = 0; i < dem.getNrows(); i++) {
                    for (int j = 0; j < dem.getNcols(); j++) {
                        g.fillOval((int)dem.points[i][j].getX(), (int)dem.points[i][j].getY(), 3, 3);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "请选择一项操作");
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
            clusterList.clear();
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
