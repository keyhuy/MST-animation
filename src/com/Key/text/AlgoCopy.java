package com.Key.text;

import com.Key.Algorithm.Algorithm;
import com.Key.Algorithm.EdgeData;
import com.Key.Algorithm.Graph;
import com.Key.GUI.AnimUI.MyPanel;

import java.util.List;

/**
 * @author Key
 * @date 2021/06/24/23:22
 **/
public class AlgoCopy {

    public MyPanel myPanel;

    private  long sleepTime;
    private  List<String> vex;
    private Algorithm algo;

    /**
     * 用于控制线程（绘图
     * ）暂停或继续
     */
    private final Object lock = new Object();
    private volatile boolean pause = false;

    /**
     * 构造器，传入图结构和算法名字
     * @param graph 图结构
     */
    public AlgoCopy(Graph graph, long time) {
        this.vex = graph.getVex();
        this.sleepTime = time;
    }

    /**
     * 执行面板中的paint方法，使用repaint()重复画图
     * @param adj 执行Prim算法每次得到的边（两个顶点）
     */
    public void drawGraph(String[] adj) {
        if (adj != null) {
            for (int i = 0;i < vex.size();i++) {
                if (adj[0].equals(vex.get(i))) {
                    myPanel.v1 = i;
                    myPanel.adj.add(i);
                }
                if (adj[1].equals(vex.get(i))) {
                    myPanel.v2 = i;
                    myPanel.adj.add(i);
                }
            }
        }

        myPanel.repaint();

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prim算法具体实现
     * @param G 图结构
     * @param firstVex 开始顶点
     */
    public void MST_Prim(Graph G, int firstVex) {

        String[] content = new String[2];
        EdgeData[] edges = getEdges(G.getEdgeNum(),vex,G.getEdges());
        int index = 0;

        // 节点个数
        int vexNum = G.getVexNum();
        // 当前结果树到所有顶点的最短距离
        int[] minWeight = new int[vexNum];
        // adjVex[C] = 0，代表C是通过A加入结果树的（0是A的下标）
        int[] adjVex = new int[vexNum];

        // 初始化两个辅助数组
        for(int i = 0; i < vexNum; i++) {
            //第一个顶点到其余顶点的距离
            minWeight[i] = (G.getEdges())[firstVex][i];
            adjVex[i] = firstVex;
        }
        // 当前挑选的最小权值
        int minEdg;
        // 最小权值对应的节点下标
        int minVex = 0;

        // 循环剩余n-1个点
        for(int i = 1; i < vexNum; i++) {
            minEdg = Integer.MAX_VALUE;
            for(int j = 0; j < vexNum; j++) {
                if(minWeight[j] != 0 && minWeight[j] < minEdg) {
                    // 寻找还没有被挑选进来的，最小权重的点
                    minEdg = minWeight[j];
                    minVex = j;
                }
            }

            // 纳入结果树
            minWeight[minVex] = 0;
            // 修改对应辅助数组的值
            for(int j = 0; j < vexNum;j++) {
                if(minWeight[j] != 0 && (G.getEdges())[minVex][j] < minWeight[j] && (G.getEdges())[minVex][j] > 0) {
                    minWeight[j] = (G.getEdges())[minVex][j];
                    adjVex[j] = minVex;
                }
            }
            int pre = adjVex[minVex];
            int end = minVex;

            // 存入每次得到的最小边顶点值
            edges[index].start = G.getVex().get(pre);
            edges[index].end = G.getVex().get(end);
            index++;
            System.out.println("("+G.getVex().get(pre)+","+G.getVex().get(end)+")");
        }

        // 根据得到的最小边数组绘图
        System.out.println("最小生成树为");
        for(int i = 0;i < index;i++) {
            content[0] = edges[i].start;
            content[1] = edges[i].end;

            // 判断是否暂停线程
            if (pause) {
                onPause();
            }
            drawGraph(content);
        }

        // 最后再画一次图，把最小生成树显示出来
        myPanel.isEnd = true;
        myPanel.repaint();
    }

    /**
     * Kruskal算法具体实现
     * @param G 图结构
     */
    public void MST_Kruskal(Graph G) {

        // 先把全部顶点选入
        myPanel.pOrK = "K1";
        drawGraph(null);

        String[] content = new String[2];

        int edgeNum = G.getEdgeNum();
        int index = 0;
        // 记录每个顶点对应的终点
        int[] end = new int[G.getVexNum()];
        // 保存最小生成树
        EdgeData[] rets = new EdgeData[edgeNum];
        // 获取边的集合
        EdgeData[] edges = getEdges(edgeNum,vex,G.getEdges());
        // 对边进行排序
        sortEdges(edges,edgeNum);

        // 遍历排序好的边数组，将边添加到最小生成树中，判断是否产生回路
        for(int i = 0;i < edgeNum;i++) {
            // 获取第i条边的第一个顶点
            int p1 = getPosition(edges[i].start,vex);
            // 获取第i条边的第二个顶点
            int p2 = getPosition(edges[i].end,vex);

            // 获取p1在已有生成树的终点
            int m = getEnd(end,p1);
            // 获取p2在已有生成树的终点
            int n = getEnd(end,p2);

            // 如果没有形成回路，就可以加入最小边中
            if(m != n) {
                end[m] = n;
                rets[index++] = edges[i];
            }
        }

        System.out.println("最小生成树为");
        for(int i = 0;i < index;i++) {
            content[0] = rets[i].start;
            content[1] = rets[i].end;

            // 判断是否暂停线程
            if (pause) {
                onPause();
            }
            // 进入绘图
            drawGraph(content);
            System.out.println(rets[i]);
        }

        myPanel.isEnd = true;
        myPanel.repaint();
    }

    /**
     * 获取顶点在已有生成树的终点
     * @param end 终点
     * @param i 顶点下标
     * @return 返回终点下标
     */
    public int getEnd(int[] end, int i){
        while(end[i] != 0) {
            i = end[i];
        }
        return i;
    }

    /**
     * 获取边实体类
     * @param edgeNum 边数
     * @param vertex 顶点集合
     * @param matrix 存放边信息的二维数组
     * @return 返回边实体类
     */
    public EdgeData[] getEdges(int edgeNum, List<String> vertex, int[][] matrix){
        int index = 0;
        EdgeData[] edges = new EdgeData[edgeNum];

        // 内循环采用j = i + 1，就可以只遍历上三角，而且，存起来的邻接顶点都是下标小的在前面
        for(int i = 0;i < vertex.size();i++){
            for(int j = i + 1;j < vertex.size();j++){
                if(matrix[i][j] != 0 && matrix[i][j] != Integer.MAX_VALUE){
                    edges[index++] = new EdgeData(vertex.get(i),vertex.get(j),matrix[i][j]);
                }
            }
        }
        return edges;
    }

    /**
     * 获取顶点位置下标
     * @param v 顶点值
     * @param vertex 顶点集合
     * @return 返回顶点位置下标
     */
    public int getPosition(String v, List<String> vertex){
        for(int i = 0;i < vertex.size();i++){
            if(vertex.get(i).equals(v)){
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据边权值对边进行排序
     * @param edges 边实体类
     * @param edgeNum 边数
     */
    public void sortEdges(EdgeData[] edges, int edgeNum){

        for(int i = 0;i < edgeNum - 1;i++){
            for(int j = 0;j < edgeNum - 1-i;j++){

                if(edges[j].weight > edges[j+1].weight){
                    EdgeData tmp = edges[j];
                    edges[j] = edges[j+1];
                    edges[j+1] = tmp;
                }
            }
        }
    }

    /**
     * 暂停线程
     *  - 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
     */
    public void onPause() {
        // 创建一把锁对象lock，调用wait()会释放锁，同时暂停线程；调用notify()函数会唤醒锁，从而重新获取这把锁
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用该方法实现线程的暂停
     */
    public void pauseThread() {
        pause = true;
    }

    /**
     * 调用该方法实现恢复线程的运行
     */
    public void resumeThread() {
        pause = false;
        // 调用notify()函数唤醒锁，恢复线程
        synchronized (lock){
            lock.notify();
        }
    }
}
