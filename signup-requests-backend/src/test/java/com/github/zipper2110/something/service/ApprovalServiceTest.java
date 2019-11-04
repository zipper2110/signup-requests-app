package com.github.zipper2110.something.service;

import com.github.zipper2110.something.entity.EmailAddress;
import com.github.zipper2110.something.entity.Password;
import com.github.zipper2110.something.entity.SignupRequest;
import com.github.zipper2110.something.entity.Username;
import com.github.zipper2110.something.messaging.MessageId;
import com.github.zipper2110.something.messaging.MessagingService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.messaging.support.GenericMessage;

import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static com.github.zipper2110.something.entity.SignupRequest.Status.APPROVED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ApprovalServiceTest {

    @Autowired
    private ApprovalService approvalService;

    @MockBean
    private MessagingService messagingService;

    @Test
    void whenSendForApprovalThenReturnsMessageId() {
        val signupRequest = new SignupRequest();
        signupRequest.setUsername(new Username("username"));
        signupRequest.setPassword(new Password("pass"));
        signupRequest.setEmailAddress(new EmailAddress("email@test.com"));
        signupRequest.setFullName("my name");

        val expected = new MessageId(UUID.randomUUID());

        when(messagingService.send(any())).thenReturn(expected);

        val received = approvalService.sendForApproval(signupRequest);
        assertEquals(expected, received);
    }

    @Test
    void whenWaitForApprovalGivenApprovedThenGetApprovedRequest() throws TimeoutException {
        val signupRequest = new SignupRequest();
        signupRequest.setUsername(new Username("username"));
        signupRequest.setPassword(new Password("pass"));
        signupRequest.setEmailAddress(new EmailAddress("email@test.com"));
        signupRequest.setFullName("my name");

        val replySignupRequest = new SignupRequest();
        replySignupRequest.setUsername(signupRequest.getUsername());
        replySignupRequest.setPassword(signupRequest.getPassword());
        replySignupRequest.setEmailAddress(signupRequest.getEmailAddress());
        replySignupRequest.setFullName(signupRequest.getFullName());
        replySignupRequest.setStatus(APPROVED);

        val waitingFor = new MessageId(UUID.randomUUID());
        when(messagingService.receive(waitingFor, SignupRequest.class))
                .thenReturn(new GenericMessage<>(replySignupRequest));

        approvalService.waitForReply(waitingFor, reply -> reply.ifPresent(signupRequest1 -> {
            val status = signupRequest1.getStatus();
            assertSame(status, APPROVED);
        }));
    }

    @Test
    void canApproveTest() throws TimeoutException {
        val signupRequest = new SignupRequest();
        signupRequest.setUsername(new Username("username"));
        signupRequest.setPassword(new Password("pass"));
        signupRequest.setEmailAddress(new EmailAddress("email@test.com"));
        signupRequest.setFullName("my name");

        val replySignupRequest = new SignupRequest();
        replySignupRequest.setUsername(signupRequest.getUsername());
        replySignupRequest.setPassword(signupRequest.getPassword());
        replySignupRequest.setEmailAddress(signupRequest.getEmailAddress());
        replySignupRequest.setFullName(signupRequest.getFullName());
        replySignupRequest.setStatus(APPROVED);

        val messageId = new MessageId(UUID.randomUUID());

        when(messagingService.send(any())).thenReturn(messageId);
        when(messagingService.receive(messageId, SignupRequest.class))
                .thenReturn(new GenericMessage<>(replySignupRequest));

        approvalService.approve(signupRequest, reply -> reply.ifPresent(signupRequest1 -> {
            val status = signupRequest1.getStatus();
            assertSame(status, APPROVED);
        }));
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public TaskExecutor taskExecutor() {
            return new SyncTaskExecutor();
        }
    }
}