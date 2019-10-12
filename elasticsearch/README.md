# Elasticsearch
### this is a Elasticsearch Spring Boot project repository

# Elasticsearch项目简介
### Elasticsearch Spring Boot的学习项目

### maven依赖
````
 <dependency>
             <dependency>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
             </dependency>
  </dependency>
  ````
  ### jdbc配置
 
```` 
spring:
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8
    username: root
    password: 111111
    hikari:
      minimum-idle: 5
      maximum-pool-size: 15
      auto-commit: true
      idle-timeout: 30000
      pool-name: DatebookHikariCP
      max-lifetime: 1800000
      connection-timeout: 30000
      connection-test-query: SELECT 1
  data:
    elasticsearch:
      repositories:
        enabled: true
      cluster-name: docker-cluster # this name you can set in elasticsearch.yml config
      cluster-nodes: localhost:9300 # elasticsearch host and port
````
### Elasticsearch repository
#### elasticsearch repository基于spring data jpa规范，对elasticsearch数据进行crud操作，如果项目本身就是使用hibernate+jpa建议使用这种方式进行elasticsearch数据操作。
````
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
    /**
     * find user by user name
     *
     * @param id user id
     * @return
     */
    User findUserById(Long id);
}

````
### Elasticsearch template
#### 如果项目使用的是mybatis等其他orm框架，建议使用elasticsearch template对数据进行操作。
### ESService 
#### 项目中ESService 是基于elasticsearch template的一层常用封装，如果无法满足需求，请直接使用elasticsearch template进行数据操作。

````
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

````


### more articles please click [my blog](https://www.jianshu.com/u/1bb4b4eaef1e)
### of course you can send email to me [nickbi@nickbi.com](mailto:nickbi@nickbi.com)

### 更多博客，请访问[我的博客](https://www.jianshu.com/u/1bb4b4eaef1e)
### 如遇问题，请发送邮件到我的邮箱[nickbi@nickbi.com](mailto:nickbi@nickbi.com)
