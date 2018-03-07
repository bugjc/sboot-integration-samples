package com.bugjc.dao;

import com.bugjc.ds.TargetDataSource;
import com.bugjc.core.mybatis.Mapper;
import com.bugjc.model.Test1;

@TargetDataSource("tx1")
public interface Test1Mapper extends Mapper<Test1> {
}