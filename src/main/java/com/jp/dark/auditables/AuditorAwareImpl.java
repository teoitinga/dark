package com.jp.dark.auditables;

import com.jp.dark.security.dto.AutheticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        AutheticatedUser usuario = new AutheticatedUser();

        SecurityContext context = SecurityContextHolder.getContext();
        if(context instanceof SecurityContext)
        {
            Authentication authentication = context.getAuthentication();
            if(authentication instanceof Authentication)
            {
                log.info("Usuario atual: {}", (((AutheticatedUser)authentication.getPrincipal())));
                usuario = (((AutheticatedUser)authentication.getPrincipal()));
            }
        }
        String username;
        try{
        username = usuario.getUsername();
        }catch (NullPointerException exc){
            username = "***";
        }
        return Optional.of(username);
    }

}
