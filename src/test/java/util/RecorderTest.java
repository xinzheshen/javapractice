package util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.Mixer;
import java.util.ArrayList;

public class RecorderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void startRecord() {

        String deviceLable1 = "microphone";
        String deviceLable2 = "jack";


        GetAudioDevice getAudioDevice = new GetAudioDevice(deviceLable1, deviceLable2);
        ArrayList<Mixer.Info> audioDevices = getAudioDevice.getAudioDevice();

        String outputPath = "D:\\sxz\\audio\\output";
        RecorderAudiosMeanwhile recorder = new RecorderAudiosMeanwhile(audioDevices, outputPath);
        recorder.startRecord();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recorder.stopRecord();

    }


    @Test
    public void stopRecord() {
    }
}