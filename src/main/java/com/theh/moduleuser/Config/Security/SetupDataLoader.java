package com.theh.moduleuser.Config.Security;

import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.*;
import com.theh.moduleuser.Repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
@Slf4j
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private LocalisationRepository localisationRepository;
    @Autowired
    private PaysRepository paysRepository;
    @Autowired
    private VilleRepository villeRepository;

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
        //TODO Initializing data
        log.info("Initializing data ");
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


        String paysName = "Cameroun";

        String[] villes = {
                "Yaoundé", "Douala", "Bafoussam", "Bamenda", "Maroua", "Garoua", "Ngaoundéré", "Bertoua", "Ebolowa", "Kribi",
                "Limbé", "Buea", "Kumba", "Nkongsamba", "Edéa", "Dschang", "Foumban", "Mbouda", "Sangmélima", "Batouri",
                "Yokadouma", "Tibati", "Meiganga", "Abong-Mbang", "Mbalmayo", "Akonolinga", "Obala", "Nanga-Eboko", "Bafia",
                "Guider", "Mokolo", "Kousseri", "Mora", "Yagoua", "Tiko", "Mamfe", "Wum", "Fundong", "Ndop", "Mbengwi",
                "Loumbé", "Loum", "Manjo", "Foumbot", "Bangangté", "Bafang", "Melong", "Penja", "Pouma", "Logbessou",
                "Kaélé", "Poli", "Figuil", "Tignère", "Lagdo", "Pitoa", "Touboro", "Ngong", "Mayo-Oulo", "Mindif", "Bogo",
                "Tokombéré", "Koza", "Makari", "Gashiga", "Bibemi", "Mayo Darle", "Ngaoundal", "Banyo", "Djohong",
                "Dir", "Kontcha", "Belel", "Ngoura", "Ketté", "Lomié", "Mindourou", "Messamena", "Moloundou",
                "Ambam", "Akom II", "Bikok", "Eseka", "Pouma", "Nkoteng", "Sa’a", "Monatélé", "Makénéné", "Ntui",
                "Ngomedzap", "Ayos", "Esse", "Okola", "Somalomo", "Campo", "Lolodorf", "Ma’an", "Oveng", "Bengbis"
        };

        Arrays.stream(villes).forEach(ville -> createLocationIfNotFound(ville, paysName));

        // == create initial user
        createUserIfNotFound("root", "Test", "Test", "test", new ArrayList<>(Arrays.asList(rootRole)),"Makénéné");
        createUserIfNotFound("admin", "Test", "Test", "test", new ArrayList<>(Arrays.asList(adminRole)),"Somalomo");
        createUserIfNotFound("staff", "Test", "Test", "test", new ArrayList<>(Arrays.asList(staffRole)),"Obala");
        createUserIfNotFound("user", "Test", "Test", "test", new ArrayList<>(Arrays.asList(userRole)),"Ntui");

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
            role.setPrivileges(privileges);
            role = roleRepository.save(role);
          //  log.info("save role ");
        }

        return role;
    }

    @Transactional
    Utilisateur createUserIfNotFound(final String email, final String firstName, final String lastName, final String password, final Collection<Role> roles,String ville) {
        Utilisateur user = utilisateurRepository.findUtilisateurByEmail(email).orElseGet(()->{
            Localisation localisation= localisationRepository.findByVille_NameIgnoreCase(ville);
            Utilisateur user1 = new Utilisateur();

                // user1= new Utilisateur();
                user1.setNom(firstName);
                user1.setPrenom(lastName);
                user1.setMotDePasse(passwordEncoder.encode(password));
                user1.setDateDeNaissance(new Date());
                user1.setEmail(email);
                user1.setEnabled(true);
                user1.setRoles(roles);
                user1.setLocalisation(localisation);
                user1 = utilisateurRepository.save(user1);

            return user1;
        });
        return user;
    }
    @Transactional
    Localisation createLocationIfNotFound(final String villeName, final String paysName ) {
        // Crée ou récupère le pays
        Pays pays = paysRepository.findByName(paysName).orElseGet(() -> {
            Pays newPays = new Pays();
            newPays.setName(paysName);
            newPays.setCode(paysName.substring(0, 3).toUpperCase()); // Exemple de code (facultatif)
            return paysRepository.save(newPays);
        });

        // Crée ou récupère la ville
        Ville ville = villeRepository.findByNameAndPays_Name(villeName, paysName).orElseGet(() -> {
            Ville newVille = new Ville();
            newVille.setName(villeName);
            newVille.setPays(pays);
            return villeRepository.save(newVille);
        });

        // Crée ou récupère la localisation
        Localisation localisation = localisationRepository.findByVille_NameAndVille_Pays_Name(villeName, paysName).orElseGet(() -> {
            Localisation newLocalisation = new Localisation();
            newLocalisation.setVille(ville);
            return localisationRepository.save(newLocalisation);
        });

        return localisation;
    }
}