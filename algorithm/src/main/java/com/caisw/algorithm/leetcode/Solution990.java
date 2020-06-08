package com.caisw.algorithm.leetcode;

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
        //记录相等关系，维度1第一个位置表示指向空，维度2最后一个位置表示该数组是否已经使用
        int[][] equals = new int[27][27];
        int[] equalsLink = new int[26];
        //记录不等关系，维度1为变量下标，维度2=0表示没有记录，=1表示不等
        int[][] noEquals = new int[26][26];
        int[] noEqualsLink = new int[26];

        int v1, v2, e, eE = '=', nE = '!', linkV1, linkV2;
        for (String equation : equations) {
            v1 = equation.charAt(0) - aIndex;
            e = equation.charAt(1);
            v2 = equation.charAt(3) - aIndex;
            if (e == eE) {//等式
                //自己等于自己不用记录
                if (v1 == v2) {
                    break;
                }
                //判断是否冲突
                if (noEquals[v1][v2] == 1) {
                    return false;
                } else if (noEquals[v2][v1] == 1) {
                    return false;
                }
                //存储相等关系
                linkV1 = equalsLink[v1];
                linkV2 = equalsLink[v2];
                if (linkV1 == 0 && linkV2 == 0) {
                    //找一个空闲的数组来存储
                    for (int link = 1; link < 27; link++) {
                        if (equals[link][26] == 0) {
                            equals[link][26] = 1;
                            equals[link][v1] = 1;
                            equals[link][v2] = 1;
                            equalsLink[v1] = link;
                            equalsLink[v2] = link;

                            //两数相等，合并他们的不等关系
                            linkV1 = noEqualsLink[v1];
                            linkV2 = noEqualsLink[v2];
                            if (linkV1 != linkV2) {
                                for (int index = 0; index < 26; index++) {
                                    noEquals[linkV1][index] |= noEquals[linkV2][index];
                                }
                                noEqualsLink[v2] = linkV1;
                            }
                            break;
                        }
                    }
                } else if (linkV1 != linkV2) {
                    if (linkV1 == 0) {
                        equals[linkV2][v1] = 1;
                    } else if (linkV2 == 0) {
                        equals[linkV1][v2] = 1;
                    } else {
                        //将v2的相等关系指向linkV1
                        equalsLink[v2] = linkV1;
                        //这里要将两个相等关系数组合并
                        int nELinkV1 = noEqualsLink[v1];
                        for (int index = 0; index < 27; index++) {
                            //linkV2数据转移到linkV1
                            if (index < 26) {
                                if (equals[linkV2][index] == 1) {
                                    //相等关系合并
                                    equals[linkV1][index] |= equals[linkV2][index];
                                    //不等关系合并
                                    for (int nEIndex = 0; nEIndex < 26; nEIndex++) {
                                        if (noEquals[noEqualsLink[index]][nEIndex] == 1) {
                                            noEquals[nELinkV1][nEIndex] = 1;
                                        }
                                    }
                                    //不等关系指向nELinkV1
                                    noEqualsLink[index] = nELinkV1;
                                }
                            }
                            //释放linkV2数组
                            equals[linkV2][index] = 0;
                        }
                    }
                } else {
                    //指向同个数组，说明已经记录过他们之间的关系了
                    break;
                }
            } else if (e == nE) {//不等式
                //判断是否冲突
                if (v1 == v2) {
                    return false;
                }
                if (equals[equalsLink[v1]][v2] == 1) {
                    return false;
                } else if (equals[equalsLink[v2]][v1] == 1) {
                    return false;
                }
                //存储不等关系
                noEquals[noEqualsLink[v1]][v2] = 1;
                noEquals[noEqualsLink[v2]][v1] = 1;
            } else {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(new Solution990().equationsPossible(new String[]{"a==b", "b!=a"}));
        System.out.println(new Solution990().equationsPossible(new String[]{"b==a", "a==b"}));
        System.out.println(new Solution990().equationsPossible(new String[]{"a==b", "b==c", "a==c"}));
        System.out.println(new Solution990().equationsPossible(new String[]{"a==b", "b!=c", "c==a"}));
        System.out.println(new Solution990().equationsPossible(new String[]{"c==c", "b==d", "x!=z"}));
    }

}
