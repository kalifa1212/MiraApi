package com.theh.moduleuser.Repository;

import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaysRepository extends JpaRepository<Pays,Integer> {
    Optional<Pays> findByName(String name);
}
