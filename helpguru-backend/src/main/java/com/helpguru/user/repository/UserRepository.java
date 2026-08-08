package com.helpguru.user.repository;

import com.helpguru.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameAndIsDeletedFalse(String username);

    Optional<UserEntity> findByEmailAndIsDeletedFalse(String email);

    @Query("SELECT u FROM UserEntity u WHERE (u.username = :usernameOrEmail OR u.email = :usernameOrEmail) AND u.isDeleted = false")
    Optional<UserEntity> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

    Boolean existsByUsernameAndIsDeletedFalse(String username);

    Boolean existsByEmailAndIsDeletedFalse(String email);
}
