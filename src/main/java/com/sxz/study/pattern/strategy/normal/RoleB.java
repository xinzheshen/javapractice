package com.sxz.study.pattern.strategy.normal;


public class RoleB extends Role {

    public RoleB(String name) {
        this.name = name;
    }

    @Override
    protected void display() {
        System.out.println("样子2");
    }

    @Override
    protected void run() {
        System.out.println("金蝉脱壳");  //拷贝,显得冗余
    }

    @Override
    protected void attack() {
        System.out.println("降龙十八掌");//拷贝,显得冗余
    }

    @Override
    protected void defend() {
        System.out.println("铁布衫");
    }
}
