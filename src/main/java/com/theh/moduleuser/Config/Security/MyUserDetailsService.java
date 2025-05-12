package com.theh.moduleuser.Config.Security;

import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Model.Privilege;
import com.theh.moduleuser.Model.Role;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Repository.UtilisateurRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service//("userDetailsService")
@Transactional
@Component
public class   MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public MyUserDetailsService() {
        super();
    }


    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        //final String ip = getClientIP();

        try {
            final Utilisateur user = utilisateurRepository.findByEmail(email);
            if (user == null) {
                throw new EntityNotFoundException("No user found with username: " + email);
            }

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getMotDePasse(), true, true, true, true, getAuthorities(user.getRoles()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    // UTIL

    private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<>();
        final List<Privilege> collection = new ArrayList<>();
        for (final Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }

        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

//    private String getClientIP() {
//        final String xfHeader = request.getHeader("X-Forwarded-For");
//        if (xfHeader != null) {
//            return xfHeader.split(",")[0];
//        }
//        return request.getRemoteAddr();
//    }
}
