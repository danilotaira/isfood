package com.isfood.domain.repository;

import com.isfood.domain.entity.GroupAccess;
import com.isfood.domain.entity.UserAccess;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccessRepository extends CustomJpaRepository<UserAccess, Integer>{

    Optional<UserAccess> findByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM UserAccess u WHERE u.email = :email and (:id is null or u.id <> :id)")
    boolean existsByEmailAndIdNot(String email, Integer id);
}

