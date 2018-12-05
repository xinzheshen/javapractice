package com.xxyy.mats.util;

import java.io.File;

public class RecordResult {
    private File audioFile;
    private long startRecordTime;
    private String errorMessage = "";

    public RecordResult(File audioFile){
        this.audioFile = audioFile;
    }

    public File getAudioFile() {
        return audioFile;
    }

    public long getStartRecordTime() {
        return startRecordTime;
    }

    public void setStartRecordTime(long startRecordTime) {
        this.startRecordTime = startRecordTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
