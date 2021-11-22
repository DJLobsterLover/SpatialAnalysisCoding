package com.cl.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class MyDEM {
    private int ncols;
    private int nrows;
    private double xllcorner;
    private double yllcorner;
    private double cellsize;
    private double NODATA_value;

    public MyPoint[][] points;
    public double[][] height;//存储高度
    public double[][] slopes;//存储坡度
    public double[][] aspects;//存储坡向
    public double[][] pMatrix;//p值矩阵
    public double[][] qMatrix;//q值矩阵
    public double[][] Kv;//剖面曲率
    public double[][] Kh;//平面曲率

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



    }

    public void setSlopeAndAspect() {
        if (height != null) {
            slopes = new double[nrows][ncols];
            aspects = new double[nrows][ncols];
            for (int i = 1; i < nrows + 1; i++) {
                for (int j = 1; j < ncols + 1; j++) {
                    double slopeWe = (height[i][j - 1] - height[i][j + 1]) / 2 * cellsize;
                    double slopeSn = (height[i - 1][j] - height[i + 1][j]) / 2 * cellsize;
                    double slope = Math.sqrt(Math.pow(slopeWe, 2) + Math.pow(slopeSn, 2));
                    slopes[i - 1][j - 1] = slope;
                    aspects[i - 1][j - 1] = slopeSn / slopeWe;
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
}
