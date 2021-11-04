package com.cl.tools.Koch;

/**
 * @author DJLobster
 */
public class KochSnow {
    private double[] angle = {0,60,-120,60};
    public void draw(double size, int n){
        Turtle t = new Turtle();
        t.move(200, 300);
        t.penDown();
        drawSnow(t, size, n);
        t.rotate(-120);
        drawSnow(t, size, n);
        t.rotate(-120);
        drawSnow(t, size, n);
    }
    public void drawSnow(Turtle t, double size, int n){
        if (n == 0) {
            t.go(size);
        }
        else{
            for (int i = 0; i < angle.length; i++) {
                t.rotate(angle[i]);
                drawSnow(t,size/3,n-1);
            }
        }
    }

    public static void main(String[] args) {
        KochSnow ks = new KochSnow();
        ks.draw(400,3);
    }
}
