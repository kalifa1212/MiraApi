package com.theh.moduleuser.Model.MetaData;

import com.theh.moduleuser.Model.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "newlocationtoken")
@Entity
@Data @EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class NewLocationToken extends AbstractEntity {

    private String token;

    @OneToOne(targetEntity = UserLocation.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_location_id")
    private UserLocation userLocation;
}
