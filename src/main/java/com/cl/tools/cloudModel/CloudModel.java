package com.cl.tools.cloudModel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author DJLobster
 * 正态云发生器
 */
public class CloudModel {
    private double Ex;
    private double En;
    private double He;
    private int N;
    private List<Double> x;
    private List<Double> y;
    private Random random = new Random();

    public CloudModel(double ex, double en, double he, int n) {
        Ex = ex;
        En = en;
        He = he;
        N = n;
        x = new ArrayList<Double>();
        y = new ArrayList<Double>();
    }

    public CloudModel() {
    }

    public void getCloudDrops()
    {
        /*
         * 产生服从N(a,b)随机数
         * Math.sqrt(b)*random.nextGaussian()+a;
         */
        for(int i = 0 ; i < N ; ++i)
        {
            double En2 = He * random.nextGaussian() + En;
            x.add(En2 * random.nextGaussian() + Ex);
            y.add(Math.exp(-1 * Math.pow((double)x.get(i) - Ex, 2.0) / (2 * Math.pow(En2, 2.0))));
        }
    }

    public void print_x_y()
    {
        System.out.println("\nData:");
        for (int i = 0 ; i < N ; ++i) {
            System.out.println(i + " (" + (double) x.get(i) + "," + (double) y.get(i) + ")");
        }
        System.out.println();
    }

    public void write_Excel(String _url) throws FileNotFoundException
    {
        /*
         * 数据写入到Excel中
         * */
        String url = "F:\\StudyingFiles\\SpatialAnalysis\\SpatialAnalysisCoding\\src";
        url += _url ;
        FileOutputStream out = new FileOutputStream(url);
        XSSFWorkbook wb = new XSSFWorkbook();

        //创建1页;
        XSSFSheet sheets = wb.createSheet("Root");
        for (int i = 0; i < N; i++) {
            // 创建行数
            XSSFRow rows = sheets.createRow(i);
            //写入单元格
            //int totalRows = sheets.getLastRowNum() + 1;
            rows.createCell(0).setCellValue((double)x.get(i));
            rows.createCell(1).setCellValue((double)y.get(i));
        }
        try {
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n数据写入Excel成功!!!\n");
    }

}
