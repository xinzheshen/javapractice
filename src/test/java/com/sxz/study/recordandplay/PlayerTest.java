package com.sxz.study.recordandplay;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.Mixer;
import java.util.ArrayList;

public class PlayerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void startPlay() {

        String deviceLable1 = "speakers";

        GetAudioDevice getAudioDevice = new GetAudioDevice(deviceLable1);
        ArrayList<Mixer.Info> audioDevices = getAudioDevice.getAudioDevice();

//        String filePath = "D:\\sxz\\audio\\jack mic (realtek audio).wav";
        String filePath = "D:\\sxz\\audio\\output\\microphone (3- usb audio device.wav";

        Player player = new Player(audioDevices, filePath);

        player.startPlay();

        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        player.stopPlay();

    }
}
