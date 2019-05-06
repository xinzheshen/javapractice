package com.sxz.study.recordandplay.zhengshan;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;

public class PlayMultiAudiosTask implements Callable<Object> {

    private volatile boolean cancelled = false;

    private CountDownLatch startGate;
    private CountDownLatch endGate;
    private CountDownLatch go = new CountDownLatch(1);
    private File audioFile;
    private Mixer.Info playDeviceInfo;

    private Clip clip = null;
    private float volumeAdjust = 0f;

    public PlayMultiAudiosTask(File wavFile, Mixer.Info playDeviceInfo, float volumeAdjust,
                               CountDownLatch startGate, CountDownLatch endGate) {
        this.audioFile = wavFile;
        this.playDeviceInfo = playDeviceInfo;
        this.volumeAdjust = volumeAdjust;
        this.startGate = startGate;
        this.endGate = endGate;
    }

    @Override
    public Object call() {
        try {
            System.out.println(Thread.currentThread().getName() + " start runTask, " + System.currentTimeMillis());
            runTask();
        } catch (InterruptedException e) {
            System.out.println("get InterruptedException startGate.");
            // ignore
        } finally {
            System.out.println(Thread.currentThread().getName() +
                    " endGate countDown");
            endGate.countDown();
        }
        System.out.println(Thread.currentThread().getName() + " end runTask, " + System.currentTimeMillis());
        return null;
    }

    private void runTask() throws InterruptedException {
        try {
            clip = AudioSystem.getClip(playDeviceInfo);
        } catch (LineUnavailableException e) {
            System.out.println("get LineUnavailableException.");
            return;
        }
        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent e) {
                if (e.getType() == LineEvent.Type.STOP) {
                    System.out.println(Thread.currentThread().getName() +
                            " STOP, clip close." + System.currentTimeMillis());
                    clip.close();
                }
                if (e.getType().equals(LineEvent.Type.START)) {
                    go.countDown();
                    System.out.println(Thread.currentThread().getName() +
                            " START, clip start." + System.currentTimeMillis());
                }
            }
        });
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile)) {
            clip.open(audioInputStream);
        } catch (Exception e) {
            return;
        }
        startGate.await();
        System.out.println(Thread.currentThread().getName() +
                " startGate await");
        clip.start();
        System.out.println("after clip start: " + Thread.currentThread().getName());
        go.await();
        System.out.println("sssss");
        while (clip.isRunning()) {
            // until presentation ceases in response to a call to <code>stop</code> or
            // because playback completes.
            if (cancelled) {
                System.out.println("cancel");
                break;
            }
        }
        clip.stop();
    }

    public void cancell() {
        this.cancelled = true;
    }
}
