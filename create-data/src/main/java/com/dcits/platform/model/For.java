package com.dcits.platform.model;

import com.dcits.platform.model.DBConnection;
import com.dcits.platform.model.Define;
import com.dcits.platform.model.If;
import com.dcits.platform.model.Import;
import com.dcits.platform.model.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class For {
    private String start;
    private String end;
    private String step;
    private String index;
    private String thread;
    private String commit;
    private List<Table> tables;
    private List<If> ifs;
    private List<For> forList;
    private List<Define> defines;
    private Map<String, Object> constMap;

    public For() {
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStep() {
        return this.step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getThread() {
        return this.thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getCommit() {
        return this.commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
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

    public List<For> getForList() {
        return this.forList;
    }

    public void setForList(List<For> forList) {
        this.forList = forList;
    }

    public List<Define> getDefines() {
        return this.defines;
    }

    public void setDefines(List<Define> defines) {
        this.defines = defines;
    }

    public Map<String, Object> getConstMap() {
        return this.constMap;
    }

    public void setConstMap(Map<String, Object> constMap) {
        this.constMap = constMap;
    }

    public long execute(long start, long end, long step, int commitCount, Import im, boolean isCal) {
        return this.execute(start, end, step, commitCount, im, (StandardEvaluationContext)null, isCal);
    }

    public long execute(long start, long end, long step, int commitCount, Import im, StandardEvaluationContext ctx, boolean isCal) {
        StandardEvaluationContext context = null;
        if(ctx == null) {
            HashMap parser = new HashMap();
            parser.put("dbs", im.getDbs());
            parser.put("dbMap", im.getDbMap());
            context = new StandardEvaluationContext(parser);
            HashMap total = new HashMap();
            if(im.getConstMap() != null && !im.getConstMap().isEmpty()) {
                total.putAll(im.getConstMap());
            }

            if(this.constMap != null && !this.constMap.isEmpty()) {
                total.putAll(this.constMap);
            }

            context.setVariables(total);
        } else {
            context = new StandardEvaluationContext(ctx.getRootObject().getValue());

            try {
                Field parser1 = StandardEvaluationContext.class.getDeclaredField("variables");
                parser1.setAccessible(true);
                Map total1 = (Map)parser1.get(ctx);
                if(this.constMap != null && !this.constMap.isEmpty()) {
                    total1.putAll(this.constMap);
                }

                context.setVariables(total1);
            } catch (Exception var19) {
                var19.printStackTrace();
            }
        }

        SpelExpressionParser parser2 = new SpelExpressionParser();
        long total2 = 0L;

        for(long i = start; i <= end; i += step) {
            context.setVariable(this.index, Long.valueOf(i));
            total2 += this.executeTable(this.tables, parser2, context, commitCount, isCal);
            total2 += this.executeDefine(this.defines, parser2, context, commitCount, isCal);
            Iterator i$;
            if(this.ifs != null && !this.ifs.isEmpty()) {
                i$ = this.ifs.iterator();

                while(i$.hasNext()) {
                    If forItem = (If)i$.next();
                    if(forItem.executeTest(parser2, context)) {
                        total2 += this.executeTable(forItem.getTables(), parser2, context, commitCount, isCal);
                        total2 += this.executeDefine(forItem.getDefines(), parser2, context, commitCount, isCal);
                    }
                }
            }

            For forItem1;
            if(this.forList != null && !this.forList.isEmpty()) {
                for(i$ = this.forList.iterator(); i$.hasNext(); total2 += forItem1.execute(im, context, isCal)) {
                    forItem1 = (For)i$.next();
                    forItem1.setCommit(this.commit);
                }
            }
        }

        return total2;
    }

    public long executeDefine(List<Define> defines, ExpressionParser parser, StandardEvaluationContext context, int commitCount, boolean isCal) {
        long total = 0L;
        Define define;
        if(defines != null && !defines.isEmpty()) {
            for(Iterator i$ = defines.iterator(); i$.hasNext(); total += define.execute(parser, context, commitCount, isCal)) {
                define = (Define)i$.next();
            }
        }

        return total;
    }

    public long executeTable(List<Table> tables, ExpressionParser parser, StandardEvaluationContext context, int commitCount, boolean isCal) {
        long total = 0L;
        if(tables != null && !tables.isEmpty()) {
            Iterator i$ = tables.iterator();

            while(i$.hasNext()) {
                Table table = (Table)i$.next();
                ++total;
                if(!isCal) {
                    table.execute(parser, context, commitCount);
                }
            }
        }

        return total;
    }

    public long execute(Import im, boolean isCal) {
        return this.execute(im, (StandardEvaluationContext)null, isCal);
    }

    public long execute(final Import im, final StandardEvaluationContext context, final boolean isCal) {
        long total = 0L;
        long start = Long.parseLong(this.start);
        final long end = Long.parseLong(this.end);
        long step;
        if(this.step != null && !this.step.isEmpty()) {
            step = Long.parseLong(this.step);
        } else {
            step = 1L;
        }

        int threadCount = 1;
        if(this.thread != null && !this.thread.isEmpty()) {
            threadCount = Integer.parseInt(this.thread);
        }

        final int commitCount = Integer.parseInt(this.commit);
        if(threadCount == 1) {
            total = this.execute(start, end, step, commitCount, im, context, isCal);
            if(!isCal && context == null) {
                Iterator queue = im.getDbs().iterator();

                while(queue.hasNext()) {
                    DBConnection executorService = (DBConnection)queue.next();
                    executorService.commitAll();
                }
            }
        } else if(threadCount > 1) {
            LinkedBlockingQueue var29 = new LinkedBlockingQueue();
            ThreadPoolExecutor var30 = new ThreadPoolExecutor(threadCount, threadCount, 60L, TimeUnit.SECONDS, var29);
            final CountDownLatch latch = new CountDownLatch(threadCount);
            ArrayList futures = new ArrayList();

            for(int i$ = 0; i$ < threadCount; ++i$) {
                final long future = start + (long)i$ * step;
                final long realStep = step * (long)threadCount;
                Callable callable = new Callable() {
                    public Long call() throws Exception {
                        Long var2;
                        try {
                            long e = For.this.execute(future, end, realStep, commitCount, im, context, isCal);
                            if(!isCal) {
                                Iterator i$ = im.getDbs().iterator();

                                while(i$.hasNext()) {
                                    DBConnection dbConnection = (DBConnection)i$.next();
                                    dbConnection.commitAll();
                                }
                            }

                            Long i$1 = Long.valueOf(e);
                            return i$1;
                        } catch (Exception var8) {
                            var8.printStackTrace();
                            var2 = Long.valueOf(0L);
                        } finally {
                            latch.countDown();
                        }

                        return var2;
                    }
                };
                futures.add(var30.submit(callable));
            }

            try {
                latch.await();
            } catch (InterruptedException var28) {
                var28.printStackTrace();
                return total;
            }

            var30.shutdown();
            Iterator var31 = futures.iterator();

            while(var31.hasNext()) {
                Future var32 = (Future)var31.next();
                if(var32 != null) {
                    try {
                        total += ((Long)var32.get()).longValue();
                    } catch (InterruptedException var26) {
                        var26.printStackTrace();
                    } catch (ExecutionException var27) {
                        var27.printStackTrace();
                    }
                }
            }
        }

        return total;
    }
}
