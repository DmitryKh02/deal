package ru.neoflex.deal.configuration.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaTopic {
    @Value("${application.kafka.topics[0].finish-registration}")
    private String finishRegistrationTopic;

    @Value("${application.kafka.topics[1].create-documents}")
    private String createDocumentsTopic;

    @Value("${application.kafka.topics[2].send-documents}")
    private String sendDocumentsTopic;

    @Value("${application.kafka.topics[3].send-ses}")
    private String sendSesTopic;

    @Value("${application.kafka.topics[4].credit-issued}")
    private String creditIssuedTopic;

    @Value("${application.kafka.topics[5].application-denied}")
    private String applicationDeniedTopic;

    public NewTopic finishRegistration()
    {
        return TopicBuilder.name(finishRegistrationTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    public NewTopic createDocument()
    {
        return TopicBuilder.name(createDocumentsTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    public NewTopic sendDocuments()
    {
        return TopicBuilder.name(sendDocumentsTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    public NewTopic sendSes()
    {
        return TopicBuilder.name(sendSesTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    public NewTopic creditIssued()
    {
        return TopicBuilder.name(creditIssuedTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    public NewTopic applicationDenied()
    {
        return TopicBuilder.name(applicationDeniedTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

}
