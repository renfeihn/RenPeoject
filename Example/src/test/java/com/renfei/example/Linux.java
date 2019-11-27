package com.renfei.example;

import com.jcraft.jsch.SftpException;
import com.renfei.example.linux.FTPUtil;
import com.renfei.example.linux.SFTPUtil;
import org.apache.arrow.flatbuf.Bool;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Renfei on 2019/7/13.
 */
public class Linux {


    @Test
    public void sftp() throws FileNotFoundException, SftpException {

        SFTPUtil sftp = new SFTPUtil("renfei", "renfei", "192.168.144.130", 22);
        sftp.login();
        sftp.upload("/home/renfei/app", "E:\\renfei\\test.txt");

    }


    @Test
    public void ftp() throws IOException, SftpException {
        FTPUtil ftpUtil = FTPUtil.createFtpCli("192.168.144.130", "renfei", "renfei");
        ftpUtil.connect();
        ftpUtil.upload("/home/renfei/app", "E:\\renfei\\test.txt");

    }


    @Test
    public void file() throws IOException, SftpException {
        File file = new File("E:\\renfei\\test.txt");
        System.out.println(file.getName());
    }


    @Test
    public void testInt() {
//        Integer a = 127;
//        Integer b = 127;
//        Integer c = 128;
//        Integer d = 128;
//
//        System.out.println("a == b : " + (a == b));
//        System.out.println("c == d : " + (c == d));
//
//        Long e = 127l;
//        Long f = 127l;
//        Long g = 128l;
//        Long h = 128l;
//
//        System.out.println("e == f : " + (e == f));
//        System.out.println("g == h : " + (g == h));

        double d = 435_936.445_667;
        System.out.println(d);

        List list;

        Map map;






    }

}
