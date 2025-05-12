package com.theh.moduleuser.Repository;

import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Ville;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VilleRepository extends JpaRepository<Ville,Integer> {
    Optional<Ville> findByNameAndPays_Name(String name, String countryName);
}
