package com.dcits.platform.model;

import com.dcits.platform.model.DBConnection;
import com.dcits.platform.model.TwoTuple;
import com.dcits.platform.utils.ExpressionUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class Table {
    private String name;
    private String connection;
    private Map<String, String> columns;
    private Map<String, String> vars;

    public Table() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnection() {
        return this.connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public Map<String, String> getColumns() {
        return this.columns;
    }

    public void setColumns(Map<String, String> columns) {
        this.columns = columns;
    }

    public Map<String, String> getVars() {
        return this.vars;
    }

    public void setVars(Map<String, String> vars) {
        this.vars = vars;
    }

    public TwoTuple<String, List> getSqlAndArgs(ExpressionParser parser, StandardEvaluationContext context) {
        if(this.vars != null && !this.vars.isEmpty()) {
            Iterator insertSql = this.vars.entrySet().iterator();

            while(insertSql.hasNext()) {
                Entry valueSql = (Entry)insertSql.next();
                Object argsList = ExpressionUtil.getObject(parser, context, (String)valueSql.getValue());
                context.setVariable((String)valueSql.getKey(), argsList);
            }
        }

        StringBuilder insertSql1 = (new StringBuilder("insert into ")).append(this.name).append(" (");
        StringBuilder valueSql1 = new StringBuilder("values (");
        if(this.columns != null && !this.columns.isEmpty()) {
            ArrayList argsList1 = new ArrayList(this.columns.size());
            boolean first = true;
            Iterator sql = this.columns.entrySet().iterator();

            while(sql.hasNext()) {
                Entry entry = (Entry)sql.next();
                Object value = ExpressionUtil.getObject(parser, context, (String)entry.getValue());
                if(value != null) {
                    context.setVariable("_" + (String)entry.getKey(), value);
                    if(first) {
                        first = false;
                        insertSql1.append((String)entry.getKey());
                        valueSql1.append("?");
                    } else {
                        insertSql1.append(",").append((String)entry.getKey());
                        valueSql1.append(",?");
                    }

                    argsList1.add(value);
                }
            }

            String sql1 = insertSql1.append(")").append(valueSql1).append(")").toString();
            return new TwoTuple(sql1, argsList1);
        } else {
            throw new RuntimeException("表" + this.name + "的列为空！");
        }
    }

    public void execute(ExpressionParser parser, StandardEvaluationContext context, int commitCount) {
        DBConnection dbConnection = (DBConnection)ExpressionUtil.getObject(parser, context, this.connection, DBConnection.class);
        String groupExpr = dbConnection.getGroupExpr();
        Object group = null;
        if(groupExpr != null && groupExpr.trim().length() > 0) {
            group = ExpressionUtil.getObject(parser, context, groupExpr);
        }

        if(group == null) {
            group = "1";
        }

        TwoTuple sqlAndArgs = this.getSqlAndArgs(parser, context);
        dbConnection.executeBatch((String)sqlAndArgs.first, (List)sqlAndArgs.second, commitCount, group);
    }
}
