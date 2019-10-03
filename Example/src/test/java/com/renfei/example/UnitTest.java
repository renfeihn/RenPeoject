package com.renfei.example;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import com.renfei.example.file.SFTPUtil;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by Renfei on 2019/6/26.
 */
public class UnitTest {


    @Test
    public void upload() {
        ChannelSftp sftp = null;
        Session session = null;
        try {
            session = SFTPUtil.connect("10.9.3.132", 22, "aim", "aim");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            SFTPUtil.upload("/home/aim/logs", "e:/example-1.0.0-SNAPSHOT.jar", sftp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sftp != null) sftp.disconnect();
            if (session != null) session.disconnect();
        }
    }


    @Test
    public void download() {
        ChannelSftp sftp = null;
        Session session = null;
        try {
            session = SFTPUtil.connect("10.9.3.132", 22, "aim", "aim");
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            SFTPUtil.download("/home/aim/logs/", "/home/aim/logs/example-1.0.0-SNAPSHOT.jar", "f:/", sftp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sftp != null) sftp.disconnect();
            if (session != null) session.disconnect();
        }
    }

    public static void main(String[] args) throws IOException {
        String a = "c:\\Log/lva_setupfull_20180813133233_0.log";
        String b = "c:/Log";

        File af = new File(a);
        if(af.isFile()){
            af = new File(af.getParent());

        }
        File bf = new File(b);
        if(bf.isFile()){
            bf = new File(bf.getParent());
        }

        System.out.println(af.getCanonicalPath().equals(bf.getCanonicalPath()));



    }

}
