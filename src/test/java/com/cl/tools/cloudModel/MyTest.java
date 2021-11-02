package com.cl.tools.cloudModel;

import java.io.FileNotFoundException;

public class MyTest {
    public static void main(String[] args) {
        CloudModel cm = new CloudModel(0, 10, 1,  2000);
        cm.getCloudDrops();
        try {
            cm.write_Excel("\\data.xlsx");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        cm.print_x_y();
    }
}
