package com.bugjc.tx.service.impl;

import com.bugjc.tx.dao.ProductMapper;
import com.bugjc.tx.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author aoki
 * @create 2018/03/20.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper tProductMapper;

}
