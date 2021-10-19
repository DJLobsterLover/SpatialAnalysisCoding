package com.cl.TestGUI;

import javax.print.attribute.standard.JobKOctets;
import javax.swing.*;

import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 * @author DJLobster
 */
public class MainFrame {
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.InitUI();
    }
    private Graphics2D g;
    private JPanel jp;

    public void InitUI() {
        JFrame jf = new JFrame("test");
        MainFrame mf = new MainFrame();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jf.setSize(800, 800);

        jp.setBackground(Color.WHITE);
        jf.add(jp, BorderLayout.CENTER);
        //线性面板
        JPanel shapePanel = new JPanel();
        shapePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        shapePanel.setBackground(Color.gray);

        Object[] Shape = {"直线", "曲线", "圆", "橡皮擦", "矩形", "椭圆",
                "弧线", "多边形", "三角形", "测试"};
//        JComboBox jb = new JComboBox(Shape);
//        shapePanel.add(jb);
//        shapePanel.add(new JButton("hello"));
        for (int i = 0; i < Shape.length; i++) {
            JButton button = new JButton((String) Shape[i]);
            button.setBackground(Color.WHITE);
//            button.addActionListener(dl);
            //添加事件监听机制
            shapePanel.add(button);
//
//        }
            jf.add(shapePanel, BorderLayout.NORTH);

            JPanel resultPanel = new JPanel();
            JButton t = new JButton("hello");
            t.setBackground(Color.RED);
            resultPanel.add(t);
            resultPanel.add(new JLabel("hello"), BorderLayout.NORTH);
            jf.add(resultPanel, BorderLayout.SOUTH);


        }

    }
}
