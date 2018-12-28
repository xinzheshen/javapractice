package com.sxz.study.recordandplay;

import javax.sound.sampled.Mixer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestRecorderAudios {

    public static void main(String args[]){

        String deviceLable1 = "microphone";
        String deviceLable2 = "jack";


        GetAudioDevice getAudioDevice = new GetAudioDevice(deviceLable1, deviceLable2);
        ArrayList<Mixer.Info> audioDevices = getAudioDevice.getAudioDevice();

        String outputPath = "D:\\sxz\\audio\\output";

//        RecorderAudios.INSTANCE.recorderAudios(audioDevices, outputPath);
//        RecorderAudios.INSTANCE.startRecord();
//
//        RecorderAudios.INSTANCE.recorderAudios(audioDevices, outputPath);
//        RecorderAudios.INSTANCE.startRecord();


        Map<String, RecorderAudios.DoRecord> doRecordMap = new HashMap<>();
        for (Map.Entry<String, RecorderAudios.DoRecord> entry : doRecordMap.entrySet()) {
            entry.getValue().closeDataLine();
            System.out.println("here");
        }
        if(doRecordMap == null){
            System.out.println("null");
        }
        if(doRecordMap.isEmpty() ){
            System.out.println("empty");
        }
        if(doRecordMap.size() == 0){
            System.out.println("zero");
        }



        System.out.println("End");
    }

}
