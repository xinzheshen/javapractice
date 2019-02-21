package com.sxz.study.pattern.observer.self.impl;

import com.sxz.study.pattern.observer.self.interfaces.Observer;
import com.sxz.study.pattern.observer.self.interfaces.Subject;
import org.apache.log4j.Logger;

/**
 *
 * 模拟第一个使用者
 */

public class ObserverUser1 implements Observer {
    private static Logger log = Logger.getLogger(ObserverUser1.class);

    public ObserverUser1(Subject subject) {
        subject.registerObserver(this);
    }

    @Override
    public void update(String msg) {
        log.info("-----ObserverUser1 得到 3D 号码:" + msg + ", 我要记下来。 ");
//        Toast.makeText(PatternApplication.getInstance(), "-----ObserverUser1 得到 3D 号码:" + msg, Toast.LENGTH_SHORT).show();
    }
}
