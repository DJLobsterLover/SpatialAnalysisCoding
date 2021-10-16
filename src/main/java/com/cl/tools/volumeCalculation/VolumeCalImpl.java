package com.cl.tools.volumeCalculation;

import com.cl.pojo.MyPoint;
import com.cl.pojo.MyRegularNet;
import com.cl.pojo.MyTriangle;
import com.cl.tools.vectorSpaceCal.VectorSpaceCal;
import com.cl.tools.vectorSpaceCal.VectorSpaceCalImpl;

import java.util.ArrayList;

/**
 * @author DJLobster
 */
public class VolumeCalImpl implements VolumeCal {
    private VectorSpaceCal vs = new VectorSpaceCalImpl();

    public double volumeCalculationBaseTriangle(MyTriangle triangle) {
        double rs = 0;
        double hSum = 0;
        double s = vs.vectorSpaceCal(triangle.getPoints());
        for (int i = 0; i < triangle.getPoints().size(); i++) {
            hSum += triangle.getPoints().get(i).getZ();
        }
        rs = s * hSum / triangle.getPoints().size();
        return rs;
    }

    public double volumeCalculationBaseRegularNet(MyRegularNet net) {
        double rs = 0;
        double hSum = 0;
        double s = vs.vectorSpaceCal(net.getPoints());
        for (int i = 0; i < net.getPoints().size(); i++) {
            hSum += net.getPoints().get(i).getZ();
        }
        rs = s * hSum / net.getPoints().size();
        return rs;
    }
}
