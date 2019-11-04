package com.github.zipper2110.something.service;

import com.github.zipper2110.something.entity.EmailAddress;
import com.github.zipper2110.something.entity.SignupRequest;
import com.github.zipper2110.something.mailing.SendMailer;
import lombok.val;
import org.springframework.core.task.TaskExecutor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

@Service
public class NotificationService {

    private SendMailer mailer;
    private TaskExecutor taskExecutor;

    public NotificationService(SendMailer mailer, TaskExecutor taskExecutor) {
        this.mailer = mailer;
        this.taskExecutor = taskExecutor;
    }

    public void notifyOnStatusChange(SignupRequest signupRequest, Consumer<SignupRequest> onNotificationSent) {
        val emailAddress = signupRequest.getEmailAddress();
        val notificationText = new StatusChangeEmailContentComposer(signupRequest).getText();

        taskExecutor.execute(() -> {
            try {
                sendMail(emailAddress, new SendMailer.EmailContent(notificationText));
                onNotificationSent.accept(signupRequest);
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        });

    }

    @Retryable(value = { TimeoutException.class }, backoff = @Backoff(delay = 5000))
    private void sendMail(EmailAddress address, SendMailer.EmailContent content) throws TimeoutException {
        mailer.sendMail(address, content);
    }

    /**
     * Этот класс нужно развивать в сторону потребностей в генерации контента сообщений.
     * Скорее всего там будут свои template-библиотеки и какие-то специализированные вещи
     */
    private static class StatusChangeEmailContentComposer {

        private SignupRequest signupRequest;

        public StatusChangeEmailContentComposer(SignupRequest signupRequest) {
            this.signupRequest = signupRequest;
        }

        public String getText() {
            return signupRequest.getStatus().name();
        }
    }
}
