package com.Key.Algorithm;

/**
 * 创建一个边类（邻接顶点）
 *  - 属性
 *   - 顶点1：start
 *   - 顶点2：end
 *   - 权值：weight
 *
 * @author Key
 * @date 2021/06/18/20:21
 **/
public class EdgeData {

    public String start;
    public String end;
    public int weight;

    public EdgeData(String start, String end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "<" + start + ", " + end + ">=" + weight + "]";
    }
}
