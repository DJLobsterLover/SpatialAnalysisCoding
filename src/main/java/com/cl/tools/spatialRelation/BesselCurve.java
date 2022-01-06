package com.cl.tools.spatialRelation;

import com.cl.pojo.MyPoint;

import java.util.ArrayList;

public class BesselCurve {
    public ArrayList<MyPoint> creatCurve(ArrayList<MyPoint> originPoint) {
        ArrayList<MyPoint> result = new ArrayList<MyPoint>();
        //控制点收缩系数 ，经调试0.6较好
        double scale = 0.6;
        int originCount = originPoint.size();
        MyPoint[] midpoints = new MyPoint[originCount];
        //生成中点
        for(int i = 0 ;i < originCount ; i++){
            int nexti = (i + 1) % originCount;
            midpoints[i] = new MyPoint();
            midpoints[i].setX((originPoint.get(i).getX() + originPoint.get(nexti).getX())/2.0);
            midpoints[i].setY((originPoint.get(i).getY() + originPoint.get(nexti).getY())/2.0);
        }
        
        //平移中点  
        MyPoint[] extraPoints = new MyPoint[2 * originCount];
        for (int i = 0; i < extraPoints.length; i++) {
            extraPoints[i] = new MyPoint();
        }
        for(int i = 0 ;i < originCount ; i++){
            int nexti = (i + 1) % originCount;
            int backi = (i + originCount - 1) % originCount;
            MyPoint midinmid = new MyPoint();
            midinmid.x = (midpoints[i].x + midpoints[backi].x)/2.0;
            midinmid.y = (midpoints[i].y + midpoints[backi].y)/2.0;
            double offsetx = originPoint.get(i).x - midinmid.x;
            double offsety = originPoint.get(i).y - midinmid.y;
            int extraindex = 2 * i;
            extraPoints[extraindex].x = midpoints[backi].x + offsetx;
            extraPoints[extraindex].y = midpoints[backi].y + offsety;
            //朝 originPoint.get(i)方向收缩   
            double addx = (extraPoints[extraindex].x - originPoint.get(i).x) * scale;
            double addy = (extraPoints[extraindex].y - originPoint.get(i).y) * scale;
            extraPoints[extraindex].x = originPoint.get(i).x + addx;
            extraPoints[extraindex].y = originPoint.get(i).y + addy;

            int extranexti = (extraindex + 1)%(2 * originCount);
            extraPoints[extranexti].x = midpoints[i].x + offsetx;
            extraPoints[extranexti].y = midpoints[i].y + offsety;
            //朝 originPoint.get(i)方向收缩   
            addx = (extraPoints[extranexti].x - originPoint.get(i).x) * scale;
            addy = (extraPoints[extranexti].y - originPoint.get(i).y) * scale;
            extraPoints[extranexti].x = originPoint.get(i).x + addx;
            extraPoints[extranexti].y = originPoint.get(i).y + addy;

        }

        MyPoint controlPoint[] = new MyPoint[4];
        //生成4控制点，产生贝塞尔曲线
        for(int i = 0 ;i < originCount ; i++){
            controlPoint[0] = originPoint.get(i);
            int extraindex = 2 * i;
            controlPoint[1] = extraPoints[extraindex + 1];
            int extranexti = (extraindex + 2) % (2 * originCount);
            controlPoint[2] = extraPoints[extranexti];
            int nexti = (i + 1) % originCount;
            controlPoint[3] = originPoint.get(nexti);
            double u = 1;
            while(u >= 0){
                double px = bezier3funcX(u,controlPoint);
                double py = bezier3funcY(u,controlPoint);
                //u的步长决定曲线的疏密
                u -= 0.005;
                MyPoint tempP = new MyPoint(px,py);
                //存入曲线点
                result.add(tempP);
            }
        }
        return result;

    }

    double bezier3funcX(double uu,MyPoint[] controlP){
        double part0 = controlP[0].x * uu * uu * uu;
        double part1 = 3 * controlP[1].x * uu * uu * (1 - uu);
        double part2 = 3 * controlP[2].x * uu * (1 - uu) * (1 - uu);
        double part3 = controlP[3].x * (1 - uu) * (1 - uu) * (1 - uu);
        return part0 + part1 + part2 + part3;
    }
    double bezier3funcY(double uu,MyPoint[] controlP){
        double part0 = controlP[0].y * uu * uu * uu;
        double part1 = 3 * controlP[1].y * uu * uu * (1 - uu);
        double part2 = 3 * controlP[2].y * uu * (1 - uu) * (1 - uu);
        double part3 = controlP[3].y * (1 - uu) * (1 - uu) * (1 - uu);
        return part0 + part1 + part2 + part3;
    }
}
