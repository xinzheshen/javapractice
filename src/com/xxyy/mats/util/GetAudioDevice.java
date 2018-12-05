package com.xxyy.mats.util;

import org.apache.log4j.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import java.util.ArrayList;

public class GetAudioDevice {

    private static Logger logger = Logger.getLogger(GetAudioDevice.class);
    private String deviceLable1 = null;
    private String deviceLable2 = null;

    public GetAudioDevice(String deviceLable1, String deviceLable2) {
        this.deviceLable1 = deviceLable1;
        this.deviceLable2 = deviceLable2;
    }
    public GetAudioDevice(String deviceLable1) {
        this.deviceLable1 = deviceLable1;
    }

    public ArrayList<Mixer.Info> getAudioDevice(){
        logger.info("===================Get audio device =============");
        ArrayList<Mixer.Info> infos = new ArrayList<Mixer.Info>();

        Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info recordInfo : mixerInfos){
            String deviceName = recordInfo.getName().trim().toLowerCase();
            if ((deviceLable1 != null && deviceName.startsWith(deviceLable1)) ||
                    (deviceLable2 != null && deviceName.startsWith(deviceLable2))) {
                logger.info("Device name : " + deviceName);
                infos.add(recordInfo);
            }
        }
        return infos;
    }



}
