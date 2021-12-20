package com.cl.drawingBoard;

import lombok.Data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Data
public class DemCalFrame implements ActionListener {
    String rsText = "";
    String tf1_value = "";
    JTextField res;
    JButton B_confirm;
    JButton B_clear;
    JButton B_plus;//+
    JButton B_sub;//-
    JButton B_mul;//*
    JButton B_div;// /
    JButton B_zero;//0
    JButton B_L_brackets;
    JButton B_R_brackets;
    JButton[] B_num = new JButton[9];
    JButton B_dem1;
    JButton B_dem2;

    JFrame f;
    DrawMain df;
    JButton plusB;

    public DemCalFrame(DrawMain df) {
        this.df = df;
    }

    public DemCalFrame() {
    }

    public void init() {


        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        res = new JTextField();
        res.setBounds(50, 40, 300, 30);
        f.add(res);

        for (int i = 0; i < 9; i++) {
            B_num[i] = new JButton(String.valueOf(i+1));
            B_num[i].setBounds((i % 3) * 50 + 50, (i / 3) * 50 + 70, 50, 50);
            B_num[i].addActionListener(this);
            f.add(B_num[i]);
        }

        B_zero = new JButton("0");
        B_zero.setBounds(50, 220, 150, 50);
        B_zero.addActionListener(this);
        f.add(B_zero);

        B_plus = new JButton("+");
        B_plus.setBounds(200, 70, 50, 50);
        B_plus.addActionListener(this);
        f.add(B_plus);

        B_sub = new JButton("-");
        B_sub.setBounds(200, 120, 50, 50);
        B_sub.addActionListener(this);
        f.add(B_sub);

        B_mul = new JButton("*");
        B_mul.setBounds(200, 170, 50, 50);
        B_mul.addActionListener(this);
        f.add(B_mul);

        B_div = new JButton("/");
        B_div.setBounds(200, 220, 50, 50);
        B_div.addActionListener(this);
        f.add(B_div);

        B_dem1 = new JButton("dem1");
        B_dem1.setBounds(250, 70, 70, 50);
        B_dem1.addActionListener(this);
        f.add(B_dem1);

        B_dem2 = new JButton("dem2");
        B_dem2.setBounds(250, 120, 70, 50);
        B_dem2.addActionListener(this);
        f.add(B_dem2);

        B_L_brackets = new JButton("(");
        B_L_brackets.setBounds(250, 170, 70, 50);
        B_L_brackets.addActionListener(this);
        f.add(B_L_brackets);

        B_R_brackets = new JButton(")");
        B_R_brackets.setBounds(250, 220, 70, 50);
        B_R_brackets.addActionListener(this);
        f.add(B_R_brackets);


        B_confirm = new JButton("确定");
        B_confirm.setBounds(50, 400, 70, 30);
        B_confirm.addActionListener(this);
        f.add(B_confirm);

        B_clear = new JButton("清空");
        B_clear.setBounds(120, 400, 70, 30);
        B_clear.addActionListener(this);
        f.add(B_clear);



        f.setSize(500, 500);
        f.setLocationRelativeTo(null);
        f.setLayout(null);
        f.setTitle("栅格计算器");
        f.setVisible(true);
    }

//    public static void main(String[] args) {
//        oneArgsFrame t = new oneArgsFrame();
//        t.init();
//    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == B_confirm) {
            df.setText(res.getText().trim());
            f.dispose();
        }
        else if (e.getSource() == B_clear) {
            res.setText("");
            rsText = "";
        }
        else if (e.getSource() == B_num[0]) {
            rsText += "1";
            res.setText(rsText);
        }
        else if (e.getSource() == B_num[1]) {
            rsText += "2";
            res.setText(rsText);
        }
        else if (e.getSource() == B_num[2]) {
            rsText += "3";
            res.setText(rsText);
        }
        else if (e.getSource() == B_num[3]) {
            rsText += "4";
            res.setText(rsText);
        }
        else if (e.getSource() == B_num[4]) {
            rsText += "5";
            res.setText(rsText);
        }
        else if (e.getSource() == B_num[5]) {
            rsText += "6";
            res.setText(rsText);
        }
        else if (e.getSource() == B_num[6]) {
            rsText += "7";
            res.setText(rsText);
        }
        else if (e.getSource() == B_num[7]) {
            rsText += "8";
            res.setText(rsText);
        }
        else if (e.getSource() == B_num[8]) {
            rsText += "9";
            res.setText(rsText);
        }
        else if (e.getSource() == B_zero) {
            rsText += "0";
            res.setText(rsText);
        }
        else if (e.getSource() == B_plus) {
            rsText += "+";
            res.setText(rsText);
        }
        else if (e.getSource() == B_sub) {
            rsText += "-";
            res.setText(rsText);
        }
        else if (e.getSource() == B_mul) {
            rsText += "*";
            res.setText(rsText);
        }
        else if (e.getSource() == B_div) {
            rsText += "/";
            res.setText(rsText);
        }
        else if(e.getSource() == B_dem1){
            rsText += "Dem1_Val";
            res.setText(rsText);
        }
        else if (e.getSource() == B_dem2) {
            rsText += "Dem2_Val";
            res.setText(rsText);
        }
        else if (e.getSource() == B_L_brackets) {
            rsText += "(";
            res.setText(rsText);
        } else if (e.getSource() == B_R_brackets) {
            rsText += ")";
            res.setText(rsText);
        }
    }

    public static void main(String[] args) {
        DemCalFrame dcf = new DemCalFrame();
        dcf.init();
    }
}
