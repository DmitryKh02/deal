package ru.neoflex.deal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.admin.NewTopic;

public interface KafkaMessageService {
    void send(NewTopic topic, Long applicationID);
}
