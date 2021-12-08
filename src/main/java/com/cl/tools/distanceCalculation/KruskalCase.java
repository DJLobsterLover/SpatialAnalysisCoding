package com.cl.tools.distanceCalculation;

public class KruskalCase {
    private int edgeNum;//边的个数
    private char[] vertexs;//顶点数组
    private int[][] matrix;//邻接矩阵
    private EData[] rets;

    //使用INF表示两个顶点不能联通
    private static final int INF=Integer.MAX_VALUE;


    public static void main(String[] args) {
        char[] vartex={'A','B','C','D','E','F','G'};
        int matrix[][]={
                {0,12,INF,INF,INF,16,14},
                {12,0,10,INF,INF,7,INF},
                {INF,10,0,3,5,6,INF},
                {INF,INF,3,0,4,INF,INF},
                {INF,INF,5,4,0,2,8},
                {16,7,6,INF,2,0,9},
                {14,INF,INF,INF,8,9,0}
        };
        KruskalCase kruskalCase =new KruskalCase(vartex,matrix);
//        EData[] edges = kruskalCase.getEdges();
//        for (EData edge : edges) {
//            System.out.println(edge);
//        }
        kruskalCase.kruskal();

    }

    public EData[] getRets() {
        return rets;
    }

    public void setRets(EData[] rets) {
        this.rets = rets;
    }

    //构造器
    public KruskalCase(char[] vertexs,int[][] matrix){
        int vlen=vertexs.length;
        this.vertexs=vertexs;
        this.matrix=matrix;
        //统计边的条数
        for(int i=0;i<vlen;i++){
            for(int j=i+1;j<vlen;j++){
                if(matrix[i][j]!=INF){
                    edgeNum++;
                }
            }
        }
        System.out.println(edgeNum);
    }

    //打印邻接矩阵
    public void print(){
        System.out.println("邻接矩阵为");
        for(int i=0;i<vertexs.length;i++){
            for(int j=0;j<vertexs.length;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }
    /**
     * 对边进行排序
     * @param edges
     */
    private void sortEdges(EData[] edges){

        for(int i=0;i<edgeNum-1;i++){
            for(int j=0;j<edgeNum-1-i;j++){

                if(edges[j].weight>edges[j+1].weight){
                    EData tmp=edges[j];
                    edges[j]=edges[j+1];
                    edges[j+1]=tmp;
                }
            }
        }
    }

    /**
     * 返回顶点的下标
     * @param ch 如'A'
     * @return
     */
    private int getPosition(char ch){
        for(int i=0;i<vertexs.length;i++){
            if(vertexs[i]==ch){
                return i;
            }
        }
        return -1;
    }
    /**
     * 获取边的数组，返回形式为[['A','B',12],....]
     * @return
     */
    public EData[] getEdges(){
        int index=0;
        EData[] edges=new EData[edgeNum];
        for(int i=0;i<vertexs.length;i++){
            for(int j=i+1;j<vertexs.length;j++){
                if(matrix[i][j]!=INF){
                    edges[index++]=new EData(vertexs[i],vertexs[j],matrix[i][j]);
                }
            }
        }
        return edges;
    }
    /**
     *
     * @param end 记录每个点对应的终点是那个
     * @param i
     * @return 返回终点
     */
    private int getEnd(int[] end,int i){
        while(end[i]!=0){
            i=end[i];
        }
        return i;
    }

    //克鲁斯卡尔算法
    public void kruskal(){
        int index=0;
        int[] end=new int[edgeNum];//记录每个顶点对应的终点
        rets = new EData[edgeNum];//保存最小生成树
        //获取边的集合
        EData[] edges=getEdges();
        //对边进行排序
        sortEdges(edges);
        //遍历数组，将边添加到最小生成树中，判断是否产生回路
        for(int i=0;i<edgeNum;i++){
            //获取第i条边的第一个顶点
            int p1=getPosition(edges[i].start);
            //获取第i条边的第二个顶点
            int p2=getPosition(edges[i].end);

            //获取p1在已有生成树的终点
            int m=getEnd(end,p1);
            //获取p2在已有生成树的终点
            int n=getEnd(end,p2);

            if(m!=n){
                end[m]=n;
                rets[index++]=edges[i];
            }
        }

        System.out.println("最小生成树为");
        for(int i=0;i<index;i++){
            System.out.println(rets[i]);
        }
    }
    //创建一个EDate类，实例就是一条边
    public class EData{
        char start;
        char end;
        int weight;
        public EData(char start,char end,int weight){
            this.start=start;
            this.end = end;
            this.weight=weight;
        }
        @Override
        public String toString() {
            return "<" + start + ", " + end + ">=" + weight + "]";
        }

        public char getStart() {
            return start;
        }

        public void setStart(char start) {
            this.start = start;
        }

        public char getEnd() {
            return end;
        }

        public void setEnd(char end) {
            this.end = end;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

}
