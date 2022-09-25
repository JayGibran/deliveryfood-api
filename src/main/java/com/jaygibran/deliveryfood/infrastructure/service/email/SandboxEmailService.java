package com.jaygibran.deliveryfood.infrastructure.service.email;

import com.jaygibran.deliveryfood.core.email.EmailProperties;
import com.jaygibran.deliveryfood.domain.util.EmailUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SandboxEmailService extends SmtpEmailService {
    
    public SandboxEmailService(JavaMailSender mailSender, EmailProperties emailProperties, EmailUtils emailUtils) {
        super(mailSender, emailUtils, emailProperties);
    }

    @Override
    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        MimeMessage mimeMessage = super.createMimeMessage(message);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailProperties.getSandbox().getReceiver());

        return mimeMessage;
    }
}
