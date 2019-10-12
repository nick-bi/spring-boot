package com.nickbi.solr;

import com.nickbi.solr.entity.User;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author nickbi
 * @date 2019/1/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SolrJTests {

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
}
