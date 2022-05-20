package com.reservetable;/**
 * @author chenfl
 * @create 2022-05-18-14:25
 */

/**
 * @author chenfl
 * @description
 * @date 2022/5/18 14:25
 */
public class TEst {
    public static void main(String[] args) {
        String x = "ab";
        String s = new String("a") + new String("b");
        String s2 = s.intern();
        System.out.println(s2 == "ab");
        System.out.println(s == "ab");
    }
}
