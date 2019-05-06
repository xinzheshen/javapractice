package com.sxz.study.recordandplay;

import org.apache.log4j.Logger;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class RecorderAudiosMeanwhileByFFmpeg {
    private static Logger logger = Logger.getLogger(RecorderAudiosMeanwhileByFFmpeg.class);

    private CountDownLatch latchStart;
    private CountDownLatch latchTimeOut;
    private CyclicBarrier startBarrier;


    private ArrayList<String> mixerInfos;
    private AudioFormat audioFormat;
    private Map<String, DoRecord> doRecordMap = new HashMap<>();
    private long timeOut = 15000;
    private String outputFolder;

    public RecorderAudiosMeanwhileByFFmpeg(ArrayList<String> mixerInfos, String outputFolder, long timeOut) {
        this.timeOut = timeOut;
        this.outputFolder = outputFolder;
        this.mixerInfos = mixerInfos;
        this.latchStart = new CountDownLatch(mixerInfos.size());
        this.latchTimeOut = new CountDownLatch(mixerInfos.size());
        this.startBarrier = new CyclicBarrier(mixerInfos.size());
    }

    public RecorderAudiosMeanwhileByFFmpeg(ArrayList<String> mixerInfos, String outputFolder) {
        this.outputFolder = outputFolder;
        this.mixerInfos = mixerInfos;
        this.latchStart = new CountDownLatch(mixerInfos.size());
        this.latchTimeOut = new CountDownLatch(mixerInfos.size());
        this.startBarrier = new CyclicBarrier(mixerInfos.size());
    }

    public void startRecord() {
        logger.info("===============Prepare for starting record ===============");
        for (String deviceInfo : mixerInfos) {
            try {
                String wavFileName = outputFolder + File.separator + deviceInfo.trim().replace(" ", "") + ".wav";
                File audioFile = new File(wavFileName);
                if (audioFile.exists()) {
                    audioFile.delete();
                }
                DoRecord doRecord = new DoRecord(deviceInfo, wavFileName);
                doRecordMap.put(deviceInfo, doRecord);
                doRecord.start();

            } catch (Exception e) {
                logger.error("fail to start record audio", e);
            }
        }

    }

    public void stopRecord() {
        synchronized (this) {
            logger.info("=============Prepare for Stopping record ===============");
            for (Map.Entry<String, DoRecord> entry : doRecordMap.entrySet()) {
                entry.getValue().stopRecodingProcess();
            }
        }
    }

    class DoRecord extends Thread {
        long startRecordTime = 0;
        private String micInfo;
        private String output;
        private Process process;
        private ArrayList<String> errorOutput = new ArrayList<>();


        public DoRecord(String micInfo, String output) {
            this.micInfo = micInfo;
            this.output = output;
        }

        @Override
        public void run() {
            String cmd = "D:\\sxz\\ffmpeg-3.1.5-win64-static\\bin\\ffmpeg -f dshow -i audio=\"" + micInfo + "\" -ar 16000.0 -ac 1 -af silencedetect=noise=-50dB:d=1 " + output;
            int exitValue = 0;
            logger.info("Record cmd is: " + cmd);
            try {
                startBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            logger.info("start time: " + System.currentTimeMillis());
            try {
                watchOutput(Runtime.getRuntime().exec(cmd));
            } catch (IOException e) {
                logger.error("execute error: " + cmd, e);
            }
            try {
                exitValue = process.waitFor();
                System.out.println("exitValue = " + exitValue);
            } catch (InterruptedException e) {
                logger.warn("Interrupt the recording audio card:" + micInfo, e);
            } catch (Exception e) {
                logger.error(micInfo + "record error", e);
            } finally {
                stopRecodingProcess();
            }
            logger.info("finished recording");

        }

        public void stopRecodingProcess() {
            logger.debug("enter stopRecodingProcess");
            if (process.isAlive()) {
                logger.debug("prepare to stop the process");
                OutputStream os = process.getOutputStream();
                try {
                    os.write("q\n".getBytes());
                    os.flush();
                } catch (IOException e) {
                    logger.error("Fail to stop the recording process", e);
                }
            }
        }

        private void watchOutput(Process process){
            this.process = process;
            new Thread(){
                @Override
                public void run(){
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                    try {
                        while (true) {
                            String line = bufferedReader.readLine();
                            if (line != null ){
                                errorOutput.add(line);
                            } else {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.debug(String.join("\n",errorOutput));
                }
            }.start();
        }
    }
}
