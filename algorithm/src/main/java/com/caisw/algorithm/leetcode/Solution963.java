package com.caisw.algorithm.leetcode;

/**
 * 从不重复的点集中寻找最小的正方形
 * https://leetcode-cn.com/problems/minimum-area-rectangle-ii/comments/
 */
public class Solution963 {

    public double minAreaFreeRect(int[][] points) {
        //从数组取三个元素组成集合，如何避免取到相同的三个数，排列是组合的6倍
        //1、三角形Map存储的key由三个点的坐标构成字符串，按照x从小到大，若x一样，则按照y从小到大
        //但这样会生成很多三角形存储起来用于判断是否是重复的三个点，很浪费内存。
        //2、还是直接遍历把，生成矩形后，先判断一下面积是否小于当前找到的最小面积矩形，若不是，则跳过
        //如何快速判断第四点是否存在，三个点构成的平行四边形第四点很容易算，但如何查找

        //怎么判断三个点是否能组成矩形
        //利用直角三角形，直角边的平方和等于斜边的平方，可以判断，不用向量（计算消耗）
        //边长平方可以直接用两点坐标求得，不用开方（计算消耗）
        //三角形大边对大角，边平方和最大的是斜边，其他两个可能是直角边。

        //面积大小比较
        //1、判断是直角三角形后，计算两直角边长度相乘，计算消耗量大啊
        //2、S=axb,ab皆>=1,若a1xb1>a2xb2,则(a1xa1)x(b1xb1)>(a2xa2)x(b2xb2)
        //可以用平方和代替面积进行初略比较，求得最终的矩形再去精确计算

        int l1 = points.length;
        int l2 = points[0].length;

        return 0.0;
    }


}
