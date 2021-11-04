package com.cl.drawingBoard;

import javax.swing.*;
import java.awt.*;

/**
 * @author DJLobster
 */
public class DrawMain extends JPanel {
    public static void main(String[] args) {
        DrawMain Draw = new DrawMain();
        Draw.InitUI();
    }
    public JLabel resLabel;
    public void InitUI() {
        JFrame jf = new JFrame();
        jf.setSize(1000, 780);
        jf.setTitle("简单画板");
        jf.setDefaultCloseOperation(3);
        jf.setLocationRelativeTo(null);
        jf.setLayout(new BorderLayout());
        // 实例化事件监听类
        DrawListener dl = new DrawListener(this);
        //实现中间面版
        this.setBackground(Color.WHITE);
        jf.add(this, BorderLayout.CENTER);
        // 实现性状面板
        JPanel ShapePanel = new JPanel();
        ShapePanel.setBackground(Color.black);
        ShapePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ShapePanel.setBackground(Color.gray);
        String[] Shape = { "直线", "圆", "橡皮擦", "矩形", "椭圆",
                "多边形","圆点", "三角形", "测试","多边形面积","点是否在多边形内","Delaunay" };
        for (int i = 0; i < Shape.length; i++) {
            JButton button = new JButton(Shape[i]);
            button.setBackground(Color.WHITE);
            button.addActionListener(dl);
            // 添加事件监听机制
            ShapePanel.add(button);
        }
        jf.add(ShapePanel, BorderLayout.NORTH);

        // 实现结果显示面板
        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(Color.GRAY);
        resultPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton test = new JButton("测试结果");
        JButton remove = new JButton("清空");
        remove.addActionListener(dl);
        test.setBackground(Color.WHITE);
        test.addActionListener(dl);
        resLabel = new JLabel("显示结果");
//        resLabel.setSize(100,100);
        resultPanel.add(test);
        resultPanel.add(resLabel);
        resultPanel.add(remove);
        jf.add(resultPanel, BorderLayout.EAST);

        // 实现颜色面板
        JPanel ColorPanel = new JPanel();
        ColorPanel.setBackground(Color.black);
        ColorPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ColorPanel.setBackground(Color.gray);
        Color[] color = { Color.BLACK, Color.blue, Color.white, Color.gray,
                Color.red, Color.CYAN, Color.green, Color.darkGray, Color.pink };
        for (int i = 0; i < color.length; i++) {
            JButton button = new JButton();
            button.addActionListener(dl);
            // 添加事件监听机制
            button.setPreferredSize(new Dimension(30, 30));
            button.setBackground(color[i]);
            ColorPanel.add(button);
        }
        jf.add(ColorPanel, BorderLayout.SOUTH);
        jf.setVisible(true);
        this.addMouseListener(dl);
        this.addMouseMotionListener(dl);
    }
}
