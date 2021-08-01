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
 * 输入参数UI
 *
 * @author Key
 * @date 2021/06/15/22:36
 **/
public class InputParam {

    /**
     * 修改样式实体类
     */
    StyleChange styleChange = new StyleChange();
    /**
     * 检查输入参数合法性实体类
     */
    ParamCheck checkInput = new ParamCheck();

    /**
     * 构造器1.0
     *  - 只输入顶点数
     * @param vexNum 顶点数
     */
    public InputParam(int vexNum) {
        final MyFrame myFrame = new MyFrame("输入邻接矩阵");
        // 控制面板布局：3行1列，行列间距都是10
        JPanel[] panels = {
                new JPanel(),
                new JPanel(new GridLayout(vexNum,vexNum,5,5)),
                new JPanel(),
        };

        // 设置窗口布局
        myFrame.setLayout(new GridLayout(3,1));
        myFrame.setSize(600,500);

        // 各个组件
        JLabel label1 = new JLabel("请输入各个顶点值(a-z)：");
        JTextField[] vexText  = new JTextField[vexNum];

        JLabel label = new JLabel("<html>" + "<br>"  + "请按格式输入矩阵元素：" + "<br>" +
                "注意：权值取值1-10;用m表示不存在边;只需填上三角" + "<html>");
        JTextField[] texts = new JTextField[vexNum * vexNum];

        JButton okBtn = new JButton("确认");

        JLabel[] labels = {label1,label};
        JButton[] buttons = {okBtn};

        // 改变样式
        styleChange.bestStyle(labels,buttons,null,null,null,80,30);

        // 面板加入组件
        panels[0].add(label1);

        for (int i = 0;i < vexText.length;i++) {
            vexText[i] = new JTextField(3);
            panels[0].add(vexText[i]);
        }
        panels[0].add(label);


        // 矩阵文本框初始化
        for (int i = 0;i < texts.length;i++) {
            texts[i] = new JTextField();
        }

        // 将矩阵下三角置为不可填
        for (int i = 0;i < vexNum;i++) {
            for (int j = i;j >= 0;j--) {
                if (j == i) {
                    texts[vexNum * i + i] = new JTextField("0");
                }
                texts[vexNum * i + j].setEditable(false);
            }
        }

        // 将文本框加入面板
        for (JTextField text : texts) {
            panels[1].add(text);
        }

        panels[2].add(okBtn);

        // 窗口中加入面板
        for (JPanel jp : panels) {
            myFrame.add(jp);
        }

        // 点击确认，收集输入信息
        okBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 先检查顶点值合法性
                String vexMessage = checkInput.checkVex(vexText);
                if ("legal".equals(vexMessage)) {

                    // 用于获取顶点值
                    List<String> vertex = new ArrayList<>();

                    // 输入参数无误后，获取顶点值
                    for (JTextField v : vexText) {
                        vertex.add(v.getText());
                    }

                    // 检测输入权值合法性
                    String edgeMessage = checkInput.checkEdge(texts,vertex.size());
                    if ("legal".equals(edgeMessage)) {

                        // 输入信息合法后，创建边信息数组，vertex.size()即顶点数
                        int[][] edges = new int[vertex.size()][vertex.size()];
                        // 获取边信息
                        int edgeNum = 0;

                        for (int i = 0;i < edges.length;i++) {
                            for (int j = i + 1;j < edges[i].length;j++) {
                                // 元素m表示无穷大，即无边
                                if ("m".equals(texts[vertex.size() * i + j].getText())) {
                                    edges[i][j] = Integer.MAX_VALUE;
                                    edges[j][i] = Integer.MAX_VALUE;
                                }else {
                                    edges[i][j] = Integer.parseInt(texts[vertex.size() * i + j].getText());
                                    edges[j][i] = edges[i][j];
                                    // 记录边的数量
                                    edgeNum++;
                                }
                            }
                        }

                        // 检测是否是连通图
                        String connMessage = checkInput.checkConnectivity(edges,vertex);
                        if ("legal".equals(connMessage)) {

                            // 创建一个图结构
                            Graph graph = new Graph(vertex,edges);
                            // 进入选择算法演示方式界面
                            new AnimWayChoice(graph);

                            myFrame.dispose();
                        }else {
                            JOptionPane.showMessageDialog(myFrame,connMessage,"提示",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(myFrame,edgeMessage,"提示",JOptionPane.INFORMATION_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(myFrame,vexMessage,"提示",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }

    /**
     * 构造器2.0
     *  - 输入顶点数和边数
     * @param vexNum 顶点数
     * @param edgeNum 边数
     */
    public InputParam(int vexNum, int edgeNum) {
        final MyFrame myFrame = new MyFrame("输入边");
        JPanel[] panels = {
                new JPanel(),
                new JPanel(),
                new JPanel(),
        };
        JPanel[] panels1;

        // 根据边数变化窗口的高度以及面板panels1的数量
        int size;
        if (edgeNum % 3 == 0) {
            size = edgeNum/3;
        }else {
            size = edgeNum/3 + 1;
        }
        panels1 = new JPanel[size];

        // 设置窗口布局
        myFrame.setLayout(new GridLayout(3 + size,1));
        myFrame.setSize(860,175 + size * 100);

        JLabel label = new JLabel("请按格式输入边的邻接顶点(a-z)和权值(1-10)，如(a b 5)");

        JLabel label1 = new JLabel( "请输入各个顶点值(a-z)：");
        JTextField[] vexText  = new JTextField[vexNum];

        JButton okBtn = new JButton("确认");

        JLabel[] labels = {label,label1};
        JButton[] buttons = {okBtn};

        // 改变样式
        styleChange.bestStyle(labels,buttons,null,null,null,80,30);

        // 面板加入组件
        panels[0].add(label);
        panels[1].add(label1);

        // 注意：用foreach循环改不了了元素值
        for (int i = 0;i < vexText.length;i++) {
            vexText[i] = new JTextField(3);
            panels[1].add(vexText[i]);
        }

        // 创建标签数组，用于存放提示填写边信息
        String[] inputManage = new String[edgeNum];
        for (int i = 0;i < inputManage.length;i++) {
            inputManage[i] = "第"+ (i+1) +"条边信息：";
        }

        JLabel[] jls = new JLabel[edgeNum];
        for (int i = 0;i < jls.length;i++) {
            jls[i] = new JLabel(inputManage[i]);
            jls[i].setFont(new Font("楷体",Font.BOLD,20));
        }

        // 创建文本数组，用于填写边邻接顶点和权值
        JTextField[] texts = new JTextField[edgeNum * 3];
        for (int i = 0;i < texts.length;i++) {
            texts[i] = new JTextField(3);
        }

        // 根据边数加入对应的标签和文本组件到面板中
        for (int j = 0,i = 0,k = 0;j < panels1.length;j++,k += 3,i += 9) {
            panels1[j] = new JPanel();

            panels1[j].add(jls[k]);
            panels1[j].add(texts[i]);
            panels1[j].add(texts[i + 1]);
            panels1[j].add(texts[i + 2]);

            if (k + 1 < jls.length) {
                panels1[j].add(jls[k + 1]);
                panels1[j].add(texts[i + 3]);
                panels1[j].add(texts[i + 4]);
                panels1[j].add(texts[i + 5]);
            }

            if (k + 2 < jls.length) {
                panels1[j].add(jls[k + 2]);
                panels1[j].add(texts[i + 6]);
                panels1[j].add(texts[i + 7]);
                panels1[j].add(texts[i + 8]);
            }
        }

        panels[2].add(okBtn);

        // 窗口中加入面板
        myFrame.add(panels[0]);
        myFrame.add(panels[1]);
        for (JPanel jp : panels1) {
            myFrame.add(jp);
        }
        myFrame.add(panels[2]);

        // 点击确认收集输入信息
        okBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 用于获取顶点值
                List<String> vertex = new ArrayList<>();
                int index1 = 0,index2 = 0;

                // 先检查顶点值合法性
                String vexMessage = checkInput.checkVex(vexText);
                if ("legal".equals(vexMessage)) {

                    // 顶点值合法，获取各个顶点
                    for (int i = 0;i < vexNum;i++) {
                        vertex.add(vexText[i].getText());
                    }

                    // 检查边信息合法性
                    String adjMessage = checkInput.checkAdj(texts,vertex);
                    if ("legal".equals(adjMessage)) {

                        // 边信息合法，创建边信息数组
                        int[][] edges = new int[vexNum][vexNum];
                        // 初始化，元素全部置为0
                        for (int[] edge : edges) {
                            // 新操作
                            Arrays.fill(edge, 0);
                        }

                        // 先把邻接顶点和权值存起来
                        String[] adj = new String[edgeNum * 2];
                        String[] weights = new String[edgeNum];
                        for (int i = 0,k = 0,a = 0;i < texts.length;i += 3) {
                            adj[k] = texts[i].getText();
                            adj[k + 1] = texts[i + 1].getText();
                            weights[a] = texts[i + 2].getText();
                            k += 2;
                            a++;
                        }

                        // 遍历顶点值集合，找到邻接顶点对应位置
                        for (int j = 0,k = 0;j < adj.length;j += 2) {
                            // 找到邻接顶点索引值
                            for (int i = 0;i < vertex.size();i++) {
                                if (adj[j].equals(vertex.get(i))) {
                                    index1 = i;
                                }
                                if (adj[j + 1].equals(vertex.get(i))) {
                                    index2 = i;
                                }
                            }

                            // 再对应边位置存入权值
                            edges[index1][index2] = Integer.parseInt(weights[k]);
                            edges[index2][index1] = edges[index1][index2];
                            k++;
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

                        // 判断生成的图是否是连通图
                        String connMessage = checkInput.checkConnectivity(edges,vertex);
                        if ("legal".equals(connMessage)) {
                            // 全部信息无误后创建图结构，进入算法演示
                            // 创建一个图结构
                            Graph graph = new Graph(vertex,edges);
                            // 进入选择算法界面
                            new AnimWayChoice(graph);

                            myFrame.dispose();
                        }else {
                            JOptionPane.showMessageDialog(myFrame,connMessage,"提示",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else {
                        JOptionPane.showMessageDialog(myFrame,adjMessage,"提示",JOptionPane.INFORMATION_MESSAGE);
                    }
                }else {
                    JOptionPane.showMessageDialog(myFrame,vexMessage,"提示",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }
}
