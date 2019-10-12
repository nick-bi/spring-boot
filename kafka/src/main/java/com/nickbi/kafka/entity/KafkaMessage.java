package com.nickbi.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author nickBi
 * create on 2019-02-15.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaMessage {

    /**
     * message title
     */
    private String title;
    /**
     * message data
     */
    private Object data;
    /**
     * message send user
     */
    private String sendUser;
    /**
     * message send date
     */
    private Date sendDate;
}
