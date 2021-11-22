package com.cl.pojo;

import com.cl.tools.distanceCalculation.DistanceCal;
import com.cl.tools.distanceCalculation.DistanceCalImpl;

import java.util.ArrayList;

public class MyClusterPoint {
    private ArrayList<MyPoint> clusterPoints;

    public MyClusterPoint(ArrayList<MyPoint> clusterPoints) {
        this.clusterPoints = clusterPoints;
    }

    public MyClusterPoint() {
    }

    public ArrayList<MyPoint> getClusterPoints() {
        return clusterPoints;
    }

    public void setClusterPoints(ArrayList<MyPoint> clusterPoints) {
        this.clusterPoints = clusterPoints;
    }

    @Override
    public String toString() {
        return "MyClusterPoint{" +
                "clusterPoints=" + clusterPoints +
                '}';
    }

    //类簇距离计算
    public double MinDistance(MyClusterPoint cluster) {
        DistanceCal dc = new DistanceCalImpl();
        double res = 0;
        double min = 999999999;
        for (int i = 0; i < this.clusterPoints.size(); i++) {
            for (int j = 0; j < cluster.clusterPoints.size(); j++) {
                double temp = dc.euclideanDistance(this.clusterPoints.get(i), cluster.clusterPoints.get(j));
                if (temp < min) {
                    min = temp;
                }
            }
        }
        res = min;
        return res;
    }

    public double MaxDistance(MyClusterPoint cluster) {
        DistanceCal dc = new DistanceCalImpl();
        double res = 0;
        double max = 0;
        for (int i = 0; i < this.clusterPoints.size(); i++) {
            for (int j = 0; j < cluster.clusterPoints.size(); j++) {
                double temp = dc.euclideanDistance(this.clusterPoints.get(i), cluster.clusterPoints.get(j));
                if (temp >= max) {
                    max = temp;
                }
            }
        }
        res = max;
        return res;
    }

    //中间距离
    public double CenterDistance(MyClusterPoint cluster) {
        double res = 0;
        res = (MaxDistance(cluster) + MinDistance(cluster)) / 2;
        return res;
    }

    //重心距离
    public double CentroidDistance(MyClusterPoint cluster) {
        DistanceCal dc = new DistanceCalImpl();
        double res = 0;
        res = dc.euclideanDistance(dc.getPolygonGravityPoint(new MyPolygon(this.clusterPoints)), dc.getPolygonGravityPoint(new MyPolygon(cluster.clusterPoints)));
        return res;
    }

    //平均距离
    public double AverageDistance(MyClusterPoint cluster) {
        double res = 0;
        DistanceCal dc = new DistanceCalImpl();
        for (int i = 0; i < this.clusterPoints.size(); i++) {
            for (int j = 0; j < cluster.clusterPoints.size(); j++) {
                res += Math.pow(dc.euclideanDistance(this.clusterPoints.get(i), cluster.clusterPoints.get((j))), 2);
            }
        }
        res = Math.sqrt(res / (this.clusterPoints.size() * cluster.clusterPoints.size()));
        return res;
    }

    //Ward
    public double WardDistance(MyClusterPoint cluster) {
        double res = 0;
        //计算当前聚类离差平方和
        double sumX = 0;
        double sumY = 0;
        for (int i = 0; i < this.clusterPoints.size(); i++) {
            sumX += this.clusterPoints.get(i).getX();
            sumY += this.clusterPoints.get(i).getY();
        }
        double avgX = sumX / this.clusterPoints.size();
        double avgY = sumY / this.clusterPoints.size();
        double Sq = 0;
        for (int i = 0; i < this.clusterPoints.size(); i++) {
            Sq += Math.pow((this.clusterPoints.get(i).getX() - avgX), 2) + Math.pow((this.clusterPoints.get(i).getY() - avgY), 2);
        }
        //计算目标类聚离差平方和
        sumX = 0;
        sumY = 0;
        for (int i = 0; i < cluster.clusterPoints.size(); i++) {
            sumX += cluster.clusterPoints.get(i).getX();
            sumY += cluster.clusterPoints.get(i).getY();
        }
        double Sp = 0;
        avgX = sumX / cluster.clusterPoints.size();
        avgY = sumY / cluster.clusterPoints.size();
        for (int i = 0; i < cluster.clusterPoints.size(); i++) {
            Sp += Math.pow((cluster.clusterPoints.get(i).getX() - avgX), 2) + Math.pow((cluster.clusterPoints.get(i).getY() - avgY), 2);
        }

        //计算两个聚类和的离差平方和
        ArrayList<MyPoint> allClusters = new ArrayList<MyPoint>(this.clusterPoints);
        for (int i = 0; i < cluster.clusterPoints.size(); i++) {
            allClusters.add(cluster.clusterPoints.get(i));
        }
        sumX = 0;
        sumY = 0;
        for (int i = 0; i < allClusters.size(); i++) {
            sumX += allClusters.get(i).getX();
            sumY += allClusters.get(i).getY();
        }
        double Sk = 0;
        avgX = sumX / allClusters.size();
        avgY = sumY / allClusters.size();
        for (int i = 0; i < allClusters.size(); i++) {
            Sk += Math.pow((allClusters.get(i).getX() - avgX), 2) + Math.pow((allClusters.get(i).getY() - avgY), 2);
        }
        res = Math.sqrt(Sk - Sq - Sp);

        return res;
    }
}
