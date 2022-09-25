package com.jaygibran.deliveryfood.infrastructure.service.email;

import com.jaygibran.deliveryfood.domain.service.EmailService;
import com.jaygibran.deliveryfood.domain.util.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class FakeEmailService implements EmailService {
    
    private final EmailUtils emailUtils;
    
    @Override
    public void send(Message message) {
        log.info("[FAKE E-MAIL] To: {}\n{}", message.getRecipients(), emailUtils.processTemplate(message)); 
    }
}
