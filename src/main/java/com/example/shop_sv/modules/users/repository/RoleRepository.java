package com.example.shop_sv.modules.users.repository;

import com.example.shop_sv.modules.users.model.entity.Role;
import com.example.shop_sv.modules.users.model.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleName(RoleName name);
}
