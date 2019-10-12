package com.nickbi.kafka.consumer;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * @author nickBi
 * create on 2019-02-15.
 */
@Component
@Slf4j
public class Receiver {

    private static final String TOPIC = "testTopic";

    @KafkaListener(topics = {TOPIC})
    public void commandListener(ConsumerRecord<?, ?> record) {
        Optional<?> value = Optional.ofNullable(record.value());
        if (!value.isPresent()) {
            return;
        }
        Object message = value.get();
        String data = JSONUtil.toBean((String) message, String.class);
        log.info("receiver message data: {}", data);
    }

}
