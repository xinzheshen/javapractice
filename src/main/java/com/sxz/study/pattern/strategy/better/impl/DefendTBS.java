package com.sxz.study.pattern.strategy.better.impl;


import com.sxz.study.pattern.strategy.better.base.IDefendBehavior;

public class DefendTBS implements IDefendBehavior {

    @Override
    public void defend() {
        System.out.println("铁布衫");
    }
}
