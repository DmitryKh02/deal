package ru.neoflex.deal.configuration.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.neoflex.deal.dto.response.EmailMessage;

import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Configuration
public class KafkaConfigProducer {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = JsonMapper.builder().build().registerModule(new JavaTimeModule());
        log.trace("KafkaConfigProducer.objectMapper - ObjectMapper {}", objectMapper);

        return objectMapper;
    }

    @Bean
    public ProducerFactory<String, EmailMessage> producerFactory(KafkaProperties kafkaProperties, ObjectMapper objectMapper) {
        log.trace("KafkaConfigProducer.producerFactory - KafkaProperties{}, ObjectMapper {}", kafkaProperties, objectMapper);

        Map<String, Object> props = kafkaProperties.buildProducerProperties();

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        DefaultKafkaProducerFactory<String, EmailMessage> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(props);
        log.trace("KafkaConfig.producerFactory - DefaultKafkaProducerFactory{}", kafkaProducerFactory);
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, EmailMessage> kafkaTemplate(ProducerFactory<String, EmailMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
