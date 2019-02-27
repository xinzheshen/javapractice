package com.sxz.study;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
//        String str = "womenbuyiyang";
//        String[] split = str.split(".");
//        System.out.println(split[0]);

//        List<String> list1 =new ArrayList();
//        list1.add("1");
//        list1.add("2");
//        list1.add("3");
//
//        StringBuilder sb = new StringBuilder();
//        String result = list1.stream().reduce("", String::concat);
//        System.out.println(result);
//
//        list1.forEach(string -> sb.append(string));
//        System.out.println(sb.toString());
//
//        String join = String.join(",", list1);
//        System.out.println(join);


        String data = "test**********";
        Boolean isCyclic = false;

        System.out.println(String.format("data=%s&isCyclic=%s", data, isCyclic));
        System.out.println(String.format("data=%d&isCyclic=%s", (byte)128, isCyclic));


        byte b1 = (byte)127;
        byte b2 = (byte)128;

        System.out.println("int 127: " + b1);
        System.out.println("int 128: " + b2);
        System.out.println("int 128: " + (b2 & 0xFF));

        new Test().testShort((short)4444);

        System.out.println("------------------");

        Integer i = 11;
        System.out.println("Integer 11 to string: " + i.toString());

        Integer num1 = 288;
        int num2 = 288;
        System.out.println("Integer 288 to string: " + (byte)num2);
        System.out.println("Integer 288 to string: " + ((byte)num2 & 0xFF));

    }

    void testShort(short num){
        System.out.println(num);
    }
}
