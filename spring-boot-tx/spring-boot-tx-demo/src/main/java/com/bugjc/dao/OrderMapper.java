package com.bugjc.dao;

import com.bugjc.core.ds.TargetDataSource;
import com.bugjc.model.Order;
import tk.mybatis.mapper.common.Mapper;

@TargetDataSource("order")
public interface OrderMapper extends Mapper<Order> {
}