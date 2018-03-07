package com.bugjc.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <p>设置数据源
 * <p>@author yangqing
 * <p>@date 2016年11月30日
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataBaseContextHolder.getDBType();
    }
}
