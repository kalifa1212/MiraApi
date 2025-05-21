package com.theh.moduleuser.Services.Impl;

import com.theh.moduleuser.Dto.RoleDto;
import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Dto.auth.ChangePassWordDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.*;
import com.theh.moduleuser.Model.MetaData.PasswordResetToken;
import com.theh.moduleuser.Model.MetaData.VerificationToken;
import com.theh.moduleuser.Repository.*;
import com.theh.moduleuser.Repository.Metadata.NewLocationTokenRepository;
import com.theh.moduleuser.Repository.Metadata.PasswordResetTokenRepository;
import com.theh.moduleuser.Repository.Metadata.UserLocationRepository;
import com.theh.moduleuser.Repository.Metadata.VerificationTokenRepository;
import com.theh.moduleuser.Services.UtilisateurService;
import com.theh.moduleuser.Validation.UtilisateurValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private MosqueRepository mosqueRepository;
    @Autowired
    private PredicationRepository predicationRepository;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    private SessionRegistry sessionRegistry;

//    @Autowired
//    @Qualifier("GeoIPCountry")
//    private DatabaseReader databaseReader;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private NewLocationTokenRepository newLocationTokenRepository;

    @Autowired
    private Environment env;

    // Variable Statique
    String Type_User="USER";
    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    public static String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
    public static String APP_NAME = "SpringRegistration";
    @Autowired
    public UtilisateurServiceImpl(
            UtilisateurRepository utilisateurRepository,
            RoleRepository roleRepository,
            VerificationTokenRepository tokenRepository,
            PasswordResetTokenRepository passwordTokenRepository,
            PredicationRepository predicationRepository
    ) {
        this.utilisateurRepository=utilisateurRepository;
        this.roleRepository=roleRepository;
        this.tokenRepository=tokenRepository;
        this.passwordTokenRepository=passwordTokenRepository;
        this.predicationRepository=predicationRepository;
    }

    // definition des methode

    @Override
    public Utilisateur registerNewUserAccount(UtilisateurDto accountDto) {

        // Validation de l'email et de tout les attribut de Utilisateur
        final Utilisateur user = new Utilisateur();

        user.setNom(accountDto.getNom());
        user.setPrenom(accountDto.getPrenom());
        user.setMotDePasse(accountDto.getMotDePasse());
        user.setEmail(accountDto.getEmail());
        user.setUsing2FA(accountDto.isUsing2FA());
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return utilisateurRepository.save(user);
    }

    @Override
    public Utilisateur getUser(String verificationToken) {

        final VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return token.getUser();
        }
        return null;
    }

    @Override
    public void saveRegisteredUser(Utilisateur user) {
        utilisateurRepository.save(user);
    }

    @Override
    public void deleteUser(Utilisateur user) {
        final VerificationToken verificationToken = tokenRepository.findByUser(user);

        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }

        final PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);

        if (passwordToken != null) {
            passwordTokenRepository.delete(passwordToken);
        }

        utilisateurRepository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser(Utilisateur user, String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {

        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID()
                .toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(Utilisateur user, String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public Utilisateur findUserByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {

        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public Optional<Utilisateur> getUserByPasswordResetToken(String token) {

        return Optional.ofNullable(passwordTokenRepository.findByToken(token) .getUser());
    }

    @Override
    public Optional<Utilisateur> getUserByID(Integer id){
        return utilisateurRepository.findById(id);
    }

    @Override
    public void changeUserPassword(Utilisateur user, String password) {
        user.setMotDePasse(password);
        utilisateurRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(Utilisateur user, String password) {

        //return passwordEncoder.matches(oldPassword, user.getPassword());
        if(user.getMotDePasse()==password){
            return true;
        }
        return false;
    }

    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final Utilisateur user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        utilisateurRepository.save(user);
        return TOKEN_VALID;
    }

    @Override
    public String generateQRUrl(Utilisateur user) throws UnsupportedEncodingException {
        return QR_PREFIX + URLEncoder.encode(String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", APP_NAME, user.getEmail(), user.getSecret(), APP_NAME), "UTF-8");

    }

    @Override
    public Utilisateur updateUser2FA(boolean use2FA) {
        return null;
    }

    @Override
    public List<String> getUsersFromSessionRegistry() {
        return null;
    }

    @Override
    public String isValidNewLocationToken(String token) {
        return null;
    }

    @Override
    public void addUserLocation(Utilisateur user, String ip) {

    }
    private boolean emailExists(final String email) {
        return utilisateurRepository.findByEmail(email) != null;
    }

    //******************************************************************************************
    @Override
    public UtilisateurDto save(UtilisateurDto dto,boolean update) {
        // TODO Enregistrer un utilisateur
        Role role=new Role();
        role=roleRepository.findByName("ROLE_USER");
        if(update){
            Optional<Utilisateur> util= utilisateurRepository.findById(dto.getId());
            UtilisateurDto user= UtilisateurDto.fromEntity(util.get());
            dto.setRoles(user.getRoles());
            //log.error("enregistrement {} ",dto);
            return UtilisateurDto.fromEntity(utilisateurRepository.save(UtilisateurDto.toEntity(dto)));
        }
        List<String> errors = UtilisateurValidation.validate(dto);

        if(!errors.isEmpty()) {
            log.error("L'utilisateur est non valide ");
            throw new InvalidEntityException("L'utilisateur n'est pas valide ", ErrorCodes.UTILISATEUR_NOT_VALID,errors);
        }
        Optional<Utilisateur> util = utilisateurRepository.findUtilisateurByEmail(dto.getEmail());
        if(!util.isEmpty()) {
            throw new InvalidEntityException("L'utilisateur Existe deja ", ErrorCodes.UTILISATEUR_ALREADY_EXIST,errors);
        }
        String passwd=dto.getMotDePasse();
        BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        dto.setMotDePasse(passwordEncoder.encode(passwd));
        dto.setTypecompte(TypeCompte.USER);
        dto.setRoles(Arrays.asList(RoleDto.fromEntity(role)));
        dto.getNom().toLowerCase();
        dto.getPrenom().toLowerCase();
        return UtilisateurDto.fromEntity(utilisateurRepository.save(UtilisateurDto.toEntity(dto)));
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        // TODO Auto-generated method stub
        if(id==null) {
            log.warn("l'id de la utilisateur est null");
            return null;
        }
        Optional<Utilisateur> utilisateur= utilisateurRepository.findById(id);

        return Optional.of(UtilisateurDto.fromEntity(utilisateur.get())).orElseThrow(() ->
                new EntityNotFoundException(
                        "Aucune utilisateur avec l'id ="+id+"n'a ete trouver dans la BD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND)
        );
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException("Aucun utilisateur trouver pour : "+email+"dans " +
                        "la base de donnée", ErrorCodes.UTILISATEUR_NOT_FOUND)
                );

    }

    @Override
    public Page<UtilisateurDto> findAll(Pageable page) {
        // TODO Auto-generated method stub
        return utilisateurRepository.findAll(page).map(UtilisateurDto::fromEntity);
    }

    @Override
    public Boolean GranteRole(String Email, String roleName) {
        //TODO passage de role
        roleName.toLowerCase();
        Boolean reussit=false;
        Optional<Utilisateur> util = utilisateurRepository.findUtilisateurByEmail(Email);
        Role roleAdmin =roleRepository.findByName("ROLE_ADMIN");
        Role roleUser=roleRepository.findByName("ROLE_USER");
        Role roleRoot =roleRepository.findByName("ROLE_ROOT");
        Role roleStaf=roleRepository.findByName("ROLE_STAFF");
        if(util.isEmpty()) {
            throw new InvalidEntityException("L'utilisateur n'existe", ErrorCodes.UTILISATEUR_NOT_FOUND);
        }
        UtilisateurDto test= findByEmail(Email);
        Utilisateur user= UtilisateurDto.toEntity(test);
        switch (roleName) {
            case "root":
                user.setRoles(Arrays.asList(RoleDto.toEntity(RoleDto.fromEntity(roleRoot))));
                utilisateurRepository.save(user);
                reussit=true;
                break;
            case "admin":
                user.setRoles(Arrays.asList(RoleDto.toEntity(RoleDto.fromEntity(roleAdmin))));
                utilisateurRepository.save(user);
                reussit=true;
                break;
            case "staff":
                user.setRoles(Arrays.asList(RoleDto.toEntity(RoleDto.fromEntity(roleStaf))));
                utilisateurRepository.save(user);
                reussit=true;
                break;
            case "user":
                user.setRoles(Arrays.asList(RoleDto.toEntity(RoleDto.fromEntity(roleUser))));
                utilisateurRepository.save(user);
                reussit=true;
                break;
            default: reussit=false;

        }
        return reussit;
    }

    @Override
    public String followMosque(Integer userId, Integer mosqueId) {
        Utilisateur utilisateur =utilisateurRepository.findById(userId).orElseThrow(()->
                new EntityNotFoundException("Utilisateur non trouver"));
        Mosque mosque = mosqueRepository.findById(mosqueId).orElseThrow(()->
                new EntityNotFoundException("Mosque non trouver"));

        if (utilisateur.getFollowedMosques().contains(mosque)) {
            utilisateur.getFollowedMosques().remove(mosque);
            utilisateurRepository.save(utilisateur);
            return "desabonné";
        } else {
            utilisateur.getFollowedMosques().add(mosque);
            utilisateurRepository.save(utilisateur);
            return "abonnée";
        }
    }

    @Override
    public String followUser(Integer userId, Integer targetUserId) {
        if(userId==targetUserId){
            return "Impossible de vous abonnée a  vous meme ";
        }
        Utilisateur utilisateur = utilisateurRepository.findById(userId).orElseThrow(()->
                new EntityNotFoundException("Utilisateur non trouver"));
        Utilisateur targetUser= utilisateurRepository.findById(targetUserId).orElseThrow(()->
                new EntityNotFoundException("Utilisateur non trouver"));

        if (utilisateur.getFollowingUsers().contains(targetUser)) {
            utilisateur.getFollowingUsers().remove(targetUser);
            utilisateurRepository.save(utilisateur);
            return "desabonné";
        } else {
            utilisateur.getFollowingUsers().add(targetUser);
            utilisateurRepository.save(utilisateur);
            return "abonnée";
        }
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub
        if(id==null) {
            throw new EntityNotFoundException("L'Id est null");
        }

        utilisateurRepository.deleteById(id);
    }

    @Override
    public void setRefreshToken(String email, String Token) {
       // log.error("error in the service setrefresh");
        utilisateurRepository.findUtilisateurByEmail(email).ifPresentOrElse(utilisateur -> {
            utilisateur.setRefreshToken(Token);
           // log.error("token set loc {}",utilisateur);
            try {
                utilisateurRepository.save(utilisateur);
                log.info("Refresh Token save successfully");
            } catch (Exception e) {
                log.error("Erreur lors du save :", e);  // affiche toute la stack
                Throwable cause = e.getCause();
                while (cause != null) {
                    log.error("Cause: ", cause);
                    cause = cause.getCause();
                    log.error("Cause---: ", cause);
                }
            }
           // log.error("Refresh token mis à jour pour l'utilisateur : {}", utilisateur);
        }, () -> {
            log.warn("Aucun utilisateur trouvé avec l'email : {}", email);
        });
    }

    @Override
    public boolean passwordReset(ChangePassWordDto changePassWordDto) {
        BCryptPasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
        String criptpasswd=passwordEncoder.encode(changePassWordDto.getPassword());
        Optional<Utilisateur> utilisateur=utilisateurRepository.findById(changePassWordDto.getUserId());
        log.error("Verification");
        if (passwordEncoder.matches(changePassWordDto.getPassword(),utilisateur.get().getMotDePasse())){
            utilisateur.get().setMotDePasse(passwordEncoder.encode(utilisateur.get().getMotDePasse()));
            utilisateurRepository.save(utilisateur.get());
            log.info("Modification effectuer");
            return true;
        }
        log.info("echec de la verification");
        return false;
    }

    @Override
    public String toggleLike(Integer userid, Integer mosqueId) {
        log.warn("debu du like");
        Utilisateur user = utilisateurRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Mosque mosque = mosqueRepository.findById(mosqueId)
                .orElseThrow(() -> new RuntimeException("Mosquée non trouvée"));

        if (user.getLikedMosques().contains(mosque)) {
            user.getLikedMosques().remove(mosque);
            utilisateurRepository.save(user);
            return "dislike";
        } else {
            user.getLikedMosques().add(mosque);
            utilisateurRepository.save(user);
            return "liked";
        }
    }

    @Override
    public String toggleFavorite(Integer userid, Integer mosqueId) {
        log.warn("debu du favorite");
        Utilisateur user = utilisateurRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Mosque mosque = mosqueRepository.findById(mosqueId)
                .orElseThrow(() -> new RuntimeException("Mosquée non trouvée"));

        if (user.getFavoriteMosques().contains(mosque)) {
            user.getFavoriteMosques().remove(mosque);
            utilisateurRepository.save(user);
            return "remove to favorite";
        } else {
            user.getFavoriteMosques().add(mosque);
            utilisateurRepository.save(user);
            return "add to fovorite";
        }
    }
    // TODO other methode
    public boolean hasLikedMosque(Integer userId, Integer mosqueId) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return user.getLikedMosques().stream().anyMatch(m -> m.getId().equals(mosqueId));
    }

    public boolean hasFavoritedMosque(Integer userId, Integer mosqueId) {
        Utilisateur user = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return user.getFavoriteMosques().stream().anyMatch(m -> m.getId().equals(mosqueId));
    }

    public Set<Mosque> getLikedMosques(Integer userId) {
        return utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"))
                .getLikedMosques();
    }

    public Set<Mosque> getFavoriteMosques(Integer userId) {
        return utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"))
                .getFavoriteMosques();
    }

    @Override
    public String toggleLikePredication(Integer userid, Integer predicationId) {
        Utilisateur user = utilisateurRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Predication predication = predicationRepository.findById(predicationId)
                .orElseThrow(() -> new RuntimeException("Predication non trouvée"));

        if (user.getLikedPredications().contains(predication)) {
            user.getLikedPredications().remove(predication);
            utilisateurRepository.save(user);
            return "dislike";
        } else {
            user.getLikedPredications().add(predication);
            utilisateurRepository.save(user);
            return "liked";
        }
    }
}
