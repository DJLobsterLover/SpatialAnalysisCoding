package com.cl.tools.volumeCalculation;

import com.cl.pojo.MyRegularNet;
import com.cl.pojo.MyTriangle;

/**
 * @author DJLobster
 */
public interface VolumeCal {
    /**
     * @return
     * 基于三角形网络计算体积
     */
    double volumeCalculationBaseTriangle(MyTriangle triangle);

    /**
     * @param net
     * @return
     * 基于规则格网计算体积
     */
    double volumeCalculationBaseRegularNet(MyRegularNet net);

    /**
     * @return
     * 基于等高线计算体积
     */
//    double volumeCalBaseContour();
}
