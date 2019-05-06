package com.sxz.study.enumtest;

public enum Week {
    MONDAY(1), TUESDAY(2);
    private int num;
    private Week(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }
    public static void main(String args[]){
        System.out.println(Week.MONDAY.toString());
        System.out.println(Week.MONDAY.getNum());
    }
}
