package com.Key.GUI.GeneralUI;

import com.Key.Algorithm.Graph;
import com.Key.Check.ParamCheck;
import com.Key.GUI.AnimUI.AlgoAnimFrame;
import com.Key.GUI.StyleChange;
import com.Key.GUI.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 选择算法
 *  - Prim/Kruskal
 *      - 记住输入的内容，便于检测合法性->content
 *      - 记住合法的输入值（Prim算法执行起始顶点）->fVex
 *
 * @author Key
 * @date 2021/06/17/13:11
 **/
public class AlgorithmChoice {

    StyleChange styleChange = new StyleChange();
    ParamCheck checkInput = new ParamCheck();
    private String content;
    int fVex = -1;

    /**
     * 构造器
     * @param graph 传入图结构
     */
    public AlgorithmChoice(Graph graph, long time) {
        final MyFrame myFrame = new MyFrame("选择算法");
        // 控制面板布局：3行1列，行列间距都是10
        JPanel panel = new JPanel(new GridLayout(3,1,10,10));

        // 设置窗口布局
        myFrame.setLayout(new FlowLayout());
        myFrame.setSize(400,300);

        // 文本
        JLabel label = new JLabel("请选择算法：");
        // 按键（修改和查询）
        JButton pBtn = new JButton("Prim算法");
        JButton kBtn = new JButton("Kruskal算法");

        // 各个组件数组
        JLabel[] labels = {label};
        JButton[] buttons = {pBtn,kBtn};

        // 改变样式
        styleChange.bestStyle(labels,buttons,null,null,null,85,40);

        // 在面板中加入组件
        panel.add(label);
        panel.add(pBtn);
        panel.add(kBtn);

        // 把面板加到窗口中
        myFrame.add(panel);

        // 建立两个线程，分别进入两个算法窗口，采用线程，程序响应更快
        Thread view1 = new Thread() {
            @Override
            public void run() {
                new AlgoAnimFrame(graph,"Prim算法演示",time).firstVex = fVex;;
                myFrame.dispose();
            }
        };

        Thread view2 = new Thread() {
            @Override
            public void run() {
                new AlgoAnimFrame(graph,"Kruskal算法演示",time);
                myFrame.dispose();
            }
        };

        // 选择Prim算法
        pBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Prim");
                showDialog(myFrame, myFrame, graph.getVexNum());

                // 输入值为空就不进行操作
                if (content != null) {
                    // 检测是否输入值是否合法（全局变量fVex是否改变）
                    if (fVex == -1) {
                        JOptionPane.showMessageDialog(myFrame,content,"提示",JOptionPane.INFORMATION_MESSAGE);
                    }else {
                        // 输入值合法后再开启线程
                        view1.start();
                    }
                }
            }
        });

        // 选择Kruskal算法
        kBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Kruskal");
                view2.start();
            }
        });

        // 窗口居中，要在对应类加，在MyFrame类加没效
        myFrame.setLocationRelativeTo(null);
        myFrame.setVisible(true);
    }

    /**
     * 输入Prim算法执行起始顶点
     * @param owner 对话框拥有者
     * @param parentComponent 对话框父级组件
     * @param vexNum 顶点数
     */
    public void showDialog(MyFrame owner, Component parentComponent, int vexNum) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "输入参数", true);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // 设置对话框的宽高
        dialog.setSize(350, 160);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);

        JLabel label1 = new JLabel("请输入算法执行起始顶点下标");
        JLabel label2 = new JLabel("范围：" + "[" + "0-" + (vexNum-1) + "]");
        JTextField text = new JTextField();

        JButton okBtn = new JButton("确认");

        JLabel[] labels = {label2,label1};
        JTextField[] texts = {text};
        JButton[] buttons = {okBtn};

        styleChange.bestStyle(labels,buttons,texts,null,null,100,30);

        panel.add(label1);
        panel.add(text);
        panel.add(label2);
        panel.add(okBtn);

        // 点击确认
        okBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("确认");

                content = text.getText();

                // 检测输入内容合法性
                String message = checkInput.checkFirstVex(content, vexNum);
                if ("legal".equals(message)) {

                    // 如果合法，就将输入值直接记住（改变全局变量fVex）
                    fVex = Integer.parseInt(content);
                }else {
                    // 不合法，记住检测结果信息
                    content = message;
                }

                dialog.dispose();
            }
        });

        dialog.setContentPane(panel);
        // 显示对话框
        dialog.setVisible(true);
    }
}
