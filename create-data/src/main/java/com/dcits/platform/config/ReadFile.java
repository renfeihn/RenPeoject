//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.dcits.platform.config;

import com.dcits.platform.model.Block;
import com.dcits.platform.model.DBConnection;
import com.dcits.platform.model.Define;
import com.dcits.platform.model.For;
import com.dcits.platform.model.If;
import com.dcits.platform.model.Import;
import com.dcits.platform.model.Table;
import com.dcits.platform.model.Truncate;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadFile {
    private Map<String, Define> defineMap;

    public ReadFile() {
    }

    public Import read(InputStream inputStream) throws Exception {
        Import im = new Import();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        if(documentBuilder != null) {
            Document doc = documentBuilder.parse(inputStream);
            Element importNode = doc.getDocumentElement();
            im.setId(importNode.getAttribute("id"));
            im.setDesc(importNode.getAttribute("desc"));
            im.setDbs(this.getDbs(this.getChildByName(importNode, "dbs")));
            this.defineMap = this.getDefineMap(this.getChildByName(importNode, "define"));
            Map constMap = this.getConstMap(this.getChildByName(importNode, "const"));
            Map listMap = this.getListMap(this.getChildByName(importNode, "list"));
            if(constMap != null) {
                im.getConstMap().putAll(constMap);
            }

            if(listMap != null) {
                im.getConstMap().putAll(listMap);
            }

            im.setTruncates(this.getTruncates(this.getChildByName(importNode, "truncate")));
            im.setBlocks(this.getBlocks(this.getChildByName(importNode, "block")));
            im.setSequences(this.getSequences(this.getChildByName(importNode, "sequences")));
        }

        return im;
    }

    private Map<String, String> getSequences(List<Element> nodeList) {
        if(nodeList != null && !nodeList.isEmpty()) {
            Element element = (Element)nodeList.get(0);
            NodeList seqs = element.getElementsByTagName("seq");
            if(seqs != null && seqs.getLength() > 0) {
                HashMap sequences = new HashMap(seqs.getLength());

                for(int i = 0; i < seqs.getLength(); ++i) {
                    Element seq = (Element)seqs.item(i);
                    sequences.put(seq.getAttribute("name"), seq.getAttribute("initValue"));
                }

                return sequences;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private List<DBConnection> getDbs(List<Element> dbNodeList) {
        ArrayList dbConnections = new ArrayList();
        Element element = (Element)dbNodeList.get(0);
        NodeList dbs = element.getElementsByTagName("db");

        for(int i = 0; i < dbs.getLength(); ++i) {
            Element db = (Element)dbs.item(i);
            String dbId = db.getAttribute("id");
            String groupExpr = db.getAttribute("group");
            String url = this.getText(db, "url");
            String username = this.getText(db, "username");
            String password = this.getText(db, "password");
            String driverClass = this.getText(db, "driverClass");
            DBConnection dbConnection = new DBConnection();
            dbConnection.setId(dbId);
            dbConnection.setUrl(url);
            dbConnection.setUsername(username);
            dbConnection.setPassword(password);
            dbConnection.setDriverClass(driverClass);
            dbConnection.setGroupExpr(groupExpr);
            dbConnections.add(dbConnection);
        }

        return dbConnections;
    }

    private Map<String, String> getConstMap(List<Element> constNodeList) {
        if(constNodeList != null && constNodeList.size() != 0) {
            HashMap constMap = new HashMap();
            Iterator i$ = constNodeList.iterator();

            while(i$.hasNext()) {
                Element constElement = (Element)i$.next();
                String name = constElement.getAttribute("name");
                String value = constElement.getAttribute("value");
                constMap.put(name, value);
            }

            return constMap;
        } else {
            return null;
        }
    }

    private Map<String, List> getListMap(List<Element> listNodeList) {
        if(listNodeList != null && listNodeList.size() != 0) {
            HashMap constMap = new HashMap();
            Iterator i$ = listNodeList.iterator();

            while(i$.hasNext()) {
                Element listElement = (Element)i$.next();
                String name = listElement.getAttribute("name");
                ArrayList list = new ArrayList();
                constMap.put(name, list);
                List itemList = this.getChildByName(listElement, "item");
                Iterator i$1 = itemList.iterator();

                while(i$1.hasNext()) {
                    Element item = (Element)i$1.next();
                    list.add(item.getAttribute("value"));
                }
            }

            return constMap;
        } else {
            return null;
        }
    }

    private List<Truncate> getTruncates(List<Element> truncateNodeList) {
        if(truncateNodeList != null && truncateNodeList.size() != 0) {
            Element truncateElement = (Element)truncateNodeList.get(0);
            List tables = this.getChildByName(truncateElement, "table");
            if(tables != null && tables.size() != 0) {
                ArrayList truncates = new ArrayList();
                Iterator i$ = tables.iterator();

                while(i$.hasNext()) {
                    Element tableElement = (Element)i$.next();
                    Truncate truncate = new Truncate();
                    truncate.setTableName(tableElement.getAttribute("name"));
                    truncate.setCondition(tableElement.getAttribute("condition"));
                    truncate.setConnection(tableElement.getAttribute("connection"));
                    truncates.add(truncate);
                }

                return truncates;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private List<Block> getBlocks(List<Element> blockNodeList) {
        if(blockNodeList != null && blockNodeList.size() != 0) {
            ArrayList blocks = new ArrayList();
            Iterator i$ = blockNodeList.iterator();

            while(i$.hasNext()) {
                Element blockElement = (Element)i$.next();
                Block block = new Block();
                block.setId(blockElement.getAttribute("id"));
                block.setDesc(blockElement.getAttribute("desc"));
                block.setForList(this.getForList(this.getChildByName(blockElement, "for")));
                blocks.add(block);
            }

            return blocks;
        } else {
            return null;
        }
    }

    private List<For> getForList(List<Element> forNodeList) {
        if(forNodeList != null && forNodeList.size() != 0) {
            ArrayList forList = new ArrayList();
            Iterator i$ = forNodeList.iterator();

            while(i$.hasNext()) {
                Element forElement = (Element)i$.next();
                For forItem = new For();
                forItem.setStart(forElement.getAttribute("start"));
                forItem.setEnd(forElement.getAttribute("end"));
                forItem.setStep(forElement.getAttribute("step"));
                forItem.setIndex(forElement.getAttribute("index"));
                forItem.setThread(forElement.getAttribute("thread"));
                forItem.setCommit(forElement.getAttribute("commit"));
                forItem.setIfs(this.getIfs(this.getChildByName(forElement, "if")));
                forItem.setTables(this.getTables(this.getChildByName(forElement, "table")));
                forItem.setForList(this.getForList(this.getChildByName(forElement, "for")));
                Object constMap = this.getConstMap(this.getChildByName(forElement, "const"));
                Map listMap = this.getListMap(this.getChildByName(forElement, "list"));
                if(constMap == null) {
                    constMap = new HashMap();
                }

                if(listMap != null && !listMap.isEmpty()) {
                    ((Map)constMap).putAll(listMap);
                }

                forItem.setConstMap((Map)constMap);
                List defines = this.getDefines(this.getChildByName(forElement, "define"));
                forItem.setDefines(defines);
                forList.add(forItem);
            }

            return forList;
        } else {
            return null;
        }
    }

    private List<Define> getDefines(List<Element> defineNodeList) {
        List defineIds = this.getDefineIds(defineNodeList);
        if(defineIds != null && !defineIds.isEmpty()) {
            if(this.defineMap == null) {
                throw new RuntimeException("没有定义全局define");
            } else {
                ArrayList defines = new ArrayList();
                Iterator i$ = defineIds.iterator();

                while(i$.hasNext()) {
                    String defineId = (String)i$.next();
                    Define define = (Define)this.defineMap.get(defineId);
                    if(define == null) {
                        throw new RuntimeException("没有定义define,Id=" + defineId);
                    }

                    defines.add(define);
                }

                return defines;
            }
        } else {
            return null;
        }
    }

    private List<String> getDefineIds(List<Element> defineNodeList) {
        if(defineNodeList != null && defineNodeList.size() != 0) {
            ArrayList defines = new ArrayList();
            Iterator i$ = defineNodeList.iterator();

            while(i$.hasNext()) {
                Element defineElement = (Element)i$.next();
                String define = defineElement.getAttribute("ref");
                defines.add(define);
            }

            return defines;
        } else {
            return null;
        }
    }

    private Map<String, Define> getDefineMap(List<Element> defineNodeList) {
        if(defineNodeList != null && defineNodeList.size() != 0) {
            HashMap defineMap = new HashMap();
            Iterator i$ = defineNodeList.iterator();

            while(i$.hasNext()) {
                Element defineElement = (Element)i$.next();
                Define define = new Define();
                define.setId(defineElement.getAttribute("id"));
                define.setTables(this.getTables(this.getChildByName(defineElement, "table")));
                define.setIfs(this.getIfs(this.getChildByName(defineElement, "if")));
                defineMap.put(define.getId(), define);
            }

            return defineMap;
        } else {
            return null;
        }
    }

    public List<Element> getChildByName(Element element, String name) {
        ArrayList elementList = new ArrayList();
        NodeList childNodes = element.getChildNodes();
        if(childNodes != null && childNodes.getLength() > 0) {
            for(int i = 0; i < childNodes.getLength(); ++i) {
                Node node = childNodes.item(i);
                if(node.getNodeType() == 1 && name.equals(node.getNodeName())) {
                    elementList.add(node);
                }
            }
        }

        return elementList;
    }

    private List<If> getIfs(List<Element> ifNodeList) {
        if(ifNodeList != null && ifNodeList.size() != 0) {
            ArrayList ifs = new ArrayList();
            Iterator i$ = ifNodeList.iterator();

            while(i$.hasNext()) {
                Element ifElement = (Element)i$.next();
                If ifItem = new If();
                ifItem.setTest(ifElement.getAttribute("test"));
                ifItem.setTables(this.getTables(this.getChildByName(ifElement, "table")));
                ifItem.setDefines(this.getDefines(this.getChildByName(ifElement, "define")));
                ifs.add(ifItem);
            }

            return ifs;
        } else {
            return null;
        }
    }

    private List<Table> getTables(List<Element> tableNodeList) {
        if(tableNodeList != null && tableNodeList.size() != 0) {
            ArrayList tables = new ArrayList();

            Table table;
            for(Iterator i$ = tableNodeList.iterator(); i$.hasNext(); tables.add(table)) {
                Element tableElement = (Element)i$.next();
                table = new Table();
                table.setName(tableElement.getAttribute("name"));
                table.setConnection(tableElement.getAttribute("connection"));

                try {
                    table.setColumns(this.getColumns(this.getChildByName(tableElement, "column")));
                    table.setVars(this.getColumns(this.getChildByName(tableElement, "var")));
                } catch (Exception var7) {
                    System.out.println(table.getName());
                    throw var7;
                }
            }

            return tables;
        } else {
            return null;
        }
    }

    private Map<String, String> getColumns(List<Element> columnNodeList) {
        if(columnNodeList != null && columnNodeList.size() != 0) {
            LinkedHashMap columns = new LinkedHashMap();
            Iterator i$ = columnNodeList.iterator();

            while(i$.hasNext()) {
                Element columnElement = (Element)i$.next();
                Node firstChild = columnElement.getFirstChild();
                if(firstChild != null) {
                    columns.put(columnElement.getAttribute("name"), firstChild.getNodeValue());
                }
            }

            return columns;
        } else {
            return null;
        }
    }

    private String getText(Element element, String name) {
        NodeList nodeList = element.getElementsByTagName(name);
        Element e = (Element)nodeList.item(0);
        return e.getFirstChild().getNodeValue();
    }
}
