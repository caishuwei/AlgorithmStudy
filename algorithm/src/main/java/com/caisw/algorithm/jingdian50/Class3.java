package com.caisw.algorithm.jingdian50;

import com.caisw.algorithm.base.BaseSubject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Class3 extends BaseSubject {

    public Class3() {
        super("正整数分解质因数");
    }

    @Override
    public void runTest() {
        super.runTest();

    }

    public class PrimeNumberIterator {

        private LinkedList<Integer> primeNumberList = new LinkedList<>();

        public List<Integer> getPrimeNumberInRange(int start, int end) {
            ArrayList<Integer> result = new ArrayList<>();
            if (start > end || start <= 1) {
                return result;
            }
            //假设end能拆成两个数相乘，则两个数中最小的那个数最多能达到多少，以便减少计算量
            double maxFactor = Math.sqrt(end);
            updatePrimeNumberList((int) (maxFactor + 1));

            for (int i = start; i <= end; i++) {
                if (isPrime(i)) {
                    result.add(i);
                }
            }
            return result;
        }

        private boolean isPrime(int i) {
            double maxFactor = Math.sqrt(i);
            for (Integer prime : primeNumberList) {
                if (prime <= maxFactor) {
                    if (i % prime == 0) {
                        //能整除，不是质数
                        return false;
                    }
                } else {
                    //到这里都没有找到能整除的，那就是质数了
                    break;
                }
            }
            return true;
        }

        private void updatePrimeNumberList(int max) {
            Integer last = primeNumberList.peekLast();
            if (last == null) {
                last = 1;//设置为1，下一个数从2开始
            } else if (last >= max) {
                return;
            }
            //需要更多的质数,往后更新至max
            for (int i = last + 1; i <= max; i++) {
                addIfIsPrime(i);
            }
        }

        private void addIfIsPrime(int i) {
            if (isPrime(i)) {
                primeNumberList.addLast(i);
            }
        }
    }

    public static void main(String[] args) {
        new Class3().runTest();
    }
}
