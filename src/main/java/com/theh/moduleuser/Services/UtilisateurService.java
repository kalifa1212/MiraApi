package com.theh.moduleuser.Services;

import com.theh.moduleuser.Dto.UtilisateurDto;
import com.theh.moduleuser.Dto.auth.ChangePassWordDto;
import com.theh.moduleuser.Model.MetaData.PasswordResetToken;
import com.theh.moduleuser.Model.Mosque;
import com.theh.moduleuser.Model.Utilisateur;
import com.theh.moduleuser.Model.MetaData.VerificationToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    Page<UtilisateurDto> findAll(Pageable page);
    Boolean GranteRole(String Email,String role);
    String followMosque(Integer userId,Integer mosqueId);
    String followUser(Integer userId,Integer targetUserId);
    void delete(Integer id);
    void setRefreshToken(String email,String Token);
    boolean passwordReset(ChangePassWordDto changePassWordDto);

    String toggleLike(Integer userid, Integer mosqueId);

    String toggleFavorite(Integer userid, Integer mosqueId);
    boolean hasLikedMosque(Integer userId, Integer mosqueId);
    boolean hasFavoritedMosque(Integer userId, Integer mosqueId);
    Set<Mosque> getLikedMosques(Integer userId);
    Set<Mosque> getFavoriteMosques(Integer userId);

    String toggleLikePredication(Integer userid, Integer predicationId);
}
