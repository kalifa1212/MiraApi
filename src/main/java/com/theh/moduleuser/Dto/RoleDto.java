package com.theh.moduleuser.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.theh.moduleuser.Model.Privilege;
import com.theh.moduleuser.Model.Role;
import com.theh.moduleuser.Model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Long id;
    private String name;
    @JsonIgnore
    private Collection<UtilisateurDto> users;
    //@JsonIgnore
    private Collection<PrivilegeDto> privileges;
    public static RoleDto fromEntity(Role role) {
        if(role==null) {
            return null;
        }
        return RoleDto.builder()
                .id(role.getId())
                //.privileges(role.getPrivileges().stream().toList())
                .privileges( role.getPrivileges() != null ?
                        role.getPrivileges().stream().flatMap(s-> Stream.ofNullable(s))
                        .map(PrivilegeDto::fromEntity).collect(Collectors.toList()):null)
                //.utilisateur(UtilisateurDto.fromEntity(role.getUtilisateur()))
                .build();
    }

    public static Role toEntity(RoleDto roleDto) {
        if(roleDto==null) {
            return null;
        }
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setPrivileges(roleDto.getPrivileges().stream().map(PrivilegeDto::toEntity).collect(Collectors.toList()));
        return role;
    }
}
