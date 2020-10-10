package com.caisw.algorithm.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 一排房子涂色，计算最小花费方案
 * https://leetcode-cn.com/problems/paint-house-iii/
 */
public class Solution1473 {

    /**
     * 最终每个房子都必须被涂色
     *
     * @param houses 房子颜色，有可能已经染好色了
     * @param cost   0房子序号，1颜色对应的花费
     * @param m      总共m个房子
     * @param n      总共n种颜色
     * @param target 目标街区数量，连续相同颜色的房子组成一个街区
     */
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        //先评估一下现在的着色可以分为几个街区，再选择最小的花费代价进行着色
        List<Area> areaList = new ArrayList<>();
        int color;
        Area area = null;
        for (int i = 0; i < houses.length; i++) {
            color = houses[i];
            if (area == null || area.getColor() != color) {
                area = new Area(color);
                areaList.add(area);
            }
            area.addHouse(i);
        }
        //查找融合成target个街区的最小代价


        return 0;
    }

    public static class Area {
        private int color;
        private List<Integer> houses = new ArrayList<>();

        public Area(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }

        public void addHouse(int houseIndex) {
            houses.add(houseIndex);
        }

        public List<Integer> getHouses() {
            return houses;
        }
    }


    public static void main(String[] args) {

    }
}
