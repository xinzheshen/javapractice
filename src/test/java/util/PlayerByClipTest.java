package util;

import org.junit.Test;

import javax.sound.sampled.Mixer;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PlayerByClipTest {

    @Test
    public void startPlay() {
        String deviceLable1 = "speakers";

        GetAudioDevice getAudioDevice = new GetAudioDevice(deviceLable1);
        ArrayList<Mixer.Info> audioDevices = getAudioDevice.getAudioDevice();

//        String filePath = "D:\\sxz\\audio\\output\\microphone (ce-link).wav";
        String filePath = "D:\\sxz\\audio\\output\\microphone (3- usb audio device.wav";

        PlayerByClip player = new PlayerByClip(audioDevices, filePath);

        player.startPlay();

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        player.stopPlay();

    }
}