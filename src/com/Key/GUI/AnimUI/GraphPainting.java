package com.Key.GUI.AnimUI;

import com.Key.Algorithm.Algorithm;
import com.Key.Algorithm.EdgeData;
import com.Key.Algorithm.Graph;

import java.util.List;

/**
 * 具体画图类
 *
 * @author Key
 * @date 2021/06/24/23:12
 **/
public class GraphPainting {

    public MyPanel myPanel;

    private final long sleepTime;
    private final List<String> vex;
    private final Graph graph;
    private final Algorithm algo;

    /**
     * 用于控制线程（绘图）暂停或继续
     */
    private final Object lock = new Object();
    private volatile boolean pause = false;

    /**
     * 构造器，传入图结构和算法名字
     * @param graph 图结构
     */
    public GraphPainting(Graph graph, long time) {
        this.graph = graph;
        this.vex = graph.getVex();
        this.sleepTime = time;

        // 把图结构传入算法实现类中
        this.algo = new Algorithm(graph);
    }

    /**
     * 执行面板中的paint方法，使用repaint()重复画图
     * @param vs 执行Prim算法每次得到的边（两个顶点）
     */
    public void drawGraph(String[] vs) {
        if (vs != null) {
            for (int i = 0;i < vex.size();i++) {
                if (vs[0].equals(vex.get(i))) {
                    myPanel.v1 = i;
                    myPanel.adj.add(i);
                }
                if (vs[1].equals(vex.get(i))) {
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
     * 绘制相应算法的图形
     * @param algoName 算法名字(P/K)
     * @param firstVex Prim算法起始顶点
     */
    public void graphPainting(String algoName, int firstVex) {
        String[] content = new String[2];
        // 获取最小边数组
        EdgeData[] edgeData;
        System.out.println("最小生成树为");

        // 根据算法名字调用对应算法具体实现方法
        if ("P".equals(algoName)) {
            edgeData = algo.MST_Prim(graph, firstVex);

            // 如果只有一个顶点（也是连通图），则直接把该顶点变红色，然后演示结束
            if (graph.getEdgeNum() == 0) {
                myPanel.pOrK = "P1";
                drawGraph((null));
            }
        }else {
            edgeData = algo.MST_Kruskal(graph);

            // 先把全部顶点选入
            myPanel.pOrK = "K1";
            drawGraph((null));
        }

        // 获取最小边的个数
        int minEdgeNum = algo.index;

        // 根据得到的最小边数组绘图
        for(int j = 0;j < minEdgeNum;j++) {
            content[0] = edgeData[j].start;
            content[1] = edgeData[j].end;

            // 判断是否暂停线程
            if (pause) {
                onPause();
            }
            // 具体绘制图形
            drawGraph(content);
        }

        // 最后再画一次图，把最小生成树显示出来
        myPanel.isEnd = true;
        myPanel.repaint();
    }

    /**
     * 暂停线程
     *  - 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
     *
     *  - 用wait()方法暂停线程和notify()方法唤醒线程，而不用Java已弃用的suspend()和resume()，原因：
     *      1） suspend() 需要与 resume() 搭配使用，使用不当时，极易造成公共的同步对象的独占，使得其他线程无法访问公共同步对象。
     *          suspend() 暂停线程时，被暂停的线程还会继续的占用同步对象，不释放锁。
     *          （也就是说调用suspend方法，线程会暂停下来，但不会释放锁，其他线程就不能获取锁定的资源，除非调用resume方法恢复线程->臭流氓行为。
     *              对任何线程来说，如果它们想恢复目标线程，同时又试图使用任何一个锁定的资源，就会造成死锁）
     *      2）在使用 suspend() 和 resume() 方法时，也容易出现因为线程的暂停而导致数据不同步的情况。
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
