package com.Key.Check;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * 参数检查工具类
 *  - 检测输入的参数是否合法
 *
 * @author Key
 * @date 2021/06/19/10:32
 **/
public class ParamCheck {

    /**
     * 统计深度优先算法访问顶点数
     */
    public int count;

    /**
     * 检测输入顶点数和边数的合法性
     * @param content 传入的参数
     * @return 返回检测结果信息
     */
    public String checkVexAndEdgeNum(String[] content) {
        String tips = "legal";

        // 异常处理
        try {

            for (String s : content) {
                Integer.parseInt(s);
            }

            if (content.length == 2) {
                // 检测顶点数和边数合法性（保证是连通图）->顶点数最多6，边数范围为[n-1,n(n-1)/2]，n为顶点数
                if (Integer.parseInt(content[0]) <= 0 || Integer.parseInt(content[0]) > 6) {

                    tips = "顶点数最多为6！";
                }else {
                    int vNum = Integer.parseInt(content[0]);
                    if (Integer.parseInt(content[1]) < vNum - 1 || Integer.parseInt(content[1]) > (vNum * (vNum - 1)) / 2) {
                        tips = "输入边数不合法！，边数范围为" + "[" + (vNum-1) + "," + (vNum * (vNum - 1)) / 2 + "]";
                    }
                }
            }

            if (content.length == 1) {
                if (Integer.parseInt(content[0]) <= 0 || Integer.parseInt(content[0]) > 6) {
                    tips = "顶点数最多为6！";
                }
            }

        } catch(Exception e) {
            tips = "输入参数不合法！";
        }

        return tips;
    }

    /**
     * 检测输入顶点值的合法性
     * @param content 输入的内容
     * @return 返回检测结果信息
     */
    public String checkVex(JTextField[] content) {
        String tips = "legal";

        // 异常处理
        try {
            // 先检测是否为空字符
            for (JTextField s : content) {
                s.getText().charAt(0);
            }

            // 再检测是否是单字母，用StringBuilder把出入的字母串起来，检查总字符数
            StringBuilder str = new StringBuilder();
            for (JTextField jTextField : content) {
                str.append(jTextField.getText());
            }

            if (str.length() != content.length) {

                tips = "请输入单个字母！";
            }else { // 最后再检测是否是a-z或A-Z之间的字母
                boolean is;
                for (JTextField jt : content) {
                    is = ((jt.getText().charAt(0) >= 'a' && jt.getText().charAt(0) <= 'z') ||
                            (jt.getText().charAt(0) >= 'A' && jt.getText().charAt(0) <= 'Z'));
                    if (!is) {
                        tips = "请输入a-z或A-Z！";
                        break;
                    }
                }

                // 最最后再检测是否有重复的字母
                if ("legal".equals(tips)) {
                    for (int i = 0, contentLength = content.length; i < contentLength; i++) {
                        for (int j = i + 1, length = content.length; j < length; j++) {
                            if (content[i].getText().equals(content[j].getText())) {
                                tips = "输入顶点值不可重复！";
                                break;
                            }
                        }
                    }
                }
            }
        } catch(Exception e) {
            tips = "输入顶点值不合法！";
        }

        return tips;
    }

    /**
     * 检测输入矩阵权值的合法性
     * @param content 输入的内容
     * @param vexNum 顶点数，用于判断边数合法性
     * @return 返回检测结果信息
     */
    public String checkEdge(JTextField[] content, int vexNum) {
        String tips = "legal";

        try {

            // 先检测是否都是数字（除了字母m）
            int size = (int) Math.sqrt(content.length);
            for (int i = 0;i < size;i++) {
                for (int j = i + 1;j < size;j++) {
                    if (!"m".equals(content[size * i + j].getText())) {
                        Integer.parseInt(content[size * i + j].getText());
                    }
                }
            }

            // 检测权值的范围（1-10）
            int sum = 0;
            for (int i = 0;i < size;i++) {
                for (int j = i + 1;j < size;j++) {
                    if (!"m".equals(content[size * i + j].getText())) {
                        // 记录边数
                        sum++;
                        int w = Integer.parseInt(content[size * i + j].getText());
                        if (w <= 0 || w > 10) {
                            tips = "边权值范围为1-10！";
                            break;
                        }
                    }
                }
            }
            // 检测边数是否合法（大于vexNum-1，小于vexNum(vexNum-1)/2）
            if (sum < vexNum - 1 || sum > (vexNum * (vexNum - 1)) / 2) {
                tips = "输入边数不合法！，边数范围为" + "[" + (vexNum-1) + "," + (vexNum * (vexNum - 1)) / 2 + "]";
            }

        } catch(Exception e) {
            tips = "输入边权值不合法！";
        }

        return tips;
    }

    /**
     * 检测输入的边信息合法性
     * @param adj 边信息内容
     * @param vex 顶点信息集合
     * @return 返回检测结果信息
     */
    public String checkAdj(JTextField[] adj, List<String> vex) {
        String tips = "legal";

        // 异常处理
        try {
            // 先检测权值是否合法
            for (int i = 0;i < adj.length;i += 3) {
                Integer.parseInt(adj[i + 2].getText());
            }

            // 再检测权值的范围以及邻接顶点的合法性
            for (int i = 0;i < adj.length;i += 3) {
                int flag = 0;

                // 邻接顶点不可重复
                if (adj[i].getText().equals(adj[i + 1].getText())) {
                    tips = "邻接顶点不可重复！";
                    break;
                }

                // 顶点值要存在
                for (String s : vex) {
                    if (adj[i].getText().equals(s)) {
                        flag++;
                    }
                    if (adj[i + 1].getText().equals(s)) {
                        flag++;
                    }
                }
                if (flag != 2) {
                    tips = "有不存在的顶点值！";
                    break;
                }

                // 权值范围1-10
                int w = Integer.parseInt(adj[i + 2].getText());
                if (w <= 0 || w > 10) {
                    tips = "权值范围为1-10！";
                    break;
                }
            }

            // 最后检测是否存在重复边
            if ("legal".equals(tips)) {
                int flag = 0;
                for (int i = 0;i < adj.length;i += 3) {
                    for (int j = i;j < adj.length - 3;j += 3) {
                        boolean is = (adj[i].getText().equals(adj[j + 3].getText()) && adj[i + 1].getText().equals(adj[j + 4].getText())) ||
                                (adj[i].getText().equals(adj[j + 4].getText()) && adj[i + 1].getText().equals(adj[j + 3].getText()));
                        if (is) {
                            tips = "存在重复边！";
                            flag = 1;
                            break;
                        }
                    }

                    if (flag == 1) {
                        break;
                    }
                }
            }

        } catch(Exception e) {
            tips = "输入边权值不合法！";
        }

        return tips;
    }

    /**
     * 检测执行Prim算法是输入的起始顶点是否合法
     * @param content 输入内容
     * @param vexNum 顶点数
     * @return 返回检测结果信息
     */
    public String checkFirstVex(String content, int vexNum) {
        String tips = "legal";

        // 异常处理
        try {
            // 先检测是否是整数
            int firstVex = Integer.parseInt(content);

            // 检测下标范围
            if (firstVex < 0 || firstVex >= vexNum) {
                tips = "顶点下标范围为：" + "[" + "0," + (vexNum-1) + "]";
            }
        } catch(Exception e) {
            tips = "输入参数不合法！";
        }

        return tips;
    }

    /**
     * 检测输入休眠时间合法性
     * @param content 输入内容
     * @return 返回检测结果
     */
    public String checkSleepTime(String content) {
        String tips = "legal";

        // 异常处理
        try {
            // 先检测是否是整数
            double time = Double.parseDouble(content);

            // 时间范围为[0,3]
            if (time <= 0 || time > 3) {
                tips = "时间范围是[0,3]！";
            }

        } catch(Exception e) {
            tips = "输入参数不合法！";
        }

        return tips;
    }

    /**
     * 检测图是否是连通图
     * @param edges 矩阵数组
     * @param vex 顶点集合
     * @return 返回检测结果
     */
    public String checkConnectivity(int[][] edges,List<String> vex) {
        String tips = "legal";

        try {
            // 用于标记顶点是否已被访问，1-已被访问，0-未访问
            int[] visited = new int[vex.size()];
            count = 0;

            // 初始化，开始全部顶点未被访问
            Arrays.fill(visited,0);

            // 调用深度优先函数，遍历整个图，找出连通分量，如果遍历顶点数不等于顶点数，则不是连通图
            DFS(visited,0,vex.size(),edges);

            if (count != vex.size()) {
                tips = "该图不是连通图！";
            }

        } catch(Exception e) {
            tips = "图结构不合法！";
        }

        return tips;
    }

    /**
     * 深度优先算法函数，递归调用
     * @param visited 标记访问数组
     * @param v 每次访问的顶点下标
     * @param vexNum 顶点数
     * @param edges 邻接矩阵数组
     */
    public void DFS(int[] visited, int v, int vexNum, int[][] edges) {
        visited[v] = 1;
        count++;

        for (int i = 0;i < vexNum;i++) {
            if (edges[v][i] != 0 && edges[v][i] != Integer.MAX_VALUE && visited[i] == 0) {
                // 递归调用
                DFS(visited,i,vexNum,edges);
            }
        }
    }
}
