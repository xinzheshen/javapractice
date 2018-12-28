package com.sxz.study.recordandplay;

import org.apache.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Player {

    private static Logger logger = Logger.getLogger(RecorderAudiosMeanwhile.class);

    private CountDownLatch latchStart;
    private CountDownLatch latchTimeOut;

    private String filename;
    private ArrayList<Mixer.Info> mixerInfos;
    private Map<String, DoPlay> doPlayMap = new HashMap<>();


    public Player(ArrayList<Mixer.Info> mixerInfos, String filename) {
        this.filename = filename;
        this.mixerInfos = mixerInfos;
        this.latchStart = new CountDownLatch(mixerInfos.size());
        this.latchTimeOut = new CountDownLatch(mixerInfos.size());
    }

    public void startPlay() {
        logger.info("===============Prepare for starting play ===============");
        File audioFile = new File(filename);

        for (Mixer.Info deviceInfo : mixerInfos) {
            String deviceName = deviceInfo.getName().trim().toLowerCase();

            DoPlay doPlay = new DoPlay(audioFile, deviceInfo);
            doPlayMap.put(deviceName, doPlay);
            doPlay.start();

        }
    }

    public void stopPlay() {
        logger.info("=============Prepare for Stopping play ===============");
        for (Map.Entry<String, DoPlay> entry : doPlayMap.entrySet()) {
            entry.getValue().closeDataLine();
        }
    }

    class DoPlay extends Thread {

        private File audioFile = null;
        private Mixer.Info playDeviceInfo = null;

        private SourceDataLine sourceDataLine = null;
        private long startPlayTime = 0;

        public DoPlay(File wavFile, Mixer.Info playDeviceInfo) {
            this.audioFile = wavFile;
            this.playDeviceInfo = playDeviceInfo;
        }

        public void run() {

            // 获取音频输入流
            AudioInputStream audioInputStream = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            } catch (Exception e) {
                logger.error("fail to get AudioInputStream", e);
                return;
            }
            // 获取音频编码对象
            AudioFormat audioFormat = audioInputStream.getFormat();
            // 设置数据输入
            try {
                sourceDataLine = AudioSystem.getSourceDataLine(audioFormat, playDeviceInfo);
            } catch (LineUnavailableException e) {
                logger.error("fail to get SourceDataLine", e);
                return;
            }

            latchStart.countDown();
            try {
                latchStart.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            startPlayTime = System.currentTimeMillis();
            logger.info("Start play time : " + startPlayTime);


            try {
                sourceDataLine.open(audioFormat);
            } catch (LineUnavailableException e) {
                logger.error("Fail to open SourceDataLine", e);
                return;
            }

            sourceDataLine.start();
            /*
             * 从输入流中读取数据发送到混音器
             */
            int nBytesRead = 0;
            byte[] abData = new byte[512];

            try {
                while (nBytesRead != -1) {
                    nBytesRead = audioInputStream.read(abData, 0, abData.length);
                    if (nBytesRead >= 0)
                        sourceDataLine.write(abData, 0, nBytesRead);
                }
            } catch (IOException e) {
                logger.error("fail to write data to sourceDataLine", e);
                return;
            } finally {
                closeDataLine();
            }
        }

        public void closeDataLine() {
            if (sourceDataLine != null && sourceDataLine.isOpen()){
                long stopPlayTime = System.currentTimeMillis();
                sourceDataLine.stop();
                sourceDataLine.close();
                logger.info("audio duration : " + (stopPlayTime - startPlayTime));
            }
        }

    }
}
