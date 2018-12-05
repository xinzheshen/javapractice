package com.xxyy.mats.util;

import org.apache.log4j.*;

import javax.sound.sampled.Mixer;
import java.util.ArrayList;
import java.util.Scanner;

public class TestRecorder {
    private static Logger logger = Logger.getLogger(TestRecorder.class);

    public static void main(String args[]){
        logger.info("Start to test");
//        System.out.println("y开始n结束");
//        Scanner input = new Scanner(System.in);
//        String Sinput = input.next();
//        if(Sinput.equals("y")){

            String deviceLable1 = "microphone";
            String deviceLable2 = "jack";

            for(int i = 0; i < 10; i ++){
                System.out.println(i);

                GetAudioDevice getAudioDevice = new GetAudioDevice(deviceLable1, deviceLable2);
                ArrayList<Mixer.Info> audioDevices = getAudioDevice.getAudioDevice();

                String outputPath = "D:\\sxz\\audio\\output";
                Recorder recorder = new Recorder(audioDevices, outputPath);
                recorder.StartRecord();

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                recorder.stopRecord();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
//        }
//        Scanner input_2 = new Scanner(System.in);
//        String Sinput_2 = input_2.next();
//        if(Sinput_2.equals("n")){
//            logger.info("End");
//        }
    }

}
