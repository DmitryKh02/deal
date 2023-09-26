package ru.neoflex.deal.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.neoflex.deal.dto.response.EmailMessage;
import ru.neoflex.deal.service.ApplicationService;
import ru.neoflex.deal.service.KafkaMessageService;

import static ru.neoflex.deal.enums.Theme.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessageServiceImpl implements KafkaMessageService {
    private final ApplicationService applicationService;
    private final KafkaTemplate<String, EmailMessage> template;

    @Override
    public void send(NewTopic topic, Long applicationId) {
        String email = applicationService.getClientEmailByApplicationId(applicationId);
        EmailMessage message = getMessageByTopicName(topic, email, applicationId);

        try {
            log.info("KafkaMessageServiceImpl.send - EmailMessage {}", message);

            template.send(topic.name(), message).completable()
                    .whenComplete(
                            (result, ex) -> {
                                //TODO сделать обработку ошибки ответа от topic
                                if (ex == null) {
                                    log.info("message {} was sent; offset {}", message, result.getRecordMetadata().offset());
                                } else {
                                    log.error("message {} was not sent;  {}", message, ex.getMessage());
                                }
                            }
                    );
        }
        //TODO написать SendException
        catch (Exception ex) {
            log.error("send error, message {}, exception {}", message, ex.getMessage());
        }
    }

    private EmailMessage getMessageByTopicName(NewTopic topic, String email, Long applicationId) {
        log.debug("KafkaMessageServiceImpl.getMessageByTopicName - NewTopic {}, ApplicationId {}", topic, applicationId);

        EmailMessage emailMessage = switch (topic.name()) {
            case "finish-registration" -> new EmailMessage(email, FINISH_REGISTRATION, applicationId);
            case "create-documents" -> new EmailMessage(email, CREATE_DOCUMENTS, applicationId);
            case "send-documents" -> new EmailMessage(email, SEND_DOCUMENTS, applicationId);
            case "send-ses" -> new EmailMessage(email, SEND_SES, applicationId);
            case "credit-issued" -> new EmailMessage(email, CREDIT_ISSUED, applicationId);
            case "application-denied" -> new EmailMessage(email, APPLICATION_DENIED, applicationId);
            default -> null;
        };

        log.debug("KafkaMessageServiceImpl.getMessageByTopicName - EmailMessage {}", emailMessage);

        return emailMessage;
    }
}
