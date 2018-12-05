package com.xxyy.mats.util;

import org.apache.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Player {

    private static Logger logger = Logger.getLogger(Player.class);

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

    public void StartPlay() {
        logger.info("===============Prepare for starting play ===============");
        File audioFile = new File(filename);

        for (Mixer.Info playDeaviceInfo : mixerInfos) {
            new DoPlay(audioFile, playDeaviceInfo).start();
        }
    }

    class DoPlay extends Thread {

        File audioFile = null;
        Mixer.Info playDeviceInfo = null;

        SourceDataLine sourceDataLine = null;

        AudioInputStream audioInputStream = null;


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
            SourceDataLine sourceDataLine = null;
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

            long startPlayTime = System.currentTimeMillis();
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
                e.printStackTrace();
                return;
            } finally {
                // 清空数据缓冲,并关闭输入
                long stopRecordTime = System.currentTimeMillis();
                logger.info("audio duration : " + (stopRecordTime - startPlayTime));
                sourceDataLine.drain();
                sourceDataLine.close();
            }
        }
    }
}
