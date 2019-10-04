//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.platform.model;

import com.dcits.platform.model.If;
import com.dcits.platform.model.Table;
import java.util.Iterator;
import java.util.List;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class Define {
    String id;
    List<Table> tables;
    private List<If> ifs;

    public Define() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Table> getTables() {
        return this.tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<If> getIfs() {
        return this.ifs;
    }

    public void setIfs(List<If> ifs) {
        this.ifs = ifs;
    }

    public long execute(ExpressionParser parser, StandardEvaluationContext context, int commitCount, boolean isCal) {
        long total = 0L;
        Iterator i$;
        if(this.tables != null && !this.tables.isEmpty()) {
            for(i$ = this.tables.iterator(); i$.hasNext(); ++total) {
                Table ifItem = (Table)i$.next();
                if(!isCal) {
                    ifItem.execute(parser, context, commitCount);
                }
            }
        }

        if(this.ifs != null && !this.ifs.isEmpty()) {
            i$ = this.ifs.iterator();

            while(true) {
                List tables;
                do {
                    do {
                        If var12;
                        do {
                            if(!i$.hasNext()) {
                                return total;
                            }

                            var12 = (If)i$.next();
                        } while(!var12.executeTest(parser, context));

                        tables = var12.getTables();
                    } while(tables == null);
                } while(tables.isEmpty());

                for(Iterator i$1 = tables.iterator(); i$1.hasNext(); ++total) {
                    Table table = (Table)i$1.next();
                    if(!isCal) {
                        table.execute(parser, context, commitCount);
                    }
                }
            }
        } else {
            return total;
        }
    }
}
