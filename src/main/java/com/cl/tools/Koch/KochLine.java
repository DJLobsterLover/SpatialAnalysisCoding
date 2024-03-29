package com.cl.tools.Koch;

/**
 * @author DJLobster
 */
public class KochLine {
    public void draw(int n) {
        Turtle t = new Turtle();
        t.move(30, 400);
        t.penDown();
        drawKoch(t, 600, n);
    }

    public void drawKoch(Turtle t, double len, int n) {
        if (n < 1) {
            t.go(len);
        } else {
            // for (int a = 60; a <= 420; a += 180){
            for (int i = 0; i < 3; i++) {
                int a = 60 + 180 * i;
                drawKoch(t, len / 3, n - 1);
                t.rotate(a);
            }
            drawKoch(t, len / 3, n - 1);
        }
    }

    public static void main(String[] args) {
        KochLine a = new KochLine();
//        for (int n = 1; n <= 6; n++) {
//            a.draw(n);
//        }
        a.draw(3);
    }
}
