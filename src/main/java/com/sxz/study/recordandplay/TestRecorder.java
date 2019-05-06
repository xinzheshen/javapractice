package com.sxz.study.recordandplay;

import org.apache.log4j.Logger;

import javax.sound.sampled.Mixer;
import java.util.ArrayList;

public class TestRecorder {
    private static Logger logger = Logger.getLogger(TestRecorder.class);

    public static void main(String args[]) {
        logger.info("Start to test");
//        System.out.println("y开始n结束");
//        Scanner input = new Scanner(System.in);
//        String Sinput = input.next();
//        if(Sinput.equals("y")){

        String deviceLable1 = "microphone";
        String deviceLable2 = "jack";
        ArrayList<String> audioDevices = new ArrayList<>();
        audioDevices.add("Microphone (2- CE-LINK)");
        audioDevices.add("Jack Mic (Realtek Audio)");


        String outputPath = "D:\\sxz\\audio\\output";
        RecorderAudiosMeanwhileByFFmpeg recorder = new RecorderAudiosMeanwhileByFFmpeg(audioDevices, outputPath);
        recorder.startRecord();
        //            RecorderAudiosMeanwhile recorder2 = new RecorderAudiosMeanwhile(audioDevices, outputPath);
        //            recorder2.startRecord();


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recorder.stopRecord();

        logger.info("End");
    }

//        }
//        Scanner input_2 = new Scanner(System.in);
//        String Sinput_2 = input_2.next();
//        if(Sinput_2.equals("n")){
//            logger.info("End");
//        }

}
