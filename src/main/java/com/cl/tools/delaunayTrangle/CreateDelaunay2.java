package com.cl.tools.delaunayTrangle;

import com.cl.pojo.MyLine;
import com.cl.pojo.MyPoint;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;

import java.util.ArrayList;

public class CreateDelaunay2 {
    private ArrayList<MyPoint> points;
    DistanceCal dc = new DistanceCalImpl();
    double[] X;
    double[] Y;
    boolean LineXY[];
    int num;
    private ArrayList<MyLine> allLines;

    public CreateDelaunay2(ArrayList<MyPoint> points) {
        this.points = points;
        allLines = new ArrayList<MyLine>();
        num = points.size();
        X = new double[num];
        Y = new double[num];
        LineXY = new boolean[(1 + num) * num / 2];
        for (int i = 0; i < LineXY.length; i++) {
            LineXY[i] = false;
        }
        for(int i = 0; i < num; i++) {
            X[i] = points.get(i).getX();
            Y[i] = points.get(i).getY();
        }
    }

    public CreateDelaunay2() {
    }

    public ArrayList<MyPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<MyPoint> points) {
        this.points = points;
    }

    public ArrayList<MyLine> getAllLines() {
        return allLines;
    }

    public void setAllLines(ArrayList<MyLine> allLines) {
        this.allLines = allLines;
    }

    //判断两点是否连线
    boolean isLinked(int p1, int p2) {
        if (p1 >= p2) {
            return LineXY[(1 + p1) * p1 / 2 + p2];
        }else{
            return LineXY[(1 + p2) * p2 / 2 + p1];
        }
    }

    //存储已经绘制过的线
    void Link(int p1, int p2) {
        if (p1 >= p2)
            LineXY[(1 + p1) * p1 / 2 + p2] = true;
        else
            LineXY[(1 + p2) * p2 / 2 + p1] = true;
    }

    public void initDelaunay(){
        double max = 999999999;			// 用窗口的长和宽来定义一个相对大的数
        int lx = 0;											// 储存临时 X 方向产生的变量
        int ly = 0;                                            // 储存临时 Y 方向产生的变量
        int[] Z = new int[num+1];
        int li = 0;                                            // 用于储存临时判断过的点的下
        //在点集中一次判断，找到最短距离的两个点
        for (int i = 0; i < num; i++) {
            for (int j = i + 1; j < num; j++) {
                if (max > dc.euclideanDistance(points.get(i), points.get(j))) {
                    lx = i;
                    ly = j;
                    max = dc.euclideanDistance(points.get(i), points.get(j));
                }
            }
        }
        allLines.add(new MyLine(points.get(lx), points.get(ly)));
        Link(lx, ly);
        Z[0] = lx;
        Z[1] = ly;
        int n = 2;
        while (true) {
            if (n > num) {
                break;
            }
            int m = 0;
            double rad,Xd,Yd,Rd;
            boolean ok = false;
            max = 999999999;
            for (int i = 0; i < num; i++) {
                m = 0;
                ok = false;
                //判断点是否判断过，如果是，返回判断下一个点，不是继续
                while (true) {
                    if (m >= n) {
                        m = 0;
                        break;
                    }
                    if (i == Z[m]) {
                        ok = true;
                        break;
                    }
                    m++;
                }
                if (ok == true) {
                    continue;
                }
                // 在已经确定的两个点和未确定的点进行计算它们形成三角形的的半径，并判断形成的圆内有无其它的点
                // 若无其它的点，则可以连线，如有其它的点，则进行判断下一个点
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        rad = get_circle(points.get(Z[j]), points.get(Z[k]), points.get(i))[2];
                        Xd = get_circle(points.get(Z[j]), points.get(Z[k]), points.get(i))[0];
                        Yd = get_circle(points.get(Z[j]), points.get(Z[k]), points.get(i))[1];
                        Rd = get_circle(points.get(Z[j]), points.get(Z[k]), points.get(i))[2];
                        int cc = 0;
                        ok = false;
                        while (true) {
                            if (cc >= num) {
                                break;
                            }
                            //判断圆内有无其它点，并且这个被判断的点不能为形成这个圆的这个三个点，如果有其它点，就跳出该循环
                            if(dc.euclideanDistance(new MyPoint(Xd,Yd),new MyPoint(X[cc],Y[cc])) <= Rd
                                    && cc != Z[k] && cc !=Z[j] && cc != i){
                                ok = true;
                                break;
                            }
                            cc++;
                        }
                        //圆内无其他点，结束本次循环
                        if (ok == true) {
                            continue;
                        }
                        // 在三个点围成圆内没有点找到半径最小
                        if (max >= rad && rad != 0) {
                            lx = Z[j];
                            ly = Z[k];
                            //存储线段判断是否已经存储过
                            if (isLinked(i, lx) == false) {
                                Link(i, lx);
                                allLines.add(new MyLine(points.get(i), points.get(lx)));
                            }

                            if (isLinked(i, ly) == false) {
                                Link(i, ly);
                                allLines.add(new MyLine(points.get(i), points.get(ly)));
                            }
                            if (isLinked(lx, ly) == false) {
                                Link(lx, ly);
                                allLines.add(new MyLine(points.get(lx), points.get(ly)));
                            }
                            li = i;
                        }

                    }
                }

            }
            Z[n] = li;
            n++;
        }
    }

    //根据三个点坐标计算外接圆圆心和半径
    double[] get_circle(MyPoint p1, MyPoint p2, MyPoint p3) {
        double x1 = p1.getX();
        double x2 = p2.getX();
        double x3 = p3.getX();
        double y1 = p1.getY();
        double y2 = p2.getY();
        double y3 = p3.getY();
        double a = ((y2 - y1) * (y3 * y3 - y1 * y1 + x3 * x3 - x1 * x1) - (y3 - y1) * (y2 * y2 - y1 * y1 + x2 * x2 - x1 * x1)) / (
                2.0 * ((x3 - x1) * (y2 - y1) - (x2 - x1) * (y3 - y1)));
        double b = ((x2 - x1) * (x3 * x3 - x1 * x1 + y3 * y3 - y1 * y1) - (x3 - x1) * (x2 * x2 - x1 * x1 + y2 * y2 - y1 * y1)) / (
                2.0 * ((y3 - y1) * (x2 - x1) - (y2 - y1) * (x3 - x1)));
        double r = Math.sqrt((x1 - a) * (x1 - a) + (y1 - b) * (y1 - b));
        double[] rs = {a, b, r};
        return rs;
    }
}
