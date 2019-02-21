package com.sxz.study.pattern.strategy.better.impl;


import com.sxz.study.pattern.strategy.better.base.IAttackBehavior;

public class AttackXL implements IAttackBehavior {
    @Override
    public void attack() {
        System.out.println("降龙十八掌");
    }
}
