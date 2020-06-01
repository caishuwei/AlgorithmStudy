package com.caisw.algorithm.jingdian50;

import com.caisw.algorithm.base.BaseSubject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Class2 extends BaseSubject {
    private PrimeNumberIterator primeNumberIterator = new PrimeNumberIterator();

    public Class2() {
        super("判断101-200之间有多少个素数，并输出所有素数。");
    }

    @Override
    public void runTest() {
        super.runTest();
        //素数（质数）是指除了1和其本身外，不能被其他数整除的数
        //优化1，若上限为100，则拆成两个数相乘，两个数肯定有一个小于根号100
        //优化2，10000也不用从2除到100，只需要判断能不能整除1~100之间的素数即可，
        //合数可以分成其他质数相乘，如果能整除合数，也能整除其组成的质数，所以只需要判断能否整除质数即可
        //以空间换时间，将其需要用到的质数存储起来遍历即可
        List<Integer> result = primeNumberIterator.getPrimeNumberInRange(100, 200);
        output(String.format(Locale.getDefault(), "total %d, %s", result.size(), result.toString()));
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
        new Class2().runTest();
    }
}
