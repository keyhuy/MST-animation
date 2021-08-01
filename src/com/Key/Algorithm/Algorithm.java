package com.Key.Algorithm;

import java.util.List;

/**
 * 算法具体实现类
 *
 * @author Key
 * @date 2021/06/17/13:18
 **/
public class Algorithm {

    private final List<String> vex;
    public int index = 0;

    /**
     * 构造器，传入图结构和算法名字
     * @param graph 图结构
     */
    public Algorithm(Graph graph) {
        this.vex = graph.getVex();
    }

    /**
     * Prim算法的具体实现（适合稠密图）
     * @param g 传入图结构
     * @param firstVex 起始顶点
     * @return 返回每次获取的最小边数组
     */
    public EdgeData[] MST_Prim(Graph g, int firstVex) {

        // 获取每次得到的最小边
        EdgeData[] edges = getEdges(g.getEdgeNum(),vex,g.getEdges());
        // 节点个数
        int vexNum = g.getVexNum();
        // 当前结果树到所有顶点的最短距离
        int[] minWeight = new int[vexNum];
        // adjVex[C] = 0，代表C是通过A加入结果树的（0是A的下标）
        int[] adjVex = new int[vexNum];

        // 初始化两个辅助数组
        for(int i = 0; i < vexNum; i++) {
            //第一个顶点到其余顶点的距离
            minWeight[i] = (g.getEdges())[firstVex][i];
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
                if(minWeight[j] != 0 && (g.getEdges())[minVex][j] < minWeight[j] && (g.getEdges())[minVex][j] > 0) {
                    minWeight[j] = (g.getEdges())[minVex][j];
                    adjVex[j] = minVex;
                }
            }
            int pre = adjVex[minVex];
            int end = minVex;

            // 存入每次得到的最小边顶点值
            edges[index].start = g.getVex().get(pre);
            edges[index].end = g.getVex().get(end);
            index++;
            System.out.println("(" + g.getVex().get(pre) + "," + g.getVex().get(end) + ")");
        }

        return edges;
    }

    /**
     * Kruskal算法具体实现（适合稀疏图）
     * @param g 传入图结构
     * @return 返回每次获取到的最小边数组
     */
    public EdgeData[] MST_Kruskal(Graph g) {

        int edgeNum = g.getEdgeNum();
        // 记录每个顶点对应的终点
        int[] end = new int[g.getVexNum()];
        // 保存最小生成树
        EdgeData[] rets = new EdgeData[edgeNum];
        // 获取边的集合
        EdgeData[] edges = getEdges(edgeNum,vex,g.getEdges());
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

                System.out.println("(" + rets[index - 1].start + "," + rets[index - 1].end + ")");
            }
        }

        return rets;
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
}
