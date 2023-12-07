package com.theh.moduleuser.Repository.Metadata;

import com.theh.moduleuser.Model.MetaData.NewLocationToken;
import com.theh.moduleuser.Model.MetaData.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewLocationTokenRepository extends JpaRepository<NewLocationToken, Long> {
    NewLocationToken findByToken(String token);

    NewLocationToken findByUserLocation(UserLocation userLocation);
}
