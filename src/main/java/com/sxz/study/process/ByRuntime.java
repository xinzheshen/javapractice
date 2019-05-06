package com.sxz.study.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Scanner;

public class ByRuntime {

    public static void main(String[] args) throws Exception {
//        String cmd = "cmd "+"/c "+"ipconfig/all";
////        String cmd = "ifconfig";
//        Process process = Runtime.getRuntime().exec(cmd);

        // 测试录音
//        String cmd = "D:\\sxz\\ffmpeg-3.1.5-win64-static\\bin\\ffmpeg -f dshow -i audio=\"Microphone (2- CE-LINK)\" -ar 16000 -ac 1 -t 00:00:05 D:\\sxz\\ffmpeg-3.1.5-win64-static\\bin\\out\\test6.wav";
//        Process process = Runtime.getRuntime().exec(cmd);
//
//        int exitValue = process.waitFor();
        new Thread(() -> {
            String cmd1 = "D:\\sxz\\ffmpeg-3.1.5-win64-static\\bin\\ffmpeg -f dshow -i audio=\"Microphone (2- CE-LINK)\" -ar 16000 -ac 1 D:\\sxz\\audio\\output\\test7.wav";
            try {
                Process process1 = Runtime.getRuntime().exec(cmd1);
                Thread.sleep(5000);
                OutputStream os = process1.getOutputStream();
                os.write("q\n".getBytes());
                os.flush();
                if(process1.isAlive()){
                    System.out.println("kill process");
//                    process1.destroy();
                }
//                process1.destroy();
                int exitValue1 = process1.waitFor();
                System.out.println("exitValue1 = " + exitValue1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            String cmd1 = "D:\\sxz\\ffmpeg-3.1.5-win64-static\\bin\\ffmpeg -f dshow -i audio=\"Jack Mic (Realtek Audio)\" -ar 16000 -ac 1 -t 00:00:10 D:\\sxz\\audio\\output\\test9.wav";
            try {
                Process process1 = Runtime.getRuntime().exec(cmd1);
                int exitValue1 = process1.waitFor();
                System.out.println("exitValue2 = " + exitValue1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

//        Scanner scanner = new Scanner(process.getInputStream());
//
//        while(scanner.hasNextLine()){
//            System.out.println(scanner.nextLine());
//        }
//        scanner.close();

//        BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//        String line = null;
//        while ((line = br.readLine()) != null){
//            System.out.println(line);
//        }
//
//
//        System.out.println("exitValue = " + exitValue);
//        // 测试录音
//        String cmd = "D:\\sxz\\ffmpeg-3.1.5-win64-static\\bin\\ffmpeg -f dshow -i audio=\"Microphone (2- CE-LINK)\" -ar 16000 -ac 1 -t 00:00:05 D:\\sxz\\ffmpeg-3.1.5-win64-static\\bin\\out\\test4.wav";
//        Process process = Runtime.getRuntime().exec(cmd);
//        process
//        Thread.sleep(5000);
//        process.destroy();
        System.out.println("end");
    }
}
