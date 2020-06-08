package com.caisw.algorithm.leetcode;

/**
 * ��21��
 * https://leetcode-cn.com/problems/new-21-game/
 */
public class Solution {

    /**
     * ÿ�δ�[1,W]�г�ȡһ�������ۼƲ�����Kʱ��������N�ĸ���
     */
    public double new21Game(int N, int K, int W) {
        //��̬�滮
        //���ÿһ���鵽ÿһ�ֿ����������ջ�ʤ���������ۼ�����
        //����鵽1 6����ֱ�ӳ鵽7��֮��ĸ��ʼ�����ȫ��ͬ�������ɺ���ǰ�㣬���Խ�ʡ�ܶ����

        //�� N=21��K=17��W=10
        //f(16) = 1/10*(f(16+1)+f(16+2)+...+f(16+W))    ����鵽��ÿһ���Ƶĸ���*ʤ�ʼ�������������16����ʱ���Ƶ�ʤ��
        //      = 1/10*(1,1,1,1,1,0,0,0,0,0)
        //      = 0.5
        //f(x) = 1/W*(f(x+1)+...f(x+w))
        //f(0)����������Ҫ�Ľ��

        //�洢��ǰÿ�ֻ��ֵ�ʤ�ʣ�
        //��һλ�洢f(0),
        //��������f(1)...f(K-1)
        //�����f(K)...f(K-1+M),�������֪��
        double[] f = new double[1 + (K - 1) + W];
        double temp;
        for (int i = f.length - 1; i >= 0; i--) {
            if (i >= K) {//>=K��ʱ�򣬽������1����0
                f[i] = i <= N ? 1 : 0;
            } else {
                temp = 0;
                for (int j = 1; j <= W; j++) {//����һ���Ƶ�ʤ��
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