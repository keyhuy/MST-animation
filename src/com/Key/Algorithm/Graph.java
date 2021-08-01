package com.Key.Algorithm;

import java.util.List;

/**
 * 图的结构
 *  - vex存储顶点
 *  - edges存储边
 *
 * @author Key
 * @date 2021/06/16/21:48
 **/
public class Graph {

    private final List<String> vex;
    private final int[][] edges;

    /**
     * 图的构造器
     * @param vex 存放顶点的礼盒
     * @param edges 存放边信息的二维数组
     */
    public Graph(List<String> vex, int[][] edges) {
        this.vex = vex;
        this.edges = edges;
    }

    public List<String> getVex() {
        return vex;
    }

    public int[][] getEdges() {
        return edges;
    }

    /**
     * 获取顶点个数
     * @return 返回顶点个数
     */
    public int getVexNum() {
        return vex.size();
    }

    /**
     * 获取边个数
     * @return 返回边个数
     */
    public int getEdgeNum() {

        int sum = 0;

        for (int i = 0;i < edges.length;i++) {
            for (int j = i + 1;j < edges[i].length;j++) {
                if (edges[i][j] != 0 && edges[i][j] != Integer.MAX_VALUE) {
                    sum++;
                }
            }
        }

        return sum;
    }
}
