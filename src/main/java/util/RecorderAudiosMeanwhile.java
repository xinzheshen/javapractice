package util;

import org.apache.log4j.Logger;

import javax.sound.sampled.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RecorderAudiosMeanwhile {
    private static Logger logger = Logger.getLogger(RecorderAudiosMeanwhile.class);

    private CountDownLatch latchStart;
    private CountDownLatch latchTimeOut;

    private ArrayList<Mixer.Info> mixerInfos;
    private AudioFormat audioFormat;
    private Map<String, DoRecord> doRecordMap = new HashMap<>();
    private long timeOut = 15000;
    private String outputFolder;
    private StopRecordWithTimeOut stopRecordWithTimeOut;

    public RecorderAudiosMeanwhile(ArrayList<Mixer.Info> mixerInfos, String outputFolder, long timeOut) {
        this.timeOut = timeOut;
        this.outputFolder = outputFolder;
        this.mixerInfos = mixerInfos;
        this.latchStart = new CountDownLatch(mixerInfos.size());
        this.latchTimeOut = new CountDownLatch(mixerInfos.size());
    }

    public RecorderAudiosMeanwhile(ArrayList<Mixer.Info> mixerInfos, String outputFolder) {
        this.outputFolder = outputFolder;
        this.mixerInfos = mixerInfos;
        this.latchStart = new CountDownLatch(mixerInfos.size());
        this.latchTimeOut = new CountDownLatch(mixerInfos.size());
    }

    public void startRecord() {
        logger.info("===============Prepare for starting record ===============");
        audioFormat = getAudioFormat();
        for (Mixer.Info deviceInfo : mixerInfos) {
            try {
                String deviceName = deviceInfo.getName().trim().toLowerCase();
//                String[] deviceNames = deviceName.split("\\(");
//                String wavFileName = "audio_" + deviceNames[deviceNames.length - 1] + ".wav";
                String wavFileName = outputFolder + File.separator + deviceName + ".wav";
                File audioFile = new File(wavFileName);
                if (audioFile.exists()) {
                    audioFile.delete();
                }
                TargetDataLine targetDataLine = AudioSystem.getTargetDataLine(audioFormat, deviceInfo);

                RecordResult recordResult = new RecordResult(audioFile);
                DoRecord doRecord = new DoRecord(targetDataLine, recordResult);
                doRecordMap.put(deviceName, doRecord);
                doRecord.start();

            } catch (Exception e) {
                logger.error("fail to get data line", e);
            }
        }

        stopRecordWithTimeOut = new StopRecordWithTimeOut();
        stopRecordWithTimeOut.start();
    }

    public void stopRecord() {
        synchronized(this){
            logger.info("=============Prepare for Stopping record ===============");
            for (Map.Entry<String, DoRecord> entry : doRecordMap.entrySet()) {
                entry.getValue().closeDataLine();
            }
            if(stopRecordWithTimeOut.isAlive()){
                stopRecordWithTimeOut.interrupt();
//                logger.info("timeout thread is alive : " + stopRecordWithTimeOut.isAlive());

            }
        }
    }

    private AudioFormat getAudioFormat() {
        // 8000,11025,16000,22050,44100 采样率
        float sampleRate = 16000F;
        // 8,16 每个样本中的位数
        int sampleSizeInBits = 16;
        // 1,2 信道数（单声道为 1，立体声为 2，等等）
        int channels = 1;
        // true,false
        boolean signed = true;
        // true,false 指示是以 big-endian 顺序还是以 little-endian 顺序存储音频数据。
        boolean bigEndian = false;
        //构造具有线性 PCM 编码和给定参数的 AudioFormat。
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }


    class DoRecord extends Thread {
        private TargetDataLine targetDataLine = null;
        private File audioFile = null;
        private RecordResult recordResult = null;
        long startRecordTime = 0;

        public DoRecord(TargetDataLine targetDataLine, RecordResult recordResult) {
            this.targetDataLine = targetDataLine;
            this.recordResult = recordResult;
            this.audioFile = recordResult.getAudioFile();
        }

        public void run() {
            latchStart.countDown();

            AudioFileFormat.Type fileType = null;
            fileType = AudioFileFormat.Type.WAVE;
            try {
                latchStart.await();
                startRecordTime = System.currentTimeMillis();
                logger.info("Start record time : " + startRecordTime);
                recordResult.setStartRecordTime(startRecordTime);

                targetDataLine.open(audioFormat);
                targetDataLine.start();

//                latchStop.countDown();

                AudioSystem.write(new AudioInputStream(targetDataLine), fileType, audioFile);

            } catch (Exception e) {
                logger.error("fail to open voice resource", e);
                recordResult.setErrorMessage("fail to open voice resource");
            } finally {
                logger.info("close dataline in finally");
                closeDataLine();
            }

            logger.info("finished recording");

        }

        public void closeDataLine() {
            if (targetDataLine != null && targetDataLine.isOpen()){
                long stopRecordTime = System.currentTimeMillis();
//                logger.info("stop record time : " + (stopRecordTime));
                targetDataLine.stop();
                targetDataLine.close();
                logger.info("audio duration : " + (stopRecordTime - startRecordTime));
            }
        }
    }

    class StopRecordWithTimeOut extends Thread {
        public void run() {
            try {
                latchTimeOut.await(timeOut, TimeUnit.MILLISECONDS);
                logger.info("================Time out, to stop record===============");
                stopRecord();
            } catch (InterruptedException e) {
                logger.error("The time out thread is interrupted", e);
            }
            logger.info("The time out thread finish");
        }
    }
}
