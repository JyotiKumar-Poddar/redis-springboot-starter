package com.app.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

public class Sender {

    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void sendMessage() {

        LOG.info("Sending message...");
        stringRedisTemplate.convertAndSend("chat", "Hello from Redis!");
        stringRedisTemplate.convertAndSend("chat", "How are you");

        stringRedisTemplate.convertAndSend("chat-1", "chat-1:Hello from Redis!");
        stringRedisTemplate.convertAndSend("chat-1", "chat-1:How are you");
    }

}
