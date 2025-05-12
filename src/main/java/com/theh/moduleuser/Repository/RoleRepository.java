package com.theh.moduleuser.Repository;

import com.theh.moduleuser.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    Optional<Role> findByNameEquals(String name);

    @Override
    void delete(Role role);
}
