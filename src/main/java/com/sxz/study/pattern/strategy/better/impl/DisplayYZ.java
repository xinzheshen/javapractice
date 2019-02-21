package com.sxz.study.pattern.strategy.better.impl;


import com.sxz.study.pattern.strategy.better.base.IDisplayBehavior;

public class DisplayYZ implements IDisplayBehavior {

    @Override
    public void display() {
        System.out.println("样子2");
    }
}
