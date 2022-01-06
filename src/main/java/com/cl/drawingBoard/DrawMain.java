package com.cl.drawingBoard;

import com.cl.pojo.MyShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * @author DJLobster
 */
public class DrawMain extends JPanel implements ItemListener{
    private static final int WIDTH = 1300;
    private static final int HEIGHT = 800;

    private String selectedComBox;
    private String text = "3";
    private String bufferClass = "";
    private int perform = 0;
    private ArrayList<MyShape> shapes = new ArrayList<MyShape>();


    ///////////////////////////////


    public int getPerform() {
        return perform;
    }

    public void setPerform(int perform) {
        this.perform = perform;
    }

    public String getBufferClass() {
        return bufferClass;
    }

    public void setBufferClass(String bufferClass) {
        this.bufferClass = bufferClass;
    }

    public String getSelectedComBox() {
        return selectedComBox;
    }

    public void setSelectedComBox(String selectedComBox) {
        this.selectedComBox = selectedComBox;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static void main(String[] args) {
        DrawMain Draw = new DrawMain();
        Draw.InitUI();
    }

    public DrawMain() {
    }

    public ArrayList<MyShape> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<MyShape> shapes) {
        this.shapes = shapes;
    }

    public void InitUI() {
        JFrame jf = new JFrame();
        jf.setSize(WIDTH, HEIGHT);
        jf.setTitle("超级画板");
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
        String[] Shape = { "直线", "圆", "橡皮擦", "多边形","圆点"};
        for (int i = 0; i < Shape.length; i++) {
            JButton button = new JButton(Shape[i]);
            button.setBackground(Color.WHITE);
            button.addActionListener(dl);
            // 添加事件监听机制
            ShapePanel.add(button);
        }
        //添加Koch下拉框
        JComboBox kochBox = new JComboBox();
        kochBox.addItem("--Koch--");    //向下拉列表中添加一项
        kochBox.addItem("Koch曲线");
        kochBox.addItem("Koch雪花");
        kochBox.addItem("分形树");
        kochBox.addItem("正态云");
        kochBox.addActionListener(dl);
        kochBox.addItemListener(this);
        ShapePanel.add(kochBox);
        //添加delaunay下拉框
        JComboBox delaunayBox = new JComboBox();
        delaunayBox.addItem("--delaunay--");
        delaunayBox.addItem("生成三角网");
        delaunayBox.addItem("生成三角网2");
        delaunayBox.addItem("生成三角网3");
        delaunayBox.addItem("线约束");
        delaunayBox.addItem("等值线");
        delaunayBox.addActionListener(dl);
        delaunayBox.addItemListener(this);
        ShapePanel.add(delaunayBox);
        //添加线性关系下拉框
        JComboBox spatialBox = new JComboBox();
        spatialBox.addItem("--空间关系--");
        spatialBox.addItem("点抽稀");
        spatialBox.addItem("平滑");
        spatialBox.addItem("多边形各个心");
        spatialBox.addItem("多边形最小外接圆");
        spatialBox.addItem("多边形最大内切圆");
        spatialBox.addItem("多边形面积");
        spatialBox.addItem("点在多边形内判断");
        spatialBox.addItem("线线相交关系");
        spatialBox.addItem("线面相交关系");
        spatialBox.addItem("面面相交关系");
        spatialBox.addItem("生成点击凸包");
        spatialBox.addItem("面状地物量测");
        spatialBox.addActionListener(dl);
        spatialBox.addItemListener(this);
        ShapePanel.add(spatialBox);
        //添加距离关系下拉框
        JComboBox distanceBox = new JComboBox();
        distanceBox.addItem("--距离关系");
        distanceBox.addItem("两点距离");
        distanceBox.addItem("点到线距离");
        distanceBox.addItem("点与面距离");
        distanceBox.addItem("线与线距离");
        distanceBox.addItem("线与面距离");
        distanceBox.addActionListener(dl);
        distanceBox.addItemListener(this);
        ShapePanel.add(distanceBox);

        //添加面积下拉框
//        JComboBox vectorBox = new JComboBox();
//        vectorBox.addItem("--面积计算--");
//        vectorBox.addItem("多边形面积");
//        vectorBox.addItemListener(this);
//        vectorBox.addActionListener(dl);
//        ShapePanel.add(vectorBox);

        //添加栅格下拉框
        JComboBox rasterBox = new JComboBox();
        rasterBox.addItem("--栅格关系--");
        rasterBox.addItem("生成栅格");
        rasterBox.addItem("最短路径");
        rasterBox.addItem("导入DEM");
        rasterBox.addItem("导入DEM2");
        rasterBox.addItem("选择范围");
        rasterBox.addItem("DEM各数据");
        rasterBox.addItem("栅格计算器");
        rasterBox.addItem("计算栅格结果");
        rasterBox.addItem("DEM面积");
        rasterBox.addItem("坡度");
        rasterBox.addItem("坡向");
        rasterBox.addItem("平地点");
        rasterBox.addItem("山顶点");
        rasterBox.addItem("凹陷点");
        rasterBox.addItem("山谷点");
        rasterBox.addItem("山脊点");
        rasterBox.addItem("鞍点");
        rasterBox.addActionListener(dl);
        rasterBox.addItemListener(this);
        ShapePanel.add(rasterBox);

        //添加聚类下拉框
        JComboBox clusterBox = new JComboBox();
        clusterBox.addItem("--点聚类--");
        clusterBox.addItem("聚类生成");
        clusterBox.addItem("聚类距离");
        clusterBox.addItem("生成图");
        clusterBox.addItem("Kruskal");
        clusterBox.addItemListener(this);
        clusterBox.addActionListener(dl);
        ShapePanel.add(clusterBox);

        //添加缓冲区下拉框
        JComboBox bufferBox = new JComboBox();
        bufferBox.addItem("-缓冲区-");
        bufferBox.addItem("设置缓冲区参数");
        bufferBox.addItem("生成缓冲区");
        bufferBox.addItemListener(this);
        bufferBox.addActionListener(dl);
        ShapePanel.add(bufferBox);

        //清空按键
        JButton remove = new JButton("清空");
        remove.addActionListener(dl);
        ShapePanel.add(remove);

        jf.add(ShapePanel, BorderLayout.NORTH);
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

    public void itemStateChanged(ItemEvent e) {
        selectedComBox = e.getItem().toString();
    }


}



