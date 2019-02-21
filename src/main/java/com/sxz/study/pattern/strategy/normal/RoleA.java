package com.sxz.study.pattern.strategy.normal;

/**
 * 不采用策略模式时，每个角色的技能有重复时，产生冗余代码
 */
public class RoleA extends Role {

    public RoleA(String name) {
        this.name = name;
    }

    @Override
    protected void display() {
        System.out.println("样子1");
    }

    @Override
    protected void run() {
        System.out.println("金蚕脱壳");
    }

    @Override
    protected void attack() {
        System.out.println("降龙十八掌");
    }

    @Override
    protected void defend() {
        System.out.println("铁头功");
    }
}
