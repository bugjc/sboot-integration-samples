package com.bugjc.tx.dao;

import com.bugjc.tx.core.ds.TargetDataSource;
import com.bugjc.tx.model.Product;
import tk.mybatis.mapper.common.Mapper;

@TargetDataSource("product")
public interface ProductMapper extends Mapper<Product> {
}