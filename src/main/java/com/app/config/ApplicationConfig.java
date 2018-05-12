package com.app.config;

import com.app.pubsub.Receiver;
import com.app.pubsub.Sender;
import com.app.pubsub.impl.MessageReceiver;
import com.app.pubsub.impl.MessageSender;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;


@ComponentScan("com.app")
@SpringBootApplication
public class ApplicationConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory());
        container.addMessageListener(listenerAdapter, topic());
        return container;
    }


    @Bean
    Topic topic() {
        return new PatternTopic("chat");
    }

    @Bean
    @Qualifier("redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port));
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    Receiver receiver() {
        return new MessageReceiver();
    }


    @Bean
    Sender sender() {
        return new MessageSender(stringTemplate(), topic());
    }

    @Bean
    StringRedisTemplate stringTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    public static void main(String[] args) throws InterruptedException {
        final ApplicationContext applicationContext = SpringApplication.run(ApplicationConfig.class, args);
        final Sender sender = applicationContext.getBean(Sender.class);
        sender.sendMessage("Hello from Redis!\n  Let's learn together :) ");

    }
}
