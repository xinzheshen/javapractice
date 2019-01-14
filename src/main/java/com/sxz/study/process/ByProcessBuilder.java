package com.sxz.study.process;

import java.util.Scanner;

/***
 * https://blog.csdn.net/Thousa_Ho/article/details/78052025
 *
 * cmd /c dir 是执行完dir命令后关闭命令窗口。
 *
 * cmd /k dir 是执行完dir命令后不关闭命令窗口。
 *
 * cmd /c start dir 会打开一个新窗口后执行dir指令，原窗口会关闭。
 *
 * cmd /k start dir 会打开一个新窗口后执行dir指令，原窗口不会关闭。
 */
public class ByProcessBuilder {
    public static void main(String[] args) throws Exception{
        ProcessBuilder pb = new ProcessBuilder("cmd","/c","ipconfig/all");
        Process process = pb.start();
        Scanner scanner = new Scanner(process.getInputStream());

        while(scanner.hasNextLine()){
            System.out.println(scanner.nextLine());
        }
        scanner.close();
    }
}
