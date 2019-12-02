package com.dcits.platform.model;

import com.dcits.platform.model.Define;
import com.dcits.platform.model.Table;
import com.dcits.platform.utils.ExpressionUtil;
import java.util.List;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class If {
    private String test;
    private List<Table> tables;
    private List<Define> defines;

    public If() {
    }

    public String getTest() {
        return this.test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public List<Table> getTables() {
        return this.tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public boolean executeTest(ExpressionParser parser, StandardEvaluationContext context) {
        return ExpressionUtil.getBoolObject(parser, context, this.test);
    }

    public List<Define> getDefines() {
        return this.defines;
    }

    public void setDefines(List<Define> defines) {
        this.defines = defines;
    }
}
