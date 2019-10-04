//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.platform.template;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class CreateTableTemplate {
    private static final String URL = "jdbc:mysql://192.168.162.210:3306/bx_upright?useUnicode=true&characterEncoding=utf8&&rewriteBatchedStatements=true&useServerPrepStmts=false";
    private static final String USERNAME = "galaxy";
    private static final String PASSWORD = "galaxy";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String[] TABLENAME = new String[]{"batch_def", "batch_std_job", "batch_status"};
    private static final String FILEPATH = "F:\\table.txt";

    public CreateTableTemplate() {
    }

    public static StringBuffer getTableXml(Connection connection, String tableName, File file) throws Exception {
        String DataSourceProductName = getDatabaseProductName(connection);
        String schema = null;
        if("Oracle".equals(DataSourceProductName)) {
            schema = connection.getMetaData().getUserName().toUpperCase();
        }

        ResultSet rs = connection.getMetaData().getColumns((String)null, schema, tableName.toUpperCase(), (String)null);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("***********************" + tableName + "***********************").append("\n");
        stringBuffer.append("<table name=\"" + tableName + "\" connection=\"[dbs][#index%[dbs].size()]\">");
        stringBuffer.append("\n");

        while(rs.next()) {
            String column_name = rs.getString("COLUMN_NAME").toUpperCase();
            stringBuffer.append("\t");
            stringBuffer.append("<template name=\"" + column_name + "\">" + "\'TEST\'" + "</template>");
            stringBuffer.append("\n");
        }

        stringBuffer.append("</table>");
        stringBuffer.append("\n");
        return stringBuffer;
    }

    private static String getDatabaseProductName(Connection connection) throws Exception {
        try {
            return connection.getMetaData().getDatabaseProductName();
        } catch (Exception var2) {
            throw new Exception(var2);
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection e = DriverManager.getConnection("jdbc:mysql://192.168.162.210:3306/bx_upright?useUnicode=true&characterEncoding=utf8&&rewriteBatchedStatements=true&useServerPrepStmts=false", "galaxy", "galaxy");
            File file = new File("F:\\table.txt");
            ArrayList list = new ArrayList();
            String[] sf = TABLENAME;
            int i$ = sf.length;

            for(int sb = 0; sb < i$; ++sb) {
                String tb = sf[sb];
                list.add(getTableXml(e, tb, file));
            }

            FileOutputStream var11 = new FileOutputStream(file);
            Iterator var12 = list.iterator();

            while(var12.hasNext()) {
                StringBuffer var13 = (StringBuffer)var12.next();
                var11.write(var13.toString().getBytes());
            }

            var11.flush();
            var11.close();
        } catch (ClassNotFoundException var8) {
            var8.printStackTrace();
        } catch (SQLException var9) {
            var9.printStackTrace();
        } catch (Exception var10) {
            var10.printStackTrace();
        }

    }
}
