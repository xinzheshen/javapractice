package com.sxz.study.pattern.strategy.better;

import com.sxz.study.pattern.strategy.better.base.Role;
import com.sxz.study.pattern.strategy.better.impl.AttackXL;
import com.sxz.study.pattern.strategy.better.impl.DefendTBS;
import com.sxz.study.pattern.strategy.better.impl.DisplayYZ;
import com.sxz.study.pattern.strategy.better.impl.RoleA;
import com.sxz.study.pattern.strategy.better.impl.RunJCTQ;

public class Test {
    public static void main(String[] args)
    {

        Role roleA = new RoleA("A");

        roleA.setAttackBehavior(new AttackXL()).setDefendBehavior(new DefendTBS()).
                setDisplayBehavior(new DisplayYZ()).setRunBehavior(new RunJCTQ());

        System.out.println(roleA.getName() + ":");
        roleA.run();
        roleA.attack();
        roleA.defend();
        roleA.display();
    }
}
