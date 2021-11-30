package com.cl.drawingBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class oneArgsFrame implements ActionListener {
    String tf1_value = "";
    JTextField tf1;
    JButton b1;
    JLabel l1;
    JFrame f;
    DrawMain df;

    public oneArgsFrame(DrawMain df) {
        this.df = df;
    }

    public void init() {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        l1 = new JLabel("距离");
        l1.setBounds(20, 50, 50, 20);
        tf1 = new JTextField();
        tf1.setBounds(50, 50, 150, 20);
        b1 = new JButton("确定");
        b1.setBounds(50, 100, 100, 30);
        b1.addActionListener(this);
        f.add(l1);
        f.add(tf1);
        f.add(b1);
        f.setSize(300, 200);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setTitle("参数选择");
        f.setVisible(true);

    }

//    public static void main(String[] args) {
//        oneArgsFrame t = new oneArgsFrame();
//        t.init();
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            df.setText(tf1.getText().trim());
            f.dispose();
        }
    }
}
