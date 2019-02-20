package com.sxz.study.model.self.impl;

import com.sxz.study.model.self.interfaces.Observer;
import com.sxz.study.model.self.interfaces.Subject;
import org.apache.log4j.Logger;

/**
 * Created by jingbin on 2016/10/21.
 * 模拟第二个使用者
 */

public class ObserverUser2 implements Observer {
    private static Logger log = Logger.getLogger(ObserverUser2.class);

    public ObserverUser2(Subject subject) {
        subject.registerObserver(this);
    }

    @Override
    public void update(String msg) {
        log.info("-----ObserverUser2 得到 3D 号码:" + msg + ", 我要告诉舍友们。");
//        Toast.makeText(PatternApplication.getInstance(), "-----ObserverUser2 得到 3D 号码:" + msg, Toast.LENGTH_SHORT).show();
    }
}
