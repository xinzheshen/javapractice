package com.sxz.study;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
//        String str = "womenbuyiyang";
//        String[] split = str.split(".");
//        System.out.println(split[0]);

        List<String> list1 =new ArrayList();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        StringBuilder sb = new StringBuilder();
        String result = list1.stream().reduce("", String::concat);
        System.out.println(result);

        list1.forEach(string -> sb.append(string));
        System.out.println(sb.toString());

        String join = String.join(",", list1);
        System.out.println(join);

    }
}
