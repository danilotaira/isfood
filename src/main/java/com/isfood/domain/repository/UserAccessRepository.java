package com.isfood.domain.repository;

import com.isfood.domain.entity.GroupAccess;
import com.isfood.domain.entity.UserAccess;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccessRepository extends CustomJpaRepository<UserAccess, Integer>{

}

