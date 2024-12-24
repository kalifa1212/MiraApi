package com.theh.moduleuser.Controller.Api;
import com.theh.moduleuser.Dto.SuivreDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.theh.moduleuser.Constant.Constants.APP_ROOT;

@SecurityRequirement(name = "Bearer Authentication")
@CrossOrigin(origins = "*")
public interface SuivreApi {


    @PostMapping(value=APP_ROOT+"suivre/nouveau/",produces= MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<SuivreDto> save(@RequestBody SuivreDto dto);

    // find by id

    @GetMapping(value=APP_ROOT+"suivre/find/id/{id}")
    SuivreDto findById(@PathVariable("id") Integer id);

    @GetMapping(value=APP_ROOT+"suivre/find/id/utilisateur/{idutilisateur}")
    List<SuivreDto> findByIdUtilisateur(@PathVariable("idutilisateur") Integer id);

    @GetMapping(value=APP_ROOT+"suivre/find/id/mosque/{idmosque}")
    List<SuivreDto> findByIdMosque(@PathVariable("idmosque") Integer id);

    @GetMapping(value=APP_ROOT+"suivre/all",produces=MediaType.APPLICATION_JSON_VALUE)
    List<SuivreDto> findAll();
    // delete

    @DeleteMapping(value=APP_ROOT+"suivre/supprimer/{id}")
    void delete(@PathVariable("id")Integer id);
}
