package com.cl.tools.fractalTree;

import javax.swing.*;
import java.awt.*;

/**
 * @author DJLobster
 */
public class FractalTree extends JFrame {
    public FractalTree() {
        super("分形树");
        setBounds(200, 200, 1000, 800);
        // 表示生成的窗体大小可改变
        setResizable(true);
        // 结束窗口所在的应用程序。在窗口被关闭的时候会退出JVM
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void drawTree(Graphics g, int x1, int y1, double angle, double length) {
        if (length < 0.5) {
            return;
        }
        int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * length*10);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * length*10);
        g.setColor( length < 1.2 ? Color.pink : Color.GRAY );

        g.drawLine(x1, y1, x2, y2);

        drawTree(g, x2, y2, angle+ 33.5, length/1.3);
        drawTree(g, x2, y2, angle- 33.5, length/1.3);
    }


    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GRAY);
        drawTree(g, 500, 700, -90, 14);
    }

    public static void main(String[] args) {
        new FractalTree().setVisible(true);
    }
}
