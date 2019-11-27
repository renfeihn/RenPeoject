package com.renfei.example.base;

/**
 * 功能说明：
 *
 * @ClassName Child
 * @Author renfei
 * @Date 2019/11/15 17:30
 * @Description TODO
 * @Version V1.0.0
 */
public class Child extends Base {

    private String rulesRootPath;

    public void b(){
        super.rulesRootPath = "/a/b";
        this.a();
    }

    @Override
    protected String setRulesRootPath() {
        return this.rulesRootPath = "/a/b";
    }
}
