package com.bugjc.service.impl;

import com.bugjc.dao.Test2Mapper;
import com.bugjc.model.Test2;
import com.bugjc.service.Test2Service;
import com.bugjc.core.mybatis.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by aoki on 2017/11/06.
 */
@Service
public class Test2ServiceImpl extends AbstractService<Test2> implements Test2Service {
    @Resource
    private Test2Mapper tdTest2Mapper;

}
