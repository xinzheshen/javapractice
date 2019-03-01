package com.sxz.study.io;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class FileReaderTest {
    public static void main(String[] args) throws Exception{
        URL url = FileReaderTest.class.getClassLoader().getResource("a.txt");
        String fileStr = url.getFile();
        System.out.println(fileStr);
        File file = new File(fileStr);

        try(FileReader fr = new FileReader(file.getAbsolutePath())){
            char[] cbuf = new char[32];

            int hasRead = 0;
            while((hasRead = fr.read(cbuf)) > 0){
                System.out.print(new String(cbuf));
            }
        }

    }


}
