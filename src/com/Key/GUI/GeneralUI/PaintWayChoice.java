package com.Key.GUI.GeneralUI;

import com.Key.Algorithm.Graph;
import com.Key.Check.ParamCheck;
import com.Key.GUI.StyleChange;
import com.Key.GUI.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 选择画图方式
 *
 * @author Key
 * @date 2021/06/17/13:03
 **/
public class PaintWayChoice {

    /**
     * 改变样式的实体类
     */
    StyleChange styleChange = new StyleChange();

    /**
     * 构造方法
     *  - 选择画图方式
     *   - 自动生成/手动输入
     */
    public PaintWayChoice() {
        final MyFrame myFrame = new MyFrame("最小生成树算法演示");
        JPanel panel = new JPanel();
        // 检查输入参数合法性实体类
        ParamCheck checkInput = new ParamCheck();

        // 设置窗口大小
        myFrame.setSize(500,200);

        // 文本
        JLabel label = new JLabel("欢迎进入算法演示，请选择绘图方式：");
        // 按键（修改和查询）
        JButton randomBtn = new JButton("随机生成");

        // 菜单栏的方式
        JMenuBar selfBar = new JMenuBar();
        JMenu selfMenu = new JMenu("手动输入参数");
        JMenuItem matrixItem = new JMenuItem("输入邻接矩阵");
        JMenuItem edgesItem = new JMenuItem("输入边");
        selfMenu.add(matrixItem);
        selfMenu.add(edgesItem);
        selfBar.add(selfMenu);

        // 各个组件数组
        JLabel[] labels = {label};
        JButton[] buttons = {randomBtn};
        JMenuItem[] items = {matrixItem,edgesItem};

        // 改变样式
        styleChange.bestStyle(labels,buttons,null,items,selfMenu,150,40);

        // 在面板中加入组件
        panel.add(label);
        panel.add(randomBtn);
        panel.add(selfBar);

        // 把面板加到窗口中
        myFrame.add(panel);

        // 点击随机生成
        randomBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("随机");
                String[] strings = showDialog(myFrame, myFrame, 2);

                // 输入值都为空时不进行任何操作
                if (strings[0] != null && strings[1] != null) {
                    // 检测输入参数合法性
                    String message = checkInput.checkVexAndEdgeNum(strings);
                    if ("legal".equals(message)) {

                        // 用于获取顶点值
                        List<String> vertex = new ArrayList<>();
                        // 获取顶点数和边数
                        int vexNum = Integer.parseInt(strings[0]);
                        int edgeNum = Integer.parseInt(strings[1]);

                        int index1,index2;

                        // 生成各个顶点
                        for (int i = 0;i < vexNum;i++) {
                            vertex.add("V" + i);
                        }

                        // 创建边信息数组(邻接矩阵)
                        int[][] edges = new int[vexNum][vexNum];

                        // 检测是否是连通图，不是的话要重新生成矩阵数组
                        int flag = 1;
                        while (flag == 1) {
                            // 每次重新生成都要初始化，元素全部置为0
                            for (int[] edge : edges) {
                                Arrays.fill(edge, 0);
                            }

                            // 根据边数创建边信息数组
                            for (int i = edgeNum;i > 0; ) {
                                // 随机生成两个不相等的索引值
                                index1 = (int)(Math.random() * vexNum);
                                index2 = (int)(Math.random() * vexNum);

                                if ((index1 != index2) && (edges[index1][index2] == 0)) {
                                    // 随机生成边，即生成权值（[1,11)之间的数）
                                    edges[index1][index2] = (int)(Math.random() * 10 + 1);
                                    edges[index2][index1] = edges[index1][index2];
                                    // 成功生成一条边后再i--
                                    i--;
                                }
                            }

                            // 生成边权值后，把剩下的0元素都改成无穷大m
                            for (int i = 0;i < edges.length;i++) {
                                for (int j = 0;j < edges[i].length;j++) {
                                    if ((i != j) && (edges[i][j] == 0)) {
                                        edges[i][j] = Integer.MAX_VALUE;
                                        edges[j][i] = Integer.MAX_VALUE;
                                    }
                                }
                            }

                            // 用于标记顶点是否已被访问，1-已被访问，0-未访问
                            int[] visited = new int[vexNum];
                            checkInput.count = 0;

                            // 初始化，开始全部顶点未被访问
                            Arrays.fill(visited,0);

                            // 调用深度优先函数，遍历整个图，找出连通分量，如果遍历顶点数不等于顶点数，则不是连通图
                            checkInput.DFS(visited,0,vexNum,edges);

                            if (checkInput.count != vexNum) {
                                System.out.println("该图不是连通图！");
                                flag = 1;
                            }else {
                                flag = 0;
                            }
                        }

                        // 创建一个图结构
                        Graph graph = new Graph(vertex,edges);
                        // 进入选择算法演示方式界面
                        new AnimWayChoice(graph);

                        myFrame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(myFrame,message,"提示",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // 点击输入矩阵
        matrixItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("输入矩阵");
                String[] strings = showDialog(myFrame, myFrame, 1);

                // 输入值为空时不进行任何操作
                if (strings[0] != null) {
                    // 检测输入参数合法性
                    String message = checkInput.checkVexAndEdgeNum(strings);
                    if ("legal".equals(message)) {
                        // 传入顶点数
                        new InputParam(Integer.parseInt(strings[0]));

                        myFrame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(myFrame,message,"提示",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // 点击输入边
        edgesItem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("输入边");
                String[] strings = showDialog(myFrame, myFrame, 2);

                // 输入值为都空时不进行任何操作
                if (strings[0] != null && strings[1] != null) {
                    // 检测输入参数合法性
                    String message = checkInput.checkVexAndEdgeNum(strings);
                    if ("legal".equals(message)) {

                        // 参数都无误，传入顶点数和边数
                        new InputParam(Integer.parseInt(strings[0]),Integer.parseInt(strings[1]));

                        myFrame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(myFrame,message,"提示",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // 窗口居中，要在对应类加，在MyFrame类加没效
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }

    /**
     * 输入顶点数和边数的对话框
     * @param owner 对话框拥有者
     * @param parentComponent 对话框父级组件
     * @param rows 是否需要输入边数
     * @return 返回输入的信息
     */
    private String[] showDialog(MyFrame owner, Component parentComponent, int rows) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "输入参数", true);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // 获取输入的内容
        String[] contents = new String[rows];

        // 设置对话框的宽高
        dialog.setSize(300, 160);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);

        JLabel vLabel = new JLabel("请输入顶点数：");
        JLabel eLabel = new JLabel("请输入边数：");
        JTextField vText = new JTextField();
        JTextField eText = new JTextField();
        JButton okBtn = new JButton("确认");

        // 各个组件数组
        JLabel[] labels = {vLabel,eLabel};
        JTextField[] texts = {vText,eText};
        JButton[] buttons = {okBtn};

        // 改变样式
        styleChange.bestStyle(labels,buttons,texts,null,null,80,30);

        // 添加组件到面板
        panel.add(vLabel);
        panel.add(vText);

        // rows = 2表示选择输入边，需要输入边数；rows = 1表示输入矩阵，不需要输入边数
        if (rows == 2) {
            panel.add(eLabel);
            panel.add(eText);
        }
        panel.add(okBtn);

        // 监听确认按键事件
        okBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取输入的参数
                contents[0] = vText.getText();
                if (rows == 2) {
                    contents[1] = eText.getText();
                }

                dialog.dispose();
            }
        });

        // 设置对话框的内容面板
        dialog.add(panel);
        // 显示对话框
        dialog.setVisible(true);

        return contents;
    }
}
