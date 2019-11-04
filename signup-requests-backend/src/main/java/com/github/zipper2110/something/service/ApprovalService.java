package com.github.zipper2110.something.service;


import com.github.zipper2110.something.entity.SignupRequest;
import com.github.zipper2110.something.messaging.MessageId;
import com.github.zipper2110.something.messaging.MessagingService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

@Service
@Slf4j
public class ApprovalService {

    private TaskExecutor taskExecutor;
    private MessagingService messagingService;

    public ApprovalService(TaskExecutor taskExecutor, MessagingService messagingService) {
        this.taskExecutor = taskExecutor;
        this.messagingService = messagingService;
    }

    void approve(SignupRequest signupRequest, Consumer<Optional<SignupRequest>> onReply) {
        MessageId messageId = sendForApproval(signupRequest);
        taskExecutor.execute(() -> waitForReply(messageId, onReply));
    }

    MessageId sendForApproval(SignupRequest signupRequest) {
        MessageId sentMessageId = messagingService.send(new GenericMessage<>(signupRequest));
        log.info("Signup request {} was sent for approval", signupRequest);
        return sentMessageId;
    }

    void waitForReply(MessageId sentMessageId, Consumer<Optional<SignupRequest>> onReply) {
        try {
            val approvalResponse = getReply(sentMessageId);
            onReply.accept(Optional.of(approvalResponse.getPayload()));
        } catch (TimeoutException e) {
            e.printStackTrace();
            log.error("Could not get the response from approval service for message id {}", sentMessageId);
        }
    }

    @Retryable(value = { TimeoutException.class }, backoff = @Backoff(delay = 5000))
    Message<SignupRequest> getReply(MessageId messageId) throws TimeoutException {
        return messagingService.receive(messageId, SignupRequest.class);
    }

//        @Recover
//        public void recover(RemoteAccessException e) {
//            // ... panic
//        }
}