package com.Key.GUI.AnimUI;

import com.Key.Algorithm.EdgeData;
import com.Key.Algorithm.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定义面板
 *  - 继承JPanel
 *  - 顶点集合 vex
 *  - 权值和顶点个数 weight,vexNum,edgeNum
 *  - 顶点值 vertex
 *  - 标记算法是否执行完 isEnd
 *  - 记录每次执行算法得到的顶点对应下标 adj
 *
 * @author Key
 * @date 2021/06/16/14:49
 **/
public class MyPanel extends JPanel {

    private final List<String> vex;
    private final int vexNum;
    private final int edgeNum;
    private final EdgeData[] edgeData;

    public String pOrK;
    public Boolean isEnd = false;
    public List<Integer> adj = new ArrayList<>();
    public int v1 = -1,v2 = -1;
    public Color[] vexColors = new Color[6];
    public Color[] edgeColors = new Color[15];

    /**
     * 构造方法
     *  - 传入图结构
     * @param graph 图结构
     */
    public MyPanel(Graph graph) {
        super();
        this.vex = graph.getVex();
        this.vexNum = graph.getVexNum();
        this.edgeNum = graph.getEdgeNum();
        int[][] edges = graph.getEdges();

        // 创建一个边实体类
        edgeData = new EdgeData[edgeNum];
        int index = 0;

        // 获取边信息，存入边实体类中
        for (int i = 0;i < vexNum;i++) {
            for (int j = i + 1;j < vexNum;j++) {
                if (edges[i][j] != 0 && edges[i][j] != Integer.MAX_VALUE) {
                    edgeData[index++] = new EdgeData(vex.get(i),vex.get(j), edges[i][j]);
                }
            }
        }

        Arrays.fill(vexColors, Color.BLACK);
        Arrays.fill(edgeColors,Color.BLACK);
    }

    /**
     * 重写paint方法，画图结构
     * @param g 画笔
     */
    @Override
    public void paint(Graphics g) {

        // 调用父类的paint，一定要写
        super.paint(g);
        // 进入画图
        paintGraph(g);
    }

    /**
     * 封装画图的具体方法
     * @param g 画笔
     */
    public void paintGraph(Graphics g) {

        // 改变面板中字体样式
        g.setFont(g.getFont().deriveFont(20f));
        // 参数Map
        GraphicalParam graphicalParam = new GraphicalParam();
        // 用于判断是否改变画笔颜色
        boolean is;

        // 画顶点
        for (int i = 0;i < vexNum;i++) {
            // 获取每个顶点值
            String vertex = vex.get(i);
            // 获取每个顶点的位置参数
            int[] vs = graphicalParam.vMap.get(i).clone();

            // 加上P1是因为如果只有一个顶点的情况，则直接把该顶点画笔边红色
            if ("K1".equals(pOrK) || "P1".equals(pOrK)) {
                // 第一次开始画图时，就显示的文字
                g.setColor(Color.BLACK);

                // 只有Kruskal算法才展示
                if ("K1".equals(pOrK)) {
                    g.drawString("选入全部顶点",655,95);
                }
                vexColors[i] = Color.RED;
            }

            // 使用对应画笔色（因为Kruskal算法一开始就选出全部顶点，故后面就不用再变顶点颜色）
            if ("P".equals(pOrK)) {
                // 把得到的最小边两个邻接顶点下标与当前顶点下标相比较，相等就把该顶点颜色变红
                for (int j = 0;j < adj.size();j += 2) {
                    is = ((adj.get(j) == i) || (adj.get(j + 1) == i));
                    if (is) {
                        vexColors[i] = Color.RED;
                        break;
                    }
                }
            }
            g.setColor(vexColors[i]);
            // 画顶点
            g.drawString(vertex,vs[0],vs[1]);
            g.drawOval(vs[2],vs[3],40,40);
        }

        // 画边
        for (int i = 0;i < edgeNum;i++) {
            // v1，v2记录每条边的邻接顶点
            int v1 = 0,v2 = 0;

            // 获取当前边邻接顶点的位置下标
            for (int k = 0;k < vex.size();k++) {
                if (vex.get(k).equals(edgeData[i].start)) {
                    v1 = k;
                }
                if (vex.get(k).equals(edgeData[i].end)) {
                    v2 = k;
                }
            }

            // 获取每条边的对应的位置参数
            String str1 = "(" + v1 + "," + v2 +")";
            String str2 = "(" + v2 + "," + v1 +")";
            int[] es = (graphicalParam.eMap.get(str1) != null) ? graphicalParam.eMap.get(str1) : graphicalParam.eMap.get(str2);

            // 根据每次得到的最小边改变画笔颜色
            for (int k = 0;k < adj.size();k += 2) {
                is = (adj.get(k) == v1 && adj.get(k + 1) == v2) || (adj.get(k) == v2 && adj.get(k + 1) == v1);
                if (is) {
                    edgeColors[i] = Color.RED;
                    break;
                }
            }
            g.setColor(edgeColors[i]);
            // 画边
            g.drawLine(es[0],es[1],es[2],es[3]);
            g.drawString(String.valueOf(edgeData[i].weight),es[4],es[5]);
        }

        // 展示算法名字
        g.setColor(Color.BLUE);
        if ("P".equals(pOrK) || "P1".equals(pOrK)) {
            g.drawString("Prim算法演示",650,60);
        }

        if ("K".equals(pOrK) || "K1".equals(pOrK)){
            g.drawString("Kruskal算法演示",650,60);
        }

        // 画流程的文字
        g.setColor(Color.BLACK);
        for (int i = 0,k = 1;i < adj.size();i += 2,k++) {
            g.drawString("第" + k + "次得到的最小边为：" + "(" + vex.get(adj.get(i)) + "," +
                    vex.get(adj.get(i + 1)) +")",590,125 + i * 15);
        }

        if (isEnd) {
            g.drawString("算法执行完毕！",650,125 + adj.size() * 20);
        }
    }
}
