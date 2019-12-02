package com.dcits.platform.model;

import com.dcits.platform.model.DBConnection;
import com.dcits.platform.utils.ExpressionUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class Truncate {
    private String tableName;
    private String condition;
    private String connection;

    public Truncate() {
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConnection() {
        return this.connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public void truncate(List<DBConnection> dbs) {
        System.out.print("造数中......清除表" + this.tableName + "...");
        String truncateSql;
        if(this.condition != null && this.condition.trim().length() > 0) {
            truncateSql = "delete from " + this.tableName + " " + this.condition;
        } else {
            truncateSql = "truncate table " + this.tableName;
        }

        if(this.connection != null && !this.connection.trim().isEmpty()) {
            HashMap map1 = new HashMap();
            map1.put("dbs", dbs);
            StandardEvaluationContext context1 = new StandardEvaluationContext(map1);
            SpelExpressionParser parser = new SpelExpressionParser();
            DBConnection dbConnection = (DBConnection)ExpressionUtil.getObject(parser, context1, this.connection, DBConnection.class);
            dbConnection.executeSql(truncateSql);
        } else {
            Iterator map = dbs.iterator();

            while(map.hasNext()) {
                DBConnection context = (DBConnection)map.next();
                context.executeSql(truncateSql);
            }
        }

        System.out.println(" 完成...");
    }
}
