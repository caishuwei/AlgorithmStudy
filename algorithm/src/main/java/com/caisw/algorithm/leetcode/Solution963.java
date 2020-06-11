package com.caisw.algorithm.leetcode;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 从不重复的点集中寻找最小的正方形
 * https://leetcode-cn.com/problems/minimum-area-rectangle-ii/comments/
 */
public class Solution963 {

    public double minAreaFreeRect(int[][] points) {
        //同样是找出三个点，判断直角三角形是利用两直角边所在斜率相乘是否=-1来判断的，为了避免斜率不存在的问题
        //将(x1-x2)/(y1-y2)*(x3-x2)/(y3-y2)=-1转化为(x1-x2)*(x3-x2)-(y1-y2)*(y3-y2)=0进行判断
        if (points == null || points.length < 4 || points[0].length != 2)
            return 0;
        int n = points.length;
        double res = Double.MAX_VALUE;
        for (int i = 0; i < n - 3; i++) {
            for (int j = i + 1; j < n - 2; j++) {
                for (int k = j + 1; k < n - 1; k++) {
                    if (isRightAngle(points[j], points[i], points[k])) {
                        res = Math.min(res, minAreaFreeRect(points, j, i, k, k + 1));
                        continue;
                    }
                    if (isRightAngle(points[i], points[j], points[k])) {
                        res = Math.min(res, minAreaFreeRect(points, i, j, k, k + 1));
                        continue;
                    }
                    if (isRightAngle(points[i], points[k], points[j])) {
                        res = Math.min(res, minAreaFreeRect(points, i, k, j, k + 1));
                        continue;
                    }
                }
            }
        }
        return res == Double.MAX_VALUE ? 0 : res;
    }

    private double minAreaFreeRect(int[][] points, int a, int b, int c, int start) {
        int n = points.length;
        double res = Double.MAX_VALUE;
        int x = points[a][0] + points[c][0] - points[b][0];
        int y = points[a][1] + points[c][1] - points[b][1];
        //遍历后续的点，看看是否存在第四点
        for (int i = start; i < n; i++) {
            if (points[i][0] == x && points[i][1] == y) {
                double ab = Math.sqrt((points[a][0] - points[b][0]) * (points[a][0] - points[b][0])
                        + (points[a][1] - points[b][1]) * (points[a][1] - points[b][1]));
                double bc = Math.sqrt((points[c][0] - points[b][0]) * (points[c][0] - points[b][0])
                        + (points[c][1] - points[b][1]) * (points[c][1] - points[b][1]));
                return ab * bc;
            }
        }
        return res;
    }

    private boolean isRightAngle(int[] a, int[] b, int[] c) {
        return (a[0] - b[0]) * (b[0] - c[0]) + (a[1] - b[1]) * (b[1] - c[1]) == 0;
    }

    //----------------------------------------------------------------------------------------------
    public double minAreaFreeRect5(int[][] points) {
        //计算两点形矩形的一边的垂直平分线，通过寻找相同垂直平分线的边作为对边组成矩形
        Arrays.sort(points, (o1, o2) -> {
            int a = o1[0] - o2[0];
            return a == 0 ? o1[1] - o2[1] : a;
        });
        Map<List<Integer>, int[][]> map = new HashMap<>();
        Double res = Double.MAX_VALUE;
        //我们需要的是组合，不是排序，不需要重复的取相同的两个点
        //他这个算法第二层遍历时从i+1开始，就不会初选重复两个点的情况，减少遍历次数
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                int[] a = points[i];
                int[] b = points[j];
                List<Integer> midLine = getMidLine(a[0], a[1], b[0], b[1]);
                //以垂直平分线做key
                if (map.containsKey(midLine)) {
                    //取得存储的上个相同垂直平分线的两个点
                    int[][] ints = map.get(midLine);
                    //直接计算面积后更新，这里就是他一开始要做排序的原因了吧，做了排序之后，相同的垂直平分线的两个点，会越来越远离原点
                    //中垂线一样，两个点的距离不一定都一样啊，为啥可以直接这样计算面积，不懂。应该需要把两点长度也存储到key中
                    res = Math.min(res, getArea(ints[1][0], ints[1][1], ints[0][0], ints[0][1], a[0], a[1]));
                }
                map.put(midLine, new int[][]{a, b});
            }
        }
        return res == Double.MAX_VALUE ? 0 : res;
    }

    double getArea(int x1, int y1, int bx, int by, int x2, int y2) {
        double h = Math.sqrt(Math.pow((x1 - bx), 2) + Math.pow((y1 - by), 2));
        double w = Math.sqrt(Math.pow((x2 - bx), 2) + Math.pow((y2 - by), 2));
        return h * w;
    }

    /**
     * 获取两个点的垂直平分线
     */
    List<Integer> getMidLine(int x1, int y1, int x2, int y2) {
        //直线一般式 Ax+By+C=0，一般式可以表示所有直线，包括与y轴平行的直线
        //两个点的垂直平分线表示方式如下
        List<Integer> res = new ArrayList<>();
        res.add(2 * (x2 - x1));//A
        res.add(2 * (y2 - y1));//B
        res.add(2 * (x1 * x1 - x2 * x2 + y1 * y1 - y2 * y2));//C
        return res;
    }

    //----------------------------------------------------------------------------------------------
    public double minAreaFreeRect4(int[][] points) {
        //这也是三重for循环的代码，不过他存储集合是通过x*40000+y,这样不用生成字符串好像是快
        Set<Integer> set = new HashSet<>();
        double ans = Double.MAX_VALUE;
        final int MAX = 40000;
        for (int[] point : points) {
            set.add(point[0] * MAX + point[1]);
        }
        //取两点计算中点
        for (int i = 0; i < points.length - 1; i++) {
            int x1 = points[i][0], y1 = points[i][1];
            for (int j = i + 1; j < points.length; j++) {
                int x2 = points[j][0], y2 = points[j][1];
                int xsum = x1 + x2, ysum = y1 + y2;
                //再取一点作为第三点
                for (int k = 0; k < points.length; k++) {
                    int x3 = points[k][0], y3 = points[k][1];
                    //根据中点计算第四点
                    int x4 = xsum - x3, y4 = ysum - y3;
                    if (k != i && k != j) {
                        //判断是否存在这一点
                        if (set.contains(x4 * MAX + y4)) {
                            //判断对角线是否等长
                            if (Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2) == Math.pow(x3 - x4, 2) + Math.pow(y3 - y4, 2)) {
                                //更新最小面积
                                double area = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2)) *
                                        Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));
                                ans = Math.min(ans, area);
                            }
                        }
                    }
                }
            }
        }
        return ans == Double.MAX_VALUE ? 0 : ans;
    }

    //----------------------------------------------------------------------------------------------
    public double minAreaFreeRect3(int[][] points) {
        //遍历每次取三个点太慢了
        //评论区有个利用矩形对角线相等，且中点在同一个点上
        //性能好像只提升了一丢丢
        HashSet<Point> pointSet = new HashSet<>(points.length);
        for (int[] point : points) {
            pointSet.add(new Point(point[0], point[1]));
        }
        Line tempLine;
        HashSet<Line> tempLineSet;
        HashSet<Line> lineSet = new HashSet<>();
        //存储同样中心点，长度的side集合
        HashMap<String, HashSet<Line>> lineSetMap = new HashMap<>();
        double area = 0.0;
        double tempArea;
        for (Point p1 : pointSet) {
            for (Point p2 : pointSet) {
                if (!p1.equals(p2)) {
                    tempLine = new Line(p1, p2);
                    if (lineSet.contains(tempLine)) {
                        continue;
                    }
                    tempLineSet = lineSetMap.get(tempLine.getKey());
                    if (tempLineSet == null) {
                        tempLineSet = new HashSet<>();
                        lineSetMap.put(tempLine.getKey(), tempLineSet);
                    }
                    for (Line s : tempLineSet) {
                        tempArea = calcRectArea(tempLine, s);
                        if (area != 0 && area <= tempArea) {
                            continue;
                        }
                        area = tempArea;
                    }
                    tempLineSet.add(tempLine);
                    lineSet.add(tempLine);
                }
            }
        }
        return area;
    }

    private double calcRectArea(Line l1, Line l2) {
        return l1.p1.distance(l2.p1) * l1.p1.distance(l2.p2);
    }

    public static class Line {
        public final Point p1;
        public final Point p2;
        public final Point compareCenter;
        public final long compareLength;

        public Line(Point point1, Point point2) {
            //左下定义为p1
            if (point1.x < point2.x || (point1.x == point2.x && point1.y < point2.y)) {
                this.p1 = point1;
                this.p2 = point2;
            } else {
                this.p1 = point2;
                this.p2 = point1;
            }
            compareLength = (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
            //不用存储中点，计算成浮点值有精度丢失
            compareCenter = new Point(p1.x + p2.x, p1.y + p2.y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Line)) return false;
            Line line = (Line) o;
            return Objects.equals(p1, line.p1) &&
                    Objects.equals(p2, line.p2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(p1, p2, compareCenter, compareLength);
        }

        public String getKey() {
            return compareCenter.toString() + "," + compareLength;
        }
    }

    //----------------------------------------------------------------------------------------------
    public double minAreaFreeRect2(int[][] points) {
        //从数组取三个元素组成集合，如何避免取到相同的三个数，排列是组合的6倍
        //1、三角形Map存储的key由三个点的坐标构成字符串，按照x从小到大，若x一样，则按照y从小到大
        //但这样会生成很多三角形存储起来用于判断是否是重复的三个点，很浪费内存。
        //2、还是直接遍历把，生成矩形后，先判断一下面积是否小于当前找到的最小面积矩形，若不是，则跳过
        //如何快速判断第四点是否存在，三个点构成的平行四边形第四点很容易算，但如何查找
        //3、看到评论里的算法恍然大悟，不想遍历到相同组合的元素，可以让第二层遍历以第一层数值的下一个为起点，
        //第三层的遍历以第二层的下一个为起点，这样就不会出现相同组合的情况（大学应该有看过，但忘了）
        //快速判断第四个点是否在集合中，

        //怎么判断三个点是否能组成矩形
        //利用直角三角形，直角边的平方和等于斜边的平方，可以判断，不用向量（计算消耗）
        //边长平方可以直接用两点坐标求得，不用开方（计算消耗）
        //三角形大边对大角，边平方和最大的是斜边，其他两个可能是直角边。

        //面积大小比较
        //1、判断是直角三角形后，计算两直角边长度相乘，计算消耗量大啊
        //2、S=axb,ab皆>=1,若a1xb1>a2xb2,则(a1xa1)x(b1xb1)>(a2xa2)x(b2xb2)
        //可以用平方和代替面积进行初略比较，求得最终的矩形再去精确计算

        int pLength = points.length;
        int maxXY = 40000;
        Map<Integer, Integer> pointMap = new HashMap<>(pLength);
        for (int i = 0; i < pLength; i++) {
            pointMap.put(points[i][0] * maxXY + points[i][1], i);
        }

        Parallelogram result = null;
        Parallelogram temp;
        for (int p1 = 0; p1 < pLength - 2; p1++) {
            for (int p2 = p1 + 1; p2 < pLength - 1; p2++) {
                for (int p3 = p2 + 1; p3 < pLength; p3++) {
                    if (p1 != p2 && p1 != p3 && p2 != p3) {
                        temp = new Parallelogram(
                                points[p1][0],
                                points[p1][1],
                                points[p2][0],
                                points[p2][1],
                                points[p3][0],
                                points[p3][1]
                        );
                        if (temp.isRect
                                && pointMap.containsKey(temp.p4x * maxXY + temp.p4y)) {
                            if (temp.compareArea == 1) {
                                return 1;
                            }
                            if (result == null || result.compareArea > temp.compareArea) {
                                result = temp;
                            }
                        }
                    }
                }
            }
        }
        return result == null ? 0 : result.calcArea();
    }

    /**
     * 根据三个同的点生成的平行四边形
     */
    public static class Parallelogram {
        final int p1x, p1y, p2x, p2y, p3x, p3y, p4x, p4y;

        //side
        final int maxSideSquare, rtSide1Square, rtSide2Square;
        final boolean isRect;
        final double compareArea;

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
            if (isRect) {
                //本想不开根计算数值用于面积比较，奈何long存储不下，只能开根计算了
//                compareArea = rtSide1Square * rtSide2Square;
                compareArea = Math.sqrt(rtSide1Square) * Math.sqrt(rtSide2Square);
            } else {
                compareArea = Double.MAX_VALUE;
            }
        }

        public double calcArea() {
            return compareArea;
        }
    }

    public static void main(String[] args) {
        System.out.println(new Solution963().minAreaFreeRect2(new int[][]{{1, 2}, {2, 1}, {1, 0}, {0, 1}}));
        //[[0,1],[2,1],[1,1],[1,0],[2,0]]
//        System.out.println(new Solution963().minAreaFreeRect(new int[][]{{0, 1}, {2, 1}, {1, 1}, {1, 0}, {2,0}}));
        //[3,1],[1,1],[0,1],[2,1],[3,3],[3,2],[0,2],[2,3]
//        System.out.println(new Solution963().minAreaFreeRect(new int[][]{{3, 1}, {1, 1}, {0, 1}, {2, 1}, {3, 3}, {3, 2}, {0, 2}, {2, 3}}));
    }

}
