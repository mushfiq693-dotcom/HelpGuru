package com.helpguru.user.repository;

import com.helpguru.user.domain.RoleEntity;
import com.helpguru.user.domain.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleEnum name);
}
