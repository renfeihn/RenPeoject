package com.renfei.example;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.SftpException;
import com.renfei.example.linux.FTPUtil;
import com.renfei.example.linux.SFTPUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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


}
