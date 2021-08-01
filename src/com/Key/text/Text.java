package com.Key.text;

import com.Key.Algorithm.Algorithm;
import com.Key.Algorithm.Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试
 *
 * @author Key
 * @date 2021/06/15/21:35
 **/
public class Text {
    public static void main(String[] args) {
        // 表示权值无穷大
        int m = Integer.MAX_VALUE;
        // 边
        int[][] edges = {
                {0, 3, 1, m, 4},
                {3, 0, 2, m, m},
                {1, 2, 0, 5, 6},
                {m, m, 5, 0, m},
                {4, m, 6, m, 0},
        };

        List<String> vex=new ArrayList<>();
        vex.add("A");
        vex.add("B");
        vex.add("C");
        vex.add("D");
        vex.add("E");
        Graph graph = new Graph(vex, edges);
    }
}
