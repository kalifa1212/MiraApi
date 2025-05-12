package com.theh.moduleuser.Dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.theh.moduleuser.Model.Privilege;
import com.theh.moduleuser.Model.Role;
import com.theh.moduleuser.Model.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class RoleDto {

    private Long id;
    private String name;
    @JsonIgnore
//    private Collection<UtilisateurDto> users;
    @JsonManagedReference
    private Collection<PrivilegeDto> privileges;
    public static RoleDto fromEntity(Role role) {
        if(role==null) {
            return null;
        }
     //   log.info("Converting Role to DTO: " + role.getId());
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .privileges( role.getPrivileges() != null ?
                        role.getPrivileges().stream().flatMap(s-> Stream.ofNullable(s))
                        .map(PrivilegeDto::fromEntity).collect(Collectors.toList()):null)
                .build();
    }

    public static Role toEntity(RoleDto roleDto) {
        if(roleDto==null) {
            return null;
        }
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        //role.setPrivileges(roleDto.getPrivileges().stream().map(PrivilegeDto::toEntity).collect(Collectors.toList()));
        if (roleDto.getPrivileges() != null) {
            role.setPrivileges(roleDto.getPrivileges().stream()
                    .map(PrivilegeDto::toEntity)
                    .collect(Collectors.toList()));
        }
        return role;
    }
}
