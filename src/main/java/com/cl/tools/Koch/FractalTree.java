package com.cl.tools.Koch;

//import com.cl.tools.Koch.Turtle;

import com.cl.tools.Koch.Turtle;

import java.awt.*;

/**
 * @author DJLobster
 */
public class FractalTree {
    public void draw(){
        Turtle t = new Turtle();
        t.setColor(Color.black);
        t.move(400, 750);
        t.rotate(90);
        t.penDown();
        drawTree(t,100,20);
    }

    public void drawTree(Turtle t, double len, double angle) {
        if (len >= 3) {
            t.go(len);
            //向左转
            t.rotate(-angle);
            //画下一枝，直到画到树枝长小于3
            drawTree(t,len - 10,angle);
            t.rotate(2*angle);
//
            drawTree(t, len - 10, angle);
            t.rotate(-angle);
            if (len <= 30) {
                t.setColor(Color.pink);
            }
            if (len > 30) {
                t.setColor(Color.black);
            }
            t.rotate(180);
            t.go(len);
            t.rotate(180);
        }
    }

    public static void main(String[] args) {
        FractalTree ft = new FractalTree();
        ft.draw();
    }
}
