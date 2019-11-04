package com.github.zipper2110.something.configuration;

import com.github.zipper2110.something.mailing.SendMailer;
import com.github.zipper2110.something.mailing.SendMailerStub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailerConfig {

    @Bean
    public static SendMailer getMailer() {
        return new SendMailerStub();
    }
}
