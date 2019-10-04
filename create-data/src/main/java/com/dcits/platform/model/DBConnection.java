//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.platform.model;

import com.dcits.platform.utils.CommitCount;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DBConnection {
    private ThreadLocal<Connection> currentConnection = new ThreadLocal();
    private ThreadLocal<Map> currentMap = new ThreadLocal();
    private String id;
    private String url;
    private String username;
    private String password;
    private String driverClass;
    private String groupExpr;

    public DBConnection() {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClass() {
        return this.driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupExpr() {
        return this.groupExpr;
    }

    public void setGroupExpr(String groupExpr) {
        this.groupExpr = groupExpr;
    }

    private boolean testConnection(Connection conn) {
        if(conn == null) {
            return false;
        } else {
            try {
                Statement e = conn.createStatement();
                e.close();
                return true;
            } catch (Exception var3) {
                return false;
            }
        }
    }

    public Connection getConnection() {
        Connection conn = (Connection)this.currentConnection.get();
        if(this.testConnection(conn)) {
            return conn;
        } else {
            conn = null;

            try {
                Class.forName(this.driverClass);

                while(conn == null) {
                    try {
                        conn = DriverManager.getConnection(this.url, this.username, this.password);
                    } catch (Exception var3) {
                        conn = null;
                        System.out.print(".");
                        Thread.sleep(200L);
                    }
                }

                this.currentConnection.set(conn);
                return conn;
            } catch (Exception var4) {
                var4.printStackTrace();
                return null;
            }
        }
    }

    public void executeSql(String sql) {
        Statement statement = null;

        try {
            this.getConnection().setAutoCommit(true);
            statement = this.getConnection().createStatement();
            statement.execute(sql);
            statement.close();
            statement = null;
        } catch (SQLException var12) {
            var12.printStackTrace();
        } finally {
            if(statement != null) {
                try {
                    statement.close();
                } catch (SQLException var11) {
                    var11.printStackTrace();
                }
            }

        }

    }

    public void executeBatch(String sql, List args, int commitCount, Object group) {
        Map cacheMap = (Map)this.currentMap.get();
        if(cacheMap == null) {
            cacheMap = new HashMap();
            this.currentMap.set((Map) cacheMap);
        }

        Object argsMap = (Map)((Map)cacheMap).get(sql);
        if(argsMap == null) {
            argsMap = new HashMap();
            ((Map)cacheMap).put(sql, argsMap);
        }

        Object argsList = (List)((Map)argsMap).get(group);
        if(argsList == null) {
            argsList = new ArrayList();
            ((Map)argsMap).put(group, argsList);
        }

        ((List)argsList).add(args);
        if(((List)argsList).size() >= commitCount) {
            this.commit(sql, (List)argsList);
            ((List)argsList).clear();
        }

    }

    public void commit(String sql, List<List> argsList) {
        if(argsList != null && !argsList.isEmpty()) {
            int retry = 12;

            while(retry > 0) {
                PreparedStatement ps = null;
                Connection connection = this.getConnection();

                try {
                    connection.setAutoCommit(false);
                    ps = connection.prepareStatement(sql);
                    Iterator e = argsList.iterator();

                    while(e.hasNext()) {
                        List e1 = (List)e.next();
                        int i = 1;
                        Iterator i$ = e1.iterator();

                        while(i$.hasNext()) {
                            Object arg = i$.next();
                            ps.setObject(i++, arg);
                        }

                        ps.addBatch();
                    }

                    ps.executeBatch();
                    connection.commit();
                    retry = 0;
                    connection.setAutoCommit(true);
                    CommitCount.commit((long)argsList.size());
                } catch (Exception var21) {
                    System.out.print(sql);
                    System.out.print("!");
                    var21.printStackTrace();
                    --retry;

                    try {
                        Thread.sleep(5000L);
                    } catch (InterruptedException var20) {
                        throw new RuntimeException(var21);
                    }
                } finally {
                    if(ps != null) {
                        try {
                            ps.close();
                        } catch (SQLException var19) {
                            throw new RuntimeException("关闭PreparedStatement异常" + var19.getMessage());
                        }
                    }

                }
            }
        }

    }

    public void commitAll() {
        Map cacheMap = (Map)this.currentMap.get();
        if(cacheMap != null && !cacheMap.isEmpty()) {
            Iterator connection = cacheMap.entrySet().iterator();

            while(connection.hasNext()) {
                Entry e = (Entry)connection.next();
                Map argsMap = (Map)e.getValue();
                Iterator i$ = argsMap.entrySet().iterator();

                while(i$.hasNext()) {
                    Entry argsEntry = (Entry)i$.next();
                    this.commit((String)e.getKey(), (List)argsEntry.getValue());
                }
            }

            cacheMap.clear();
        }

        Connection connection1 = (Connection)this.currentConnection.get();
        if(connection1 != null) {
            try {
                connection1.close();
            } catch (SQLException var7) {
                var7.printStackTrace();
            }

            this.currentConnection.remove();
        }

    }
}
