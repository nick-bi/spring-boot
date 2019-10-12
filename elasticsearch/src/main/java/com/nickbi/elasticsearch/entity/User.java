package com.nickbi.elasticsearch.entity;

import com.nickbi.elasticsearch.constant.ElasticsearchConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * user entity
 *
 * @author nickbi
 * @date 2019/1/18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Document(indexName = ElasticsearchConstant.USER_INDEX, type = ElasticsearchConstant.USER_INDEX_TYPE)
public class User extends BaseEntity {
    private static final long serialVersionUID = 1208704268051941622L;

    private String username;
    private String password;
    private Date createDate;
    private String remark;
}
