package com.theh.moduleuser.Controller;

import com.theh.moduleuser.Config.Security.JwtUtil;
import com.theh.moduleuser.Config.Security.MyUserDetailsService;
import com.theh.moduleuser.Controller.Api.UtilisateurApi;
import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Dto.SuivreDto;
import com.theh.moduleuser.Dto.auth.AuthenticationRequest;
import com.theh.moduleuser.Dto.auth.AuthenticationResponse;
import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Suivre;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Repository.MosqueRepository;
import com.theh.moduleuser.Repository.SuivreRepository;
import com.theh.moduleuser.Repository.UtilisateurRepository;
import com.theh.moduleuser.Services.MosqueService;
import com.theh.moduleuser.Services.UtilisateurService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
@OpenAPIDefinition(info = @Info(title = "Muslem API", version = "1.2.0"))
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

    @Autowired
    public UtilisateurController(
            UtilisateurRepository utilisateurRepository,
            UtilisateurService utilisateurService,
            AuthenticationManager authenticationManager,
            MosqueRepository mosqueRepository,
            MosqueService mosqueService,
            SuivreRepository suivreRepository
    ) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurService=utilisateurService;
        this.authenticationManager=authenticationManager;
        this.mosqueRepository=mosqueRepository;
        this.mosqueService=mosqueService;
        this.suivreRepository=suivreRepository;
    }


    public ResponseEntity<AuthenticationResponse> authentification(@RequestBody AuthenticationRequest authenticationRequestrequest) {

        final UserDetails userDetails= userDetailsService.loadUserByUsername(authenticationRequestrequest.getLogin());

        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        authenticationRequestrequest.getLogin(), authenticationRequestrequest.getPassword());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);
//        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
//        context.setAuthentication(authenticationResponse);
//        securityContextHolderStrategy.setContext(context);
//        securityContextRepository.saveContext(context, request, response);
        final String jwt = jwtUtil.generateToken(userDetails);
        final String email=jwtUtil.extractUserName(jwt);
//          final String jwt = "token";
//          final String email="mail";

        if (authenticationResponse.isAuthenticated()) {
            //return new ResponseEntity<>("succeed", HttpStatus.ACCEPTED);
            return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).email(email).build());
        } else {
            //return new ResponseEntity<>("error", HttpStatus.ACCEPTED);
            return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).email(email).build());
        }
    }

    @Override
    public ResponseEntity<Boolean> VerifyToken(String jwtToken) {
        boolean isExpired=jwtUtil.isTokenExpired(jwtToken);
        //log.error("test verification {}",isExpired);
        return ResponseEntity.ok(isExpired);
    }

    //TODO fonctionnalité a revoir
    @Override
    public boolean suivreMosque(int utilisateurId, int mosqueId) {
        //boolean update=true;
            Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).get();
            Mosque mosque = mosqueRepository.findById(mosqueId).get();
            Suivre suivre=new Suivre();
            suivre.setMosq(mosque);
            suivre.setUser(utilisateur);
            utilisateur.getFollowedMosques().add(suivre);
            mosque.getFollowers().add(suivre);
            utilisateurRepository.save(utilisateur);
            mosqueRepository.save(mosque);
        return true;
    }

    //TODO fonctionnalité a revoir
    @Override
    public boolean nePlusSuivreMosque(int utilisateurId, int mosqueId) {
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElseThrow();
        Mosque mosque = mosqueRepository.findById(mosqueId).orElseThrow();

        //utilisateur.removeMosqueSuivie(mosque);
       // mosque.removeFollower(utilisateur);

        utilisateurRepository.save(utilisateur);
        mosqueRepository.save(mosque);
        return false;
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
//        List greetings = (List) request.getSession().getAttribute("GREETING_MESSAGES");
//        if(greetings == null) {
//            greetings = new ArrayList<>();
//            request.getSession().setAttribute("GREETING_MESSAGES", greetings);
//        }
//        greetings.add(greetings);
        //log.error("ip addesse is {}",request.getLocalAddr());
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
    public UtilisateurDto GranteCompteType(String email,char type) {
        // TODO grante autority changement du type de compte
        //UtilisateurDto utilisateur = utilisateurService.findById(id);
        UtilisateurDto utilisateur=utilisateurService.findByEmail(email);
        String Type_Imam="IMAM";
        if(type=='I'){
            utilisateur.setTypecompte(Type_Imam);
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
}