package com.Key.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * 改变样式
 *
 * @author Key
 * @date 2021/06/15/20:32
 **/
public class StyleChange {
    /**
     * 改变样式方法
     *
     * @param labels 标签数组
     * @param buttons 按键数组
     * @param texts 文本框数组
     * @param menuItems 菜单栏按键数组
     * @param menu 菜单
     * @param width dimension长度
     * @param height dimension宽度
     */
    public void bestStyle(JLabel[] labels, JButton[] buttons, JTextField[] texts,
                          JMenuItem[] menuItems, JMenu menu,
                          int width, int height) {
        // 用于改变标签字体样式，楷体、加粗、字体大小20
        Font font = new Font("楷体",Font.BOLD,20);
        // 用于改变按键或文本框大小
        Dimension dimension = new Dimension(width,height);

        if (labels != null) {
            for (JLabel jl : labels) {
                jl.setFont(font);
            }
        }
        if (buttons != null) {
            for (JButton jb : buttons) {
                // 按键文本样式
                jb.setFont(font);
                // 按键大小
                jb.setPreferredSize(dimension);
                // 按键边框不可见
                jb.setBorderPainted(false);
                // 按键凸起
                jb.setBorder(BorderFactory.createRaisedBevelBorder());
            }
        }
        if (texts != null) {
            // 文本框大小
            for (JTextField jt : texts) {
                jt.setPreferredSize(dimension);
            }
        }

        if (menuItems != null) {
            for (JMenuItem jm : menuItems) {
                jm.setFont(font);
                jm.setPreferredSize(dimension);
                jm.setBorderPainted(false);
                jm.setBorder(BorderFactory.createRaisedBevelBorder());
            }
        }

        if (menu != null) {
            menu.setFont(font);
            menu.setPreferredSize(dimension);
        }
    }
}
