package com.cl.Frame;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class FrameWithMouseEvent extends JFrame implements MouseMotionListener,MouseListener{
    JButton button1;
    JLabel label1;
    private int flag=0;
    private int count=0;


    public void init(){
        FlowLayout f=new FlowLayout(FlowLayout.LEFT);
        setLayout(f);
        setTitle("GUI Test Mouse Motion");
        button1=new JButton("Start");
        label1=new JLabel("X: Y:");
        add(button1);
        add(label1);
        button1.addMouseListener(this);
        this.addMouseMotionListener(this);


        setSize(600,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }



    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        if (flag==1) {
            label1.setText("X:"+e.getX()+",Y:"+e.getY());
        }
    }



    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        count++;
        if (e.getSource()==button1 && count%2==1){
            flag=1;
            button1.setText("End");
        }

        if (e.getSource()==button1 && count%2==0){
            flag=0;
            button1.setText("Start");
            label1.setText("X: Y:");
        }


    }



    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }



    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }



    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }


    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}

