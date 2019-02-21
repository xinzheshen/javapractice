package com.sxz.study.pattern.strategy.better.impl;


import com.sxz.study.pattern.strategy.better.base.IRunBehavior;

public class RunJCTQ implements IRunBehavior {

    @Override
    public void run() {
        System.out.println("金蝉脱壳");
    }
}
