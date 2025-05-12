package com.theh.moduleuser.Controller;

import com.theh.moduleuser.Config.Security.JwtUtil;
import com.theh.moduleuser.Config.Security.MyUserDetailsService;
import com.theh.moduleuser.Controller.Api.UtilisateurApi;
import com.theh.moduleuser.Dto.auth.AuthenticationRequest;
import com.theh.moduleuser.Dto.auth.AuthenticationResponse;
import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Dto.auth.ChangePassWordDto;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Mapper.UtilisateurMapper;
import com.theh.moduleuser.Model.Privilege;
import com.theh.moduleuser.Model.Role;
import com.theh.moduleuser.Model.TypeCompte;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Repository.MosqueRepository;
import com.theh.moduleuser.Repository.SuivreRepository;
import com.theh.moduleuser.Repository.UtilisateurRepository;
import com.theh.moduleuser.Services.MosqueService;
import com.theh.moduleuser.Services.UtilisateurService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@OpenAPIDefinition(info = @Info(title = "MIRA API- Muslim Information Resources and Assistance", version = "1.2.0"))
@Slf4j
public class UtilisateurController  implements UtilisateurApi {

   // @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    private SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();
    private final SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();
    private UtilisateurRepository utilisateurRepository;
    private MosqueRepository mosqueRepository;
    private MosqueService mosqueService;
    private UtilisateurService utilisateurService;
    private SuivreRepository suivreRepository;
    private UtilisateurMapper utilisateurMapper;

    @Autowired
    public UtilisateurController(
            UtilisateurRepository utilisateurRepository,
            UtilisateurService utilisateurService,
            AuthenticationManager authenticationManager,
            MosqueRepository mosqueRepository,
            MosqueService mosqueService,
            SuivreRepository suivreRepository
           // UtilisateurMapper utilisateurMapper
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurService=utilisateurService;
        this.authenticationManager=authenticationManager;
        this.mosqueRepository=mosqueRepository;
        this.mosqueService=mosqueService;
        this.suivreRepository=suivreRepository;
        //this.utilisateurRepository=utilisateurMapper;
    }


    public ResponseEntity<AuthenticationResponse> authentification(@RequestBody AuthenticationRequest authenticationRequestrequest) {

        final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequestrequest.getLogin());

        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        authenticationRequestrequest.getLogin(), authenticationRequestrequest.getPassword());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);
        final String jwt = jwtUtil.generateToken(userDetails);

        final String RefreshToken= jwtUtil.generateRefreshToken(userDetails);
        final String email=jwtUtil.extractUserName(jwt);
        // TODO saving refresh token

        if(authenticationResponse.isAuthenticated()) {
            utilisateurService.setRefreshToken(email,RefreshToken);
            return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).email(email).refreshToken(RefreshToken).build());
        } else {
            return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).email(email).build());
        }
    }

    @Override
    public ResponseEntity<Boolean> PasswordReset(ChangePassWordDto changePassWordDto) {

        return ResponseEntity.ok(utilisateurService.passwordReset(changePassWordDto));
    }

    @Override
    public ResponseEntity<?> refreshToken(String refreshToken) {
        if (refreshToken == null || jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token invalide ou expiré");
        }

        String username = jwtUtil.extractUserName(refreshToken);
        log.error(username);
        var user = utilisateurRepository.findUtilisateurByEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        UserDetails userDetails =new org.springframework.security.core.userdetails.User(user.getEmail(), user.getMotDePasse(), true, true, true, true, getAuthorities(user.getRoles()));
        String newAccessToken = jwtUtil.generateToken(userDetails);


        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(newAccessToken).refreshToken(refreshToken).email(user.getEmail()).build());

    }

    @Override
    public ResponseEntity<Boolean> VerifyToken(String jwtToken) {
        boolean isExpired=jwtUtil.isTokenExpired(jwtToken);
        //log.error("test verification {}",isExpired);
        return ResponseEntity.ok(isExpired);
    }

    @Override
    public ResponseEntity<UtilisateurDto> save(UtilisateurDto dto,Boolean update) {
        // TODO Enregistrement d'un utilisateur ou mis a jour utilisateur

        return ResponseEntity.ok(utilisateurService.save(dto,update));
    }

    @Override
    public String test(){
        return "jsdf";
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        // TODO recherche utilisateur par id
        return utilisateurService.findById(id);
    }

    @Override
    public UtilisateurDto findByEmail(String email, HttpServletRequest request) {
        // TODO Recherche  Utilisateur by email

        Optional<Utilisateur> util = utilisateurRepository.findUtilisateurByEmail(email);
        if(util.isEmpty()){
            throw new InvalidEntityException("L'email n'existe pas");
        }
        Utilisateur utilisateur=util.get();
        UtilisateurDto dto = UtilisateurDto.fromEntity(utilisateur);
        return dto;
    }

    @Override
    public Boolean GranteCompteRole(String email, String role) {
        Boolean result=utilisateurService.GranteRole(email,role);
        return result;
    }

    @Override
    public String followMosque(Integer userid, Integer mosqueId) {
        return utilisateurService.followMosque(userid,mosqueId);
    }

    @Override
    public String unfollowMosque(Integer userid, Integer mosqueId) {
        return utilisateurService.unfollowMosque(userid,mosqueId);
    }

    @Override
    public String followUser(Integer userid, Integer usertarget) {
        return utilisateurService.followUser(userid,usertarget);
    }

    @Override
    public String unfollowUser(Integer userid, Integer usertarget) {
        return utilisateurService.unfollowUser(userid,usertarget);
    }

    @Override
    public UtilisateurDto GranteCompteType(String email,char type) {
        // TODO grante autority changement du type de compte
        //UtilisateurDto utilisateur = utilisateurService.findById(id);
        UtilisateurDto utilisateur=utilisateurService.findByEmail(email);
        String Type_Imam="IMAM";
        if(type=='I'){
            utilisateur.setTypecompte(TypeCompte.IMAM);
            boolean update=true;
            return utilisateurService.save(utilisateur,update);
        }
        else return null;
    }

    @Override
    public Page<UtilisateurDto> findAll(String sortColumn, int page, int taille, String sortDirection) {
        Pageable paging = PageRequest.of(page, taille, Sort.by(sortColumn).ascending());
        // TODO afficher tout les utilisateurs
        return utilisateurService.findAll(paging);
    }

    @Override
    public Page<UtilisateurDto> findAllByType(String type,String sortColumn, int page, int taille, String sortDirection) {
        type=type.toUpperCase();
        Pageable paging = PageRequest.of(page, taille,Sort.by(sortColumn).ascending());
        return utilisateurRepository.findUtilisateurByTypecompte(type,paging)
                .map(UtilisateurDto::fromEntity);
    }

    @Override
    public void delete(Integer id) {
        // TODO Suppprimer utilisateur

        utilisateurService.delete(id);
    }
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
}