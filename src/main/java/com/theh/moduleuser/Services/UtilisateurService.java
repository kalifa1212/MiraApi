package com.theh.moduleuser.Services;

import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Model.MetaData.PasswordResetToken;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Model.MetaData.VerificationToken;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface    UtilisateurService {
    Utilisateur registerNewUserAccount(UtilisateurDto accountDto);

    Utilisateur getUser(String verificationToken);

    void saveRegisteredUser(Utilisateur user);

    void deleteUser(Utilisateur user);

    void createVerificationTokenForUser(Utilisateur user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String token);

    void createPasswordResetTokenForUser(Utilisateur user, String token);

    Utilisateur findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    Optional<Utilisateur> getUserByPasswordResetToken(String token);

    Optional<Utilisateur> getUserByID(Integer id);

    void changeUserPassword(Utilisateur user, String password);

    boolean checkIfValidOldPassword(Utilisateur user, String password);

    String validateVerificationToken(String token);

    String generateQRUrl(Utilisateur user) throws UnsupportedEncodingException;

    Utilisateur updateUser2FA(boolean use2FA);

    List<String> getUsersFromSessionRegistry();

    //NewLocationToken isNewLoginLocation(String username, String ip);

    String isValidNewLocationToken(String token);

    void addUserLocation(Utilisateur user, String ip);

    //************
    UtilisateurDto save(UtilisateurDto  dto,boolean update);
    UtilisateurDto findById(Integer id);
    UtilisateurDto findByEmail(String email);
    List<UtilisateurDto> findAll();
    Boolean GranteRole(String Email,String role);
    void delete(Integer id);
}
