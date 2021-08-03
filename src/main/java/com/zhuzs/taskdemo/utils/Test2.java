package com.zhuzs.taskdemo.utils;

import java.util.*;

/**
 * SSQ 6+1
 * 1-33 1-16
 *
 * @author: zhu_zishuang
 * @date: 2020-08-04
 */
public class Test2 {
    public static void main(String[] args) {
        // 给定待排除的数
        // 2 9 10 21 35 1 7
//        Integer[] pass1 = {};
//        Integer[] pass2 = {1, 2, 3, 4, 5, 6, 7};
//        List<Set<Integer>> result = generate(pass1, pass2);

        // 给定待选的数
        Integer[] integerArr1 = {1, 4, 7, 6, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20, 21, 22, 24, 23, 28, 29, 31, 32, 33};
        Integer[] integerArr2 = {7, 6, 9, 12, 15};
        List<Set<Integer>> result = generate2(integerArr1, integerArr2);

        for (Set<Integer> set : result) {
            System.out.println(set);
        }
    }

    public static List<Set<Integer>> generate(Integer[] pass1, Integer[] pass2) {
        // 结果
        List<Set<Integer>> result = new ArrayList<>();

        List<Integer> list1 = new ArrayList<>();
        for (int i = 1; i <= 33; i++) {
            list1.add(i);
        }
        // 过滤
        list1.removeAll(Arrays.asList(pass1));

        List<Integer> list2 = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            list2.add(i);
        }
        // 过滤
        list2.removeAll(Arrays.asList(pass2));
        System.out.println("list2,过滤后： " + list2.toString());

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
        while (QQ.size() < 6) {
            int index = new Random().nextInt(list1.size());
            QQ.add(list1.get(index));
        }

        //hou
        while (HQ.size() < 1) {
            int index = new Random().nextInt(list2.size());
            HQ.add(list2.get(index));
        }
        result.add(QQ);
        result.add(HQ);
    }
}

