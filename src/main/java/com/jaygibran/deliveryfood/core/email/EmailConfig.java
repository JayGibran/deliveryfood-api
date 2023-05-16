package com.jaygibran.deliveryfood.core.email;

import com.jaygibran.deliveryfood.domain.service.EmailService;
import com.jaygibran.deliveryfood.infrastructure.service.email.EmailUtils;
import com.jaygibran.deliveryfood.infrastructure.service.email.FakeEmailService;
import com.jaygibran.deliveryfood.infrastructure.service.email.SandboxEmailService;
import com.jaygibran.deliveryfood.infrastructure.service.email.SmtpEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
@Configuration
public class EmailConfig {

    private final EmailProperties emailProperties;
    private final JavaMailSender mailSender;

    @Bean
    public EmailService emailService(EmailUtils emailUtils) {
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEmailService(emailUtils);
            case SMTP:
                return new SmtpEmailService(mailSender, emailUtils, emailProperties);
            case SANDBOX:
                return new SandboxEmailService(mailSender, emailProperties, emailUtils);
            default:
                return null;
        }
    }
}
