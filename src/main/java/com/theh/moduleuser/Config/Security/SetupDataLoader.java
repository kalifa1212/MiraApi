package com.theh.moduleuser.Config.Security;

import com.theh.moduleuser.Model.Privilege;
import com.theh.moduleuser.Model.Role;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Repository.PrivilegeRepository;
import com.theh.moduleuser.Repository.RoleRepository;
import com.theh.moduleuser.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // API

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege modifiedPrivilege = createPrivilegeIfNotFound("MODIFIED_PRIVILEGE");
        final Privilege grantTypePrivilege = createPrivilegeIfNotFound("GRANT_TYPE_PRIVILEGE");
        final Privilege grantRolePrivilege = createPrivilegeIfNotFound("GRANT_ROLE_PRIVILEGE");
        final Privilege resetPasswordPrivilege = createPrivilegeIfNotFound("RESET_PASSWORD_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");
        final Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");

        // == create initial roles
        final List<Privilege> rootPrivileges = new ArrayList<>(Arrays.asList(readPrivilege,
                writePrivilege, passwordPrivilege,modifiedPrivilege,grantRolePrivilege,
                deletePrivilege,grantTypePrivilege,resetPasswordPrivilege));
        final List<Privilege> adminPrivileges = new ArrayList<>(Arrays.asList(readPrivilege,
                writePrivilege, resetPasswordPrivilege,grantTypePrivilege,passwordPrivilege,modifiedPrivilege));
        final List<Privilege> staffPrivileges = new ArrayList<>(Arrays.asList(readPrivilege,
                writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<>(Arrays.asList(readPrivilege, passwordPrivilege));


        final Role rootRole = createRoleIfNotFound("ROLE_ROOT", rootPrivileges);
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final Role staffRole =  createRoleIfNotFound("ROLE_STAFF", staffPrivileges);
        final Role userRole =  createRoleIfNotFound("ROLE_USER", userPrivileges);


        // == create initial user
        createUserIfNotFound("root", "Test", "Test", "test", new ArrayList<>(Arrays.asList(rootRole)));
        createUserIfNotFound("admin", "Test", "Test", "test", new ArrayList<>(Arrays.asList(adminRole)));
        createUserIfNotFound("staff", "Test", "Test", "test", new ArrayList<>(Arrays.asList(staffRole)));
        createUserIfNotFound("user", "Test", "Test", "test", new ArrayList<>(Arrays.asList(userRole)));

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        return role;
    }

    @Transactional
    Utilisateur createUserIfNotFound(final String email, final String firstName, final String lastName, final String password, final Collection<Role> roles) {
        Utilisateur user1 = utilisateurRepository.findByEmail(email);
        Utilisateur user = new Utilisateur();
        if (user1==null) {
            user.setNom(firstName);
            user.setPrenom(lastName);
            user.setMotDePasse(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setEnabled(true);
        }
        user.setRoles(roles);
        user = utilisateurRepository.save(user);
        return user;
    }
}