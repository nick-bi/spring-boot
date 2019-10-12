package com.nickbi.kafka.producer;

import cn.hutool.json.JSONUtil;
import com.nickbi.kafka.entity.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author nickBi
 * create on 2019-02-15.
 */
@Component
@Slf4j
public class Sender {

    /**
     * kafka test topic
     */
    private static final String TOPIC = "testTopic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    /**
     * send kafka message to consumer
     *
     * @param topic data topic
     * @param data  send data
     */
    public void sendMessage(String topic, Object data) {
        //send data to consumer
        String dataStr = JSONUtil.toJsonStr(data);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, dataStr);
        log.info("send topic: {}, send data: {}", topic, dataStr);
        // add callback listener
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("sendMessage callback error: ", throwable);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("sendMessage success key:{}", result.getProducerRecord().key());
            }
        });
    }

    /**
     * send message
     *
     * @param message send data
     */
    public void sendData(KafkaMessage message) {
        sendMessage(TOPIC, message);
    }
}
