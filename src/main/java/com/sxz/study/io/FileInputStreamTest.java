package com.sxz.study.io;

import java.io.InputStream;
import java.net.URL;

/**
 * https://www.cnblogs.com/lakeslove/p/6143568.html
 */
public class FileInputStreamTest {

    public static void main(String[] args) throws Exception{


        URL url = FileInputStreamTest.class.getClassLoader().getResource("a.txt");
        System.out.println("url: " + url);
        // normal
//        File file = new File(url.getFile());
//        FileInputStream is = new FileInputStream(file.getAbsolutePath());
        //或者 FileInputStream is = new FileInputStream(file);

        //或者 直接打开输入流
//        InputStream is = url.openStream();

        //或者 直接将文件转为流
        InputStream is = FileInputStreamTest.class.getClassLoader().getResourceAsStream("a.txt");
        //InputStream is = this.getClass().getResourceAsStream("a.txt");

        byte[] bbuf = new byte[1024];

        int hasRead = 0;

        while((hasRead = is.read(bbuf)) > 0){
            System.out.println(new String(bbuf, 0, hasRead));
        }
        is.close();


        //


    }

}
