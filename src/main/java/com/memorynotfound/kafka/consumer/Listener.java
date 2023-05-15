package com.memorynotfound.kafka.consumer;

import com.memorynotfound.kafka.model.User;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    private static final Logger LOG = LoggerFactory.getLogger(Listener.class);

    @KafkaListener(topics = "${app.topic.foo}")
    public void receive(@Payload User user,
                        @Header(KafkaHeaders.OFFSET) Long offset,
                        @Header(KafkaHeaders.CONSUMER) KafkaConsumer<String, String> consumer,
                        @Header(KafkaHeaders.TIMESTAMP_TYPE) String timestampType,
                        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partitionId,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey,
                        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp,
                        @Header("X-Custom-Header") String customHeader) {

        LOG.info("- - - - - - - - - - - - - - -");
        LOG.info("received message='{}'", user.toString());
        LOG.info("consumer: {}", consumer);
        LOG.info("topic: {}", topic);
        LOG.info("message key: {}", messageKey);
        LOG.info("partition id: {}", partitionId);
        LOG.info("offset: {}", offset);
        LOG.info("timestamp type: {}", timestampType);
        LOG.info("timestamp: {}", timestamp);
        LOG.info("custom header: {}", customHeader);
    }

    @KafkaListener(topics = "${app.topic.bar}")
    public void receive(@Payload User user,
                        @Headers MessageHeaders messageHeaders) {

        LOG.info("- - - - - - - - - - - - - - -");
        LOG.info("received message='{}'", user.toString());
        messageHeaders.keySet().forEach(key -> {
            Object value = messageHeaders.get(key);
            if (key.equals("X-Custom-Header")){
                LOG.info("{}: {}", key, new String((byte[])value));
            } else {
                LOG.info("{}: {}", key, value);
            }
        });

    }
}
