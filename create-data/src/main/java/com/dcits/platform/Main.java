package com.dcits.platform;

import com.dcits.platform.config.ReadFile;
import com.dcits.platform.create.DataCreate;
import com.dcits.platform.filter.XmlFilter;
import com.dcits.platform.model.Import;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {


    public static void main(String[] args) throws Exception {
        Main.Command command = new Main.Command();
        String path = "";
        if (args != null && args.length > 0) {
            for (int start = 0; start < args.length - 1; ++start) {
                String arg = args[start];
                if ("-i".equals(arg)) {
                    ++start;
                    path = args[start];
                    if (path != null && path.trim().length() > 0) {
                        command.importId = path.split(",");
                    }
                } else if ("-b".equals(arg)) {
                    ++start;
                    path = args[start];
                    if (path != null && path.trim().length() > 0) {
                        command.blockId = path.split(",");
                    }
                } else if ("-t".equals(arg)) {
                    ++start;
                    command.truncate = args[start];
                }
            }
        }

        long var15 = System.currentTimeMillis();
        path = Class.class.getClass().getResource("/").getPath();

        File filePath = new File(URLDecoder.decode(path, "utf-8"));
        File[] files = filePath.listFiles(new XmlFilter());
        if (files != null && files.length != 0) {
            ArrayList imports = new ArrayList();
            File[] dataCreate = files;
            int i$ = files.length;

            for (int im = 0; im < i$; ++im) {
                File arr$ = dataCreate[im];
                FileInputStream len$ = new FileInputStream(arr$);
                ReadFile i$1 = new ReadFile();
                Import imId = i$1.read(len$);
                imId.setFileName(arr$.getName());
                imports.add(imId);
            }

            if (imports.isEmpty()) {
                System.out.println("conf下没有造数配置文件");
            }

            System.out.println("造数开始");
            DataCreate var16 = new DataCreate();
            Iterator var17 = imports.iterator();

            while (true) {
                while (var17.hasNext()) {
                    Import var18 = (Import) var17.next();
                    if (command.importId != null && command.importId.length > 0) {
                        String[] var19 = command.importId;
                        int var20 = var19.length;

                        for (int var21 = 0; var21 < var20; ++var21) {
                            String var22 = var19[var21];
                            if (var22 != null && var22.equals(var18.getId())) {
                                var16.create(var18, command);
                            }
                        }
                    } else {
                        var16.create(var18, command);
                    }
                }

                System.out.println("造数完成,耗时 ：" + (System.currentTimeMillis() - var15));
                return;
            }
        } else {
            System.out.println("conf下没有xml文件");
        }
    }

    public static class Command {
        public String[] importId;
        public String[] blockId;
        public String truncate;

        public Command() {
        }
    }
}
