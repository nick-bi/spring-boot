package com.nickbi.elasticsearch.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.nickbi.elasticsearch.entity.BaseEntity;
import com.nickbi.elasticsearch.service.ESService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * elasticsearch template封装
 *
 * @author nickbi
 * @date 2019/1/18
 */
@Service
public class ESServiceImpl implements ESService {

    private static Logger logger = LoggerFactory.getLogger(ESServiceImpl.class);

    @Autowired
    private ElasticsearchTemplate template;

    /**
     * 创建索引
     *
     * @param clazz 实体类型
     * @param <T>
     * @return
     */
    @Override
    public <T> boolean createIndex(Class<T> clazz) {
        return template.createIndex(clazz);
    }

    /**
     * 批量插入或更新数据
     *
     * @param list
     * @param <T>
     * @return
     */
    @Override
    public <T> boolean batchInsertOrUpdate(List<T> list) {
        List<IndexQuery> queriesList = new ArrayList<>();
        list.forEach(t -> {
            IndexQuery query = buildIndexQuery(t);
            queriesList.add(query);
        });
        try {
            template.bulkIndex(queriesList);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("insert or update user info error");
            return false;
        }
    }

    /**
     * 获取或更新实体id
     *
     * @param t
     * @return
     */
    private Long getEntityId(BaseEntity t) {
        Long id = t.getId();
        //生成id
        if (id == null) {
            id = generateId();
            t.setId(id);
        }
        return id;
    }

    public static void main(String[] args) {
        ESServiceImpl esService = new ESServiceImpl();
        Long aLong = esService.generateId();
        System.out.println(aLong);
    }

    /**
     * 通过snowFlake获取id
     *
     * @return
     */
    private synchronized Long generateId() {
        Long id;
        Snowflake snowflake = IdUtil.createSnowflake(11L, 11L);
        id = snowflake.nextId();
        return id;
    }


    /**
     * 新增或更新数据
     *
     * @param t 存储实体
     * @return
     */
    @Override
    public <T> boolean insertOrUpdate(T t) {
        try {
            IndexQuery query = buildIndexQuery(t);
            template.index(query);
            return true;
        } catch (Exception e) {
            logger.error("insert or update user info error： ", e);
            return false;
        }
    }

    /**
     * 组件indexQuery
     *
     * @param t
     * @param <T>
     * @return
     */
    private <T> IndexQuery buildIndexQuery(T t) {
        //拿到或更新id
        Long id = getEntityId((BaseEntity) t);
        IndexQueryBuilder builder = new IndexQueryBuilder();
        //设置indexName及type
        setIndexNameAndType(t.getClass(), builder);
        //创建indexQuery 对象
        return builder.withId(id.toString()).withObject(t).build();
    }

    /**
     * 动态设置indexName及type
     *
     * @param clazz
     * @param builder
     */
    private void setIndexNameAndType(Class<?> clazz, IndexQueryBuilder builder) {
        if (clazz.isAnnotationPresent(Document.class)) {
            ElasticsearchPersistentEntity entity = template.getPersistentEntityFor(clazz);
            String indexName = entity.getIndexName();
            String indexType = entity.getIndexType();
            builder.withIndexName(indexName).withType(indexType);
        }
    }

    /**
     * 通过实体类型及id删除数据
     *
     * @param clazz 实体类型
     * @param id    id
     * @param <T>
     * @return
     */
    @Override
    public <T> boolean deleteById(Class<T> clazz, String id) {
        try {
            template.delete(clazz, id);
            return true;
        } catch (Exception e) {
            logger.error("delete " + clazz.getName() + " by id： " + id
                    + " error.", e);
            return false;
        }
    }

    /**
     * 通过id查询数据
     *
     * @param clazz 实体类型
     * @param id    数据id
     * @param <T>
     * @return
     */
    @Override
    public <T> T queryById(Class<T> clazz, String id) {
        StringQuery query = new StringQuery("id=" + id);
        T result = template.queryForObject(query, clazz);
        return result;
    }

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
    @Override
    public <
            T> List<T> queryList(Class<T> clazz, Map<String, Object> queryMap, String sortBy, SortOrder order) {
        NativeSearchQueryBuilder nativeQuery = buildFieldAndSortQuery(queryMap, sortBy, order);
        SearchQuery query = nativeQuery.build();
        List<T> list = template.queryForList(query, clazz);
        return list;
    }

    @Override
    public <T> Page<T> queryPage(Class<T> clazz, Page<T> page, Map<String, Object> queryMap, String sortBy,
                                 SortOrder order) {
        NativeSearchQueryBuilder nativeQuery = buildFieldAndSortQuery(queryMap, sortBy, order);
        //分页查询设置
        nativeQuery.withPageable(PageRequest.of(Math.toIntExact(page.getNumber()),
                Math.toIntExact(page.getSize())));
        SearchQuery query = nativeQuery.build();
        page = template.queryForPage(query, clazz);
        return page;
    }

    @Override
    public <T> T queryByString(String queryStr, Class<T> clazz) {
        StringQuery query = new StringQuery(queryStr);
        T t = template.queryForObject(query, clazz);
        return t;
    }

    /**
     * 基本查询创建
     *
     * @param queryMap 查询条件
     * @param sortBy   排序字段名称
     * @param order    排序方式
     * @return
     */
    private NativeSearchQueryBuilder buildFieldAndSortQuery(Map<String, Object> queryMap, String sortBy,
                                                            SortOrder order) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryMap.forEach((key, value) -> {
            if (value != null) {
                queryBuilder.must(QueryBuilders.matchQuery(key, value));
            }
        });
        NativeSearchQueryBuilder nativeQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder);
        //设置排序
        if (StringUtils.isNotBlank(sortBy) && order != null) {
            nativeQuery.withSort(new FieldSortBuilder(sortBy).order(order));
        }
        return nativeQuery;
    }

}
