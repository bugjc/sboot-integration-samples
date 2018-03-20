package com.bugjc.dao;

import com.bugjc.core.ds.TargetDataSource;
import com.bugjc.model.Product;
import tk.mybatis.mapper.common.Mapper;

@TargetDataSource("product")
public interface ProductMapper extends Mapper<Product> {
}