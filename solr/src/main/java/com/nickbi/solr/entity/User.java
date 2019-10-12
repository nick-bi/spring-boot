package com.nickbi.solr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;
import java.util.Date;

/**
 * user entity
 *
 * @author nickbi
 * @date 2019/1/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@SolrDocument(solrCoreName = "user", collection = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 6437870368800677897L;

    @Field("id")
    private String id;

    @Field("username")
    private String username;

    @Field("password")
    private String password;

    @Field("remark")
    private String remark;

    @Field("createDate")
    private Date createDate;

}
