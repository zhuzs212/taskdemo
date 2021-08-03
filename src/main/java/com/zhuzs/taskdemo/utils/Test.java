package com.zhuzs.taskdemo.utils;

import java.util.*;

/**
 * DLT 5+2
 * 1-35 1-12
 *
 * @author: zhu_zishuang
 * @date: 2020-08-04
 */
public class Test {
    public static void main(String[] args) {
        // 给定待排除的数
//        Integer[] pass1 = {};
//        Integer[] pass2 = {};
//        List<Set<Integer>> result = generate(pass1, pass2);

        // 给定待选的数
        Integer[] integerArr1 = {1, 3, 8, 5,9, 10, 11, 12,13,15, 16, 17, 18,19, 20, 23, 27, 25, 26, 28,29, 30, 31, 33, 34};
        Integer[] integerArr2 = {2, 4, 6, 7, 3, 9};
        List<Set<Integer>> result = generate2(integerArr1, integerArr2);


        for (Set<Integer> set : result) {
            System.out.println(set);
        }

    }

    public static List<Set<Integer>> generate(Integer[] pass1, Integer[] pass2) {
        // 结果
        List<Set<Integer>> result = new ArrayList<>();

        List<Integer> list1 = new ArrayList<>();
        for (int i = 1; i <= 35; i++) {
            list1.add(i);
        }
        // 过滤
        list1.removeAll(Arrays.asList(pass1));

        List<Integer> list2 = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list2.add(i);
        }
        // 过滤
        list2.removeAll(Arrays.asList(pass2));

        for (int i = 0; i < 5; i++) {
            generateFinal(result, list1, list2);
        }

        return result;
    }

    public static List<Set<Integer>> generate2(Integer[] integerArr1, Integer[] integerArr2) {
        // 结果
        List<Set<Integer>> result = new ArrayList<>();

        List<Integer> list1 = Arrays.asList(integerArr1);
        List<Integer> list2 = Arrays.asList(integerArr2);

        for (int i = 0; i < 5; i++) {
            generateFinal(result, list1, list2);
        }

        return result;
    }

    private static void generateFinal(List<Set<Integer>> result, List<Integer> list1, List<Integer> list2) {
        Set<Integer> QQ = new TreeSet<>();
        Set<Integer> HQ = new TreeSet<>();
        // qian
        while (QQ.size() < 5) {
            int index = new Random().nextInt(list1.size());
            QQ.add(list1.get(index));
        }

        //hou
        while (HQ.size() < 2) {
            int index = new Random().nextInt(list2.size());
            HQ.add(list2.get(index));
        }
        result.add(QQ);
        result.add(HQ);
    }
}

