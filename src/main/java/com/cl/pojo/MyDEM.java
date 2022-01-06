package com.cl.pojo;


import com.cl.tools.GeometryBuilder;
import com.cl.tools.Transform;
import com.cl.tools.vectorSpaceCal.VectorSpaceCal;
import com.cl.tools.vectorSpaceCal.VectorSpaceCalImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.awt.*;
import java.util.ArrayList;


public class MyDEM {
    private int ncols;
    private int nrows;
    private double xllcorner;
    private double yllcorner;
    private double cellsize;
    private double NODATA_value;
    private double demArea;

    public MyPoint[][] points;
    public ArrayList<MyPoint> demPoints;
    public float[][] colors;//颜色数值
    public float[][] changeColors;//叠加分析后的颜色数值
    public double[][] pFactors;//宏观坡向因子
    public double[][] height;//存储高度
    public double[][] slopes;//存储坡度
    public double[][] aspects;//存储坡向
    public double[][] pMatrix;//p值矩阵
    public double[][] qMatrix;//q值矩阵
    public double[][] Kv;//剖面曲率
    public double[][] Kh;//平面曲率
    public double[][] R;//地表粗糙度
    public ArrayList<MyPoint> peaks;//山顶点
    public ArrayList<MyPoint> pits;//凹陷点
    public ArrayList<MyPoint> ridges;//山脊点
    public ArrayList<MyPoint> channels;///山谷点
    public ArrayList<MyPoint> pass;//鞍点
    public ArrayList<MyPoint> plane;//平地点
    public int[][] changeHeight;//叠加分析后的高度矩阵
    public double[][] areas;//每个格网的面积
    public double[][] volumes;//每个格网上的体积
    VectorSpaceCal vs = new VectorSpaceCalImpl();



    public void init() {
        this.demArea = 0;
        SetHeight();
        setSlopeAndAspect();
        setKvAndKh();
        setPFactor();
        setSlopeComplexityFactor();
        setColorsMatrix();
        setPeakAndPit();
        setRidgesChannelPass();
        pointsToArray();
        setAreas();
        setVolume();
    }

    public void SetHeight() {
        if (nrows > 0 && ncols > 0 && points != null) {
            this.height = new double[nrows + 2][ncols + 2];
            //内部
            for (int i = 1; i < nrows + 1; i++) {
                for (int j = 1; j < ncols + 1; j++) {
                    height[i][j] = points[i - 1][j - 1].getZ();
                }
            }
            //四个顶点
            height[0][0] = points[0][0].getZ();
            height[0][ncols + 1] = points[0][ncols - 1].getZ();
            height[nrows + 1][0] = points[nrows - 1][0].getZ();
            height[nrows + 1][ncols + 1] = points[nrows - 1][ncols - 1].getZ();
            //四条边
            for (int i = 1; i < ncols + 1; i++) {
                height[0][i] = points[0][i - 1].getZ();
            }
            for (int i = 1; i < ncols + 1; i++) {
                height[nrows + 1][i] = points[nrows - 1][i - 1].getZ();
            }
            for (int i = 1; i < nrows + 1; i++) {
                height[i][0] = points[i - 1][0].getZ();
            }
            for (int i = 1; i < nrows + 1; i++) {
                height[i][ncols + 1] = points[i - 1][ncols - 1].getZ();
            }
        }
    }

    public void setKvAndKh() {
        double heightExpend[][] = new double[nrows + 4][ncols + 4];
        //内部
        for (int i = 1; i < nrows + 1; i++) {
            for (int j = 1; j < ncols + 1; j++) {
                heightExpend[i][j] = height[i - 1][j - 1];
            }
        }
        //四个顶点
        heightExpend[0][0] = height[0][0];
        heightExpend[0][ncols + 3] = height[0][ncols + 1];
        heightExpend[nrows + 3][0] = height[nrows + 1][0];
        heightExpend[nrows + 3][ncols + 3] = height[nrows + 1][ncols + 1];
        //四条边
        for (int i = 1; i < ncols + 3; i++) {
            heightExpend[0][i] = height[0][i - 1];
        }
        for (int i = 1; i < ncols + 3; i++) {
            heightExpend[nrows + 3][i] = height[nrows - 1][i - 1];
        }
        for (int i = 1; i < nrows + 3; i++) {
            heightExpend[i][0] = height[i - 1][0];
        }
        for (int i = 1; i < nrows + 3; i++) {
            heightExpend[i][ncols + 3] = height[i - 1][ncols - 1];
        }

        //计算p值矩阵和q值矩阵
        pMatrix = new double[nrows + 2][ncols + 2];
        qMatrix = new double[nrows + 2][ncols + 2];

        for (int i = 1; i < nrows + 3; i++) {
            for (int j = 1; j < ncols + 3; j++) {
                pMatrix[i - 1][j - 1] = ((heightExpend[i - 1][j - 1] + 2 * heightExpend[i][j - 1] + heightExpend[i + 1][j - 1]) - (heightExpend[i - 1][j + 1] + 2 * heightExpend[i][j + 1] + heightExpend[i + 1][j + 1])) / (8 * cellsize);
                qMatrix[i - 1][j - 1] = ((heightExpend[i - 1][j - 1] + 2 * heightExpend[i - 1][j] + heightExpend[i - 1][j + 1]) - (heightExpend[i + 1][j - 1] + 2 * heightExpend[i + 1][j] + heightExpend[i + 1][j + 1])) / (8 * cellsize);
            }
        }
        //计算Kv和Kh
        Kv = new double[nrows][ncols];
        Kh = new double[nrows][ncols];

        for (int i = 1; i < nrows + 1; i++) {
            for (int j = 1; j < ncols + 1; j++) {
                Kv[i - 1][j - 1] = ((pMatrix[i - 1][j - 1] + 2 * pMatrix[i][j - 1] + pMatrix[i + 1][j - 1]) - (pMatrix[i - 1][j + 1] + 2 * pMatrix[i][j + 1] + pMatrix[i + 1][j + 1])) / (8 * cellsize);
                Kh[i - 1][j - 1] = ((qMatrix[i - 1][j - 1] + 2 * qMatrix[i - 1][j] + qMatrix[i - 1][j + 1]) - (qMatrix[i + 1][j - 1] + 2 * qMatrix[i + 1][j] + qMatrix[i + 1][j + 1])) / (8 * cellsize);
            }
        }

    }

    public void setSlopeAndAspect() {
        if (height != null) {
            slopes = new double[nrows][ncols];
            aspects = new double[nrows][ncols];
            for (int i = 1; i < nrows + 1; i++) {
                for (int j = 1; j < ncols + 1; j++) {
                    double slopeWe = (height[i][j - 1] - height[i][j + 1]) / 2 * cellsize;
                    double slopeSn = (height[i - 1][j] - height[i + 1][j]) / 2 * cellsize;
                    double slope =Math.sqrt(Math.pow(slopeWe, 2) + Math.pow(slopeSn, 2));
                    slopes[i - 1][j - 1] = slope;
                    aspects[i - 1][j - 1] = Math.toDegrees(Math.atan(slopeSn / slopeWe));
                }
            }
        }
    }

    //设置宏观波形因子
    public void setPFactor() {
        if (height != null) {
            pFactors = new double[nrows][ncols];
            for (int i = 1; i < nrows + 1; i++) {
                for (int j = 1; j < ncols + 1; j++) {
                    double temp = (height[i - 1][j - 1] + height[i - 1][j] + height[i - 1][j + 1] + height[i][j - 1] + height[i][j + 1] + height[i + 1][j - 1] + height[i + 1][j] + height[i + 1][j + 1]) / 8;
                    pFactors[i - 1][j - 1] = height[i][j] - temp;
                }
            }
        }
    }

    //计算坡面复杂度因子
    public void setSlopeComplexityFactor() {
        if (height != null) {
            R = new double[nrows][ncols];
            for (int i = 1; i < nrows + 1; i++) {
                for (int j = 1; j < ncols + 1; j++) {
                    R[i - 1][j - 1] = 1 / Math.cos(slopes[i - 1][j - 1]);
                }
            }
        }
    }

    //设置颜色矩阵
    public void setColorsMatrix() {
        double maxValue = 0;
        double minValue = 999999999;
        //找到高程数值中的最大值和最小值
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                if (points[i][j].getZ() != NODATA_value) {
                    if (points[i][j].getZ() < minValue) {
                        minValue = points[i][j].getZ();
                    }
                    if (points[i][j].getZ() > maxValue) {
                        maxValue = points[i][j].getZ();
                    }
                }
            }
        }
        System.out.println(maxValue);
        System.out.println(minValue);
        double range = maxValue - minValue;
        colors = new float[nrows][ncols];
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                if (points[i][j].getZ() == NODATA_value) {
                    colors[i][j] = 0;
                } else {
                    colors[i][j] = (float)((points[i][j].getZ() - minValue) * 255 / range);
                }
            }
        }
    }

    //生成叠加分析后的颜色矩阵
    public void setChangeColor(MyDEM dem2) {
        if (changeHeight != null) {
            double maxValue = 0;
            double minValue = 999999999;
            //找到高程数值中的最大值和最小值
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    if (points[i][j].getZ() != NODATA_value && dem2.points[i][j].getZ() != dem2.getNODATA_value()) {
                        if (changeHeight[i][j] < minValue) {
                            minValue = changeHeight[i][j];
                        }
                        if (changeHeight[i][j] > maxValue) {
                            maxValue = changeHeight[i][j];
                        }
                    }
                }
            }
            double range = maxValue - minValue;
            changeColors = new float[nrows][ncols];
            for (int i = 0; i < nrows; i++) {
                for (int j = 0; j < ncols; j++) {
                    if (points[i][j].getZ() == NODATA_value || dem2.points[i][j].getZ() == dem2.getNODATA_value()) {
                        changeColors[i][j] = 0;
                    } else {
                        changeColors[i][j] = (float)((changeHeight[i][j] - minValue) * 255 / range);
                    }
                }
            }
        }
    }

    //计算地形起伏度和地表切割深度
    public double[] getRelativeRelief(int i, int j, int edge) {
        GeometryBuilder gb = new GeometryBuilder();
        Transform tf = new Transform();
        ArrayList<MyPoint> tempList = new ArrayList<MyPoint>();
        //目标点为圆心，edge为半径做圆
        Polygon circle = gb.createCircle(points[i][j].getX(), points[i][j].getY(), edge * cellsize);
        //找到范围圆内的所有点
        for (int m = 0; m < nrows; m++) {
            for (int n = 0; n < ncols; n++) {
                Point tempPoint = tf.PointTrans(points[m][n]);
                //如果点在范围圆内
                if (tempPoint.within(circle)) {
                    tempList.add(points[m][n]);
                }
            }
        }
        //找到最大值和最小值
        double maxValue = 0;
        double minValue = 999999999;
        double sum = 0;
        //找到高程数值中的最大值和最小值
        for (int m = 0; m < tempList.size(); m++) {
            if (tempList.get(m).getZ() != NODATA_value) {
                if (tempList.get(m).getZ() < minValue) {
                    minValue = tempList.get(m).getZ();
                }
                if (tempList.get(m).getZ() > maxValue) {
                    maxValue = tempList.get(m).getZ();
                }
                sum += tempList.get(m).getZ();
            }
        }
        //计算区域内高程平均值
        double avgValue = sum / tempList.size();
        double[] res = new double[2];
        res[0] = maxValue - minValue;
        res[1] = avgValue - minValue;
        return res;
    }

    //计算高程变异系数
    public double getElevationVariation(int i, int j) {
        //获取平均值
        double avgValue = (points[i][j].getZ() + points[i][j + 1].getZ() + points[i - 1][j].getZ() + points[i - 1][j + 1].getZ()) / 4;
        //计算标准差
        double s = Math.sqrt((Math.pow((points[i][j].getZ() - avgValue), 2) + Math.pow((points[i][j + 1].getZ() - avgValue), 2) + Math.pow((points[i - 1][j].getZ() - avgValue), 2) +
                Math.pow((points[i - 1][j + 1].getZ() - avgValue), 2)) / 3);
        return s / avgValue;
    }

    //计算山顶点和凹陷点
    public void setPeakAndPit() {
        if (height != null) {
            peaks = new ArrayList<MyPoint>();
            pits = new ArrayList<MyPoint>();
            plane = new ArrayList<MyPoint>();
            for (int i = 1; i < nrows + 1; i++) {
                for (int j = 1; j < ncols + 1; j++) {
                    if (height[i][j] != NODATA_value) {
                        //如果此点高程大于领域内所有，即为山顶点
                        if (height[i][j] > height[i][j-1]) {
                            if (height[i][j] > height[i][j+1]) {
                                if (height[i][j] > height[i - 1][j - 1]) {
                                    if (height[i][j] > height[i - 1][j]) {
                                        if (height[i][j] > height[i - 1][j + 1]) {
                                            if (height[i][j] > height[i + 1][j - 1]) {
                                                if (height[i][j] > height[i + 1][j]) {
                                                    if (height[i][j] > height[i + 1][j + 1]) {
                                                        peaks.add(points[i-1][j-1]);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        //如果此点高程小于领域内所有，即为凹陷点
                        if (height[i][j] != NODATA_value) {
                            if (height[i][j] < height[i][j+1]) {
                                if (height[i][j] < height[i - 1][j - 1]) {
                                    if (height[i][j] < height[i - 1][j]) {
                                        if (height[i][j] < height[i - 1][j + 1]) {
                                            if (height[i][j] < height[i + 1][j - 1]) {
                                                if (height[i][j] < height[i + 1][j]) {
                                                    if (height[i][j] < height[i + 1][j + 1]) {
                                                        pits.add(points[i - 1][j - 1]);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        //如果此点高程等于领域所有，平地点
                        if (height[i][j] == height[i][j-1]) {
                            if (height[i][j] == height[i][j+1]) {
                                if (height[i][j] == height[i - 1][j - 1]) {
                                    if (height[i][j] == height[i - 1][j]) {
                                        if (height[i][j] == height[i - 1][j + 1]) {
                                            if (height[i][j] == height[i + 1][j - 1]) {
                                                if (height[i][j] == height[i + 1][j]) {
                                                    if (height[i][j] == height[i + 1][j + 1]) {
                                                        plane.add(points[i-1][j-1]);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    //就算山脊山谷鞍点
    public void setRidgesChannelPass() {
        if (height != null) {
            ridges = new ArrayList<MyPoint>();
            channels = new ArrayList<MyPoint>();
            pass = new ArrayList<MyPoint>();
            for (int i = 1; i < nrows + 1; i++) {
                for (int j = 1; j < ncols + 1; j++) {
                    int rs = 0;
                    boolean b1 = false;
                    boolean b2 = false;
                    boolean b3 = false;
                    boolean b4 = false;
                    if ((height[i][j - 1] - height[i][j]) * (height[i][j + 1] - height[i][j]) > 0) {
                        if (height[i][j + 1] > height[i][j]) {
                            rs = -1;
                            b1 = true;
                        }
                        if (height[i][j + 1] < height[i][j]) {
                            rs = 1;
                            b2 = true;
                        }
                    }
                    if ((height[i - 1][j] - height[i][j]) * (height[i + 1][j] - height[i][j]) > 0) {
                        if (height[i - 1][j] > height[i][j]) {
                            rs = -1;
                            b3 = true;
                        }
                        if (height[i + 1][j] < height[i][j]) {
                            rs = 1;
                            b4 = true;
                        }
                    }

                    if ((b1 && b4) || (b2 && b3)) {
                        rs = 2;
                    }

                    if (rs == -1) {
                        //表示谷点
                        channels.add(points[i - 1][j - 1]);
                    } else if (rs == 1) {
                        //表示山脊点
                        ridges.add(points[i - 1][j - 1]);
                    } else if (rs == 2) {
                        //表示鞍点
                        pass.add(points[i - 1][j - 1]);
                    }
                }
            }
        }
    }

    //重绘
    public void reDraw(Graphics g,MyDEM dem,int x,int y,float[][] colors) {
        for (int i = 0; i < dem.getNrows(); i++) {
            for (int j = 0; j < dem.getNcols(); j++) {
                g.setColor(new Color((int) colors[i][j], (int) colors[i][j], (int) colors[i][j]));
                g.fillRect((int) dem.points[i][j].getX() - (int) (0.5 * dem.getCellsize()) + x, (int) dem.points[i][j].getY() - (int) (0.5 * dem.getCellsize()) + y, (int) dem.getCellsize(), (int) dem.getCellsize());
            }
        }
    }

    public void reDraw(Graphics g,MyDEM dem) {
        for (int i = 0; i < dem.getNrows(); i++) {
            for (int j = 0; j < dem.getNcols(); j++) {
                g.setColor(new Color((int) dem.colors[i][j], (int) dem.colors[i][j], (int) dem.colors[i][j]));
                g.fillRect((int) dem.points[i][j].getX() - (int) (0.5 * dem.getCellsize()), (int) dem.points[i][j].getY() - (int) (0.5 * dem.getCellsize()), (int) dem.getCellsize(), (int) dem.getCellsize());
            }
        }
    }

    public void pointsToArray() {
        demPoints = new ArrayList<MyPoint>();
        for (int i = 0; i < nrows; i++) {
            for (int j = 0; j < ncols; j++) {
                demPoints.add(points[i][j]);
            }
        }
    }

    public void setAreas() {
        if (height != null) {
            areas = new double[nrows][ncols];
            //周围有8个领域
            for (int i = 1; i < nrows-1; i++) {
                for (int j = 1; j < ncols-1; j++) {
                    double temp = 0;
                    //分别计算8个三角形
                    ArrayList tempList = new ArrayList<MyPoint>();
                    tempList.add(points[i][j]);
                    //1
                    tempList.add(points[i - 1][j - 1]);
                    tempList.add(points[i - 1][j]);
                    temp += vs.triangleArea(tempList);
                    tempList.remove(2);
                    tempList.remove(1);
                    //2
                    tempList.add(points[i - 1][j]);
                    tempList.add(points[i - 1][j + 1]);
                    temp += vs.triangleArea(tempList);
                    tempList.remove(2);
                    tempList.remove(1);
                    //3
                    tempList.add(points[i - 1][j - 1]);
                    tempList.add(points[i][j - 1]);
                    temp += vs.triangleArea(tempList);
                    tempList.remove(2);
                    tempList.remove(1);
                    //4
                    tempList.add(points[i - 1][j + 1]);
                    tempList.add(points[i][j + 1]);
                    temp += vs.triangleArea(tempList);
                    tempList.remove(2);
                    tempList.remove(1);
                    //5
                    tempList.add(points[i][j - 1]);
                    tempList.add(points[i + 1][j - 1]);
                    temp += vs.triangleArea(tempList);
                    tempList.remove(2);
                    tempList.remove(1);
                    //6
                    tempList.add(points[i + 1][j - 1]);
                    tempList.add(points[i + 1][j]);
                    temp += vs.triangleArea(tempList);
                    tempList.remove(2);
                    tempList.remove(1);
                    //7
                    tempList.add(points[i + 1][j]);
                    tempList.add(points[i + 1][j + 1]);
                    temp += vs.triangleArea(tempList);
                    tempList.remove(2);
                    tempList.remove(1);
                    //8
                    tempList.add(points[i][j + 1]);
                    tempList.add(points[i + 1][j + 1]);
                    temp += vs.triangleArea(tempList);
                    tempList.remove(2);
                    tempList.remove(1);

                    areas[i][j] = temp / 4;
                    demArea += areas[i][j];
                }
            }
        }

    }

    public void setVolume() {
        if (height != null) {
            volumes = new double[nrows][ncols];
            //周围有8个领域
            for (int i = 1; i < nrows - 1; i++) {
                for (int j = 1; j < ncols - 1; j++) {
                    volumes[i][j] = areas[i][j] * points[i][j].getZ();
                }
            }
        }
    }






    public MyDEM() {
    }

    public int getNcols() {
        return ncols;
    }

    public void setNcols(int ncols) {
        this.ncols = ncols;
    }

    public int getNrows() {
        return nrows;
    }

    public void setNrows(int nrows) {
        this.nrows = nrows;
    }

    public double getXllcorner() {
        return xllcorner;
    }

    public void setXllcorner(double xllcorner) {
        this.xllcorner = xllcorner;
    }

    public double getYllcorner() {
        return yllcorner;
    }

    public void setYllcorner(double yllcorner) {
        this.yllcorner = yllcorner;
    }

    public double getCellsize() {
        return cellsize;
    }

    public void setCellsize(double cellsize) {
        this.cellsize = cellsize;
    }

    public double getNODATA_value() {
        return NODATA_value;
    }

    public void setNODATA_value(double NODATA_value) {
        this.NODATA_value = NODATA_value;
    }

    public void setChangeHeight(int[][] changeHeight) {
        this.changeHeight = changeHeight;
    }

    public double getDemArea() {
        return demArea;
    }

    public void setDemArea(double demArea) {
        this.demArea = demArea;
    }
}
