package com.javadev.app.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.javadev.app.dto.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageQueueService {

    @Value("${app.config.message.queue.topic}")
    private String messageQueueTopic;
    private final AmazonSQS amazonSQSClient;

    public void createMessageQueue() {
        log.info("Creating message queue on AWS SQS");
        CreateQueueRequest request = new CreateQueueRequest();
        request.setQueueName(messageQueueTopic);
        try {
            CreateQueueResult queue = amazonSQSClient.createQueue(request);
            log.info("Create Queue Response {}", queue.getQueueUrl());
        } catch (QueueNameExistsException e) {
            log.error("Queue Name Exists {}", e.getErrorMessage());
        }
    }

    public void publishExpense(MessageDTO createExpenseDto) {
        try {
            GetQueueUrlResult queueUrl = amazonSQSClient.getQueueUrl(messageQueueTopic);
            log.info("Reading SQS Queue done: URL {}", queueUrl.getQueueUrl());
            amazonSQSClient.sendMessage(queueUrl.getQueueUrl(), createExpenseDto.getMessage());
        } catch (QueueDoesNotExistException | InvalidMessageContentsException e) {
            log.error("Queue does not exist {}", e.getMessage());
        }
    }
}
