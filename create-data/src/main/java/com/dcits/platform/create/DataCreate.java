//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.platform.create;

import com.dcits.platform.Main.Command;
import com.dcits.platform.model.Block;
import com.dcits.platform.model.Import;
import com.dcits.platform.model.Truncate;
import com.dcits.platform.utils.Tools;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DataCreate {
    public DataCreate() {
    }

    public void create(Import im, Command command) {
        Map sequences = im.getSequences();
        if(sequences != null && !sequences.isEmpty()) {
            Iterator blockIds = sequences.entrySet().iterator();

            while(blockIds.hasNext()) {
                Entry i$ = (Entry)blockIds.next();
                String block = (String)i$.getKey();
                String arr$ = (String)i$.getValue();
                long len$ = Long.parseLong(arr$);
                Tools.setInitValue(block, len$);
            }
        }

        String[] var11 = command.blockId;
        System.out.println("造数中...当前文件为：" + im.getFileName() + " id为:" + im.getId() + " " + im.getDesc());
        Iterator var12;
        if(im.getTruncates() != null && !im.getTruncates().isEmpty() && !"n".equals(command.truncate) && !"N".equals(command.truncate)) {
            System.out.println("造数中......开始清除数据...");
            var12 = im.getTruncates().iterator();

            while(var12.hasNext()) {
                Truncate var13 = (Truncate)var12.next();
                var13.truncate(im.getDbs());
            }

            System.out.println("造数中......清除数据完成...");
        }

        if(im.getBlocks() != null && !im.getBlocks().isEmpty()) {
            var12 = im.getBlocks().iterator();

            while(true) {
                while(var12.hasNext()) {
                    Block var14 = (Block)var12.next();
                    if(var11 != null && var11.length > 0) {
                        String[] var15 = var11;
                        int var16 = var11.length;

                        for(int i$1 = 0; i$1 < var16; ++i$1) {
                            String blockId = var15[i$1];
                            if(blockId != null && blockId.equals(var14.getId())) {
                                var14.execute(im);
                            }
                        }
                    } else {
                        var14.execute(im);
                    }
                }

                return;
            }
        }
    }
}
