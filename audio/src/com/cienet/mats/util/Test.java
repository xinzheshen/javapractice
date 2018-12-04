package com.cienet.mats.util;

import org.apache.log4j.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Test {
    private static Logger logger = Logger.getLogger(Test.class);

    public static void main(String args[]){
        logger.info("Start to test");

        GetAudioDevice getAudioDevice = new GetAudioDevice();
        ArrayList<Mixer.Info> audioDevices = getAudioDevice.getAudioDevice();

        String outputPath = "D:\\sxz\\audio\\output";
        Recorder recorder = new Recorder(audioDevices, outputPath);
        recorder.StartRecord();

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recorder.stopRecord();

    }

}
