package com.theh.moduleuser.Controller.Api;

import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Dto.auth.AuthenticationRequest;
import com.theh.moduleuser.Dto.auth.AuthenticationResponse;
import com.theh.moduleuser.Dto.auth.ChangePassWordDto;
import com.theh.moduleuser.Model.Mosque;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.theh.moduleuser.Constant.Constants.*;

//@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "X-Get-Header",allowCredentials = "true") // juste le cors ici suffi de resoudre le pb
@CrossOrigin(origins = "*")
public interface UtilisateurApi {

    // TODO Non fontionnel
    @Operation(summary = "Authentication ",description = "Connexion")
    @PostMapping(value = AUTHENTICATION_ENDPOINT+"authenticate")
    ResponseEntity<AuthenticationResponse> authentification(@RequestBody AuthenticationRequest authenticationRequest);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Password reset ",description = "password reset")
    @PostMapping(value = UTILISATEUR_ENDPOINT+"account/password/reset")
    ResponseEntity<Boolean> PasswordReset(@RequestBody ChangePassWordDto changePassWordDto);

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "refresh token ",description = "Refresh token")
    @PostMapping(value = UTILISATEUR_ENDPOINT+"authenticate/refresh/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> refreshToken(@RequestParam String refreshToken);

    @Operation(summary = "Authentication ",description = "Veritfy token : False pour valid and true to obselet")
    @PostMapping(value = AUTHENTICATION_ENDPOINT+"token/verify/{jwtToken}")
    ResponseEntity<Boolean> VerifyToken(@PathVariable("jwtToken") String jwtToken);
    //--------
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Enregistrer un utilisateur ",description = "permet d'enregistrer un utilisateur")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",description = "Enregistrer",content = {
                    @Content(mediaType ="application/json",schema = @Schema(implementation = UtilisateurDto.class))
            }),
            @ApiResponse(responseCode = "401",description = "Utilisateur non Autoriser",content = @Content),
            @ApiResponse(responseCode = "400",description = "Utilisateur Invalide",content = @Content)
    })
    @PostMapping(value = UTILISATEUR_ENDPOINT+"nouveau/{update}")
    ResponseEntity<UtilisateurDto> save(@RequestBody UtilisateurDto  dto, @PathVariable("update") Boolean update);

    //@SecurityRequirement(name = "Bearer Authentication")
   // @PreAuthorize("hasAuthority('ROLE_STAFF')")
    @GetMapping()
    String test();

    @Operation(summary = "Recherche ",description = "Recherche par ID")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value=UTILISATEUR_ENDPOINT+"find/id/{idutilisateur}")
    UtilisateurDto findById(@PathVariable("idutilisateur") Integer id);

    @Operation(summary = "Recherche ",description = "Recherche par Email")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value=UTILISATEUR_ENDPOINT+"find/email/{email}")
    UtilisateurDto findByEmail(@PathVariable("email") String email, HttpServletRequest request);
    //  HttpServletRequest request to implement ip addresse of user

    @Operation(summary = "Grant ",description = "Grante role {root,admin,staff,user }")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value=UTILISATEUR_ENDPOINT+"grantrole/{email}/{role}")
    Boolean GranteCompteRole(@PathVariable("email") String email,@PathVariable("role") String role);

    @Operation(summary = "Suivre Mosque ",description = "Suivre une mosque ")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = APP_ROOT+"suivre/{userid}/follow-mosque/{mosqueId}")
    String followMosque(@PathVariable("userid") Integer userid,@PathVariable("mosqueId") Integer mosqueId);

    @Operation(summary = "Suivre Utilisateur ",description = "Suivre une mosque ")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = APP_ROOT+"suivre/{userid}/follow-user/{usertarget}")
    String followUser(@PathVariable("userid") Integer userid,@PathVariable("usertarget") Integer usertarget);


    //TODO impl

    @Operation(summary = "Like Mosque ",description = "Like une mosque ")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = APP_ROOT+"like/{userid}/{mosqueId}")
    ResponseEntity<String>  likeMosque(@PathVariable("userid") Integer userid,@PathVariable("mosqueId") Integer mosqueId);

    @Operation(summary = "Like Predication ",description = "Like une predication ")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = APP_ROOT+"like/predication/{userid}/{predicationId}")
    ResponseEntity<String>  likePredication(@PathVariable("userid") Integer userid,@PathVariable("predicationId") Integer PredicationId);

    @Operation(summary = "Favorite Mosque ",description = "Favorite mosque ")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping(value = APP_ROOT+"favorite/{userid}/{mosqueId}")
    ResponseEntity<String> favoriteMosque(@PathVariable("userid") Integer userid,@PathVariable("mosqueId") Integer mosqueId);

    @Operation(summary = "Liste like mosque ",description = "Like mosque ")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value =APP_ROOT+ "liked-mosques/{userId}")
     ResponseEntity<Set<Mosque>> getLikedMosques(@PathVariable Integer userId);

    @Operation(summary = "Liste favorite mosque ",description = "favorite mosque ")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value =APP_ROOT+ "favorite-mosques/{userId}")
     ResponseEntity<Set<Mosque>> getFavoriteMosques(@PathVariable Integer userId);

    @Operation(summary = "Liste like mosque ",description = "Like mosque ")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = APP_ROOT+"has-liked/{userid}/{mosqueId}")
    ResponseEntity<Boolean> hasLiked(@PathVariable("userid") Integer userId, @PathVariable("mosqueId") Integer mosqueId);

    @Operation(summary = "Liste like mosque ",description = "Like mosque ")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value = APP_ROOT+"has-favorited/{userId}/{mosqueId}")
    ResponseEntity<Boolean> hasFavorited(@PathVariable("userId")  Integer userId, @PathVariable("mosqueId")  Integer mosqueId);

    @Operation(summary = "Grant ",description = "Grant type Compte, U for User and I for Imam")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value=UTILISATEUR_ENDPOINT+"grantcompte/{email}/{type}")
    UtilisateurDto GranteCompteType(@PathVariable("email") String email,@PathVariable("type") char type);
    //find all

    @Operation(summary = "Recherche ",description = "afficher tout les utilisateur")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value=UTILISATEUR_ENDPOINT+"find/all",produces=MediaType.APPLICATION_JSON_VALUE)
    Page<UtilisateurDto> findAll(@RequestParam(required = false,defaultValue = "nom") String sortColumn,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "2") int taille,
                                 @RequestParam(defaultValue = "ascending") String sortDirection);

    @Operation(summary = "Recherche ",description = "Recherche par Type compte (USER, IMAM )")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping(value=UTILISATEUR_ENDPOINT+"find/all/type/{typecompte}",produces=MediaType.APPLICATION_JSON_VALUE)
    Page<UtilisateurDto> findAllByType(@PathVariable("typecompte") String type,
                                       @RequestParam(required = false,defaultValue = "nom") String sortColumn,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "2") int taille,
                                       @RequestParam(defaultValue = "ascending") String sortDirection);

    // delete

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping(value=UTILISATEUR_ENDPOINT+"supprimer/{id}")
    void delete(@PathVariable("id")Integer id);
}
