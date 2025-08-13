package com.furqan.crud_demo_inventory.repository;

import com.furqan.crud_demo_inventory.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);


}
