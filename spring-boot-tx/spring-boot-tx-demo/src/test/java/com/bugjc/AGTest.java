package com.bugjc;

import com.alibaba.fastjson.JSON;
import com.bugjc.ds.CustomTransactional;

@CustomTransactional(attributes = {"{\"name\":\"delete*\",\"readOnly\":\"true\",\"rollbackFor\":\"Exception\"}","{}"})
public class AGTest {

    private String name;
    private String rollbackFor;
    private String readOnly;

    public static void main(String[] args) {
        AGTest agTest = new AGTest();
        agTest.setName("delete*");
        agTest.setReadOnly("true");
        agTest.setRollbackFor("Exception");
        System.out.println(JSON.toJSON(agTest));
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollbackFor() {
        return rollbackFor;
    }

    public void setRollbackFor(String rollbackFor) {
        this.rollbackFor = rollbackFor;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }
}
