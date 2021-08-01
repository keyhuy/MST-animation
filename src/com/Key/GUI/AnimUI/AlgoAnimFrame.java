package com.Key.GUI.AnimUI;

import com.Key.Algorithm.Graph;
import com.Key.GUI.StyleChange;
import com.Key.GUI.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 放MyPanel算法演示面板的窗口
 *  - 算法实体类->algo
 *  - 算法名字->algoName
 *  - 是否点击暂停->isPause
 *  - 是否点击开始->isStart
 *  - Prim算法执行起始顶点下标->firstVex
 *
 * @author Key
 * @date 2021/06/17/13:32
 **/
public class AlgoAnimFrame {

    private final GraphPainting paintG;

    private final String algoName;
    private boolean isPause = false;
    private boolean isStart = false;

    public int firstVex;

    public AlgoAnimFrame(Graph graph, String name, long time) {
        this.algoName = name;
        final MyFrame jf = new MyFrame(name);
        // 显示演示方式
        String showText;

        // 设置窗口的大小和布局
        jf.setSize(1000, 700);
        jf.setLayout(new GridLayout(2,1));

//      +-------------------------------初始化第一个面板myPanel->用于显示图形和演示过程--------------------------------+

        // 根据time值选择对应演示方式（手动演示time为-1）->初始化paintG
        if (time == -1) {
            this.paintG = new GraphPainting(graph,1000);
            showText = "手动演示";
        }else {
            this.paintG = new GraphPainting(graph,time);
            showText = "自动步演示";
        }

        // 创建有图结构的面板
        paintG.myPanel = new MyPanel(graph);

//      +----------------------------------------------------------------------------+


//      +--------------------------------初始化第二个面板panel->用于显示按钮和文字标签----------------------------------+

        // 创建第二个面板，用于显示按钮和标签文字
        JPanel panel = new JPanel();

        // 控制演示的按键
        JButton startBtn = new JButton("开始演示");
        JButton pauseBtn = new JButton("暂停/继续演示");
        JButton nextBtn = new JButton("下一步");

        JLabel label  = new JLabel("<html><br><br><br>" +
                "------------------------------------" +
                showText +
                "------------------------------------" +
                "<html>");

        JLabel[] labels = {label};
        JButton[] buttons = {pauseBtn,startBtn,nextBtn};

        new StyleChange().bestStyle(labels,buttons,null,null,null,150,30);

        panel.add(label);
        panel.add(startBtn);

        // 根据传入的时间值选择对应的按钮组件
        if (time == -1) {
            panel.add(nextBtn);
        }else {
            panel.add(pauseBtn);
        }

//      +----------------------------------------------------------------------------+

        // 在窗口中加入两个面板
        jf.add(paintG.myPanel);
        jf.add(panel);

        // 根据算法名字显示对应的文字
        if ("Prim算法演示".equals(name)) {
            paintG.myPanel.pOrK = "P";
        }else {
            paintG.myPanel.pOrK = "K";
        }

//        +--------------------------------------------监听事件-----------------------------------------------+

        // 建立两个线程，分别执行两个算法
        Thread algo1 = new Thread() {
            @Override
            public void run() {
                // 算法执行前先把原图画出来
                paintG.drawGraph(null);
                paintG.graphPainting("P",firstVex);
            }
        };

        Thread algo2 = new Thread() {
            @Override
            public void run() {
                // 开始执行前先把原图画出来
                paintG.drawGraph(null);
                paintG.graphPainting("K",-1);
            }
        };

        /*
          每次对子线程进行操作前都要先让主线程休眠一段时间
         */

        // 开始演示按键
        startBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("开始演示算法");

                   try {
                       if ("Prim算法演示".equals(algoName)) {
                           algo1.start();
                       }else {
                           algo2.start();
                       }
                       isStart = true;

                       // 如果是手动演示，则需要开始演示后就暂停演示
                       if (time == -1) {
                           Thread.sleep(100);
                           // 暂停线程
                           paintG.pauseThread();
                       }
                       // 执行完开始按键后把按键置为不可点击
                       startBtn.setEnabled(false);
                   } catch(InterruptedException e1) {
                       e1.printStackTrace();
                   }
            }
        });

        // 暂定/继续按键
        pauseBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("暂停/继续演示");

                try {
                    if (!isPause) {
                        // 暂停线程
                        Thread.sleep(100);
                        paintG.pauseThread();
                        isPause = true;
                    }else {
                        // 恢复线程
                        Thread.sleep(100);
                        paintG.resumeThread();
                        isPause = false;
                    }

                    // 演示结束，关闭按键
                    if (paintG.myPanel.isEnd) {
                        pauseBtn.setEnabled(false);
                    }
                } catch(InterruptedException e1) {
                    e1.printStackTrace();
                }

            }
        });

        // 点击下一步按键
        nextBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("下一步");

                try {
                    // 演示开始后再进行操作
                    if (isStart) {
                        // 先恢复进程，过0.1秒后再暂停线程
                        Thread.sleep(100);
                        paintG.resumeThread();
                        Thread.sleep(100);
                        paintG.pauseThread();
                        Thread.sleep(100);
                    }

                    // 演示结束后关闭按键
                    if (paintG.myPanel.isEnd) {
                        nextBtn.setEnabled(false);
                    }
                } catch(InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}
