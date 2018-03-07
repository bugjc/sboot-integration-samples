package com.bugjc.service.impl;

import com.bugjc.ds.CustomTransactional;
import com.bugjc.core.mybatis.AbstractService;
import com.bugjc.dao.Test1Mapper;
import com.bugjc.model.Test1;
import com.bugjc.model.Test2;
import com.bugjc.service.Test1Service;
import com.bugjc.service.Test2Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * Created by aoki on 2017/11/06.
 */
@Service
@CustomTransactional
public class Test1ServiceImpl extends AbstractService<Test1> implements Test1Service {
    @Resource
    private Test1Mapper tdTest1Mapper;
    @Resource
    private Test2Service test2Service;


    @Override
    public void test() {
        Test1 test1 = new Test1();
        test1.setName("1");
        mapper.insert(test1);

        Test2 test2 = new Test2();
        test2.setName("1");
        test2Service.save(test2);

        Test1 test12 = new Test1();
        test12.setName("1");
        mapper.insert(test12);

    }
}
