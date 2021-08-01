package com.Key.GUI.AnimUI;

import java.util.*;

/**
 * 参数对应Map
 *  - 顶点Map->vMap
 *  - 边Map->eMap
 *
 * @author Key
 * @date 2021/06/17/17:13
 **/
public class GraphicalParam {

    public Map<Integer,int[]> vMap;
    public Map<String,int[]> eMap;

    /**
     * 构造方法
     *  - 初始化参数顶点Map和边Map
     */
    public GraphicalParam() {

        int[] v0,v1,v2,v3,v4,v5;
        int[] e0,e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12,e13,e14;

        // 存放顶点位置参数
        v0 = new int[]{210,65,200,40};
        v1 = new int[]{310,65,300,40};
        v2 = new int[]{130,185,120,160};
        v3 = new int[]{390,185,380,160};
        v4 = new int[]{210,305,200,280};
        v5 = new int[]{310,305,300,280};

        vMap = new HashMap<>();
        vMap.put(0,v0);
        vMap.put(1,v1);
        vMap.put(2,v2);
        vMap.put(3,v3);
        vMap.put(4,v4);
        vMap.put(5,v5);

        // 存放边位置参数
        e0 = new int[]{240,60,300,60,250,60};
        e1 = new int[]{160,180,380,180,180,180};
        e2 = new int[]{240,300,300,300,250,310};
        e3 = new int[]{205,75,155,165,180,100};
        e4 = new int[]{335,75,385,165,350,100};
        e5 = new int[]{385,195,335,285,370,225};
        e6 = new int[]{155,195,205,285,155,225};
        e7 = new int[]{220,80,220,280,205,120};
        e8 = new int[]{220,80,320,280,240,120};
        e9 = new int[]{220,80,380,180,350,160};
        e10 = new int[]{320,80,160,180,180,160};
        e11 = new int[]{320,80,220,280,300,120};
        e12 = new int[]{320,80,320,280,320,260};
        e13 = new int[]{380,180,220,280,345,210};
        e14 = new int[]{160,180,320,280,280,275};

        eMap = new HashMap<>();
        eMap.put("(0,1)",e0);
        eMap.put("(2,3)",e1);
        eMap.put("(4,5)",e2);
        eMap.put("(0,2)",e3);
        eMap.put("(1,3)",e4);
        eMap.put("(3,5)",e5);
        eMap.put("(2,4)",e6);
        eMap.put("(0,4)",e7);
        eMap.put("(0,5)",e8);
        eMap.put("(0,3)",e9);
        eMap.put("(1,2)",e10);
        eMap.put("(1,4)",e11);
        eMap.put("(1,5)",e12);
        eMap.put("(3,4)",e13);
        eMap.put("(2,5)",e14);
    }
}
