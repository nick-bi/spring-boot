package com.nickbi.elasticsearch.service;

import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 数据检索Service
 *
 * @author nickbi
 * @date 2018/12/26
 */
public interface ESService {

    /**
     * 建立索引
     *
     * @param clazz
     * @return
     */
    public <T> boolean createIndex(Class<T> clazz);


    /**
     * 批量插入或更新数据
     *
     * @param list
     * @param <T>
     * @return
     */
    public <T> boolean batchInsertOrUpdate(List<T> list);


    /**
     * 新增或更新数据
     *
     * @param t
     * @return
     */
    public <T> boolean insertOrUpdate(T t);


    /**
     * 通过实体类型及id删除数据
     *
     * @param clazz 实体类型
     * @param id    id
     * @param <T>
     * @return
     */
    public <T> boolean deleteById(Class<T> clazz, String id);

    /**
     * 通过id查询数据
     *
     * @param clazz 实体类型
     * @param id    数据id
     * @param <T>
     * @return
     */
    public <T> T queryById(Class<T> clazz, String id);


    /**
     * 列表查询
     *
     * @param clazz    实体类型
     * @param queryMap 查询参数
     * @param sortBy   排序字段
     * @param order    排序方式
     * @param <T>
     * @return
     */
    public <T> List<T> queryList(Class<T> clazz, Map<String, Object> queryMap, String sortBy, SortOrder order);

    /**
     * 分页查询
     *
     * @param clazz    实体类型
     * @param page     分页
     * @param queryMap 查询参数
     * @param sortBy   排序字段
     * @param order    排序方式
     *                 * @param <T>
     * @return
     */
    public <T> Page<T> queryPage(Class<T> clazz, Page<T> page, Map<String, Object> queryMap, String sortBy,
                                 SortOrder order);


    /**
     * 通过string查询数据
     *
     * @param <T>
     * @return
     */
    public <T> T queryByString(String id, Class<T> clazz);
}
