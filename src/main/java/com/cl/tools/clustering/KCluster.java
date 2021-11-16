package com.cl.tools.clustering;


import com.cl.pojo.MyPoint;
import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;

import java.util.ArrayList;
import java.util.Random;

/**
 * 聚类函数
 */
public class KCluster {
    private ArrayList<MyPoint> ypo;
    private MyPoint[] pacore;
    private MyPoint[] pacoren;
    private int core; //聚类中心个数

    public KCluster() {
    }

    public KCluster(ArrayList<MyPoint> ypo, int core) {
        this.ypo = ypo;
        this.core = core;
    }

    public void productPoint() {
        if (core > 1) {
            int num = ypo.size();

            //初始化类聚中的心的位置
            pacore = new MyPoint[core];
            pacoren = new MyPoint[core];
            Random rand = new Random();
            int temp[] = new int[core];
            temp[0] = rand.nextInt(num);
            pacore[0] = new MyPoint();
            pacore[0].setX(ypo.get(temp[0]).getX());
            pacore[0].setY(ypo.get(temp[0]).getY());
            pacore[0].setFlage(0);

            //避免产生重复的点
            for (int i = 0; i < core; i++) {
                int flage = 0;
                int thistemp = rand.nextInt(num);
                for (int j = 0; j < i; j++) {
                    if (temp[j] == thistemp) {
                        flage = 1;//有重复
                        break;
                    }
                }
                if (flage == 1) {
                    i--;
                } else {
                    pacore[i] = new MyPoint(ypo.get(thistemp).getX(), ypo.get(thistemp).getY());
                    pacore[i].setFlage(0);//表示聚类中心
                }
            }
            System.out.println("初始化类聚中心");
            for (int i = 0; i > pacore.length; i++) {
                System.out.println(pacore[i].getX() + " " + pacore[i].getY());
            }
        }
    }

    //找出每个点属于那个类聚中心
    public void searchBelong() {
        DistanceCal dc = new DistanceCalImpl();
        for (int i = 0; i < ypo.size(); i++) {
            double dist = 999;
            int label = -1;
            for (int j = 0; j < pacore.length; j++) {
                double distance = dc.euclideanDistance(ypo.get(i), pacore[j]);
                if (distance < dist) {
                    dist = distance;
                    label = j;
                }
            }
            ypo.get(i).setFlage(label + 1);
        }
    }

    //更新类聚中心
    public void calAverage() {
        for (int i = 0; i < pacore.length; i++) {
            System.out.println("以<" + pacore[i].getX() + "," + pacore[i].getY() + ">为中心的点：");
            int numc = 0;
            MyPoint newCore = new MyPoint();
            for (int j = 0; j < ypo.size(); j++) {
                if (ypo.get(j).getFlage() == (i + 1)) {
                    System.out.println(ypo.get(j).getX() + "," + ypo.get(j).getY());
                    numc += 1;
                    double tempX = newCore.getX();
                    double tempY = newCore.getY();
                    newCore.setX(tempX + ypo.get(j).getX());
                    newCore.setY(tempY + ypo.get(j).getY());
                }
            }
            //新的聚类中心
            pacoren[i] = new MyPoint(newCore.getX() / numc, newCore.getY() / numc);
            pacoren[i].setFlage(0);
            System.out.println("新的聚类中心：" + pacoren[i].getX() + "," + pacoren[i].getY());
        }
    }

    public void change_oldtonew(MyPoint[] old, MyPoint[] news) {
        for (int i = 0; i < old.length; i++) {
            old[i].setX(news[i].getX());
            old[i].setY(news[i].getY());
            old[i].setFlage(0);
        }
    }

    public void moveCore() {
        DistanceCal dc = new DistanceCalImpl();
        searchBelong();
        calAverage();
        double moveDistance = 0;
        int biao = -1;//标志，聚类中心点的移动是否符合最小距离
        for (int i = 0; i < pacore.length; i++) {
            moveDistance = dc.euclideanDistance(pacore[i], pacoren[i]);
            System.out.println("distcore:" + moveDistance);
            if (moveDistance < 0.01) {
                biao = 0;
            } else {
                biao = 1;
                break;
            }
            if (biao == 0) {
                System.out.println("迭代完成");
            } else {
                change_oldtonew(pacore, pacoren);
                moveCore();
            }
        }
    }




}
