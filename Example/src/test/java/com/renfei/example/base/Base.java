package com.renfei.example.base;

/**
 * 功能说明：
 *
 * @ClassName Base
 * @Author renfei
 * @Date 2019/11/15 17:28
 * @Description TODO
 * @Version V1.0.0
 */
public abstract class Base {

    protected String rulesRootPath;

    protected String getRulesRootPath() {
        return rulesRootPath;
    }

    protected abstract String setRulesRootPath();


    protected void a() {
//        setRulesRootPath();
        System.out.println("rulesRootPath:" + getRulesRootPath());
        System.out.println("getRulesRootPath():" + getRulesRootPath());
    }


}
