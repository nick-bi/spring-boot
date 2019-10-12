package com.nickbi.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * base entity
 *
 * @author nickbi
 * @date 2019/1/18
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 8152048348166952579L;

    protected Long id;
}
