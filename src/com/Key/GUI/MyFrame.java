package com.Key.GUI;

import javax.swing.*;

/**
 * 我的窗口
 *  - 集成JFrame类
 *
 * @author Key
 * @date 2021/06/15/20:30
 **/
public class MyFrame extends JFrame {
    /**
     * 重写构造方法
     *
     * @param title 窗口标题
     */
    public MyFrame(String title) {
        this.setTitle(title);
        // 窗口不可调大小
        this.setResizable(false);

        // 设置图标
        this.setIconImage(new ImageIcon("dun.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

