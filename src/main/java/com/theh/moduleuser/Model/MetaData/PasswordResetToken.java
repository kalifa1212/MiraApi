package com.theh.moduleuser.Model.MetaData;

import com.theh.moduleuser.Model.AbstractEntity;
import com.theh.moduleuser.Model.Utilisateur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Table(name = "passwordresettoken")
@Entity
@Data @EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken extends AbstractEntity {
    private static final int EXPIRATION = 60 * 24;


    private String token;

    @OneToOne(targetEntity = Utilisateur.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Utilisateur user;

    private Date expiryDate;

    public PasswordResetToken(final String token, final Utilisateur user) {
        super();

        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

}
