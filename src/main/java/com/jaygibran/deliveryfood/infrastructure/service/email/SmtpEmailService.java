package com.jaygibran.deliveryfood.infrastructure.service.email;

import com.jaygibran.deliveryfood.core.email.EmailProperties;
import com.jaygibran.deliveryfood.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service
public class SmtpEmailService implements EmailService {
    
    private final JavaMailSender mailSender;
    private final EmailUtils emailUtils;
    protected final EmailProperties emailProperties;

    @Override
    public void send(Message message) {
        try {
            MimeMessage mimeMessage = createMimeMessage(message);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("It was not possible sent e-mail", e);
        }
    }

    protected MimeMessage createMimeMessage(Message message) throws MessagingException {
        String body = emailUtils.processTemplate(message);

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getSender());
        helper.setTo(message.getRecipients().toArray(new String[0]));
        helper.setSubject(message.getSubject());
        helper.setText(body, true);

        return mimeMessage;
    }
}
