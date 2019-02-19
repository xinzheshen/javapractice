package com.sxz.study.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ByRuntime {

    public static void main(String[] args) throws Exception {
//        String cmd = "cmd "+"/c "+"ipconfig/all";
        String cmd = "ifconfig";
        Process process = Runtime.getRuntime().exec(cmd);

        int exitValue = process.waitFor();

//        Scanner scanner = new Scanner(process.getInputStream());
//
//        while(scanner.hasNextLine()){
//            System.out.println(scanner.nextLine());
//        }
//        scanner.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        while ((line = br.readLine()) != null){
            System.out.println(line);
        }


        System.out.println("exitValue = " + exitValue);
    }
}
