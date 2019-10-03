package com.renfei.example.socket;
/**
 * Created by Renfei on 2019/6/26.
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CMDUtil {
    public static String run(String command) throws IOException {
//        List<String> list = new ArrayList();
//
//        list.add("/bin/sh");
//        list.add("-c");
//        list.add(command);
//        return CMDUtil.run(list.toArray(new String[list.size()]));

        List<String> list = new ArrayList();
        list.add(command);
        List<String> resp =  CMDUtil.executeFlow(list);
        StringBuffer sb = new StringBuffer();
        for (String s : resp){
            sb.append(s).append("   ");
        }

        return sb.toString();
    }


    public static String run(String[] command) throws IOException {
        Scanner input = null;
        String result = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            try {
                //等待命令执行完成
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            InputStream is = process.getInputStream();
            input = new Scanner(is);
            while (input.hasNextLine()) {
                result += input.nextLine() + "\n";
            }
//            result = command + ":" + "\n" + result; //加上命令本身，打印出来
        } finally {
            if (input != null) {
                input.close();
            }
            if (process != null) {
                process.destroy();
            }
        }
        return result;
    }

    public static List<String> executeFlow(List<String> commands) {
        List<String> rspList = new ArrayList<String>();
        Runtime run = Runtime.getRuntime();
        try {
            Process proc = run.exec("/bin/bash", null, null);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
            for (String line : commands) {
                out.println(line);
            }
            // out.println("cd /home/test");
            // out.println("pwd");
            // out.println("rm -fr /home/proxy.log");
            out.println("exit");// 这个命令必须执行，否则in流不结束。
            String rspLine = "";
            while ((rspLine = in.readLine()) != null) {
                System.out.println(rspLine);
                rspList.add(rspLine);
            }
            proc.waitFor();
            in.close();
            out.close();
            proc.destroy();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rspList;
    }

    public static void main(String[] args) throws IOException {
        List<String> list = new ArrayList();

        list.add("/bin/sh");
        list.add("-c");
        list.add("ps -ef | grep java");
        String result = CMDUtil.run(list.toArray(new String[list.size()]));
        System.out.println(result);
    }
}
