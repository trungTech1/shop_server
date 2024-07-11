package com.example.shop_sv.modules.users.repository;

import com.example.shop_sv.modules.users.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    @Query("select U from User  U where U.username=:username or U.email=:username or U.phone=:username")
    Optional<User> findByUsernameOrEmailOrPhone(@Param("username") String username);

    boolean existsByEmail(String value);

    boolean existsByUsername(String value);
}
