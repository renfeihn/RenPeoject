package com.dcits.platform.filter;

import java.io.File;
import java.io.FileFilter;

public class XmlFilter implements FileFilter {
    public XmlFilter() {
    }

    public boolean accept(File pathname) {
        return pathname.getName().endsWith(".xml");
    }
}
