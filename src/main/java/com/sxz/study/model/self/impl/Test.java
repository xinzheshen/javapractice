package com.sxz.study.model.self.impl;

import com.sxz.study.model.self.interfaces.Observer;

public class Test {
    public static void main(String[] args){
        //模拟一个3D的服务号
        ObjectFor3D subjectFor3d = new ObjectFor3D();
        //客户1
        Observer observer1 = new ObserverUser1(subjectFor3d);
        Observer observer2 = new ObserverUser2(subjectFor3d);

        subjectFor3d.setMsg("第一个3D号码是：127" );
        subjectFor3d.setMsg("第二个3D号码是：333" );
    }
}
