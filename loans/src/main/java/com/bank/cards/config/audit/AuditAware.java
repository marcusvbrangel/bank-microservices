package com.bank.cards.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAware implements AuditorAware<String> {

    @Override
    public Optional getCurrentAuditor() {
        return Optional.of("LOANS_MS");
    }

}
