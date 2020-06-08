package com.caisw.algorithm.leetcode;

/**
 * 新21点
 * https://leetcode-cn.com/problems/new-21-game/
 */
public class Solution {

    /**
     * 每次从[1,W]中抽取一个数，累计不少于K时，不超过N的概率
     */
    public double new21Game(int N, int K, int W) {
        //动态规划
        //求解每一步抽到每一种卡，若是最终获胜，将概率累加起来
        //如果抽到1 6，跟直接抽到7，之后的概率计算完全相同，所以由后往前算，可以节省很多计算

        //若 N=21，K=17，W=10
        //f(16) = 1/10*(f(16+1)+f(16+2)+...+f(16+W))    计算抽到的每一种牌的概率*胜率加起来，就是在16积分时抽牌的胜率
        //      = 1/10*(1,1,1,1,1,0,0,0,0,0)
        //      = 0.5
        //f(x) = 1/W*(f(x+1)+...f(x+w))
        //f(0)即是我们想要的结果

        //存储当前每种积分的胜率，
        //第一位存储f(0),
        //接下来是f(1)...f(K-1)
        //最后是f(K)...f(K-1+M),这个是已知的
        double[] f = new double[1 + (K - 1) + W];
        double temp;
        for (int i = f.length - 1; i >= 0; i--) {
            if (i >= K) {//>=K的时候，结果不是1就是0
                f[i] = i <= N ? 1 : 0;
            } else {
                temp = 0;
                for (int j = 1; j <= W; j++) {//抽下一张牌的胜率
                    temp += f[i + j];
                }
                f[i] = temp / W;
            }
        }
        return f[0];
    }

    public static void main(String[] args) {
        System.out.println(new Solution().new21Game(21, 17, 10));
        System.out.println(new Solution().new21Game(3655, 2958, 713));
    }

}