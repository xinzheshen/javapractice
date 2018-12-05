package com.xxyy.mats.util;

import org.apache.log4j.Logger;

import javax.sound.sampled.Mixer;
import java.util.ArrayList;

public class TestPlayer {
    private static Logger logger = Logger.getLogger(TestRecorder.class);

    public static void main(String args[]){
        logger.info("Start to test");

        String deviceLable1 = "speakers";

        for(int i = 0; i < 1; i ++){
            System.out.println(i);

            GetAudioDevice getAudioDevice = new GetAudioDevice(deviceLable1);
            ArrayList<Mixer.Info> audioDevices = getAudioDevice.getAudioDevice();

            String filePath = "D:\\sxz\\audio\\output\\jack mic (realtek audio).wav";
            Player player = new Player(audioDevices, filePath);
            player.StartPlay();
        }

    }

}
