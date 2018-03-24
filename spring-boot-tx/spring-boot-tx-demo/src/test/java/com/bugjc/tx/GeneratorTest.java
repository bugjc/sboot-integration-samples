package com.bugjc.tx;

import org.junit.Test;

public class GeneratorTest{

    /**
     * 生成代码
     */
    @Test
    public void test(){
        CodeGenerator.genCodeByCustomModelName("t_product","Product");
    }

}
