package com.bugjc.core.mybatis;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 * @author : aoki
 */
public interface Service<T> {
    /**
     * 通用持久化方法
     * @param model
     */
    void save(T model);

    /**
     * 通用批量持久化方法
     * @param models
     */
    void save(List<T> models);

    /**
     * 通用主鍵刪除方法
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 通用批量主鍵刪除 eg：ids -> “1,2,3,4”方法
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 通用更新方法
     * @param model
     */
    void update(T model);

    /**
     * 通用主键查找方法
     * @param id
     * @return
     */
    T findById(Integer id);

    /**
     * 通用单字段查询方法
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     * @param fieldName
     * @param value
     * @return
     * @throws TooManyResultsException
     */
    T findBy(String fieldName, Object value) throws TooManyResultsException;

    /**
     * 通用批量id查找方法
     * @param ids -> “1,2,3,4”
     * @return
     */
    List<T> findByIds(String ids);

    /**
     * 通用据条件查找
     * @param condition
     * @return
     */
    List<T> findByCondition(Condition condition);

    /**
     * 通用据T对象非空属性查找方法
     * @param model
     * @return
     */
    List<T> findByEntity(T model);

    /**
     * 通用查找所有的方法
     * @return
     */
    List<T> findAll();
}

