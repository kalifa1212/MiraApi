package com.theh.moduleuser.Controller;
import com.theh.moduleuser.Controller.Api.SuivreApi;
import com.theh.moduleuser.Dto.SuivreDto;
import com.theh.moduleuser.Model.Suivre;
import com.theh.moduleuser.Repository.SuivreRepository;
import com.theh.moduleuser.Services.Suivre_Utilisateur_MosqueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class SuivreController implements SuivreApi {

    private SuivreRepository suivreRepository;
    private Suivre_Utilisateur_MosqueService suivre_utilisateur_mosqueService;

    @Autowired
    public SuivreController(
            SuivreRepository suivreRepository,
            Suivre_Utilisateur_MosqueService suivre_utilisateur_mosqueService

    ) {
        this.suivreRepository = suivreRepository;
        this.suivre_utilisateur_mosqueService=suivre_utilisateur_mosqueService;
    }

    @Override
    public ResponseEntity<SuivreDto> save(SuivreDto dto) {
        // TODO enregistrer le followers
        return ResponseEntity.ok(suivre_utilisateur_mosqueService.save(dto));
    }

    @Override
    public SuivreDto findById(Integer id) {
        // TODO rechercher par id
        Optional<Suivre> suivre =suivreRepository.findById(id);
        if(suivre.isEmpty()){
            return null;
        }
        else {
            SuivreDto suivreDto= SuivreDto.fromEntity(suivre.get());
            return suivreDto;
        }
    }

    @Override
    public List<SuivreDto> findByIdUtilisateur(Integer id) {
        // TODO rechercher par id
        return suivreRepository.findByUtilisateur(id).stream().map(SuivreDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<SuivreDto> findByIdMosque(Integer id) {
        // TODO rechercher par id mosque
        return suivreRepository.findSuivreByMosque(id).stream().map(SuivreDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<SuivreDto> findAll() {
        // TODO rechercher tout les mosque
        return suivreRepository.findAll().stream().map(SuivreDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        // TODO supprimer

    }
}
