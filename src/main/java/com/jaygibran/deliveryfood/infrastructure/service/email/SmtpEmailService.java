package com.jaygibran.deliveryfood.infrastructure.service.email;

import com.jaygibran.deliveryfood.core.email.EmailProperties;
import com.jaygibran.deliveryfood.domain.service.EmailService;
import com.jaygibran.deliveryfood.domain.util.EmailUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class SmtpEmailService implements EmailService {

    private final JavaMailSender mailSender;
    private final EmailProperties emailProperties;
    private final EmailUtils emailUtils;
    
    @Override
    public void send(Message message) {
        try {
            String body = emailUtils.processTemplate(message);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailProperties.getSender());
            helper.setTo(message.getRecipients().toArray(new String[0]));
            helper.setSubject(message.getSubject());
            helper.setText(body, true);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("It was not possible sent e-mail", e);
        }
    }
}
