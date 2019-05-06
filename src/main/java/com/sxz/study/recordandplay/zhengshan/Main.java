package com.sxz.study.recordandplay.zhengshan;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;


public class Main {

    private static CountDownLatch startGate = new CountDownLatch(1);
    private static CountDownLatch endGate;
    private static List<PlayMultiAudiosTask> playMultiAudiosThreads = new ArrayList<>();
    static String audioFile = "D:\\sxz\\audio\\jack mic (realtek audio).wav";

    public static void main(String[] args) {
        // get deviceInfo
        List<String> speakers = new ArrayList<>();
        speakers.add("speakers");
        List<Mixer.Info> infos = getDeviceInfo(speakers);

        // new playMultiAudiosThreads
        endGate = new CountDownLatch(infos.size());
        File file = new File(audioFile);
        for (int i = 0; i < infos.size(); i++) {
            PlayMultiAudiosTask playMultiAudiosTask = new PlayMultiAudiosTask(file, infos.get(i),
                    0.8F, startGate, endGate);
            playMultiAudiosThreads.add(playMultiAudiosTask);
            FutureTask ft = new FutureTask(playMultiAudiosTask);
            Thread t = new Thread(ft, "I am thread-" + i);
            t.start();
        }

        long start = System.currentTimeMillis();
        startGate.countDown();
        try {
           // Thread.sleep(5000);
//            playMultiAudiosThreads.stream().forEach(t -> {
//                t.cancell();
//            });
           // playMultiAudiosThreads.get(0).cancell();

            endGate.await();
            System.out.println("endGate await end.." + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("spend time total : " + (end - start));
    }

    public static List<Mixer.Info> getDeviceInfo(List<String> speakers){
        List<Mixer.Info> infos = new ArrayList<Mixer.Info>();

        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info recordInfo : mixerInfos){
            String deviceName = recordInfo.getName().trim().toLowerCase();
            speakers.stream().forEach(s -> {
                if ((s != null && deviceName.startsWith(s))) {
                    System.out.println("Device name : " + deviceName);
                    infos.add(recordInfo);
                }
            });
        }
        return infos;
    }
}
