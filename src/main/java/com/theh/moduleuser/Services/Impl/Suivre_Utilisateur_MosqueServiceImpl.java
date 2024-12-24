package com.theh.moduleuser.Services.Impl;

import com.theh.moduleuser.Dto.MosqueDto;
import com.theh.moduleuser.Dto.SuivreDto;
import com.theh.moduleuser.Exceptions.EntityNotFoundException;
import com.theh.moduleuser.Exceptions.ErrorCodes;
import com.theh.moduleuser.Exceptions.InvalidEntityException;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Suivre;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Repository.MosqueRepository;
import com.theh.moduleuser.Repository.SuivreRepository;
import com.theh.moduleuser.Repository.UtilisateurRepository;
import com.theh.moduleuser.Services.Suivre_Utilisateur_MosqueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class Suivre_Utilisateur_MosqueServiceImpl implements Suivre_Utilisateur_MosqueService {

    private SuivreRepository suivreRepository;
    private MosqueRepository mosqueRepository;
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public Suivre_Utilisateur_MosqueServiceImpl(
            SuivreRepository suivreRepository,
            UtilisateurRepository utilisateurRepository,
            MosqueRepository mosqueRepository
    ) {
        this.suivreRepository=suivreRepository;
        this.mosqueRepository=mosqueRepository;
        this.utilisateurRepository=utilisateurRepository;
    }

    @Override
    public SuivreDto save(SuivreDto dto) {
        List<String> errors = validate(dto);
        if(!errors.isEmpty()) {
            //log.error("La mosque est non valide {}",dto);
            throw new InvalidEntityException("Les information entrer ne sont pas valide ", ErrorCodes.SUIVRE_NOT_VALID,errors);
        }
        Optional<Mosque> mosque= Optional.of(new Mosque());
        Optional<Utilisateur> utilisateur= Optional.of(new Utilisateur());
        if(dto.getMosque()!=0){
             mosque=mosqueRepository.findById(dto.getMosque());
            if(mosque.isEmpty()) {
                throw new EntityNotFoundException("Aucune mosque avec l'id "+dto.getMosque()+" n'a ete trouver",ErrorCodes.MOSQUE_NOT_EXIST);
            }
        }

        if(dto.getUtilisateur()!=0){
            utilisateur=utilisateurRepository.findById(dto.getUtilisateur());
            if(utilisateur.isEmpty()) {
                throw new EntityNotFoundException("Aucun utilisateur avec l'id "+dto.getUtilisateur()+" n'a ete trouver",ErrorCodes.MOSQUE_NOT_EXIST);
            }
        }


        return SuivreDto.fromEntity(suivreRepository.save(SuivreDto.toEntity(dto)));
    }

    @Override
    public List<Suivre> findAll() {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    public static List<String> validate(SuivreDto suivreDto){
        List<String> errors = new ArrayList<>();
        if(suivreDto==null ) {
            errors.add("veullez renseigner les données");
        }
        return errors;
    }
}
