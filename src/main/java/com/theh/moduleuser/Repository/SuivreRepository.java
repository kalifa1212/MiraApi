package com.theh.moduleuser.Repository;

import com.theh.moduleuser.Model.Suivre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuivreRepository extends JpaRepository<Suivre,Integer> {

    List<Suivre> findSuivreByMosque(Integer id);
    Suivre findSuivreByMosqueAndUtilisateur(Integer idmosque,Integer idUtilisateur); //si xa contient au moins un element alors l'utilisateur suit la mosque
    Suivre findSuivreByUtilisateurAndIdimamsuivie(Integer idUtilisateur,Integer idImamsuivie); // si cela contient au moins un element alors l'utilisateur suit l'imam
    List<Suivre> findSuivreByMosqueOrUtilisateur(Integer idMosque,Integer idUtilisateur);
    List<Suivre> findByUtilisateur(Integer id);
    List<Suivre> findByIdimamsuivie(Integer id);
}
