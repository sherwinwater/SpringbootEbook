package com.sherwin.ebook.config;

import com.sherwin.ebook.domain.Customer;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() == null ||
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return Optional.of("admin@gmail.com");
        } else {
            return Optional.of(((Customer) SecurityContextHolder.getContext().
                    getAuthentication().getPrincipal()).getFirstName());
        }
    }
}
