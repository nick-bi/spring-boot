package com.nickbi.solr.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.solr.core.SolrTemplate;

/**
 * if you just use solrj in project,  you need open this config
 *
 * @author nickbi
 * @date 2019/1/21
 */
//@Configuration
//@Component
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
