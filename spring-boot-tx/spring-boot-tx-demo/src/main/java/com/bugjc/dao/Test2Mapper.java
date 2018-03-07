package com.bugjc.dao;

import com.bugjc.ds.TargetDataSource;
import com.bugjc.core.mybatis.Mapper;
import com.bugjc.model.Test2;

@TargetDataSource("tx2")
public interface Test2Mapper extends Mapper<Test2> {
}