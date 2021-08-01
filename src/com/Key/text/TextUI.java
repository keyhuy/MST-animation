package com.Key.text;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

/**
 * @author Key
 * @date 2021/06/16/15:24
 **/
public class TextUI extends JFrame {

    public TextUI() {
        super();
        initialize();
    }

     //初始化窗体
     private void initialize(){

         this.setSize(800, 600);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setContentPane(new Plot());
         this.setTitle("绘图小Demo");
         this.setLocationRelativeTo(null);
         this.setVisible(true);
     }

    public static void main(String[] args) {
        new TextUI();
    }

     class Plot extends JPanel {

        @Override
        public void paint(Graphics gp){

            super.paint(gp);
            Graphics2D gp2D = (Graphics2D) gp;

            gp2D.setColor(Color.BLACK);
            gp2D.setFont(gp2D.getFont().deriveFont(15f));

            // 画顶点
            gp2D.drawString("a",315,65);
            gp2D.drawOval(300, 40, 40, 40);

            gp2D.drawString("b",415,65);
            gp2D.drawOval(400, 40, 40, 40);

            gp2D.drawString("c",235,180);
            gp2D.drawOval(220,160,40,40);

            gp2D.drawString("d",495,180);
            gp2D.drawOval(480,160,40,40);

            gp2D.drawString("e",315,305);
            gp2D.drawOval(300,280,40,40);

            gp2D.drawString("f",415,305);
            gp2D.drawOval(400,280,40,40);

            // 画边
            // 1(a,b)
            gp2D.drawLine(340,60,400,60);
            gp2D.drawString("10",350,60);

            // 2(c,d)
            gp2D.drawLine(260,180,480,180);
            gp2D.drawString("10",280,180);

            // 3(e,f)
            gp2D.drawLine(340,300,400,300);
            gp2D.drawString("10",350,310);

            // 4(a,c)
            gp2D.drawLine(305,75,255,165);
            gp2D.drawString("10",280,100);

            // 5(b,d)
            gp2D.drawLine(435,75,485,165);
            gp2D.drawString("10",450,100);

            // 6(d,f)
            gp2D.drawLine(485,195,435,285);
            gp2D.drawString("10",470,225);

            // 7(c,e)
            gp2D.drawLine(255,195,305,285);
            gp2D.drawString("10",255,225);

            // 8(a,e)
            gp2D.drawLine(320,80,320,280);
            gp2D.drawString("10",305,120);

            // 9(a,f)
            gp2D.drawLine(320,80,420,280);
            gp2D.drawString("10",340,120);

            // 10(a,d)
            gp2D.drawLine(320,80,480,180);
            gp2D.drawString("10",450,160);

            // 11(b,c)
            gp2D.drawLine(420,80,260,180);
            gp2D.drawString("10",280,160);

            // 12(b,e)
            gp2D.drawLine(420,80,320,280);
            gp2D.drawString("10",400,120);

            // 13(b,f)
            gp2D.drawLine(420,80,420,280);
            gp2D.drawString("10",420,260);

            // 14(d,e)
            gp2D.drawLine(480,180,320,280);
            gp2D.drawString("10",445,210);

            // 15(c,f)
            gp2D.drawLine(260,180,420,280);
            gp2D.drawString("10",380,275);



            // 画权值
//            gp2D.drawString("10",350,60);
//            gp2D.drawString("10",280,180);
//            gp2D.drawString("10",350,310);
//            gp2D.drawString("10",280,100);
//            gp2D.drawString("10",450,100);
//            gp2D.drawString("10",470,225);
//            gp2D.drawString("10",255,225);
//            gp2D.drawString("10",305,120);
//            gp2D.drawString("10",420,260);
//            gp2D.drawString("10",380,275);
//            gp2D.drawString("10",400,120);
//            gp2D.drawString("10",450,160);
//            gp2D.drawString("10",445,210);
//            gp2D.drawString("10",340,120);
//            gp2D.drawString("10",280,160);

        }
    }

}
