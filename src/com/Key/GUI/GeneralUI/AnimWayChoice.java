package com.Key.GUI.GeneralUI;

import com.Key.Algorithm.Graph;
import com.Key.Check.ParamCheck;
import com.Key.GUI.StyleChange;
import com.Key.GUI.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * 选择算法演示方式
 *
 * @author Key
 * @date 2021/06/21/23:56
 **/
public class AnimWayChoice {

    /**
     * 改变样式的实体类
     */
    StyleChange styleChange = new StyleChange();

    /**
     * 获取输入的内容
     */
    private String content;

    /**
     * 构造方法
     *  - 选择演示方式
     *   - 自动步/手动进行
     */
    public AnimWayChoice(Graph graph) {
        final MyFrame myFrame = new MyFrame("选择演示方式");
        JPanel panel = new JPanel();

        // 检查输入参数合法性实体类
        ParamCheck checkInput = new ParamCheck();

        // 设置窗口大小
        myFrame.setSize(350,200);

        // 文本
        JLabel label = new JLabel("请选择算法演示方式：");
        // 按键（修改和查询）
        JButton autoBtn = new JButton("自动步进行");
        JButton selBtn = new JButton("手动进行");

        // 各个组件数组
        JLabel[] labels = {label};
        JButton[] buttons = {autoBtn,selBtn};

        // 改变样式
        styleChange.bestStyle(labels,buttons,null,null,null,150,40);

        // 在面板中加入组件
        panel.add(label);
        panel.add(autoBtn);
        panel.add(selBtn);

        // 把面板加到窗口中
        myFrame.add(panel);

        // 点击自动
        autoBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("自动");
                // 弹出对话框，输入时间间隔参数
                showDialog(myFrame, myFrame);

                // 输入值为空就不进行操作
                if (content != null) {
                    // 检测输入参数合法性
                    String timeMessage = checkInput.checkSleepTime(content);
                    if ("legal".equals(timeMessage)) {
                        // 进入选择算法界面窗口
                        new AlgorithmChoice(graph, (long) (Double.parseDouble(content) * 1000));

                        myFrame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(myFrame,timeMessage,"提示",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        // 点击手动
        selBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("手动演示");

                // 进入选择算法界面窗口
                new AlgorithmChoice(graph,-1);

                myFrame.dispose();
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
     */
    private void showDialog(MyFrame owner, Component parentComponent) {
        // 创建一个模态对话框
        final JDialog dialog = new JDialog(owner, "输入参数", true);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // 设置对话框的宽高
        dialog.setSize(450, 160);
        // 设置对话框大小不可改变
        dialog.setResizable(false);
        // 设置对话框相对显示的位置
        dialog.setLocationRelativeTo(parentComponent);

        JLabel timeLabel = new JLabel("请输入演示时间间隔：(范围[0,3],单位秒)");
        JTextField timeText = new JTextField();
        JButton okBtn = new JButton("确认");

        // 各个组件数组
        JLabel[] labels = {timeLabel};
        JTextField[] texts = {timeText};
        JButton[] buttons = {okBtn};

        // 改变样式
        styleChange.bestStyle(labels,buttons,texts,null,null,80,30);

        // 添加组件到面板
        panel.add(timeLabel);
        panel.add(timeText);
        panel.add(okBtn);

        // 监听确认按键事件
        okBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                content = timeText.getText();

                dialog.dispose();
            }
        });

        // 设置对话框的内容面板
        dialog.add(panel);
        // 显示对话框
        dialog.setVisible(true);
    }
}
