package util;

import org.apache.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class PlayerByClip {
    private static Logger logger = Logger.getLogger(RecorderAudiosMeanwhile.class);

    private CountDownLatch latchStart;
    private CountDownLatch latchTimeOut;
    private CyclicBarrier startBarrier;

    private String filename;
    private ArrayList<Mixer.Info> mixerInfos;
    private Map<String, PlayerByClip.DoPlay> doPlayMap = new HashMap<>();


    public PlayerByClip(ArrayList<Mixer.Info> mixerInfos, String filename) {
        this.filename = filename;
        this.mixerInfos = mixerInfos;
        this.latchStart = new CountDownLatch(mixerInfos.size());
        this.latchTimeOut = new CountDownLatch(mixerInfos.size());
        this.startBarrier = new CyclicBarrier(mixerInfos.size());
    }

    public void startPlay() {
        logger.info("===============Prepare for starting play ===============");
        File audioFile = new File(filename);

        for (Mixer.Info deviceInfo : mixerInfos) {
            String deviceName = deviceInfo.getName().trim().toLowerCase();

            float volumeAdjust =  (float)Math.random()*(Math.random()>0.5?1:-1)*10;
            System.out.println("volumeAdjust : " + volumeAdjust);

            PlayerByClip.DoPlay doPlay = new PlayerByClip.DoPlay(audioFile, deviceInfo, volumeAdjust);
            doPlayMap.put(deviceName, doPlay);
            doPlay.start();
        }

        logger.info("start play end");
    }

    public void stopPlay() {
        logger.info("=============Prepare for Stopping play ===============");
        for (Map.Entry<String, PlayerByClip.DoPlay> entry : doPlayMap.entrySet()) {
            entry.getValue().closeDataLine();
        }
    }

    class DoPlay extends Thread {

        private File audioFile = null;
        private Mixer.Info playDeviceInfo = null;

        private Clip clip = null;
        private long startPlayTime = 0;
        // 如果音量减小10分贝， volumeAdjust 为 -10f，如果增加10分贝， 为 10f
        private float volumeAdjust = 0f;

        public DoPlay(File wavFile, Mixer.Info playDeviceInfo, float volumeAdjust) {
            this.audioFile = wavFile;
            this.playDeviceInfo = playDeviceInfo;
            this.volumeAdjust = volumeAdjust;
        }

        public void run() {

            logger.info("run");

            try {
                clip = AudioSystem.getClip(playDeviceInfo);
            } catch (LineUnavailableException e) {
                logger.error("fail to get clip", e);
                return;
            }

            clip.addLineListener(new LineListener() {
                public void update(LineEvent e) {
                    if (e.getType() == LineEvent.Type.STOP) {
                        synchronized(clip) {
                            clip.notify();
                        }
                    }
                }
            });


            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile)){
                clip.open(audioInputStream);
            } catch (Exception e) {
                logger.error("fail to open clip", e);
                return;
            }

            float level1 = clip.getLevel();
            logger.info("level-1 :" + level1);
//            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float minVolume = gainControl.getMinimum();
            float maxVolume = gainControl.getMaximum();
            logger.info("volume before adjusted : "+ gainControl.getValue());
            System.out.println("min volume before adjusted : "+ minVolume);
            System.out.println("max volume before adjusted : "+ maxVolume);
            if(volumeAdjust > maxVolume){
                volumeAdjust = maxVolume;
            }
            else if (volumeAdjust < minVolume){
                volumeAdjust = minVolume;
            }
            // adjust the audio volume

            gainControl.setValue(-5f);
            logger.info("volume after adjusted : "+ gainControl.getValue());
            float level2 = clip.getLevel();
            logger.info("level-2 :" + level2);

//            latchStart.countDown();
//            try {
//                latchStart.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            try {
                startBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            startPlayTime = System.currentTimeMillis();
            logger.info("Start play time : " + startPlayTime);

            clip.start();
            synchronized (clip) {
                try {
                    clip.wait();
                } catch (InterruptedException e) {
                    logger.info("clip is interrupted");
                    e.printStackTrace();
                }
            }
            logger.info("over");
            clip.close();

        }

        public void closeDataLine() {
            if (clip.isRunning()){
                long stopPlayTime = System.currentTimeMillis();
                clip.stop();
                clip.close();
                logger.info("audio duration : " + (stopPlayTime - startPlayTime));
            }
        }
    }

}
