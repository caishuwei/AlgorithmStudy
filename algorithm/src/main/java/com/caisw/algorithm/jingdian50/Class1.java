package com.caisw.algorithm.jingdian50;

import com.caisw.algorithm.base.BaseSubject;

import java.util.Locale;

/**
 * 经典算法50题
 * https://blog.csdn.net/YaoChung/article/details/80793691
 */
public class Class1 extends BaseSubject {

    public Class1() {
        super("古典问题：有一对兔子，从出生后第3个月起每个月都生一对兔子，小兔子长到第三个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？");
    }

    @Override
    public void runTest() {
        super.runTest();
        //相当于每个单位，有1、2、3种状态，进入第三种状态后，每次都诞生新的单位
        //问的是每个月的兔子总数是多少（应该写一个方法，每次调用则演化下一次单位数量）
        RabbitIterator rabbitIterator = new RabbitIterator();
        int[] data = new int[4];
        for (int i = 0; i < 20; i++) {
            rabbitIterator.nextAndGet(data);
            output(String.format(Locale.getDefault(), "in %2d month, total %d, [%d,%d,%d]", data[0], data[1] + data[2] + data[3], data[1], data[2], data[3]));
        }
    }

    /**
     * 兔子迭代
     */
    public class RabbitIterator {

        private int currMonth;//当前年份
        private int inStatus1;//第一阶段兔子数量（对）
        private int inStatus2;//第二阶段兔子数量（对）
        private int inStatus3;//第三阶段兔子数量（对）

        public RabbitIterator() {
        }

        public void nextAndGet(int[] data) {
            if (currMonth == 0) {
                //初始化 第一个月状态，拥有一对出生第一个月的兔子
                currMonth = 1;
                inStatus1 = 1;
                inStatus2 = 0;
                inStatus3 = 0;
            } else {
                currMonth++;
                //2进入3
                inStatus3 = inStatus2 + inStatus3;
                //1进入2
                inStatus2 = inStatus1;
                //进入3阶段的可以诞生新的兔子
                inStatus1 = inStatus3;
            }
            data[0] = currMonth;
            data[1] = inStatus1;
            data[2] = inStatus2;
            data[3] = inStatus3;
        }
    }

    public static void main(String[] args) {
        new Class1().runTest();
    }
}
