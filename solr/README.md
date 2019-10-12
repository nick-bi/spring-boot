# Solr
### this is a Solr Spring Boot project repository

# Solr项目简介
### Solr Spring Boot的学习项目

### maven依赖
````
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-solr</artifactId>
    </dependency>

  ````
  ### jdbc配置
 
```` 
spring:
  data:
    solr:
      host: http://localhost:8983/solr
````
### Solr repository
#### solr repository基于spring data jpa规范，对solr数据进行crud操作，如果项目本身就是使用hibernate+jpa建议使用这种方式进行solr数据操作。
````

public interface UserRepository extends SolrCrudRepository<User, Long> {
    /**
     * find user by username
     *
     * @param username
     * @return
     */
    public List<User> findByUsernameContains(String username);

    

}


````
#### 添加索引、更新索引、删除索引
````
    @Test
    public void index() {
        User test =
                User.builder().id("1002").username("test").password("123123").remark("solr user index test!").build();
        User save = userRepository.save(test);
        logger.debug(" user document saved with id: ", save.getId());
    }

    @Test
    public void update() {
        User user = User.builder().id("1001").createDate(new Date()).build();
        userRepository.save(user);
        logger.debug("user document updated  with id: ", user.getId());
    }

    @Test
    public void delete() {
        User user = User.builder().id("1001").build();
        userRepository.delete(user);
        logger.debug("user document deleted  with id: ", user.getId());
    }
````

### SolrJ
#### 如果项目使用的是mybatis等其他orm框架，建议使用SolrJ对数据进行操作。
````
private static Logger logger = LoggerFactory.getLogger(SolrJTests.class);
    @Autowired
    private SolrClient solrClient;

    private static final String SOLR_COLLECTION_USER = "user";

    @Test
    public void createIndex() throws Exception {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", 1001L);
        document.addField("username", "test");
        document.addField("password", "123123");
        document.addField("remark", "solr index test");
        document.addField("createDate", new Date());
        solrClient.add(SOLR_COLLECTION_USER, document);
        solrClient.commit(SOLR_COLLECTION_USER);
    }

    @Test
    public void query() throws Exception {
        SolrQuery query = new SolrQuery();
        query.set("q", "id:1001");
        QueryResponse response = solrClient.query(SOLR_COLLECTION_USER, query);
        List<User> userList = response.getBeans(User.class);
        logger.debug("results size is: ", userList.size());
    }
````
### solr client auto config
#### 如果项目使用的是springboot，建议使用spring data solr 进行solr数据操作，如果不使用springboot，可以使用如下配置实现solrclient的配置
````
@Configuration
@Component
public class SolrConfig {
    /**
     * solr host value
     */
    @Value("${spring.data.solr.host}")
    private String solrHost;

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient.Builder(solrHost).build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient client) throws Exception {
        return new SolrTemplate(client);
    }
}

````


### more articles please click [my blog](https://www.jianshu.com/u/1bb4b4eaef1e)
### of course you can send email to me [nickbi@nickbi.com](mailto:nickbi@nickbi.com)

### 更多博客，请访问[我的博客](https://www.jianshu.com/u/1bb4b4eaef1e)
### 如遇问题，请发送邮件到我的邮箱[nickbi@nickbi.com](mailto:nickbi@nickbi.com)
