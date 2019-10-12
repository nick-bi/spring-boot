# Kafka
### this is a kafka Spring Boot project repository

# Kafka项目简介
### Kafka Spring Boot的学习项目

### maven依赖
````
    <dependency>
         <groupId>org.springframework.kafka</groupId>
         <artifactId>spring-kafka</artifactId>
     </dependency>

  ````
  ### config配置
 
```` 
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: Kafka Broker Default Group
    producer:
      acks: all

````
### Kafka producer
#### Kafka producer 使用 kafkaTemplate进行消息send 操作
````

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
}


````
#### Consumer 消息接收
##### 消息接收，通过使用KafkaListener来进行consumer的消息监听
````
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
````

### more articles please click [my blog](https://www.jianshu.com/u/1bb4b4eaef1e)
### of course you can send email to me [nickbi@nickbi.com](mailto:nickbi@nickbi.com)

### 更多博客，请访问[我的博客](https://www.jianshu.com/u/1bb4b4eaef1e)
### 如遇问题，请发送邮件到我的邮箱[nickbi@nickbi.com](mailto:nickbi@nickbi.com)
