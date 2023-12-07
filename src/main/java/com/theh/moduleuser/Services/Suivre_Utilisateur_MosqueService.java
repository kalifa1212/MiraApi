package com.theh.moduleuser.Services;

import com.theh.moduleuser.Dto.SuivreDto;
import com.theh.moduleuser.Model.Suivre;

import java.util.List;

public interface Suivre_Utilisateur_MosqueService {
    SuivreDto save(SuivreDto  dto);
    List<Suivre> findAll();
    void delete(Integer id);
}
