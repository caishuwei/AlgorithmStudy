package com.caisw.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

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

        int pLength = points.length;

        Map<String, Integer> pointMap = new HashMap<>(pLength);
        for (int i = 0; i < pLength; i++) {
            pointMap.put(points[i][0] + "," + points[i][1], i);
        }

        double result = 0;
        Parallelogram temp;
        double tempArea;
        for (int p1 = 0; p1 < pLength; p1++) {
            for (int p2 = 0; p2 < pLength; p2++) {
                for (int p3 = 0; p3 < pLength; p3++) {
                    if (p1 != p2 && p1 != p3 && p2 != p3) {
                        temp = new Parallelogram(
                                points[p1][0],
                                points[p1][1],
                                points[p2][0],
                                points[p2][1],
                                points[p3][0],
                                points[p3][1]
                        );
                        if (temp.isRect()
                                && pointMap.containsKey(temp.getP4x() + "," + temp.getP4y())) {
                            tempArea = temp.calcArea();
                            if (result != 0 && result <= tempArea) {
                                continue;
                            }
                            result = tempArea;
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * 根据三个同的点生成的平行四边形
     */
    public static class Parallelogram {
        final int p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y;

        //side
        final int maxSideSquare, rtSide1Square, rtSide2Square;
        final boolean isRect;

        public Parallelogram(int p1x, int p1y, int p2x, int p2y, int p3x, int p3y) {
            this.p1x = p1x;
            this.p1y = p1y;
            this.p2x = p2x;
            this.p2y = p2y;
            this.p3x = p3x;
            this.p3y = p3y;

            int side12Square = (p1x - p2x) * (p1x - p2x) + (p1y - p2y) * (p1y - p2y);
            int side13Square = (p1x - p3x) * (p1x - p3x) + (p1y - p3y) * (p1y - p3y);
            int side23Square = (p3x - p2x) * (p3x - p2x) + (p3y - p2y) * (p3y - p2y);

            if (side12Square > side23Square && side12Square > side13Square) {
                //p3是未知点的对角
                maxSideSquare = side12Square;
                rtSide1Square = side23Square;
                rtSide2Square = side13Square;
                p4x = p1x + (p2x - p3x);
                p4y = p1y + (p2y - p3y);
            } else if (side13Square > side12Square && side13Square > side23Square) {
                //p2是未知点对角
                maxSideSquare = side13Square;
                rtSide1Square = side12Square;
                rtSide2Square = side23Square;
                p4x = p1x + (p3x - p2x);
                p4y = p1y + (p3y - p2y);
            } else {
                //p1是未知点对角
                maxSideSquare = side23Square;
                rtSide1Square = side12Square;
                rtSide2Square = side13Square;
                p4x = p2x + (p3x - p1x);
                p4y = p2y + (p3y - p1y);
            }
            isRect = ((rtSide1Square + rtSide2Square) == maxSideSquare);
        }

        public boolean isRect() {
            return isRect;
        }

        public int getP4x() {
            return p4x;
        }

        public int getP4y() {
            return p4y;
        }

        public double calcArea() {
            return Math.sqrt(rtSide1Square) * Math.sqrt(rtSide2Square);
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution963().minAreaFreeRect(new int[][]{{1, 2}, {2, 1}, {1, 0}, {0, 1}}));
    }

}
