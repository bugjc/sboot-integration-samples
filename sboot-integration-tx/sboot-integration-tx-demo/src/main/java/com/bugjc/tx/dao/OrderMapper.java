package com.bugjc.tx.dao;

import com.bugjc.tx.core.ds.TargetDataSource;
import com.bugjc.tx.model.Order;
import tk.mybatis.mapper.common.Mapper;

@TargetDataSource("order")
public interface OrderMapper extends Mapper<Order> {
}