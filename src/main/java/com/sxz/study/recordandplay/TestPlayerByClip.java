package com.sxz.study.recordandplay;

import javax.sound.sampled.Mixer;
import java.util.ArrayList;

public class TestPlayerByClip {

    public static void main(String[] args) {
        String deviceLable1 = "speakers";

        GetAudioDevice getAudioDevice = new GetAudioDevice(deviceLable1);
        ArrayList<Mixer.Info> audioDevices = getAudioDevice.getAudioDevice();

//        String filePath = "D:\\sxz\\audio\\output\\microphone (ce-link).wav";
//        String filePath = "D:\\sxz\\audio\\output\\microphone (3- usb audio device.wav";
        String filePath = "D:\\sxz\\audio\\output\\untitled.wav";

//        Player player = new Player(audioDevices, filePath);

//        clip这种方式，主线程结束了，子线程就会结束-------通过添加监听器解决
        PlayerByClip player = new PlayerByClip(audioDevices, filePath);

        player.startPlay();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        player.stopPlay();

    }

}
