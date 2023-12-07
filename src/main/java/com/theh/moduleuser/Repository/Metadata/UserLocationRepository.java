package com.theh.moduleuser.Repository.Metadata;

import com.theh.moduleuser.Model.MetaData.UserLocation;
import com.theh.moduleuser.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationRepository extends JpaRepository<UserLocation, Long> {
    UserLocation findByCountryAndUser(String country, Utilisateur user);
}
