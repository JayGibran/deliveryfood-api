package com.jaygibran.deliveryfood.domain.util;

import com.jaygibran.deliveryfood.domain.service.EmailService;
import com.jaygibran.deliveryfood.infrastructure.service.email.EmailException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

@AllArgsConstructor
@Component
public class EmailUtils {

    private final Configuration freemarkerConfig;

    public String processTemplate(EmailService.Message message) {

        try {
            Template template = freemarkerConfig.getTemplate(message.getBody());

            return FreeMarkerTemplateUtils.processTemplateIntoString(template, message.getVariables());
        } catch (Exception e) {
            throw new EmailException("It was not possible process email template", e);
        }
    }
}
