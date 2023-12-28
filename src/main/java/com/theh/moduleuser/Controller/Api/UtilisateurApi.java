package com.theh.moduleuser.Controller.Api;

import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Dto.auth.AuthenticationRequest;
import com.theh.moduleuser.Dto.auth.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.theh.moduleuser.Constant.Constants.*;

@CrossOrigin(value = "*") // juste le cors ici suffi de resoudre le pb
@RestController
@SecurityRequirement(name = "Bearer Authentication")
public interface UtilisateurApi {

    // TODO Non fontionnel
    @PostMapping(value = AUTHENTICATION_ENDPOINT+"authenticate")
    ResponseEntity<AuthenticationResponse> authentification(@RequestBody AuthenticationRequest authenticationRequest, HttpSession session,HttpServletRequest request, HttpServletResponse response);

    @Operation(summary = "Enregistrer un utilisateur ")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",description = "Enregistrer",content = {
                    @Content(mediaType ="application/json",schema = @Schema(implementation = UtilisateurDto.class))
            }),
            @ApiResponse(responseCode = "401",description = "Utilisateur non Autoriser",content = @Content),
            @ApiResponse(responseCode = "400",description = "Utilisateur Invalide",content = @Content)
    })
    @PostMapping(value = UTILISATEUR_ENDPOINT+"nouveau/{update}")
    ResponseEntity<UtilisateurDto> save(@RequestBody UtilisateurDto  dto, @PathVariable("update") Boolean update);

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @GetMapping()
    String test();

    @GetMapping(value=UTILISATEUR_ENDPOINT+"/find/id/{idutilisateur}")
    UtilisateurDto findById(@PathVariable("idutilisateur") Integer id);

    @GetMapping(value=UTILISATEUR_ENDPOINT+"/find/email/{email}")
    UtilisateurDto findByEmail(@PathVariable("email") String email, HttpServletRequest request);
    //  HttpServletRequest request to implement ip addresse of user

    @GetMapping(value=UTILISATEUR_ENDPOINT+"/grantrole/{email}/{role}")
    Boolean GranteCompteRole(@PathVariable("email") String email,@PathVariable("role") String role);
    //find all
    @GetMapping(value=UTILISATEUR_ENDPOINT+"/grantcompte/{email}/{type}")
    UtilisateurDto GranteCompteType(@PathVariable("email") String email,@PathVariable("type") char type);
    //find all

    @GetMapping(value=UTILISATEUR_ENDPOINT+"find/all",produces=MediaType.APPLICATION_JSON_VALUE)
    List<UtilisateurDto> findAll();

    @GetMapping(value=UTILISATEUR_ENDPOINT+"find/all/type/{typecompte}",produces=MediaType.APPLICATION_JSON_VALUE)
    List<UtilisateurDto> findAllByType(@PathVariable("typecompte") String type);

    // delete

    @DeleteMapping(value=UTILISATEUR_ENDPOINT+"supprimer/{id}")
    void delete(@PathVariable("id")Integer id);
}
