package com.caisw.algorithm.leetcode;

/**
 * 等式方程的可满足性
 * https://leetcode-cn.com/problems/satisfiability-of-equality-equations/
 */

public class Solution990 {

    /**
     * 提示：
     * 1 <= equations.length <= 500
     * equations[i].length == 4
     * equations[i][0] 和 equations[i][3] 是小写字母
     * equations[i][1] 要么是 '='，要么是 '!'
     * equations[i][2] 是 '='
     */
    public boolean equationsPossible(String[] equations) {
        // 全是一个小写字母==或!=另一个小写字母
        // 判断所有等式是否都成立
        // 解析的问题不大，问题在于比较的性能上
        int aIndex = 'a';
        //记录相等关系，0表示没有关系，1表示相等，2表示不等
        int[][] linkEquals = new int[26][26];
        int[] charLink = new int[26];
        for (int i = 0; i < charLink.length; i++) {
            charLink[i] = i;
            linkEquals[i][i] = 1;
        }
        int v1, v2, e, eE = '=', nE = '!', linkV1, linkV2;
        for (String equation : equations) {
            v1 = equation.charAt(0) - aIndex;
            e = equation.charAt(1);
            v2 = equation.charAt(3) - aIndex;
            if (e == eE) {//等式
                //自己等于自己不用记录
                if (v1 == v2) {
                    continue;
                }
                //判断是否存在不等关系
                if (linkEquals[charLink[v1]][v2] == 2
                        || linkEquals[charLink[v2]][v1] == 2) {
                    return false;
                }
                //存储相等关系
                linkV1 = charLink[v1];
                linkV2 = charLink[v2];
                if (linkV1 == linkV2) {
                    //已经指向同个存储关系,不需要做什么操作
                } else {
                    linkEquals[linkV1][v2] = 1;
                    //将linkV2相等关系合并到linkV1
                    for (int i = 0; i < 26; i++) {
                        if (linkEquals[linkV1][i] + linkEquals[linkV2][i] == 3) {
                            //同时存在相等与不相等关系
                            return false;
                        }
                        linkEquals[linkV1][i] |= linkEquals[linkV2][i];
                        if (linkEquals[linkV2][i] == 1) {
                            //指向linkV1
                            charLink[i] = linkV1;
                        }
                    }
                }
            } else if (e == nE) {//不等式
                //判断是否冲突
                if (v1 == v2) {
                    return false;
                }
                linkV1 = charLink[v1];
                linkV2 = charLink[v2];
                //判断是否存在相等关系
                if (linkV1 == linkV2) {
                    return false;
                }
                //存储不等关系
                linkEquals[linkV1][v2] = 2;
                linkEquals[linkV2][v1] = 2;
            } else {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
//        System.out.println(new Solution990().equationsPossible(new String[]{"a==b", "b!=a"}));
//        System.out.println(new Solution990().equationsPossible(new String[]{"b==a", "a==b"}));
//        System.out.println(new Solution990().equationsPossible(new String[]{"a==b", "b==c", "a==c"}));
//        System.out.println(new Solution990().equationsPossible(new String[]{"a==b", "b!=c", "c==a"}));
//        System.out.println(new Solution990().equationsPossible(new String[]{"c==c", "b==d", "x!=z"}));
        System.out.println(new Solution990().equationsPossible(new String[]{"f==f","a!=a","d==b","a==f","f!=a"}));
    }

}
