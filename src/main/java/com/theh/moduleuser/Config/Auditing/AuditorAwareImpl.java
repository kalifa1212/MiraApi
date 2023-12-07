package com.theh.moduleuser.Config.Auditing;

import com.theh.moduleuser.Model.Utilisateur;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null){
            return Optional.empty();
        }else if(authentication.isAuthenticated()) {
            User user =(User) authentication.getPrincipal();
            return Optional.of(user.getUsername());
        }else

        return Optional.empty();
    }
}
