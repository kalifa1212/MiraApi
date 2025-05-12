package com.theh.moduleuser.Dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theh.moduleuser.Model.Privilege;
import com.theh.moduleuser.Model.Role;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class PrivilegeDto {
    private Long id;

    private String name;
//    @JsonBackReference // TODO spring ignore la liste des role dans privilege
//    private Collection<Role> roles;
    public static PrivilegeDto fromEntity(Privilege privilege) {
        if(privilege==null) {
            return null;
        }
            //log.info("Converting Privilege to DTO: " + privilege.getId());
            return PrivilegeDto.builder()
                    .id(privilege.getId())
                    .name(privilege.getName())
                    //.roles(privilege.getRoles().stream().toList())
                    .build();

    }

    public static Privilege toEntity(PrivilegeDto privilegeDto) {
        if(privilegeDto==null) {
            return null;
        }
        Privilege privilege = new Privilege();
        privilege.setId(privilegeDto.getId());
        privilege.setName(privilegeDto.getName());
       // privilege.setRoles(privilegeDto.getRoles()
       //         .stream().toList());
        return privilege;
    }
}
