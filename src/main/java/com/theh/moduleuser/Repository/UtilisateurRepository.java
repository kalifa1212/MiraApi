package com.theh.moduleuser.Repository;

import com.theh.moduleuser.Model.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
    Utilisateur findByEmail(String email);
    @Query(value = "select u from Utilisateur u where u.email=:email")
    Optional<Utilisateur> findUtilisateurByEmail(@Param("email") String email);
    Page<Utilisateur> findUtilisateurByTypecompte(String typecompte,Pageable page);
    List<Utilisateur> findByNom(String nom);
    List<Utilisateur> findUtilisateurByLocalisationId(Integer id);
    List<Utilisateur> findUtilisateurByNomAndTypecompte(String nom, String typcompte);
    @Override
    void delete(Utilisateur user);
    //Page and sorting

    @Override
    Page<Utilisateur> findAll(Pageable pageable);

}
