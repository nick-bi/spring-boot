package com.nickbi.kafka.controller;

import cn.hutool.extra.tokenizer.Result;
import com.nickbi.kafka.entity.KafkaMessage;
import com.nickbi.kafka.producer.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nickBi
 * create on 2019-02-15.
 */
@RestController
public class KafkaSendController {

    @Autowired
    private Sender sender;

    /**
     * send data to kafka consumer
     *
     * @param message Data
     * @return
     */
    @RequestMapping("/send")
    public String getSendInfo(KafkaMessage message) {
        sender.sendData(message);
        return "send data success";
    }
}
