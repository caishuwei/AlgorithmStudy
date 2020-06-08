package com.caisw.algorithm.leetcode;

/**
 * 新21点
 * https://leetcode-cn.com/problems/new-21-game/
 */
public class Solution837 {

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

        //空间
        // O(K+W)
        //时间
        // O(W+KW)
        return f[0];
    }

    public double new21Game2(int N, int K, int W) {
        //算法时间超时了，需要优化
        //f(x) = 1/W*(f(x+1)+...f(x+w))
        //f(x+1) = 1/W*(f(x+2)+...f(x+1+w))
        //相邻两项的差为
        //f(x) - f(x+1) = 1/W*(f(x+1)+...f(x+w))-1/W*(f(x+2)+...f(x+1+w))
        //f(x) - f(x+1) = 1/W*(f(x+1)-f(x+1+w))
        //f(x) - f(x+1) = 1/W*(f(x+1)-f(x+1+w))
        //f(x) = f(x+1)+1/W*(f(x+1)-f(x+1+w))
        //     = (f(x+1)*(w+1)-f(x+1+w))/w
        //转化成这样的好处是不用遍历累加后W项的概率
        //注意定义域，f(x)的累加计算只能在[0,K),[K,K-1+W]是通过判断与N的关系来决定的
        //由于等式利用了到f(x+1),所以上面的等式定义域为[0,K-1)
        //f(K-1)需要通过(f(K)+...+f(K-1+W))/W来计算
        double[] f = new double[1 + (K - 1) + W];
        double temp;
        for (int i = f.length - 1; i >= 0; i--) {
            if (i >= K) {//>=K的时候，结果不是1就是0
                f[i] = i <= N ? 1 : 0;
            } else if (i == K - 1) {
                temp = 0;
                for (int j = 1; j <= W; j++) {//抽下一张牌的胜率
                    temp += f[i + j];
                }
                f[i] = temp / W;
            } else {
                f[i] = (f[i + 1] * (W + 1) - f[i + 1 + W]) / W;
            }
        }
        //空间
        // O(K+W)
        //时间
        // O(W+W+K-1)
        return f[0];
    }

    public static void main(String[] args) {
        System.out.println(new Solution837().new21Game(21, 17, 10));
        System.out.println(new Solution837().new21Game2(21, 17, 10));
        System.out.println(new Solution837().new21Game(3655, 2958, 713));
        System.out.println(new Solution837().new21Game2(3655, 2958, 713));
    }

}