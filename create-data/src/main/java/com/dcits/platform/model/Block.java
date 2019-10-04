//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.platform.model;

import com.dcits.platform.model.For;
import com.dcits.platform.model.Import;
import com.dcits.platform.utils.CommitCount;
import java.util.Iterator;
import java.util.List;

public class Block {
    private String id;
    private String desc;
    private List<For> forList;

    public Block() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<For> getForList() {
        return this.forList;
    }

    public void setForList(List<For> forList) {
        this.forList = forList;
    }

    public void execute(Import im) {
        System.out.println("造数中......当前块为：" + this.getId() + " " + this.getDesc());
        System.out.print("造数中......开始计算总数...");
        long start = System.currentTimeMillis();
        long total = this.cal(im);
        System.out.println("总数为：" + total + " 耗时：" + (System.currentTimeMillis() - start));
        CommitCount.init(total);
        if(this.forList != null && !this.forList.isEmpty()) {
            Iterator i$ = this.forList.iterator();

            while(i$.hasNext()) {
                For forItem = (For)i$.next();
                forItem.execute(im, false);
            }
        }

        System.out.println();
    }

    public long cal(Import im) {
        long total = 0L;
        For forItem;
        if(this.forList != null && !this.forList.isEmpty()) {
            for(Iterator i$ = this.forList.iterator(); i$.hasNext(); total += forItem.execute(im, true)) {
                forItem = (For)i$.next();
            }
        }

        return total;
    }
}
