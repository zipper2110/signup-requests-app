package com.github.zipper2110.something.mailing;

import com.github.zipper2110.something.entity.EmailAddress;

import java.util.concurrent.TimeoutException;

/**
 * Ориентировочный интерфейс мейлера.
 */
public interface SendMailer {
    void sendMail (EmailAddress toAddress, EmailContent messageBody) throws TimeoutException;

    public static class EmailContent {
        private final String content;

        public EmailContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}