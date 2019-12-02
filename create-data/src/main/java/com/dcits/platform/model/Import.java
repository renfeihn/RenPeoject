package com.dcits.platform.model;

import com.dcits.platform.model.Block;
import com.dcits.platform.model.DBConnection;
import com.dcits.platform.model.Truncate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Import {
    private String id;
    private String desc;
    private String fileName;
    private Map<String, DBConnection> dbMap;
    private List<DBConnection> dbs;
    private Map<String, Object> constMap;
    private List<Truncate> truncates;
    private List<Block> blocks;
    Map<String, String> sequences;

    public Import() {
    }

    public Map<String, DBConnection> getDbMap() {
        return this.dbMap;
    }

    public void setDbMap(Map<String, DBConnection> dbMap) {
        this.dbMap = dbMap;
    }

    public List<DBConnection> getDbs() {
        return this.dbs;
    }

    public void setDbs(List<DBConnection> dbs) {
        this.dbMap = new HashMap();
        Iterator i$ = dbs.iterator();

        while(i$.hasNext()) {
            DBConnection dbConnection = (DBConnection)i$.next();
            this.dbMap.put(dbConnection.getId(), dbConnection);
        }

        this.dbs = dbs;
    }

    public Map<String, Object> getConstMap() {
        if(this.constMap == null) {
            this.constMap = new HashMap();
        }

        return this.constMap;
    }

    public Map<String, String> getSequences() {
        return this.sequences;
    }

    public void setSequences(Map<String, String> sequences) {
        this.sequences = sequences;
    }

    public void setConstMap(Map<String, Object> constMap) {
        this.constMap = constMap;
    }

    public List<Truncate> getTruncates() {
        return this.truncates;
    }

    public void setTruncates(List<Truncate> truncates) {
        this.truncates = truncates;
    }

    public List<Block> getBlocks() {
        return this.blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
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

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
