package com.cienet.mats.util;

import org.apache.log4j.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.util.ArrayList;

public class GetAudioDevice {
    private static Logger logger = Logger.getLogger(GetAudioDevice.class);
    private String deviceLable1 = "microphone";
    private String deviceLable2 = "jack";

    public ArrayList<Mixer.Info> getAudioDevice(){
        logger.info("===================Get audio device =============");
        ArrayList<Mixer.Info> infos = new ArrayList<Mixer.Info>();

        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info recordInfo : mixerInfos){
            String deviceName = recordInfo.getName().trim().toLowerCase();
            if (deviceName.startsWith(deviceLable1) || deviceName.startsWith(deviceLable2)) {
                logger.info("Device name : " + deviceName);
                infos.add(recordInfo);
            }
        }
        return infos;
    }



}
